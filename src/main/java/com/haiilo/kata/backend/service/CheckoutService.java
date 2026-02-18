package com.haiilo.kata.backend.service;

import com.haiilo.kata.backend.model.dto.PriceDto;
import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.model.http.request.CheckoutRequest;

public interface CheckoutService {
    PriceDto precalculatePrice(Long cartId);
    ReceiptDto executeCheckout(CheckoutRequest checkoutRequest);
}
