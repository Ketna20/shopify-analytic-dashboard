package com.ketna.shopify_analytics.dto.analytics;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RevenueSpikeDTO {
    private LocalDate date;
    private BigDecimal revenue;
    private BigDecimal previousRevenue;
    private Double spikeRatio;
    private Double increasePercentage;

    public RevenueSpikeDTO(LocalDate date, BigDecimal revenue, BigDecimal previousRevenue, Double spikeRatio) {
        this.date = date;
        this.revenue = revenue;
        this.previousRevenue = previousRevenue;
        this.spikeRatio = spikeRatio;
        this.increasePercentage = (spikeRatio - 1) * 100;
    }

    public LocalDate getDate() { return date; }
    public BigDecimal getRevenue() { return revenue; }
    public BigDecimal getPreviousRevenue() { return previousRevenue; }
    public Double getSpikeRatio() { return spikeRatio; }
    public Double getIncreasePercentage() { return increasePercentage; }
}
