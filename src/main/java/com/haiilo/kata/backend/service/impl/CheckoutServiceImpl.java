package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.model.http.CheckoutRequest;
import com.haiilo.kata.backend.service.CheckoutService;
import com.haiilo.kata.backend.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final ReceiptService receiptService;

    @Override
    public ReceiptDto executeCheckout(CheckoutRequest checkoutRequest) {
        return null;
    }
}
