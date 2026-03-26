package com.ketna.shopify_analytics.repository;

import com.ketna.shopify_analytics.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByShopifyProductId(Long shopifyProductId);
}

