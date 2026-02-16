package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.exception.OfferNotFoundException;
import com.haiilo.kata.backend.model.dto.OfferDto;
import com.haiilo.kata.backend.model.entity.Offer;
import com.haiilo.kata.backend.repository.OfferRepository;
import com.haiilo.kata.backend.service.ProductOfferService;
import com.haiilo.kata.backend.utils.JsonTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class OfferServiceImplTest {

    @Autowired
    private OfferServiceImpl offerService;

    @MockitoBean
    private OfferRepository offerRepository;

    @MockitoBean
    private ProductOfferService productOfferService;

    @Autowired
    private JsonTestUtils jsonTestUtils;

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

        when(offerRepository.save(any())).thenReturn(offer);

        var response = offerService.addOffer(offerDto);

        assertNotNull(response);

        verify(productOfferService, times(1)).addProductOffer(any());
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