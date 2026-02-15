package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.model.dto.CartDto;
import com.haiilo.kata.backend.repository.CartRepository;
import com.haiilo.kata.backend.service.CartItemService;
import com.haiilo.kata.backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartItemService cartItemService;

    @Override
    public CartDto getCurrentCart() {
        return null;
    }

    @Override
    public CartDto getCart(Long id) {
        return null;
    }

    @Override
    public CartDto addCart(CartDto cartDto) {
        return null;
    }

    @Override
    public void updateCart(CartDto cartDto) {

    }
}
