package com.ketna.shopify_analytics.controller;

import com.ketna.shopify_analytics.dto.analytics.*;
import com.ketna.shopify_analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/top-products")
    public List<TopProductDTO> getTopProducts() {
        return analyticsService.getTopProducts();
    }

    @GetMapping("/revenue")
    public List<RevenueDTO> getRevenue() {
        return analyticsService.getRevenueOverTime();
    }

    @GetMapping("/kpis")
    public DashboardKPIDTO getKPIs() {
        return analyticsService.getDashboardKPIs();
    }

    @GetMapping("/revenue-spikes")
    public List<RevenueSpikeDTO> getRevenueSpikes() {
        return analyticsService.getRevenueSpikes();
    }

    @GetMapping("/momentum-products")
    public List<MomentumProductDTO> getMomentumProducts() {
        return analyticsService.getMomentumProducts();
    }
}
