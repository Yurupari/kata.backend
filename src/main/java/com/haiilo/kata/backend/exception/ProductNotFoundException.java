package com.haiilo.kata.backend.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super(String.format("Product not found: id=%s", id));
    }
}
