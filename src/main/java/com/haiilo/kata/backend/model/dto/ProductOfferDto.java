package com.haiilo.kata.backend.model.dto;

import com.haiilo.kata.backend.model.enums.DiscountType;
import com.haiilo.kata.backend.model.enums.Status;

public record ProductOfferDto(
        Long id,
        Long productId,
        Long offerId,
        Double discount,
        String currency,
        DiscountType discountType,
        String from,
        String until,
        Integer quantity,
        Status status
) {
}
