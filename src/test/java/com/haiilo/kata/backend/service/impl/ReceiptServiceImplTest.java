package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.exception.ReceiptNotFoundException;
import com.haiilo.kata.backend.exception.ValidationException;
import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.model.entity.Receipt;
import com.haiilo.kata.backend.repository.ReceiptRepository;
import com.haiilo.kata.backend.utils.JsonTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ReceiptServiceImplTest {

    @Autowired
    private ReceiptServiceImpl receiptService;

    @MockitoBean
    private ReceiptRepository receiptRepository;

    @Autowired
    private JsonTestUtils jsonTestUtils;

    @Test
    void getReceipt_Success() throws IOException {
        var receipt = jsonTestUtils.loadObject("model/domain/v1/receipt.json", Receipt.class);

        when(receiptRepository.findById(any())).thenReturn(Optional.of(receipt));

        var response = receiptService.getReceipt(1L, 1L);

        assertNotNull(response);
    }

    @Test
    void getReceiptOnlyId_Success() throws IOException {
        var receipt = jsonTestUtils.loadObject("model/domain/v1/receipt.json", Receipt.class);

        when(receiptRepository.findById(any())).thenReturn(Optional.of(receipt));

        var response = receiptService.getReceipt(1L, null);

        assertNotNull(response);
    }

    @Test
    void getReceiptOnlyCartId_Success() throws IOException {
        var receipt = jsonTestUtils.loadObject("model/domain/v1/receipt.json", Receipt.class);

        when(receiptRepository.findById(any())).thenReturn(Optional.of(receipt));

        var response = receiptService.getReceipt(null, 1L);

        assertNotNull(response);
    }

    @Test
    void getReceiptBothNull_ValidationError() throws IOException {
        assertThrows(ValidationException.class, () -> receiptService.getReceipt(null, null));
    }

    @Test
    void getReceipt_NotFound() throws IOException {
        when(receiptRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ReceiptNotFoundException.class, () -> receiptService.getReceipt(1L, 1L));
    }

    @Test
    void addReceipt_Success() throws IOException {
        var receiptDto = jsonTestUtils.loadObject("model/dto/v1/receipt_dto.json", ReceiptDto.class);
        var receipt = jsonTestUtils.loadObject("model/domain/v1/receipt.json", Receipt.class);

        when(receiptRepository.save(any())).thenReturn(receipt);

        var response = receiptService.addReceipt(receiptDto);

        assertNotNull(response);
    }
}