package com.haiilo.kata.backend.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AppliedOffer(
    String description,
    @JsonProperty("discount_amount") Double discountAmount
) {}
