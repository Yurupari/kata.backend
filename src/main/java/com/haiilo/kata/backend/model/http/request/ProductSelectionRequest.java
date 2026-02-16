package com.haiilo.kata.backend.model.http.request;

public record ProductSelectionRequest(
        Long productId,
        Long offerId,
        Integer quantity
) {
}
