package com.haiilo.kata.backend.controller.v1;

import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.service.ReceiptService;
import com.haiilo.kata.backend.utils.UtilsTest;
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
class ReceiptControllerV1Test {

    @Autowired
    private ReceiptControllerV1 receiptControllerV1;

    @MockitoBean
    private ReceiptService receiptService;

    @Test
    void getReceipt_Success() throws IOException {
        var receiptDto = UtilsTest.loadObject("model/dto/v1/receipt_dto.json", ReceiptDto.class);

        when(receiptService.getReceipt(any(), any())).thenReturn(receiptDto);

        var response = receiptControllerV1.getReceipt(1L, 1L);

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }
}