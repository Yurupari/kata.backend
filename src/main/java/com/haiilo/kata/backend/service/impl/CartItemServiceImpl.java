package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.exception.CartItemNotFoundException;
import com.haiilo.kata.backend.exception.ValidationException;
import com.haiilo.kata.backend.model.dto.CartItemDto;
import com.haiilo.kata.backend.model.enums.Status;
import com.haiilo.kata.backend.model.mapper.CartItemMapper;
import com.haiilo.kata.backend.repository.CartItemRepository;
import com.haiilo.kata.backend.service.CartItemService;
import com.haiilo.kata.backend.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    private final CartItemMapper cartItemMapper;

    private final CartService cartService;

    @Override
    public List<CartItemDto> getCartItems(Long cartId) {
        Optional.ofNullable(cartId).orElseThrow(() -> new ValidationException("Cart ID cannot be null"));

        return cartItemRepository.findByCartId(cartId).stream()
                .map(cartItemMapper::toDto)
                .toList();
    }

    @Override
    public CartItemDto getCartItem(Long id) {
        Optional.ofNullable(id).orElseThrow(() -> new ValidationException("ID cannot be null"));

        return cartItemRepository.findById(id)
                .map(cartItemMapper::toDto)
                .orElseThrow(() -> new CartItemNotFoundException(id));
    }

    @Override
    public CartItemDto addCartItem(CartItemDto cartItemDto) {
        log.info("Create cart item: {}", cartItemDto.toString());

        validateCartItem(cartItemDto, false);

        return cartItemRepository.findByCartIdAndProductId(cartItemDto.cartId(), cartItemDto.productId()).stream()
                .filter(cartItem -> Status.ACTIVE.equals(cartItem.getStatus()))
                .findAny()
                .map(cartItem -> {
                    cartItem.setQuantity(cartItem.getQuantity() + cartItemDto.quantity());
                    var updatedCartItem =cartItemRepository.save(cartItem);

                    return cartItemMapper.toDto(updatedCartItem);
                })
                .orElseGet(() -> {
                    var cartItem = cartItemRepository.save(cartItemMapper.toEntity(cartItemDto));

                    return cartItemMapper.toDto(cartItem);
                });
    }

    @Override
    public void updateCartItem(CartItemDto cartItemDto) {
        log.info("Update cart item: {}", cartItemDto.toString());

        validateCartItem(cartItemDto, true);

        var existingCartItem = cartItemRepository.findById(cartItemDto.id())
                .orElseThrow(() -> new CartItemNotFoundException(cartItemDto.id()));

        cartItemMapper.updateEntityFromDto(cartItemDto, existingCartItem);

        cartItemRepository.save(existingCartItem);
    }

    private void validateCartItem(CartItemDto cartItemDto, boolean isUpdate) {
        if (cartItemDto.quantity() < 1) {
            throw new ValidationException(String.format("Quantity cannot be less than 1: quantity=%s", cartItemDto.quantity()));
        }

        if (isUpdate) {
            Optional.ofNullable(cartItemDto.id())
                    .orElseThrow(() -> new ValidationException("ID cannot be null"));
        }

        Optional.ofNullable(cartItemDto.cartId())
                .orElseThrow(() -> new ValidationException("Cart ID cannot be null"));

        Optional.ofNullable(cartItemDto.productId())
                .orElseThrow(() -> new ValidationException("Product ID cannot be null"));
    }
}
