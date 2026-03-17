package com.ketna.shopify_analytics.service;

import com.ketna.shopify_analytics.entity.Store;
import com.ketna.shopify_analytics.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderSyncService {

    private static final Logger log = LoggerFactory.getLogger(OrderSyncService.class);

    private final StoreRepository storeRepository;
    private final ShopifyOrderService shopifyOrderService;

    public void syncOrders(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Store not found with id: " + storeId));

        String ordersJson = shopifyOrderService.fetchOrders(store);

        // For now, just print/log the raw JSON response
        log.info("Fetched orders for store {}: {}", store.getShopDomain(), ordersJson);
    }
}

