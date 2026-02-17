package com.haiilo.kata.backend.service;

import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.model.http.request.CreateProductOffersRequest;
import com.haiilo.kata.backend.model.http.request.ProductSelectionRequest;
import com.haiilo.kata.backend.model.http.request.UpdateProductOffersRequest;

import java.util.List;

public interface ProductOfferService {
    List<ProductOfferDto> getProductOffers(Long productId, Long offerId);
    ProductOfferDto getProductOffer(Long id);
    ProductOfferDto addProductOffer(ProductSelectionRequest productSelectionRequest);
    List<ProductOfferDto> addProductOffers(CreateProductOffersRequest createProductOffersRequest);
    void updateProductOffer(ProductOfferDto productOfferDto);
    void updateProductOffers(UpdateProductOffersRequest createProductOffersRequest);
}
