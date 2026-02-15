package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.exception.CartNotFoundException;
import com.haiilo.kata.backend.model.dto.CartDto;
import com.haiilo.kata.backend.model.entity.Cart;
import com.haiilo.kata.backend.model.enums.CartStatus;
import com.haiilo.kata.backend.repository.CartRepository;
import com.haiilo.kata.backend.service.CartItemService;
import com.haiilo.kata.backend.service.ReceiptService;
import com.haiilo.kata.backend.utils.JsonTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

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

@SpringBootTest
class CartServiceImplTest {

    @Autowired
    private CartServiceImpl cartService;

    @MockitoBean
    private CartRepository cartRepository;

    @MockitoBean
    private CartItemService cartItemService;

    @MockitoBean
    private ReceiptService receiptService;

    @Autowired
    private JsonTestUtils jsonTestUtils;

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
    void addCart_Success() throws IOException {
        var cartDto = jsonTestUtils.loadObject("model/request/v1/new_cart_request.json", CartDto.class);
        var cart = jsonTestUtils.loadObject("model/domain/v1/cart.json", Cart.class);

        when(cartRepository.save(any())).thenReturn(cart);

        var response = cartService.addCart(cartDto);

        assertNotNull(response);
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