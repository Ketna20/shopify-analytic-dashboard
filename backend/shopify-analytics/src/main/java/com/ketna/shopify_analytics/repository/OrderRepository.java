package com.ketna.shopify_analytics.repository;

import com.ketna.shopify_analytics.dto.analytics.DashboardKPIDTO;
import com.ketna.shopify_analytics.dto.analytics.OrderDTO;
import com.ketna.shopify_analytics.dto.analytics.RevenueDTO;
import com.ketna.shopify_analytics.entity.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByShopifyOrderId(Long shopifyOrderId);

    @Query(value = """
    SELECT DATE(order_date) as date, SUM(total_price) as revenue
    FROM orders
    WHERE DATE(order_date) BETWEEN :from AND :to
    GROUP BY DATE(order_date)
    ORDER BY DATE(order_date)
""", nativeQuery = true)
    List<Object[]> getRevenueRaw(@Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query("""
    SELECT new com.ketna.shopify_analytics.dto.analytics.DashboardKPIDTO(
        SUM(o.totalPrice),
        COUNT(o),
        AVG(o.totalPrice)
    )
    FROM Order o
    WHERE o.orderDate >= :from AND o.orderDate <= :to
""")
    DashboardKPIDTO getDashboardKPIs(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    @Query("""
    SELECT new com.ketna.shopify_analytics.dto.analytics.OrderDTO(
        o.shopifyOrderId,
        o.orderDate,
        c.firstName,
        c.lastName,
        c.email,
        o.totalPrice,
        o.currency
    )
    FROM Order o
    LEFT JOIN o.customer c
    WHERE o.orderDate >= :from AND o.orderDate <= :to
    ORDER BY o.orderDate DESC
""")
    Page<OrderDTO> findOrdersByDateRange(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to, Pageable pageable);
}

