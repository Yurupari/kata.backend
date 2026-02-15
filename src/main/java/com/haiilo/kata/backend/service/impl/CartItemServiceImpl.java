package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.model.dto.CartDto;
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
    public List<CartDto> getCartItems() {
        return List.of();
    }

    @Override
    public CartDto getCartItem(Long id) {
        return null;
    }

    @Override
    public CartDto addCartItem(CartDto cartDto) {
        return null;
    }

    @Override
    public void updateCartItem(CartDto cartDto) {

    }

    @Override
    public void deleteCartItem(Long id) {

    }
}
