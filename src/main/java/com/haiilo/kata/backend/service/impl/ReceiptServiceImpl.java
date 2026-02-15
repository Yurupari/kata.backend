package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.service.ReceiptService;
import org.springframework.stereotype.Service;

@Service
public class ReceiptServiceImpl implements ReceiptService {
    @Override
    public ReceiptDto getReceipt(Long id, Long cartId) {
        return null;
    }

    @Override
    public ReceiptDto addReceipt(ReceiptDto receiptDto) {
        return null;
    }
}
