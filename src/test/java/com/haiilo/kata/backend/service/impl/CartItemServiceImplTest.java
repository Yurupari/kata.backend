package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.exception.CartItemNotFoundException;
import com.haiilo.kata.backend.model.dto.CartItemDto;
import com.haiilo.kata.backend.model.entity.CartItem;
import com.haiilo.kata.backend.repository.CartItemRepository;
import com.haiilo.kata.backend.utils.JsonTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CartItemServiceImplTest {

    @Autowired
    private CartItemServiceImpl cartItemService;

    @MockitoBean
    private CartItemRepository cartItemRepository;

    @Autowired
    private JsonTestUtils jsonTestUtils;

    @Test
    void getCartItems_Success() throws IOException {
        var cartItems = jsonTestUtils.loadListObjects("model/domain/v1/cart_items.json", CartItem.class);

        when(cartItemRepository.findByCartId(any())).thenReturn(cartItems);

        var response = cartItemService.getCartItems(1L);

        assertNotNull(response);
    }

    @Test
    void getCartItem_Success() throws IOException {
        var cartItem = jsonTestUtils.loadObject("model/domain/v1/cart_item.json", CartItem.class);

        when(cartItemRepository.findById(any())).thenReturn(Optional.of(cartItem));

        var response = cartItemService.getCartItem(1L);

        assertNotNull(response);
    }

    @Test
    void getCartItem_NotFound() {
        when(cartItemRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(CartItemNotFoundException.class, () -> cartItemService.getCartItem(1L));
    }

    @Test
    void addCartItem_Success() throws IOException {
        var cartItemDto = jsonTestUtils.loadObject("model/request/v1/new_cart_item_request.json", CartItemDto.class);
        var cartItem = jsonTestUtils.loadObject("model/domain/v1/cart_item.json", CartItem.class);

        when(cartItemRepository.save(any())).thenReturn(cartItem);

        var response = cartItemService.addCartItem(cartItemDto);

        assertNotNull(response);
    }

    @Test
    void updateCartItem_Success() throws IOException {
        var cartItemDto = jsonTestUtils.loadObject("model/dto/v1/cart_item_dto.json", CartItemDto.class);
        var cartItem = jsonTestUtils.loadObject("model/domain/v1/cart_item.json", CartItem.class);

        when(cartItemRepository.findById(any())).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.save(any())).thenReturn(new CartItem());

        assertDoesNotThrow(() -> cartItemService.updateCartItem(cartItemDto));

        verify(cartItemRepository, times(1)).findById(any(Long.class));
        verify(cartItemRepository, times(1)).save(any(CartItem.class));
    }
}