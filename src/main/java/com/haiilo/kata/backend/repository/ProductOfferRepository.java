package com.haiilo.kata.backend.repository;

import com.haiilo.kata.backend.model.entity.ProductOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOfferRepository extends JpaRepository<ProductOffer, Long> {
}
