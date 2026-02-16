package com.haiilo.kata.backend.model.dto;

import java.math.BigDecimal;

public record PriceDto(
        BigDecimal unitPrice,
        BigDecimal subTotal,
        BigDecimal total,
        BigDecimal discount
) {
}
