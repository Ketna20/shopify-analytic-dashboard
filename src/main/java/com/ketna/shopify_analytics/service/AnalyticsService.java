package com.ketna.shopify_analytics.service;

import com.ketna.shopify_analytics.dto.analytics.*;
import com.ketna.shopify_analytics.entity.OrderItem;
import com.ketna.shopify_analytics.repository.OrderItemRepository;
import com.ketna.shopify_analytics.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    public List<TopProductDTO> getTopProducts() {
        return orderItemRepository.findTopProducts();
    }

    public List<RevenueDTO> getRevenueOverTime() {
        List<Object[]> results = orderRepository.getRevenueRaw();

        return results.stream()
                .map(r -> new RevenueDTO(
                        ((Date) r[0]).toLocalDate(),
                        (BigDecimal) r[1]
                ))
                .toList();
    }

    public DashboardKPIDTO getDashboardKPIs() {
        return orderRepository.getDashboardKPIs();
    }

    public List<RevenueSpikeDTO> getRevenueSpikes() {

        List<RevenueDTO> revenueData = getRevenueOverTime();

        List<RevenueSpikeDTO> spikes = new ArrayList<>();

        for (int i = 1; i < revenueData.size(); i++) {

            RevenueDTO today = revenueData.get(i);
            RevenueDTO yesterday = revenueData.get(i - 1);

            BigDecimal todayRevenue = today.getRevenue();
            BigDecimal yesterdayRevenue = yesterday.getRevenue();

            if (yesterdayRevenue.compareTo(BigDecimal.ZERO) == 0) continue;

            double ratio = todayRevenue.doubleValue() / yesterdayRevenue.doubleValue();

            if (ratio >= 1.5) {
                spikes.add(new RevenueSpikeDTO(
                        today.getDate(),
                        todayRevenue,
                        yesterdayRevenue,
                        ratio
                ));
            }
        }

        return spikes;
    }

    public List<MomentumProductDTO> getMomentumProducts() {

        List<OrderItem> items = orderItemRepository.findAll();

        Map<String, Map<LocalDate, BigDecimal>> productDailyRevenue = new HashMap<>();

        for (OrderItem item : items) {
            String productName = item.getProduct().getTitle();
            LocalDate date = item.getOrder().getOrderDate().toLocalDate();

            BigDecimal revenue = item.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            productDailyRevenue
                    .computeIfAbsent(productName, k -> new HashMap<>())
                    .merge(date, revenue, BigDecimal::add);
        }

        List<MomentumProductDTO> result = new ArrayList<>();

        for (String product : productDailyRevenue.keySet()) {

            Map<LocalDate, BigDecimal> revenueMap = productDailyRevenue.get(product);

            List<LocalDate> dates = revenueMap.keySet().stream().sorted().toList();

            if (dates.size() < 4) continue;

            int size = dates.size();

            BigDecimal recent = BigDecimal.ZERO;
            BigDecimal previous = BigDecimal.ZERO;

            // Last 2 days
            for (int i = size - 2; i < size; i++) {
                recent = recent.add(revenueMap.get(dates.get(i)));
            }

            // Previous 2 days
            for (int i = size - 4; i < size - 2; i++) {
                previous = previous.add(revenueMap.get(dates.get(i)));
            }

            if (previous.compareTo(BigDecimal.ZERO) == 0) continue;

            double ratio = recent.doubleValue() / previous.doubleValue();

            if (ratio >= 1.5) {
                result.add(new MomentumProductDTO(product, recent, previous, ratio));
            }
        }

        return result.stream()
                .sorted((a, b) -> Double.compare(b.getGrowthRatio(), a.getGrowthRatio()))
                .toList();
    }
}
