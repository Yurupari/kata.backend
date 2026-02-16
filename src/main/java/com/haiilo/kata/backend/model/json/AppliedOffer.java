package com.haiilo.kata.backend.model.json;

import com.haiilo.kata.backend.model.enums.DiscountType;

import java.math.BigDecimal;

public record AppliedOffer(
    String description,
    BigDecimal discountAmount,
    DiscountType discountType
) {}
