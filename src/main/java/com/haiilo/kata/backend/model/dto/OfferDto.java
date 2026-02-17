package com.haiilo.kata.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haiilo.kata.backend.model.enums.DiscountType;
import com.haiilo.kata.backend.model.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OfferDto(
        Long id,
        String name,
        BigDecimal discount,
        String currency,
        DiscountType discountType,
        @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss") LocalDateTime fromDate,
        @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss") LocalDateTime untilDate,
        Status status,
        List<ProductOfferDto> products
) {
}
