package com.haiilo.kata.backend.service;

import com.haiilo.kata.backend.model.dto.CartDto;

public interface CartService {
    CartDto getCurrentCart();
    CartDto getCart(Long id);
    void updateCart(CartDto cartDto);
}
