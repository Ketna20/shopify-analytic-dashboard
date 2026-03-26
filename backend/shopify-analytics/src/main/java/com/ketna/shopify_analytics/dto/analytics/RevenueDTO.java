package com.ketna.shopify_analytics.dto.analytics;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RevenueDTO {
    private LocalDate date;
    private BigDecimal revenue;

    public RevenueDTO(LocalDate date, BigDecimal revenue) {
        this.date = date;
        this.revenue = revenue;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }
}
