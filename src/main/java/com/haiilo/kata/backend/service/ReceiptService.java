package com.haiilo.kata.backend.service;

import com.haiilo.kata.backend.model.dto.ReceiptDto;

import java.util.List;

public interface ReceiptService {
    List<ReceiptDto> getReceipts();
    ReceiptDto getReceipt(Long id, Long cartId);
    ReceiptDto addReceipt(ReceiptDto receiptDto);
}
