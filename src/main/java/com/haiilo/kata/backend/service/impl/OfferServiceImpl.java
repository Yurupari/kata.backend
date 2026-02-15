package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.model.dto.CartDto;
import com.haiilo.kata.backend.service.CartService;
import org.springframework.stereotype.Service;

@Service
public class OfferServiceImpl implements CartService {
    @Override
    public CartDto getCurrentCart() {
        return null;
    }

    @Override
    public CartDto getProduct(Long id) {
        return null;
    }

    @Override
    public CartDto addProduct(CartDto cartDto) {
        return null;
    }

    @Override
    public void updateProduct(CartDto cartDto) {

    }
}
