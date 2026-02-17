package com.haiilo.kata.backend.service;

import com.haiilo.kata.backend.model.dto.OfferDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OfferService {
    Page<OfferDto> getOffers(Pageable pageable);
    OfferDto getOffer(Long id);
    OfferDto addOffer(OfferDto offerDto);
    void updateOffer(OfferDto offerDto);
}
