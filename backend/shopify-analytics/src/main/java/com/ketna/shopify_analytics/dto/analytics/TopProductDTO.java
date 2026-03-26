package com.ketna.shopify_analytics.dto.analytics;

import java.math.BigDecimal;

public class TopProductDTO {
    private String productName;
    private BigDecimal revenue;

    public TopProductDTO(String productName, BigDecimal revenue) {
        this.productName = productName;
        this.revenue = revenue;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }
}
