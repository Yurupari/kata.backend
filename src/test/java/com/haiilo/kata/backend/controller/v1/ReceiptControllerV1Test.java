package com.haiilo.kata.backend.controller.v1;

import com.haiilo.kata.backend.BaseUnitTest;
import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.model.entity.Receipt;
import com.haiilo.kata.backend.service.ReceiptService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

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
        var pageable = PageRequest.of(0, 10);
        var receiptDto = jsonTestUtils.loadObject("model/dto/v1/receipt_dto.json", ReceiptDto.class);
        var receiptList = List.of(receiptDto);
        var receiptPage = new PageImpl<>(receiptList, pageable, receiptList.size());

        when(receiptService.getReceipts(pageable)).thenReturn(receiptPage);

        var response = receiptControllerV1.getReceipts(pageable);

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