package com.haiilo.kata.backend.exception;

public class ReceiptNotFoundException extends RuntimeException {
    public ReceiptNotFoundException(Long id, Long cartId) {
        super(String.format("Receipt not found: id=%s, cartId=%s", id, cartId));
    }
}
