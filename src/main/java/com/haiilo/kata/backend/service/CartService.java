package com.haiilo.kata.backend.service;

import com.haiilo.kata.backend.model.dto.CartDto;

public interface CartService {
    CartDto getCurrentCart();
    CartDto getProduct(Long id);
    CartDto addProduct(CartDto cartDto);
    void updateProduct(CartDto cartDto);
}
