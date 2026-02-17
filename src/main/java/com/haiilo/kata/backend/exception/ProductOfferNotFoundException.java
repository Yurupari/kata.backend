package com.haiilo.kata.backend.exception;

public class ProductOfferNotFoundException extends RuntimeException {
    public ProductOfferNotFoundException(Long id) {
        super(String.format("Product offer not found: id=%s", id));
    }
}
