package com.ketna.shopify_analytics.dto.analytics;

import java.math.BigDecimal;

public class DashboardKPIDTO {
    private BigDecimal totalRevenue;
    private Long totalOrders;
    private Double avgOrderValue;

    public DashboardKPIDTO(BigDecimal totalRevenue, Long totalOrders, Double avgOrderValue) {
        this.totalRevenue = totalRevenue;
        this.totalOrders = totalOrders;
        this.avgOrderValue = avgOrderValue;
    }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public Long getTotalOrders() { return totalOrders; }
    public Double getAvgOrderValue() { return avgOrderValue; }
}
