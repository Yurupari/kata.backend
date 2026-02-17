package com.haiilo.kata.backend.service;

import com.haiilo.kata.backend.model.dto.CartItemDto;

import java.util.List;

public interface CartItemService {
    List<CartItemDto> getCartItems(Long cartId);
    CartItemDto getCartItem(Long id);
    CartItemDto addCartItem(CartItemDto cartDto);
    void updateCartItem(CartItemDto cartDto);
}
