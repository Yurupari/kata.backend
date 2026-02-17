package com.haiilo.kata.backend.model.dto;

import com.haiilo.kata.backend.model.enums.Status;

import java.math.BigDecimal;
import java.util.List;

public record ProductDto(
        Long id,
        String name,
        String description,
        BigDecimal unitPrice,
        String currency,
        Status status,
        List<ProductOfferDto> offers
) {
}
