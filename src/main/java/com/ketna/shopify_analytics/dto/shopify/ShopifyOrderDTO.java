package com.ketna.shopify_analytics.dto.shopify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyOrderDTO {

    private Long id;

    @JsonProperty("created_at")
    private OffsetDateTime createdAt;

    @JsonProperty("total_price")
    private BigDecimal totalPrice;

    private String currency;

    private ShopifyCustomerDTO customer;

    @JsonProperty("line_items")
    private List<ShopifyLineItemDTO> lineItems;
}

