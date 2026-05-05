package com.ketna.shopify_analytics.dto.analytics;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDTO {
    private Long shopifyOrderId;
    private LocalDateTime orderDate;
    private String customerName;
    private String customerEmail;
    private BigDecimal totalPrice;
    private String currency;

    public OrderDTO(Long shopifyOrderId, LocalDateTime orderDate,
                    String firstName, String lastName, String email,
                    BigDecimal totalPrice, String currency) {
        this.shopifyOrderId = shopifyOrderId;
        this.orderDate = orderDate;
        this.customerName = ((firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "")).trim();
        this.customerEmail = email;
        this.totalPrice = totalPrice;
        this.currency = currency;
    }

    public Long getShopifyOrderId() { return shopifyOrderId; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public String getCustomerName() { return customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public String getCurrency() { return currency; }
}
