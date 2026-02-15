package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.model.dto.CartDto;
import com.haiilo.kata.backend.repository.OfferRepository;
import com.haiilo.kata.backend.service.CartService;
import com.haiilo.kata.backend.service.ProductOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements CartService {

    private final OfferRepository offerRepository;

    private final ProductOfferService productOfferService;

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
