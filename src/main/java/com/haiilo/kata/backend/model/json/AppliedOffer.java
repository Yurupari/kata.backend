package com.haiilo.kata.backend.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.haiilo.kata.backend.model.enums.DiscountType;

import java.math.BigDecimal;

public record AppliedOffer(
    @JsonProperty("description") String description,
    @JsonProperty("discountAmount") BigDecimal discountAmount,
    @JsonProperty("discountType") DiscountType discountType
) {}
