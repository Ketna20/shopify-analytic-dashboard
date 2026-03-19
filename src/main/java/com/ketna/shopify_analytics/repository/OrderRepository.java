package com.ketna.shopify_analytics.repository;

import com.ketna.shopify_analytics.entity.Order;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByShopifyOrderId(Long shopifyOrderId);
}

