package com.haiilo.kata.backend.model.http.request;

public record CreateProductSelectionRequest(
        Long productId,
        Long offerId,
        Integer quantity
) {
}
