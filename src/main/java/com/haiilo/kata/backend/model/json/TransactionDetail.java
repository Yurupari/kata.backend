package com.haiilo.kata.backend.model.json;

public record TransactionDetail(
    Long productId,
    String name,
    Integer quantity,
    Double unitPrice,
    AppliedOffer appliedOffer,
    Double subtotal
) {}
