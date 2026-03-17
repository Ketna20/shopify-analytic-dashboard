package com.ketna.shopify_analytics.repository;

import com.ketna.shopify_analytics.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

