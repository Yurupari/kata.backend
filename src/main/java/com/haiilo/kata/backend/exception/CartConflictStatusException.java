package com.haiilo.kata.backend.exception;

import com.haiilo.kata.backend.model.enums.CartStatus;

public class CartConflictStatusException extends RuntimeException {
    public CartConflictStatusException(CartStatus cartStatus) {
        super(String.format("With the current status of the cart is not possible to continue: %s", cartStatus));
    }
}
