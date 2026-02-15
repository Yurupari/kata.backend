package com.haiilo.kata.backend.service;

import com.haiilo.kata.backend.model.dto.ReceiptDto;

public interface ReceiptService {
    ReceiptDto getReceipt(Long id, Long cartId);
    ReceiptDto addReceipt(ReceiptDto receiptDto);
}
