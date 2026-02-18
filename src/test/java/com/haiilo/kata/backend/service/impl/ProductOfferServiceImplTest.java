package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.BaseUnitTest;
import com.haiilo.kata.backend.exception.ProductOfferNotFoundException;
import com.haiilo.kata.backend.exception.ValidationException;
import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.model.entity.ProductOffer;
import com.haiilo.kata.backend.model.http.request.CreateProductOffersRequest;
import com.haiilo.kata.backend.model.http.request.CreateProductSelectionRequest;
import com.haiilo.kata.backend.model.http.request.UpdateProductOffersRequest;
import com.haiilo.kata.backend.model.http.request.UpdateProductSelectionRequest;
import com.haiilo.kata.backend.model.mapper.ProductOfferMapper;
import com.haiilo.kata.backend.model.mapper.ProductOfferMapperImpl;
import com.haiilo.kata.backend.repository.ProductOfferRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

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

class ProductOfferServiceImplTest extends BaseUnitTest {

    @InjectMocks
    private ProductOfferServiceImpl productOfferService;

    @Mock
    private ProductOfferRepository productOfferRepository;

    @Spy
    private ProductOfferMapper productOfferMapper = new ProductOfferMapperImpl();

    @Test
    void getProductOffers_Success() throws IOException {
        var productOffers = jsonTestUtils.loadListObjects(
                "model/domain/v1/product_offers.json",
                ProductOffer.class);

        when(productOfferRepository.findByProductIdOrOfferId(any(), any())).thenReturn(productOffers);

        var response = productOfferService.getProductOffers(1L, 1L);

        assertNotNull(response);
    }

    @Test
    void getProductOffers_OnlyByProductId_Success() throws IOException {
        var productOffers = jsonTestUtils.loadListObjects(
                "model/domain/v1/product_offers.json",
                ProductOffer.class);

        when(productOfferRepository.findByProductIdOrOfferId(any(), any())).thenReturn(productOffers);

        var response = productOfferService.getProductOffers(1L, null);

        assertNotNull(response);
    }

    @Test
    void getProductOffers_OnlyByOfferId_Success() throws IOException {
        var productOffers = jsonTestUtils.loadListObjects(
                "model/domain/v1/product_offers.json",
                ProductOffer.class);

        when(productOfferRepository.findByProductIdOrOfferId(any(), any())).thenReturn(productOffers);

        var response = productOfferService.getProductOffers(null, 1L);

        assertNotNull(response);
    }

    @Test
    void getProductOffers_BothNull_BadRequest() {
        assertThrows(ValidationException.class,
                () -> productOfferService.getProductOffers(null, null));
    }

    @Test
    void getProductOffer_Success() throws IOException {
        var productOffer = jsonTestUtils.loadObject(
                "model/domain/v1/product_offer.json",
                ProductOffer.class);

        when(productOfferRepository.findById(any())).thenReturn(Optional.of(productOffer));

        var response = productOfferService.getProductOffer(1L);

        assertNotNull(response);
    }

    @Test
    void getProductOffer_NotFound() {
        when(productOfferRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ProductOfferNotFoundException.class,
                () -> productOfferService.getProductOffer(1L));
    }

    @Test
    void addProductOffer_Success() throws IOException {
        var productSelectionRequest = jsonTestUtils.loadObject(
                "model/request/v1/new_product_offer_request.json",
                CreateProductSelectionRequest.class);
        var productOffer = jsonTestUtils.loadObject(
                "model/domain/v1/product_offer.json",
                ProductOffer.class);

        when(productOfferRepository.save(any())).thenReturn(productOffer);

        var response = productOfferService.addProductOffer(productSelectionRequest);

        assertNotNull(response);
    }

    @Test
    void addProductOffers_Success() throws IOException {
        var productSelectionRequest = jsonTestUtils.loadObject(
                "model/request/v1/new_product_offer_request.json",
                CreateProductSelectionRequest.class);
        var productOffer = jsonTestUtils.loadObject(
                "model/domain/v1/product_offer.json",
                ProductOffer.class);

        when(productOfferRepository.saveAll(any())).thenReturn(List.of(productOffer));

        var response = productOfferService.addProductOffers(new CreateProductOffersRequest(List.of(productSelectionRequest)));

        assertNotNull(response);
        assertEquals(1, response.size());
    }

    @Test
    void updateProductOffer_Success() throws IOException {
        var updateProductSelectionRequest = jsonTestUtils.loadObject(
                "model/dto/v1/update_product_selection_request.json",
                UpdateProductSelectionRequest.class);
        var productOffer = jsonTestUtils.loadObject(
                "model/domain/v1/product_offer.json",
                ProductOffer.class);

        when(productOfferRepository.findById(any())).thenReturn(Optional.of(productOffer));
        when(productOfferRepository.save(any())).thenReturn(new ProductOffer());

        assertDoesNotThrow(() -> productOfferService.updateProductOffer(updateProductSelectionRequest));

        verify(productOfferRepository, times(1)).findById(any(Long.class));
        verify(productOfferRepository, times(1)).save(any(ProductOffer.class));
    }

    @Test
    void updateProductOffers_Success() throws IOException {
        var updateProductSelectionRequest = jsonTestUtils.loadObject(
                "model/dto/v1/update_product_selection_request.json",
                UpdateProductSelectionRequest.class);
        var productOffer = jsonTestUtils.loadObject(
                "model/domain/v1/product_offer.json",
                ProductOffer.class);

        when(productOfferRepository.findByIdIn(any())).thenReturn(List.of(productOffer));
        when(productOfferRepository.saveAll(any())).thenReturn(List.of(new ProductOffer()));

        assertDoesNotThrow(() -> productOfferService.updateProductOffers(new UpdateProductOffersRequest(List.of(updateProductSelectionRequest))));

        verify(productOfferRepository, times(1)).findByIdIn(any());
        verify(productOfferRepository, times(1)).saveAll(any());
    }
}