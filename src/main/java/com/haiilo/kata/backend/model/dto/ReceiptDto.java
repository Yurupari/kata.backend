package com.haiilo.kata.backend.model.dto;

import com.haiilo.kata.backend.model.json.TransactionDetail;

import java.util.List;

public record ReceiptDto(
        Long id,
        Long cartId,
        Double subTotal,
        Double discount,
        Double total,
        String currency,
        List<TransactionDetail> transactionDetails
) {
}
