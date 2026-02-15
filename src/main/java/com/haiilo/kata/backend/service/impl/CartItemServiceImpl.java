package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.model.dto.CartItemDto;
import com.haiilo.kata.backend.repository.CartItemRepository;
import com.haiilo.kata.backend.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    @Override
    public List<CartItemDto> getCartItems(Long cartId) {
        return List.of();
    }

    @Override
    public CartItemDto getCartItem(Long id) {
        return null;
    }

    @Override
    public CartItemDto addCartItem(CartItemDto cartDto) {
        return null;
    }

    @Override
    public void updateCartItem(CartItemDto cartDto) {

    }
}
