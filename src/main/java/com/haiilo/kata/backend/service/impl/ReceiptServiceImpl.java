package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.exception.ReceiptNotFoundException;
import com.haiilo.kata.backend.exception.ValidationException;
import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.model.mapper.CartMapper;
import com.haiilo.kata.backend.model.mapper.ReceiptMapper;
import com.haiilo.kata.backend.repository.ReceiptRepository;
import com.haiilo.kata.backend.service.CartService;
import com.haiilo.kata.backend.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;

    private final ReceiptMapper receiptMapper;

    private final CartService cartService;

    @Override
    public List<ReceiptDto> getReceipts() {
        var receipts = receiptRepository.findAll();

        return receipts.stream()
                .map(receiptMapper::toDto)
                .toList();
    }

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
        log.info("Create receipt: {}", receiptDto.toString());

        var receipt = receiptMapper.toEntity(receiptDto);

        var cart = cartService.getCartById(receiptDto.cartId());
        receipt.setCart(cart);

        return receiptMapper.toDto(receiptRepository.save(receipt));
    }

    private void validateIdAndCartId(Long id, Long cartId) {
        if (id == null && cartId == null) {
            throw new ValidationException("Both id and cartId cannot be null");
        }
    }
}
