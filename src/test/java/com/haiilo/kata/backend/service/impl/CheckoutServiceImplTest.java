package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.model.dto.CartDto;
import com.haiilo.kata.backend.model.dto.OfferDto;
import com.haiilo.kata.backend.model.dto.ProductDto;
import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.model.http.CheckoutRequest;
import com.haiilo.kata.backend.service.CartService;
import com.haiilo.kata.backend.service.OfferService;
import com.haiilo.kata.backend.service.ProductOfferService;
import com.haiilo.kata.backend.service.ProductService;
import com.haiilo.kata.backend.service.ReceiptService;
import com.haiilo.kata.backend.utils.UtilsTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class CheckoutServiceImplTest {

    @Autowired
    private CheckoutServiceImpl checkoutService;

    @MockitoBean
    private CartService cartService;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private ProductOfferService productOfferService;

    @MockitoBean
    private OfferService offerService;

    @MockitoBean
    private ReceiptService receiptService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void executeCheckout_Success() throws IOException {
        var checkoutRequest = new CheckoutRequest(1L);
        var cartDto = UtilsTest.loadObject("model/dto/v1/cart_dto.json", CartDto.class);
        var apple_dto = UtilsTest.loadObject("model/dto/v1/product_dto.json", ProductDto.class);
        var product_offer_dto = List.of(UtilsTest.loadObject("model/dto/v1/product_offer_dto.json", ProductOfferDto.class));
        var offer_dto = UtilsTest.loadObject("model/dto/v1/offer_dto.json", OfferDto.class);

        when(cartService.getCurrentCart()).thenReturn(cartDto);
        when(productService.getProduct(1L)).thenReturn(apple_dto);
        when(productOfferService.getProductOffers(1L, null)).thenReturn(product_offer_dto);
        when(offerService.getOffer(1L)).thenReturn(offer_dto);

        var response = checkoutService.executeCheckout(checkoutRequest);

        assertNotNull(response);
    }
}