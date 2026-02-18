package com.haiilo.kata.backend.model.http.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haiilo.kata.backend.model.enums.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CreateOfferRequest(
        String name,
        BigDecimal discount,
        String currency,
        DiscountType discountType,
        @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss") LocalDateTime fromDate,
        @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss") LocalDateTime untilDate,
        List<CreateProductSelectionRequest> products
) {
}
