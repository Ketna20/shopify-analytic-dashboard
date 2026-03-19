package com.ketna.shopify_analytics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ShopifyAuthService {

    private final RestTemplate restTemplate;

    @Value("${shopify.client-id}")
    private String clientId;

    @Value("${shopify.client-secret}")
    private String clientSecret;

    public String generateAccessToken(String shopDomain) {
        return generateAccessToken(shopDomain, clientId, clientSecret);
    }

    public String generateAccessToken(String shopDomain, String clientId, String clientSecret) {
        String url = "https://" + shopDomain + "/admin/oauth/access_token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "client_credentials");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(form, headers);

        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
        @SuppressWarnings("rawtypes")
        Map body = response.getBody();
        Object token = body == null ? null : body.get("access_token");

        if (token == null) {
            throw new IllegalStateException("Shopify access_token missing in response");
        }

        return token.toString();
    }
}

