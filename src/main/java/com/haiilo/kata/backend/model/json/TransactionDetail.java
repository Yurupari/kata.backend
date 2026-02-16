package com.haiilo.kata.backend.model.json;

import java.math.BigDecimal;
import java.util.List;

public record TransactionDetail(
    Long productId,
    String productName,
    Integer quantity,
    BigDecimal unitPrice,
    List<AppliedOffer> appliedOffers,
    BigDecimal subtotal
) {}
