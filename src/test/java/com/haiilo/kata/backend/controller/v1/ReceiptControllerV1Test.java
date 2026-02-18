package com.haiilo.kata.backend.controller.v1;

import com.haiilo.kata.backend.BaseUnitTest;
import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.service.ReceiptService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ReceiptControllerV1Test extends BaseUnitTest {

    @InjectMocks
    private ReceiptControllerV1 receiptControllerV1;

    @Mock
    private ReceiptService receiptService;

    @Test
    void getReceipts_Success() throws IOException {
        var receiptDto = jsonTestUtils.loadObject("model/dto/v1/receipt_dto.json", ReceiptDto.class);

        when(receiptService.getReceipts()).thenReturn(List.of(receiptDto));

        var response = receiptControllerV1.getReceipts();

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void getReceipt_Success() throws IOException {
        var receiptDto = jsonTestUtils.loadObject("model/dto/v1/receipt_dto.json", ReceiptDto.class);

        when(receiptService.getReceipt(any(), any())).thenReturn(receiptDto);

        var response = receiptControllerV1.getReceipt(1L, 1L);

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }
}