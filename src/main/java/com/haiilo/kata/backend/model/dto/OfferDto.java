package com.haiilo.kata.backend.model.dto;

import com.haiilo.kata.backend.model.enums.DiscountType;

import java.util.List;

public record OfferDto(
        Long id,
        String name,
        Double discount,
        String currency,
        DiscountType discountType,
        String from,
        String until,
        List<ProductOfferDto> products
) {
}
