package com.ketna.shopify_analytics.controller;

import com.ketna.shopify_analytics.dto.analytics.*;
import com.ketna.shopify_analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    private LocalDate[] resolveDateRange(LocalDate from, LocalDate to) {
        LocalDate resolvedTo = to != null ? to : LocalDate.now();
        LocalDate resolvedFrom = from != null ? from : resolvedTo.minusDays(29);
        return new LocalDate[]{resolvedFrom, resolvedTo};
    }

    @GetMapping("/top-products")
    public List<TopProductDTO> getTopProducts(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        LocalDate[] range = resolveDateRange(from, to);
        return analyticsService.getTopProducts(range[0], range[1]);
    }

    @GetMapping("/revenue")
    public List<RevenueDTO> getRevenue(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        LocalDate[] range = resolveDateRange(from, to);
        return analyticsService.getRevenueOverTime(range[0], range[1]);
    }

    @GetMapping("/kpis")
    public DashboardKPIDTO getKPIs(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        LocalDate[] range = resolveDateRange(from, to);
        return analyticsService.getDashboardKPIs(range[0], range[1]);
    }

    @GetMapping("/revenue-spikes")
    public List<RevenueSpikeDTO> getRevenueSpikes(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        LocalDate[] range = resolveDateRange(from, to);
        return analyticsService.getRevenueSpikes(range[0], range[1]);
    }

    @GetMapping("/momentum-products")
    public List<MomentumProductDTO> getMomentumProducts(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        LocalDate[] range = resolveDateRange(from, to);
        return analyticsService.getMomentumProducts(range[0], range[1]);
    }

    @GetMapping("/orders")
    public Page<OrderDTO> getOrders(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        LocalDate[] range = resolveDateRange(from, to);
        return analyticsService.getOrders(range[0], range[1], page, size);
    }
}
