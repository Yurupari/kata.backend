package com.haiilo.kata.backend.service;

import com.haiilo.kata.backend.model.dto.CartDto;
import com.haiilo.kata.backend.model.entity.Cart;

public interface CartService {
    CartDto getCurrentCart();
    CartDto getCart(Long id);
    Cart getCartById(Long id);
    void updateCart(CartDto cartDto);
}
