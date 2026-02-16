package com.haiilo.kata.backend.model.dto;

import java.math.BigDecimal;
import java.util.List;

public record PriceDto(
        Long productId,
        List<ProductOfferDto> offers,
        BigDecimal unitPrice,
        BigDecimal subTotal,
        BigDecimal total,
        BigDecimal discount
) {
}
