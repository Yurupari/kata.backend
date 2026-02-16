package com.haiilo.kata.backend.model.http.request;

import com.haiilo.kata.backend.model.enums.DiscountType;

import java.math.BigDecimal;
import java.util.List;

public record CreateOfferRequest(
        String name,
        BigDecimal discount,
        String currency,
        DiscountType discountType,
        String from,
        String until,
        List<ProductSelectionRequest> products
) {
}
