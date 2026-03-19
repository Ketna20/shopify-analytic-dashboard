package com.ketna.shopify_analytics.repository;

import com.ketna.shopify_analytics.entity.Customer;
import com.ketna.shopify_analytics.entity.Store;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByShopifyCustomerId(Long id);
}

