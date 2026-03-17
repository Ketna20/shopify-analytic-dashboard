package com.ketna.shopify_analytics.service;

import com.ketna.shopify_analytics.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ShopifyOrderService {

    private final RestTemplate restTemplate;
    private final ShopifyAuthService shopifyAuthService;

    public String fetchOrders(Store store) {
        String url = "https://" + store.getShopDomain() + "/admin/api/2024-01/orders.json";

        String accessToken = shopifyAuthService.generateAccessToken(store.getShopDomain());
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Shopify-Access-Token", accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        return response.getBody();
    }
}

