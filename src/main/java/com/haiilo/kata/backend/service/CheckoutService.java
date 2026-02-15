package com.haiilo.kata.backend.service;

import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.model.http.CheckoutRequest;

public interface CheckoutService {
    ReceiptDto executeCheckout(CheckoutRequest checkoutRequest);
}
