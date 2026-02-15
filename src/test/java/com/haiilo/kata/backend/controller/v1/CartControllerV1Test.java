package com.haiilo.kata.backend.controller.v1;

import com.haiilo.kata.backend.model.dto.CartDto;
import com.haiilo.kata.backend.service.CartService;
import com.haiilo.kata.backend.utils.UtilsTest;
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
class CartControllerV1Test {

    @Autowired
    private CartControllerV1 cartControllerV1;

    @MockitoBean
    private CartService cartService;

    @Test
    void getCurrentCart_Success() throws IOException {
        var cartDto = UtilsTest.loadObject("model/dto/v1/cart_dto.json", CartDto.class);
        when(cartService.getCurrentCart()).thenReturn(cartDto);

        var response = cartControllerV1.getCurrentCart();

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void getCart_Success() throws IOException {
        var cartDto = UtilsTest.loadObject("model/dto/v1/cart_dto.json", CartDto.class);
        when(cartService.getCart(any())).thenReturn(cartDto);

        var response = cartControllerV1.getCart(1L);

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void updateCart_Success() throws IOException {
        var cartDto = UtilsTest.loadObject("model/dto/v1/cart_dto.json", CartDto.class);

        var response = cartControllerV1.updateCart(cartDto);

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(204, response.getStatusCode().value());

        verify(cartService, times(1)).updateCart(any(CartDto.class));
    }
}