package com.haiilo.kata.backend.model.http.request;

import java.math.BigDecimal;

public record CreateProductRequest(
        String name,
        String description,
        BigDecimal unitPrice,
        String currency
) {
}
