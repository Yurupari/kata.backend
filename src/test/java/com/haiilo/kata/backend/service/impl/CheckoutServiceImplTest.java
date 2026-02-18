package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.BaseUnitTest;
import com.haiilo.kata.backend.exception.CheckoutProcessException;
import com.haiilo.kata.backend.model.dto.CartDto;
import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.model.enums.CartStatus;
import com.haiilo.kata.backend.model.http.request.CheckoutRequest;
import com.haiilo.kata.backend.service.CartService;
import com.haiilo.kata.backend.service.ProductOfferService;
import com.haiilo.kata.backend.service.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CheckoutServiceImplTest extends BaseUnitTest {

    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    @Mock
    private CartService cartService;

    @Mock
    private ProductOfferService productOfferService;

    @Mock
    private ReceiptService receiptService;

    private CheckoutRequest request;
    private CartDto pendingCart;

    @BeforeEach
    void setUp() throws IOException {
        request = new CheckoutRequest(1L);
        pendingCart = jsonTestUtils.loadObject("model/dto/v1/pending_cart_dto.json", CartDto.class);

        lenient().when(cartService.getCart(any())).thenReturn(pendingCart);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "model/dto/v1/fixed_amount_product_offer_dto.json",
            "model/dto/v1/percentage_product_offer_dto.json",
            "model/dto/v1/not_available_product_offer_dto.json"
    })
    void checkoutFlow_VariousOffers_Success(String offerJsonPath) throws IOException {
        var offers = List.of(jsonTestUtils.loadObject(offerJsonPath, ProductOfferDto.class));
        when(productOfferService.getProductOffers(any(), any())).thenReturn(offers);
        when(receiptService.addReceipt(any())).thenReturn(mock(ReceiptDto.class));

        assertNotNull(checkoutService.precalculatePrice(1L));

        var receipt = checkoutService.executeCheckout(request);
        assertNotNull(receipt);

        verify(cartService).updateCart(argThat(cart -> cart.cartStatus() == CartStatus.PROCESSED));
    }

    @ParameterizedTest
    @CsvSource({
            "model/dto/v1/cart_dto.json",
            "model/dto/v1/no_items_cart_dto.json"
    })
    void checkoutFlow_Conflicts(String cartJsonPath) throws IOException {
        var invalidCart = jsonTestUtils.loadObject(cartJsonPath, CartDto.class);
        when(cartService.getCart(any())).thenReturn(invalidCart);

        assertThrows(CheckoutProcessException.class, () -> checkoutService.executeCheckout(request));
    }
}