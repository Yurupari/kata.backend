package com.haiilo.kata.backend.model.dto;

import com.haiilo.kata.backend.model.enums.Status;

import java.util.List;

public record ProductDto(
        Long id,
        String name,
        String description,
        Double unitPrice,
        String currency,
        Status status,
        List<ProductOfferDto> offers
) {
}
