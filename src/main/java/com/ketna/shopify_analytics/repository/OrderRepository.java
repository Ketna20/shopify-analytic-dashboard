package com.ketna.shopify_analytics.repository;

import com.ketna.shopify_analytics.dto.analytics.DashboardKPIDTO;
import com.ketna.shopify_analytics.dto.analytics.RevenueDTO;
import com.ketna.shopify_analytics.entity.Order;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByShopifyOrderId(Long shopifyOrderId);

    @Query(value = """
    SELECT DATE(order_date) as date, SUM(total_price) as revenue
    FROM orders
    GROUP BY DATE(order_date)
    ORDER BY DATE(order_date)
""", nativeQuery = true)
    List<Object[]> getRevenueRaw();

    @Query("""
    SELECT new com.ketna.shopify_analytics.dto.analytics.DashboardKPIDTO(
        SUM(o.totalPrice),
        COUNT(o),
        AVG(o.totalPrice)
    )
    FROM Order o
""")
    DashboardKPIDTO getDashboardKPIs();
}

