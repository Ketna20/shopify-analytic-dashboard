package com.ketna.shopify_analytics.repository;

import com.ketna.shopify_analytics.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

