package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.repository.ReceiptRepository;
import com.haiilo.kata.backend.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;

    @Override
    public ReceiptDto getReceipt(Long id, Long cartId) {
        return null;
    }

    @Override
    public ReceiptDto addReceipt(ReceiptDto receiptDto) {
        return null;
    }
}
