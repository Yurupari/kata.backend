package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.repository.ProductOfferRepository;
import com.haiilo.kata.backend.service.ProductOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductOfferServiceImpl implements ProductOfferService {

    private final ProductOfferRepository productOfferRepository;

    @Override
    public List<ProductOfferDto> getProductOfferItems() {
        return List.of();
    }

    @Override
    public ProductOfferDto getProductOfferItem(Long id) {
        return null;
    }

    @Override
    public ProductOfferDto addProductOfferItem(ProductOfferDto productOfferDto) {
        return null;
    }

    @Override
    public void updateProductOfferItem(ProductOfferDto productOfferDto) {

    }

    @Override
    public void deleteProductOfferItem(Long id) {

    }
}
