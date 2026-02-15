package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.model.dto.OfferDto;
import com.haiilo.kata.backend.repository.OfferRepository;
import com.haiilo.kata.backend.service.OfferService;
import com.haiilo.kata.backend.service.ProductOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    private final ProductOfferService productOfferService;

    @Override
    public OfferDto getOffer(Long id) {
        return null;
    }

    @Override
    public OfferDto addOffer(OfferDto offerDto) {
        return null;
    }

    @Override
    public void updateOffer(OfferDto offerDto) {

    }
}
