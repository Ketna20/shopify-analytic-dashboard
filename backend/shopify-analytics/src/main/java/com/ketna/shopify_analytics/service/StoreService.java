package com.ketna.shopify_analytics.service;

import com.ketna.shopify_analytics.dto.ConnectStoreRequest;
import com.ketna.shopify_analytics.entity.Store;
import com.ketna.shopify_analytics.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public Store connectStore(ConnectStoreRequest request) {
        return saveStore(request.getShopDomain());
    }

    public Store saveStore(String shopDomain) {

        Store store = Store.builder()
                .shopDomain(shopDomain)
                .createdAt(LocalDateTime.now())
                .build();

        return storeRepository.save(store);
    }
}

