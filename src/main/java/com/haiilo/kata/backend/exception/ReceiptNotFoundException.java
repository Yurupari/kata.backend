package com.haiilo.kata.backend.exception;

public class ReceiptNotFoundException extends RuntimeException {
    public ReceiptNotFoundException(Long id) {
        super(String.format("Receipt not found: id=%s", id));
    }
}
