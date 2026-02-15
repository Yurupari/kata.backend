package com.haiilo.kata.backend.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TransactionDetail(
    @JsonProperty("product_id") Long productId,
    String name,
    Integer quantity,
    @JsonProperty("unit_price") Double unitPrice,
    @JsonProperty("applied_offer") AppliedOffer appliedOffer,
    Double subtotal
) {}
