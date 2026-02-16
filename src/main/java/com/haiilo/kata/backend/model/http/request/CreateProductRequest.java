package com.haiilo.kata.backend.model.http.request;

import java.math.BigDecimal;

public record CreateProductRequest(
        String name,
        BigDecimal unitPrice,
        String currency
) {
}
