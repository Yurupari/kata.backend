package com.haiilo.kata.backend.model.json;

import java.math.BigDecimal;

public record TransactionDetail(
    Long productId,
    String name,
    Integer quantity,
    BigDecimal unitPrice,
    AppliedOffer appliedOffer,
    BigDecimal subtotal
) {}
