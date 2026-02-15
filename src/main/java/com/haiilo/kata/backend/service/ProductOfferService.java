package com.haiilo.kata.backend.service;

import com.haiilo.kata.backend.model.dto.ProductOfferDto;

import java.util.List;

public interface ProductOfferService {
    List<ProductOfferDto> getProductOfferItems();
    ProductOfferDto getProductOfferItem(Long id);
    ProductOfferDto addProductOfferItem(ProductOfferDto productOfferDto);
    void updateProductOfferItem(ProductOfferDto productOfferDto);
    void deleteProductOfferItem(Long id);
}
