package com.ketna.shopify_analytics.scheduler;

import com.ketna.shopify_analytics.service.OrderSyncService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderSyncScheduler {

    private static final Logger log = LoggerFactory.getLogger(OrderSyncScheduler.class);

    private final OrderSyncService orderSyncService;

    // Runs every night at 2:00 AM Pacific Time
    // To change: update sync.cron in application.properties
    @Scheduled(cron = "${sync.cron:0 0 2 * * *}", zone = "America/Los_Angeles")
    public void scheduledOrderSync() {
        log.info("Scheduled order sync starting...");
        try {
            orderSyncService.syncOrders(2L);
            log.info("Scheduled order sync completed successfully.");
        } catch (Exception e) {
            log.error("Scheduled order sync failed: {}", e.getMessage(), e);
        }
    }
}
