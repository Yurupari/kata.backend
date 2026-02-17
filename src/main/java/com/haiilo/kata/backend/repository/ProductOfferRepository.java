package com.haiilo.kata.backend.repository;

import com.haiilo.kata.backend.model.entity.ProductOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductOfferRepository extends JpaRepository<ProductOffer, Long> {
    List<ProductOffer> findByIdIn(List<Long> ids);

    @Query("""
       SELECT po FROM ProductOffer po
       WHERE (:productId IS NULL OR po.product.id = :productId)
       AND (:offerId IS NULL OR po.offer.id = :offerId)
       """)
    List<ProductOffer> findByProductIdOrOfferId(
            @Param("productId") Long productId,
            @Param("offerId") Long offerId
    );
}
