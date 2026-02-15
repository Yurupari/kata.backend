package com.haiilo.kata.backend.controller.v1;

import com.haiilo.kata.backend.model.dto.CartItemDto;
import com.haiilo.kata.backend.service.CartItemService;
import com.haiilo.kata.backend.utils.JsonTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CartItemControllerV1Test {

    @Autowired
    private CartItemControllerV1 cartItemControllerV1;

    @MockitoBean
    private CartItemService cartItemService;

    @Autowired
    private JsonTestUtils jsonTestUtils;

    @Test
    void addCartItem() throws IOException {
        var cartItemDto = jsonTestUtils.loadObject("model/request/v1/new_cart_item_request.json", CartItemDto.class);
        var newCartItemDto = jsonTestUtils.loadObject("model/dto/v1/cart_item_dto.json", CartItemDto.class);

        when(cartItemService.addCartItem(any())).thenReturn(newCartItemDto);

        var response = cartItemControllerV1.addCartItem(cartItemDto);

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