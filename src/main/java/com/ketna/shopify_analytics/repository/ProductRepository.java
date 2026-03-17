package com.ketna.shopify_analytics.repository;

import com.ketna.shopify_analytics.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

