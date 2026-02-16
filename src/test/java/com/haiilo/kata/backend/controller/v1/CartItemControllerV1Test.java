package com.haiilo.kata.backend.controller.v1;

import com.haiilo.kata.backend.BaseUnitTest;
import com.haiilo.kata.backend.model.dto.CartItemDto;
import com.haiilo.kata.backend.model.http.request.CreateCartItemRequest;
import com.haiilo.kata.backend.model.mapper.CartItemMapper;
import com.haiilo.kata.backend.model.mapper.CartItemMapperImpl;
import com.haiilo.kata.backend.service.CartItemService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CartItemControllerV1Test extends BaseUnitTest {

    @InjectMocks
    private CartItemControllerV1 cartItemControllerV1;

    @Mock
    private CartItemService cartItemService;

    @Spy
    private CartItemMapper cartItemMapper = new CartItemMapperImpl();

    @Test
    void addCartItem() throws IOException {
        var createCartItemRequest = jsonTestUtils.loadObject("model/request/v1/new_cart_item_request.json", CreateCartItemRequest.class);
        var newCartItemDto = jsonTestUtils.loadObject("model/dto/v1/cart_item_dto.json", CartItemDto.class);

        when(cartItemService.addCartItem(any())).thenReturn(newCartItemDto);

        var response = cartItemControllerV1.addCartItem(createCartItemRequest);

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void updateCartItem() throws IOException {
        var cartItemDto = jsonTestUtils.loadObject("model/dto/v1/cart_item_dto.json", CartItemDto.class);

        var response = cartItemControllerV1.updateCartItem(cartItemDto);

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(204, response.getStatusCode().value());

        verify(cartItemService, times(1)).updateCartItem(any(CartItemDto.class));
    }
}