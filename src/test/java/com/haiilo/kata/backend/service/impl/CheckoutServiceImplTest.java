package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.exception.CartConflictStatusException;
import com.haiilo.kata.backend.model.dto.CartDto;
import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.model.http.request.CheckoutRequest;
import com.haiilo.kata.backend.service.CartService;
import com.haiilo.kata.backend.service.ProductOfferService;
import com.haiilo.kata.backend.service.ReceiptService;
import com.haiilo.kata.backend.utils.JsonTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CheckoutServiceImplTest {

    @Autowired
    private CheckoutServiceImpl checkoutService;

    @MockitoBean
    private CartService cartService;

    @MockitoBean
    private ProductOfferService productOfferService;

    @MockitoBean
    private ReceiptService receiptService;

    @Autowired
    private JsonTestUtils jsonTestUtils;

    @Test
    void executeCheckout_FixedAmount_Success() throws IOException {
        var checkoutRequest = new CheckoutRequest(1L);
        var cartDto = jsonTestUtils.loadObject("model/dto/v1/pending_cart_dto.json", CartDto.class);
        var product_offer_dto = List.of(jsonTestUtils.loadObject("model/dto/v1/fixed_amount_product_offer_dto.json", ProductOfferDto.class));
        var receipt_dto = jsonTestUtils.loadObject("model/dto/v1/receipt_dto.json", ReceiptDto.class);

        when(cartService.getCart(any())).thenReturn(cartDto);
        when(productOfferService.getProductOffers(any(), any())).thenReturn(product_offer_dto);
        when(receiptService.addReceipt(any())).thenReturn(receipt_dto);

        var response = checkoutService.executeCheckout(checkoutRequest);

        assertNotNull(response);

        verify(cartService, times(1)).updateCart(any(CartDto.class));
    }

    @Test
    void executeCheckout_Percentage_Success() throws IOException {
        var checkoutRequest = new CheckoutRequest(1L);
        var cartDto = jsonTestUtils.loadObject("model/dto/v1/pending_cart_dto.json", CartDto.class);
        var product_offer_dto = List.of(jsonTestUtils.loadObject("model/dto/v1/percentage_product_offer_dto.json", ProductOfferDto.class));
        var receipt_dto = jsonTestUtils.loadObject("model/dto/v1/receipt_dto.json", ReceiptDto.class);

        when(cartService.getCart(any())).thenReturn(cartDto);
        when(productOfferService.getProductOffers(any(), any())).thenReturn(product_offer_dto);
        when(receiptService.addReceipt(any())).thenReturn(receipt_dto);

        var response = checkoutService.executeCheckout(checkoutRequest);

        assertNotNull(response);

        verify(cartService, times(1)).updateCart(any(CartDto.class));
    }

    @Test
    void executeCheckout_NotOffersAvailable_Success() throws IOException {
        var checkoutRequest = new CheckoutRequest(1L);
        var cartDto = jsonTestUtils.loadObject("model/dto/v1/pending_cart_dto.json", CartDto.class);
        var product_offer_dto = List.of(jsonTestUtils.loadObject("model/dto/v1/not_available_product_offer_dto.json", ProductOfferDto.class));
        var receipt_dto = jsonTestUtils.loadObject("model/dto/v1/receipt_dto.json", ReceiptDto.class);

        when(cartService.getCart(any())).thenReturn(cartDto);
        when(productOfferService.getProductOffers(any(), any())).thenReturn(product_offer_dto);
        when(receiptService.addReceipt(any())).thenReturn(receipt_dto);

        var response = checkoutService.executeCheckout(checkoutRequest);

        assertNotNull(response);

        verify(cartService, times(1)).updateCart(any(CartDto.class));
    }

    @Test
    void executeCheckout_CartIsNotPending_Conflict() throws IOException {
        var checkoutRequest = new CheckoutRequest(1L);
        var cartDto = jsonTestUtils.loadObject("model/dto/v1/cart_dto.json", CartDto.class);

        when(cartService.getCart(any())).thenReturn(cartDto);

        assertThrows(CartConflictStatusException.class, () -> checkoutService.executeCheckout(checkoutRequest));
    }
}