package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.model.http.CheckoutRequest;
import com.haiilo.kata.backend.service.CheckoutService;
import org.springframework.stereotype.Service;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    @Override
    public ReceiptDto executeCheckout(CheckoutRequest checkoutRequest) {
        return null;
    }
}
