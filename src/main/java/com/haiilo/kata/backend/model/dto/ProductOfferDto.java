package com.haiilo.kata.backend.model.dto;

public record ProductOfferDto(
        Long id,
        Long productId,
        Long offerId,
        Integer quantity
) {
}
