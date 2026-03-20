package com.ketna.shopify_analytics.repository;

import com.ketna.shopify_analytics.dto.analytics.TopProductDTO;
import com.ketna.shopify_analytics.entity.Order;
import com.ketna.shopify_analytics.entity.OrderItem;
import com.ketna.shopify_analytics.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Optional<OrderItem> findByOrderAndProduct(Order order, Product product);

    @Query("""
        SELECT new com.ketna.shopify_analytics.dto.analytics.TopProductDTO(
            p.title,
            SUM(oi.quantity * oi.price)
        )
        FROM OrderItem oi
        JOIN oi.product p
        GROUP BY p.title
        ORDER BY SUM(oi.quantity * oi.price) DESC
    """)
    List<TopProductDTO> findTopProducts();
}

