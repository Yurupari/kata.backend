package com.haiilo.kata.backend.model.dto;

import com.haiilo.kata.backend.model.enums.CartStatus;

import java.math.BigDecimal;
import java.util.List;

public record CartDto(
        Long id,
        CartStatus cartStatus,
        List<CartItemDto> items,
        BigDecimal total,
        String currency
) {
}
