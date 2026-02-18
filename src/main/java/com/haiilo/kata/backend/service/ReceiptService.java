package com.haiilo.kata.backend.service;

import com.haiilo.kata.backend.model.dto.ReceiptDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReceiptService {
    Page<ReceiptDto> getReceipts(Pageable pageable);
    ReceiptDto getReceipt(Long id, Long cartId);
    ReceiptDto addReceipt(ReceiptDto receiptDto);
}
