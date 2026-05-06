package com.ketna.shopify_analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShopifyAnalyticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopifyAnalyticsApplication.class, args);
	}

}
