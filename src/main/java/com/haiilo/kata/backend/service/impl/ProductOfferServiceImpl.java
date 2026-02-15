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
    public List<ProductOfferDto> getProductOffers(Long productId, Long offerId) {
        return List.of();
    }

    @Override
    public ProductOfferDto getProductOffer(Long id) {
        return null;
    }

    @Override
    public ProductOfferDto addProductOffer(ProductOfferDto productOfferDto) {
        return null;
    }

    @Override
    public void updateProductOffer(ProductOfferDto productOfferDto) {

    }
}
