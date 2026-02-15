package com.haiilo.kata.backend.controller.v1;

import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.model.http.CheckoutRequest;
import com.haiilo.kata.backend.service.CheckoutService;
import com.haiilo.kata.backend.utils.JsonTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CheckoutControllerV1Test {

    @Autowired
    private CheckoutControllerV1 checkoutControllerV1;

    @MockitoBean
    private CheckoutService checkoutService;

    @Autowired
    private JsonTestUtils jsonTestUtils;

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