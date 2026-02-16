package com.haiilo.kata.backend.model.http.request;

import com.haiilo.kata.backend.model.enums.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CreateOfferRequest(
        String name,
        BigDecimal discount,
        String currency,
        DiscountType discountType,
        LocalDateTime from,
        LocalDateTime until,
        List<ProductSelectionRequest> products
) {
}
