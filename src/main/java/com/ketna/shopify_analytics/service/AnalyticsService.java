package com.ketna.shopify_analytics.service;

import com.ketna.shopify_analytics.dto.analytics.TopProductDTO;
import com.ketna.shopify_analytics.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final OrderItemRepository orderItemRepository;

    public List<TopProductDTO> getTopProducts() {
        return orderItemRepository.findTopProducts();
    }
}
