package com.haiilo.kata.backend.service;

import com.haiilo.kata.backend.model.dto.ProductOfferDto;

import java.util.List;

public interface ProductOfferService {
    List<ProductOfferDto> getProductOffers(Long productId, Long offerId);
    ProductOfferDto getProductOffer(Long id);
    ProductOfferDto addProductOffer(ProductOfferDto productOfferDto);
    void updateProductOffer(ProductOfferDto productOfferDto);
}
