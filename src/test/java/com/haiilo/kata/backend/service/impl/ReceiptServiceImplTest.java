package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.BaseUnitTest;
import com.haiilo.kata.backend.exception.ReceiptNotFoundException;
import com.haiilo.kata.backend.exception.ValidationException;
import com.haiilo.kata.backend.model.dto.CartDto;
import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.model.entity.Offer;
import com.haiilo.kata.backend.model.entity.Receipt;
import com.haiilo.kata.backend.model.mapper.CartMapper;
import com.haiilo.kata.backend.model.mapper.CartMapperImpl;
import com.haiilo.kata.backend.model.mapper.ReceiptMapper;
import com.haiilo.kata.backend.model.mapper.ReceiptMapperImpl;
import com.haiilo.kata.backend.repository.ReceiptRepository;
import com.haiilo.kata.backend.service.CartService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ReceiptServiceImplTest extends BaseUnitTest {

    @InjectMocks
    private ReceiptServiceImpl receiptService;

    @Mock
    private ReceiptRepository receiptRepository;

    @Mock
    private CartService cartService;

    @Spy
    private ReceiptMapper receiptMapper = new ReceiptMapperImpl();

    private final CartMapper cartMapper = new CartMapperImpl();

    @Test
    void getReceipts_Success() throws IOException {
        var pageable = PageRequest.of(0, 10);
        var receipt = jsonTestUtils.loadObject("model/domain/v1/receipt.json", Receipt.class);
        var receiptList = List.of(receipt);
        var receiptPage = new PageImpl<>(receiptList, pageable, receiptList.size());

        when(receiptRepository.findAll(pageable)).thenReturn(receiptPage);

        var response = receiptService.getReceipts(pageable);

        assertNotNull(response);
    }

    @Test
    void getReceipt_Success() throws IOException {
        var receipt = jsonTestUtils.loadObject("model/domain/v1/receipt.json", Receipt.class);

        when(receiptRepository.findByIdOrCartId(any(), any())).thenReturn(List.of(receipt));

        var response = receiptService.getReceipt(1L, 1L);

        assertNotNull(response);
    }

    @Test
    void getReceiptOnlyId_Success() throws IOException {
        var receipt = jsonTestUtils.loadObject("model/domain/v1/receipt.json", Receipt.class);

        when(receiptRepository.findByIdOrCartId(any(), any())).thenReturn(List.of(receipt));

        var response = receiptService.getReceipt(1L, null);

        assertNotNull(response);
    }

    @Test
    void getReceiptOnlyCartId_Success() throws IOException {
        var receipt = jsonTestUtils.loadObject("model/domain/v1/receipt.json", Receipt.class);

        when(receiptRepository.findByIdOrCartId(any(), any())).thenReturn(List.of(receipt));

        var response = receiptService.getReceipt(null, 1L);

        assertNotNull(response);
    }

    @Test
    void getReceiptBothNull_ValidationError() throws IOException {
        assertThrows(ValidationException.class, () -> receiptService.getReceipt(null, null));
    }

    @Test
    void getReceipt_NotFound() throws IOException {
        when(receiptRepository.findByIdOrCartId(any(), any())).thenReturn(List.of());

        assertThrows(ReceiptNotFoundException.class, () -> receiptService.getReceipt(1L, 1L));
    }

    @Test
    void addReceipt_Success() throws IOException {
        var receiptDto = jsonTestUtils.loadObject("model/dto/v1/receipt_dto.json", ReceiptDto.class);
        var receipt = jsonTestUtils.loadObject("model/domain/v1/receipt.json", Receipt.class);
        var cartDto = jsonTestUtils.loadObject("model/dto/v1/cart_dto.json", CartDto.class);

        when(receiptRepository.save(any())).thenReturn(receipt);
        when(cartService.getCartById(any())).thenReturn(cartMapper.toEntity(cartDto));

        var response = receiptService.addReceipt(receiptDto);

        assertNotNull(response);
    }
}