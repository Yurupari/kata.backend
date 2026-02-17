package com.haiilo.kata.backend.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public record TransactionDetail(
    @JsonProperty("productId") Long productId,
    @JsonProperty("productName") String productName,
    @JsonProperty("quantity") Integer quantity,
    @JsonProperty("unitPrice") BigDecimal unitPrice,
    @JsonProperty("appliedOffers") List<AppliedOffer> appliedOffers,
    @JsonProperty("subtotal") BigDecimal subtotal
) {}
