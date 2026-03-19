package com.ketna.shopify_analytics.dto.shopify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyOrderResponse {

    private List<ShopifyOrderDTO> orders;
}

