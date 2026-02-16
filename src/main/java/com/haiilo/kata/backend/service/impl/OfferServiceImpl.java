package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.exception.OfferNotFoundException;
import com.haiilo.kata.backend.model.dto.OfferDto;
import com.haiilo.kata.backend.model.mapper.OfferMapper;
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

    private final OfferMapper offerMapper;

    @Override
    public OfferDto getOffer(Long id) {
        return offerRepository.findById(id)
                .map(offerMapper::toDto)
                .orElseThrow(() -> new OfferNotFoundException(id));
    }

    @Override
    public OfferDto addOffer(OfferDto offerDto) {
        var offer = offerRepository.save(offerMapper.toEntity(offerDto));

        return offerMapper.toDto(offer);
    }

    @Override
    public void updateOffer(OfferDto offerDto) {
        var existingOffer = offerRepository.findById(offerDto.id())
                .orElseThrow(() -> new OfferNotFoundException(offerDto.id()));

        offerMapper.updateEntityFromDto(offerDto, existingOffer);

        offerRepository.save(existingOffer);
    }
}
