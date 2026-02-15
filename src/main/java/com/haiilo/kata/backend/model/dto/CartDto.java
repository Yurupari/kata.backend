package com.haiilo.kata.backend.model.dto;

import com.haiilo.kata.backend.model.enums.CartStatus;

import java.util.List;

public record CartDto(
        Long id,
        CartStatus status,
        List<CartItemDto> items,
        Double total
) {
}
