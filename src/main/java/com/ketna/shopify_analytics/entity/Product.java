package com.ketna.shopify_analytics.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shopify_product_id", unique = true)
    private Long shopifyProductId;

    @Column(name = "title", columnDefinition = "text")
    private String title;

    @Column(name = "vendor", length = 255)
    private String vendor;

    @Column(name = "product_type", length = 255)
    private String productType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}

