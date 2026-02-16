package com.haiilo.kata.backend.model.json;

import java.math.BigDecimal;

public record AppliedOffer(
    String description,
    BigDecimal discountAmount
) {}
