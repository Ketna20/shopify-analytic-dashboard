package com.ketna.shopify_analytics.repository;

import com.ketna.shopify_analytics.entity.Order;
import com.ketna.shopify_analytics.entity.OrderItem;
import com.ketna.shopify_analytics.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Optional<OrderItem> findByOrderAndProduct(Order order, Product product);
}

