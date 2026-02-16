package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.exception.CartItemNotFoundException;
import com.haiilo.kata.backend.model.dto.CartItemDto;
import com.haiilo.kata.backend.model.mapper.CartItemMapper;
import com.haiilo.kata.backend.repository.CartItemRepository;
import com.haiilo.kata.backend.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    private final CartItemMapper cartItemMapper;

    @Override
    public List<CartItemDto> getCartItems(Long cartId) {
        return cartItemRepository.findByCartId(cartId).stream()
                .map(cartItemMapper::toDto)
                .toList();
    }

    @Override
    public CartItemDto getCartItem(Long id) {
        return cartItemRepository.findById(id)
                .map(cartItemMapper::toDto)
                .orElseThrow(() -> new CartItemNotFoundException(id));
    }

    @Override
    public CartItemDto addCartItem(CartItemDto cartDto) {
        var cartItem = cartItemRepository.save(cartItemMapper.toEntity(cartDto));

        return cartItemMapper.toDto(cartItem);
    }

    @Override
    public void updateCartItem(CartItemDto cartDto) {
        var existingCartItem = cartItemRepository.findById(cartDto.id())
                .orElseThrow(() -> new CartItemNotFoundException(cartDto.id()));

        cartItemMapper.updateEntityFromDto(cartDto, existingCartItem);

        cartItemRepository.save(existingCartItem);
    }
}
