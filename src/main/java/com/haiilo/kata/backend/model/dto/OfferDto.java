package com.haiilo.kata.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.haiilo.kata.backend.model.enums.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OfferDto(
        Long id,
        String name,
        BigDecimal discount,
        String currency,
        DiscountType discountType,
        LocalDateTime from,
        LocalDateTime until,
        @JsonIgnoreProperties("offerDto") List<ProductOfferDto> products
) {
}
