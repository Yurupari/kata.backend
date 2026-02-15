package com.haiilo.kata.backend.service;

import com.haiilo.kata.backend.model.dto.OfferDto;

import java.util.List;

public interface OfferService {
    OfferDto getOffer(Long id);
    OfferDto addOffer(OfferDto offerDto);
    void updateOffer(OfferDto offerDto);
}
