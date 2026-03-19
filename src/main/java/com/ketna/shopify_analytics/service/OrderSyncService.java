package com.ketna.shopify_analytics.service;

import com.ketna.shopify_analytics.dto.shopify.ShopifyCustomerDTO;
import com.ketna.shopify_analytics.dto.shopify.ShopifyOrderDTO;
import com.ketna.shopify_analytics.dto.shopify.ShopifyOrderResponse;
import com.ketna.shopify_analytics.entity.Customer;
import com.ketna.shopify_analytics.entity.Order;
import com.ketna.shopify_analytics.entity.Store;
import com.ketna.shopify_analytics.repository.StoreRepository;
import com.ketna.shopify_analytics.repository.OrderRepository;
import com.ketna.shopify_analytics.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderSyncService {

    private static final Logger log = LoggerFactory.getLogger(OrderSyncService.class);

    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ShopifyOrderService shopifyOrderService;

    public void syncOrders(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Store not found with id: " + storeId));

        String ordersJson = shopifyOrderService.fetchOrders(store);

        // For now, just print/log the raw JSON response
        log.info("Fetched orders for store {}: {}", store.getShopDomain(), ordersJson);

        ObjectMapper objectMapper = new ObjectMapper();

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

            Optional<Order> existingOrder = orderRepository.findByShopifyOrderId(orderDTO.getId());

            if (existingOrder.isPresent()) {
                log.info("Order already exists, skipping: {}", orderDTO.getId());
                continue;
            }

            Order order = Order.builder()
                    .shopifyOrderId(orderDTO.getId())
                    .orderDate(orderDTO.getCreatedAt().toLocalDateTime())
                    .totalPrice(orderDTO.getTotalPrice())
                    .currency(orderDTO.getCurrency())
                    .customer(customer)
                    .build();

            orderRepository.save(order);
        }

    }
}
