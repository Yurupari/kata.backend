package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.exception.OfferNotFoundException;
import com.haiilo.kata.backend.model.dto.OfferDto;
import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.model.http.request.CreateProductOffersRequest;
import com.haiilo.kata.backend.model.http.request.ProductSelectionRequest;
import com.haiilo.kata.backend.model.mapper.OfferMapper;
import com.haiilo.kata.backend.repository.OfferRepository;
import com.haiilo.kata.backend.service.OfferService;
import com.haiilo.kata.backend.service.ProductOfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    private final OfferMapper offerMapper;

    private final ProductOfferService productOfferService;

    @Override
    public Page<OfferDto> getOffers(Pageable pageable) {
        var offerPage = offerRepository.findAll(pageable);

        return offerPage.map(offerMapper::toDto);
    }

    @Override
    public OfferDto getOffer(Long id) {
        return offerRepository.findById(id)
                .map(offerMapper::toDto)
                .orElseThrow(() -> new OfferNotFoundException(id));
    }

    @Override
    @Transactional
    public OfferDto addOffer(OfferDto offerDto) {
        log.info("Create offer: {}", offerDto.toString());

        var newOfferDto = new OfferDto(
                offerDto.id(),
                offerDto.name(),
                offerDto.discount(),
                offerDto.currency(),
                offerDto.discountType(),
                offerDto.fromDate(),
                offerDto.untilDate(),
                offerDto.status(),
                null
        );

        log.info("Saving offer: offer={}", newOfferDto.toString());
        var offer = offerRepository.save(offerMapper.toEntity(newOfferDto));

        log.info("Creating product offers: productOffers={}", offerDto.products().size());
        var productSelectionRequests = offerDto.products().stream()
                .map(po -> new ProductSelectionRequest(
                        po.productId(),
                        offer.getId(),
                        po.quantity()
                ))
                .toList();

        var productOffers = productOfferService.addProductOffers(new CreateProductOffersRequest(productSelectionRequests));

        return offerMapper.toDto(offer, productOffers);
    }

    @Override
    public void updateOffer(OfferDto offerDto) {
        log.info("Update offer: {}", offerDto.toString());
        var existingOffer = offerRepository.findById(offerDto.id())
                .orElseThrow(() -> new OfferNotFoundException(offerDto.id()));

        offerMapper.updateEntityFromDto(offerDto, existingOffer);

        offerRepository.save(existingOffer);
    }
}
