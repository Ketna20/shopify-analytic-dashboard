package com.ketna.shopify_analytics.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ketna.shopify_analytics.dto.shopify.ShopifyCustomerDTO;
import com.ketna.shopify_analytics.dto.shopify.ShopifyLineItemDTO;
import com.ketna.shopify_analytics.dto.shopify.ShopifyOrderDTO;
import com.ketna.shopify_analytics.dto.shopify.ShopifyOrderResponse;
import com.ketna.shopify_analytics.entity.*;
import com.ketna.shopify_analytics.repository.*;
import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderSyncService {

    private static final Logger log = LoggerFactory.getLogger(OrderSyncService.class);

    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShopifyOrderService shopifyOrderService;
    @Autowired
    private ObjectMapper objectMapper;
    public void syncOrders(Long storeId)  {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Store not found with id: " + storeId));

        String ordersJson = shopifyOrderService.fetchOrders(store);

        // For now, just print/log the raw JSON response
        log.info("Fetched orders for store {}: {}", store.getShopDomain(), ordersJson);

        try {
            ShopifyOrderResponse orderResponse = objectMapper.readValue(ordersJson, ShopifyOrderResponse.class);


            for (ShopifyOrderDTO orderDTO : orderResponse.getOrders()) {
                System.out.println("Order ID: " + orderDTO.getId());

                ShopifyCustomerDTO customerDTO = orderDTO.getCustomer();

                Customer customer = null;

                if (customerDTO != null) {
                    customer = customerRepository
                            .findByShopifyCustomerId(customerDTO.getId())
                            .orElseGet(() -> {
                                Customer newCustomer = Customer.builder()
                                        .shopifyCustomerId(customerDTO.getId())
                                        .email(customerDTO.getEmail())
                                        .firstName(customerDTO.getFirstName())
                                        .lastName(customerDTO.getLastName())
                                        .build();

                                return customerRepository.save(newCustomer);
                            });
                }


                Optional<Order> existingOrder =
                        orderRepository.findByShopifyOrderId(orderDTO.getId());

                Order order;

                if (existingOrder.isPresent()) {
                    order = existingOrder.get();
                } else {
                    order = Order.builder()
                            .shopifyOrderId(orderDTO.getId())
                            .orderDate(orderDTO.getCreatedAt().toLocalDateTime())
                            .totalPrice(orderDTO.getTotalPrice())
                            .currency(orderDTO.getCurrency())
                            .customer(customer)
                            .build();

                    order = orderRepository.save(order);
                }

                // Now handle line items
                for (ShopifyLineItemDTO itemDTO : orderDTO.getLineItems()) {
                    if (itemDTO.getProductId() == null) continue;
                    Product product = productRepository
                            .findByShopifyProductId(itemDTO.getProductId())
                            .orElseGet(() -> productRepository.save(
                                    Product.builder()
                                            .shopifyProductId(itemDTO.getProductId())
                                            .title(itemDTO.getName())
                                            .build()
                            ));

                    OrderItem orderItem = OrderItem.builder()
                            .order(order)
                            .product(product)
                            .quantity(itemDTO.getQuantity())
                            .price(BigDecimal.valueOf(Double.valueOf(String.valueOf(itemDTO.getPrice()))))
                            .build();

                    Optional<OrderItem> existingItem =
                            orderItemRepository.findByOrderAndProduct(order, product);

                    if (existingItem.isPresent()) {
                        continue;
                    }

                    orderItemRepository.save(orderItem);
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse Shopify orders JSON", e);
        }
    }
}
