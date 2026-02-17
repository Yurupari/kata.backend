package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.BaseUnitTest;
import com.haiilo.kata.backend.exception.OfferNotFoundException;
import com.haiilo.kata.backend.model.dto.OfferDto;
import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.model.entity.Offer;
import com.haiilo.kata.backend.model.mapper.OfferMapper;
import com.haiilo.kata.backend.model.mapper.OfferMapperImpl;
import com.haiilo.kata.backend.repository.OfferRepository;
import com.haiilo.kata.backend.service.ProductOfferService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OfferServiceImplTest extends BaseUnitTest {

    @InjectMocks
    private OfferServiceImpl offerService;

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private ProductOfferService productOfferService;

    @Spy
    private OfferMapper offerMapper = new OfferMapperImpl();

    @Test
    void getOffers_Success() throws IOException {
        var pageable = PageRequest.of(0, 10);
        var offer = jsonTestUtils.loadObject("model/domain/v1/offer.json", Offer.class);
        var offerList = List.of(offer);
        var offerPage = new PageImpl<>(offerList, pageable, offerList.size());

        when(offerRepository.findAll(pageable)).thenReturn(offerPage);

        var response = offerService.getOffers(pageable);

        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
    }

    @Test
    void getOffer_Success() throws IOException {
        var offer = jsonTestUtils.loadObject("model/domain/v1/offer.json", Offer.class);

        when(offerRepository.findById(any())).thenReturn(Optional.of(offer));

        var response = offerService.getOffer(1L);

        assertNotNull(response);
    }

    @Test
    void getOffer_NotFound() {
        when(offerRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(OfferNotFoundException.class, () -> offerService.getOffer(1L));
    }

    @Test
    void addOffer_Success() throws IOException {
        var offerDto = jsonTestUtils.loadObject("model/request/v1/new_offer_request.json", OfferDto.class);
        var offer = jsonTestUtils.loadObject("model/domain/v1/offer.json", Offer.class);
        var productOfferDto = jsonTestUtils.loadObject("model/dto/v1/fixed_amount_product_offer_dto.json", ProductOfferDto.class);

        when(offerRepository.save(any())).thenReturn(offer);
        when(productOfferService.addProductOffers(any())).thenReturn(List.of(productOfferDto));

        var response = offerService.addOffer(offerDto);

        assertNotNull(response);

        verify(productOfferService, times(1)).addProductOffers(any());
    }

    @Test
    void updateOffer_Success() throws IOException {
        var offerDto = jsonTestUtils.loadObject("model/dto/v1/offer_dto.json", OfferDto.class);
        var offer = jsonTestUtils.loadObject("model/domain/v1/offer.json", Offer.class);

        when(offerRepository.findById(any())).thenReturn(Optional.of(offer));
        when(offerRepository.save(any())).thenReturn(new Offer());
        assertDoesNotThrow(() -> offerService.updateOffer(offerDto));

        verify(offerRepository, times(1)).findById(any(Long.class));
        verify(offerRepository, times(1)).save(any(Offer.class));
    }
}