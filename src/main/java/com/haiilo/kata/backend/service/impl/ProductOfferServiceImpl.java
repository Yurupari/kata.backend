package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.service.ProductOfferService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductOfferServiceImpl implements ProductOfferService {
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
