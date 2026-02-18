package com.haiilo.kata.backend.model.dto;

import com.haiilo.kata.backend.model.enums.Status;

import java.math.BigDecimal;

public record CartItemDto(
        Long id,
        Long cartId,
        Long productId,
        String productName,
        Status productStatus,
        BigDecimal unitPrice,
        String currency,
        Integer quantity,
        Status status
) {
}
