package com.haiilo.kata.backend.service;

import com.haiilo.kata.backend.model.dto.CartDto;

import java.util.List;

public interface CartItemService {
    List<CartDto> getCartItems();
    CartDto getCartItem(Long id);
    CartDto addCartItem(CartDto cartDto);
    void updateCartItem(CartDto cartDto);
    void deleteCartItem(Long id);
}
