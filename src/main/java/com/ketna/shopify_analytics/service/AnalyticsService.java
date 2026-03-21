package com.ketna.shopify_analytics.service;

import com.ketna.shopify_analytics.dto.analytics.RevenueDTO;
import com.ketna.shopify_analytics.dto.analytics.TopProductDTO;
import com.ketna.shopify_analytics.repository.OrderItemRepository;
import com.ketna.shopify_analytics.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
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
}
