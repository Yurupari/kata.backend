package com.haiilo.kata.backend.exception;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(Long id) {
        super(String.format("Cart not found: id=%s", id));
    }
}
