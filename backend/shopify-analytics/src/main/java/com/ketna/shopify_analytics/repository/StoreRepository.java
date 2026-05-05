package com.ketna.shopify_analytics.repository;

import com.ketna.shopify_analytics.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByShopDomain(String shopDomain);
}

