package com.haiilo.kata.backend.controller.v1;

import com.haiilo.kata.backend.BaseUnitTest;
import com.haiilo.kata.backend.model.dto.PriceDto;
import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.model.http.request.CheckoutRequest;
import com.haiilo.kata.backend.service.CheckoutService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CheckoutControllerV1Test extends BaseUnitTest {

    @InjectMocks
    private CheckoutControllerV1 checkoutControllerV1;

    @Mock
    private CheckoutService checkoutService;

    @Test
    void getPrecalculatedPrice_Success() throws IOException {
        var priceDto = jsonTestUtils.loadObject("model/dto/v1/price_dto.json", PriceDto.class);

        when(checkoutService.precalculatePrice(1L)).thenReturn(priceDto);

        var response = checkoutControllerV1.getPrecalculatedPrice(1L);

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void executeCheckout_Success() throws IOException {
        var receiptDto = jsonTestUtils.loadObject("model/dto/v1/receipt_dto.json", ReceiptDto.class);

        when(checkoutService.executeCheckout(any())).thenReturn(receiptDto);

        var response = checkoutControllerV1.executeCheckout(new CheckoutRequest(1L));

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }
}