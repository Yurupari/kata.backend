package com.haiilo.kata.backend.model.http.request;

public record CreateCartItemRequest(
        Long cartId,
        Long productId,
        String productName,
        int quantity
) {
}
