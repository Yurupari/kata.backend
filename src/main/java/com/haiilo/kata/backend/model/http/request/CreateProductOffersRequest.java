package com.haiilo.kata.backend.model.http.request;

import java.util.List;

public record CreateProductOffersRequest(
        List<CreateProductSelectionRequest> productOffers
) {
}
