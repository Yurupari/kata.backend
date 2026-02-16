package com.haiilo.kata.backend.model.dto;

import com.haiilo.kata.backend.model.json.TransactionDetail;

import java.math.BigDecimal;
import java.util.List;

public record ReceiptDto(
        Long id,
        Long cartId,
        BigDecimal subTotal,
        BigDecimal discount,
        BigDecimal total,
        String currency,
        List<TransactionDetail> transactionDetails
) {
}
