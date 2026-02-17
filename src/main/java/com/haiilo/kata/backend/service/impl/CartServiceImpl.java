package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.exception.CartNotFoundException;
import com.haiilo.kata.backend.model.dto.CartDto;
import com.haiilo.kata.backend.model.entity.Cart;
import com.haiilo.kata.backend.model.enums.CartStatus;
import com.haiilo.kata.backend.model.mapper.CartMapper;
import com.haiilo.kata.backend.repository.CartRepository;
import com.haiilo.kata.backend.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartMapper cartMapper;

    @Override
    public CartDto getCurrentCart() {
        var cart = cartRepository.findByCartStatusIn(List.of(CartStatus.OPEN, CartStatus.PENDING)).stream()
                .findFirst()
                .orElseGet(() -> {
                    var newCart = Cart.builder().build();

                    return cartRepository.save(newCart);
                });

        return cartMapper.toDto(cart);
    }

    @Override
    public CartDto getCart(Long id) {
        return cartRepository.findById(id)
                .map(cartMapper::toDto)
                .orElseThrow(() -> new CartNotFoundException(id));
    }

    @Override
    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException(id));
    }

    @Override
    public void updateCart(CartDto cartDto) {
        log.info("Update cart: {}", cartDto.toString());
        var existingCart = cartRepository.findById(cartDto.id())
                .orElseThrow(() -> new CartNotFoundException(cartDto.id()));

        cartMapper.updateEntityFromDto(cartDto, existingCart);

        cartRepository.save(existingCart);
    }
}
