package com.haiilo.kata.backend.model.dto;

public record CartItemDto(
        Long id,
        Long cartId,
        Long productId,
        String productName,
        Integer quantity
) {
}
