package com.haiilo.kata.backend.model.dto;

import java.util.List;

public record OfferDto(
        Long id,
        String name,
        Double discount,
        String currency,
        String discountType,
        String from,
        String until,
        List<ProductOfferDto> products
) {
}
