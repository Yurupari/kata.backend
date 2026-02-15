package com.haiilo.kata.backend.exception;

public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException(Long id) {
        super(String.format("Cart item not found: id=%s", id));
    }
}
