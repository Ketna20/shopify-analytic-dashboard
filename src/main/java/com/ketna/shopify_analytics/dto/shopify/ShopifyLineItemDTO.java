package com.ketna.shopify_analytics.dto.shopify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyLineItemDTO {

    @JsonProperty("product_id")
    private Long productId;

    private String name;

    private Integer quantity;

    private BigDecimal price;
}

