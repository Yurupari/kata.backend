package com.haiilo.kata.backend.exception;

public class OfferNotFoundException extends RuntimeException {
    public OfferNotFoundException(Long id) {
        super(String.format("Offer not found: id=%s", id));
    }
}
