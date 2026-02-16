package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.BaseUnitTest;
import com.haiilo.kata.backend.exception.CartNotFoundException;
import com.haiilo.kata.backend.model.dto.CartDto;
import com.haiilo.kata.backend.model.entity.Cart;
import com.haiilo.kata.backend.model.enums.CartStatus;
import com.haiilo.kata.backend.model.mapper.CartItemMapper;
import com.haiilo.kata.backend.model.mapper.CartMapperImpl;
import com.haiilo.kata.backend.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CartServiceImplTest extends BaseUnitTest {

    private CartServiceImpl cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemMapper cartItemMapper;

    @Spy
    @InjectMocks
    private CartMapperImpl cartMapper;

    @BeforeEach
    void setup() {
        super.setUpBase();

        this.cartService = new CartServiceImpl(cartRepository, cartMapper);
    }

    @Test
    void getCurrentCart_Success() throws IOException {
        var cart = jsonTestUtils.loadObject("model/domain/v1/cart.json", Cart.class);

        when(cartRepository.findByCartStatusIn(List.of(CartStatus.OPEN, CartStatus.PENDING)))
                .thenReturn(List.of(cart));

        var response = cartService.getCurrentCart();

        assertNotNull(response);
    }

    @Test
    void getCurrentCart_NotFound_Success() throws IOException {
        var cart = jsonTestUtils.loadObject("model/domain/v1/cart.json", Cart.class);

        when(cartRepository.findByCartStatusIn(List.of(CartStatus.OPEN, CartStatus.PENDING)))
                .thenReturn(new ArrayList<>());
        when(cartRepository.save(any())).thenReturn(cart);

        var response = cartService.getCurrentCart();

        assertNotNull(response);
    }

    @Test
    void getCart_Success() throws IOException {
        var cart = jsonTestUtils.loadObject("model/domain/v1/cart.json", Cart.class);

        when(cartRepository.findById(any())).thenReturn(Optional.of(cart));

        var response = cartService.getCart(1L);

        assertNotNull(response);
    }

    @Test
    void getCart_NotFound() {
        when(cartRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(CartNotFoundException.class, () -> cartService.getCart(1L));
    }

    @Test
    void updateCart_Success() throws IOException {
        var carDto = jsonTestUtils.loadObject("model/dto/v1/cart_dto.json", CartDto.class);
        var cart = jsonTestUtils.loadObject("model/domain/v1/cart.json", Cart.class);

        when(cartRepository.findById(any())).thenReturn(Optional.of(cart));
        when(cartRepository.save(any())).thenReturn(new Cart());

        assertDoesNotThrow(() -> cartService.updateCart(carDto));

        verify(cartRepository, times(1)).findById(any(Long.class));
        verify(cartRepository, times(1)).save(any(Cart.class));
    }
}