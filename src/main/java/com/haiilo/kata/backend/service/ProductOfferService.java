package com.haiilo.kata.backend.service;

import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.model.http.request.CreateProductOffersRequest;
import com.haiilo.kata.backend.model.http.request.CreateProductSelectionRequest;
import com.haiilo.kata.backend.model.http.request.UpdateProductOffersRequest;
import com.haiilo.kata.backend.model.http.request.UpdateProductSelectionRequest;

import java.util.List;

public interface ProductOfferService {
    List<ProductOfferDto> getProductOffers(Long productId, Long offerId);
    ProductOfferDto getProductOffer(Long id);
    ProductOfferDto addProductOffer(CreateProductSelectionRequest createProductSelectionRequest);
    List<ProductOfferDto> addProductOffers(CreateProductOffersRequest createProductOffersRequest);
    void updateProductOffer(UpdateProductSelectionRequest updateProductSelectionRequest);
    void updateProductOffers(UpdateProductOffersRequest createProductOffersRequest);
}
