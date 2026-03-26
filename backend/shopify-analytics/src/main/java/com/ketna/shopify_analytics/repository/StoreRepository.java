package com.ketna.shopify_analytics.repository;

import com.ketna.shopify_analytics.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}

