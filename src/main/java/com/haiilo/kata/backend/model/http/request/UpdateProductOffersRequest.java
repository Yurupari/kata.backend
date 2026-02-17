package com.haiilo.kata.backend.model.http.request;

import com.haiilo.kata.backend.model.dto.ProductOfferDto;

import java.util.List;

public record UpdateProductOffersRequest(
        List<ProductOfferDto> productOffers
) {
}
