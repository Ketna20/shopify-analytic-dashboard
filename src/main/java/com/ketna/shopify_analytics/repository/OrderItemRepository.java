package com.ketna.shopify_analytics.repository;

import com.ketna.shopify_analytics.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}

