package com.ketna.shopify_analytics.dto.analytics;

import java.math.BigDecimal;

public class MomentumProductDTO {
    private String productName;
    private Double momentum;
    private Double pastRevenue;
    private Double recentRevenue;

    public MomentumProductDTO(String productName, Double momentum, Double pastRevenue, Double recentRevenue) {
        this.productName = productName;
        this.momentum = momentum;
        this.pastRevenue = pastRevenue;
        this.recentRevenue = recentRevenue;
    }

    public String getProductName() { return productName; }
    public Double getMomentum() { return momentum; }
    public Double getPastRevenue() { return pastRevenue; }
    public Double getRecentRevenue() { return recentRevenue; }
}
