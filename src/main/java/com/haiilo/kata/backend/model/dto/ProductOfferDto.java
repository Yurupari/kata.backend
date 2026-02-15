package com.haiilo.kata.backend.model.dto;

import com.haiilo.kata.backend.model.enums.Status;

public record ProductOfferDto(
        Long id,
        Long productId,
        Long offerId,
        Integer quantity,
        Status status
) {
}
