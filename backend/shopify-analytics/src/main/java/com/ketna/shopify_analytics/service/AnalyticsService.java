package com.ketna.shopify_analytics.service;

import com.ketna.shopify_analytics.dto.analytics.*;
import com.ketna.shopify_analytics.entity.OrderItem;
import com.ketna.shopify_analytics.repository.OrderItemRepository;
import com.ketna.shopify_analytics.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    @Autowired
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

        List<Object[]> rows = orderItemRepository.getProductRevenueByDate();

        Map<String, Map<LocalDate, Double>> data = new HashMap<>();

        for (Object[] row : rows) {
            String product = (String) row[0];

            LocalDate date;
            if (row[1] instanceof java.sql.Date sqlDate) {
                date = sqlDate.toLocalDate();
            } else {
                date = ((java.util.Date) row[1]).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
            }

            Double revenue = ((Number) row[2]).doubleValue();

            data.computeIfAbsent(product, k -> new TreeMap<>())
                    .put(date, revenue);
        }

        // DEBUG (important for you right now)
        System.out.println("---- MOMENTUM DEBUG ----");
        data.forEach((p, d) -> System.out.println(p + " -> " + d));

        List<MomentumProductDTO> result = new ArrayList<>();

        for (String product : data.keySet()) {

            List<Double> values = new ArrayList<>(data.get(product).values());

            if (values.size() < 2) continue;

            int mid = values.size() / 2;

            double past = values.subList(0, mid)
                    .stream().mapToDouble(Double::doubleValue).sum();

            double recent = values.subList(mid, values.size())
                    .stream().mapToDouble(Double::doubleValue).sum();

            if (past == 0 && recent > 0) {
                result.add(new MomentumProductDTO(product, 999.0, past, recent));
                continue;
            }

            if (past == 0) continue;

            double momentum = recent / past;

            if (momentum >= 2.0) {
                result.add(new MomentumProductDTO(product, momentum, past, recent));
            }
        }

        return result;
    }
}
