package com.ketna.shopify_analytics.dto.analytics;

import java.math.BigDecimal;

public class MomentumProductDTO {
    private String productName;
    private BigDecimal recentRevenue;
    private BigDecimal previousRevenue;
    private Double growthRatio;

    public MomentumProductDTO(String productName, BigDecimal recentRevenue, BigDecimal previousRevenue, Double growthRatio) {
        this.productName = productName;
        this.recentRevenue = recentRevenue;
        this.previousRevenue = previousRevenue;
        this.growthRatio = growthRatio;
    }

    public String getProductName() { return productName; }
    public BigDecimal getRecentRevenue() { return recentRevenue; }
    public BigDecimal getPreviousRevenue() { return previousRevenue; }
    public Double getGrowthRatio() { return growthRatio; }
}
