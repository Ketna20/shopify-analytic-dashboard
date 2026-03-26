package com.ketna.shopify_analytics.controller;

import com.ketna.shopify_analytics.dto.ConnectStoreRequest;
import com.ketna.shopify_analytics.entity.Store;
import com.ketna.shopify_analytics.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/connect")
    public ResponseEntity<Store> connectStore(@RequestBody ConnectStoreRequest request) {
        Store saved = storeService.connectStore(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}

