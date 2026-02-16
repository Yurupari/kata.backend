package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.exception.ReceiptNotFoundException;
import com.haiilo.kata.backend.exception.ValidationException;
import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.model.mapper.ReceiptMapper;
import com.haiilo.kata.backend.repository.ReceiptRepository;
import com.haiilo.kata.backend.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;

    private final ReceiptMapper receiptMapper;

    @Override
    public ReceiptDto getReceipt(Long id, Long cartId) {
        validateIdAndCartId(id, cartId);

        return receiptRepository.findByIdOrCartId(id, cartId).stream()
                .findFirst()
                .map(receiptMapper::toDto)
                .orElseThrow(() -> new ReceiptNotFoundException(id, cartId));
    }

    @Override
    public ReceiptDto addReceipt(ReceiptDto receiptDto) {
        var receipt = receiptRepository.save(receiptMapper.toEntity(receiptDto));

        return receiptMapper.toDto(receipt);
    }

    private void validateIdAndCartId(Long id, Long cartId) {
        if (id == null && cartId == null) {
            throw new ValidationException("Both id and cartId cannot be null");
        }
    }
}
