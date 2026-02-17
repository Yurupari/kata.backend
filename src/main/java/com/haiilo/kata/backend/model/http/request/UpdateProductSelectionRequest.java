package com.haiilo.kata.backend.model.http.request;

import com.haiilo.kata.backend.model.enums.Status;

public record UpdateProductSelectionRequest(
        Long id,
        Long productId,
        Long offerId,
        Integer quantity,
        Status status
) {
}
