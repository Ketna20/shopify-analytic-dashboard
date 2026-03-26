package com.ketna.shopify_analytics.controller;

import com.ketna.shopify_analytics.service.OrderSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderSyncController {

    private final OrderSyncService orderSyncService;

    @PostMapping("/sync/{storeId}")
    public ResponseEntity<Void> syncOrders(@PathVariable Long storeId) {
        orderSyncService.syncOrders(storeId);
        return ResponseEntity.accepted().build();
    }
}

