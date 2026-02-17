package com.haiilo.kata.backend.controller.v1;

import com.haiilo.kata.backend.BaseUnitTest;
import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.model.http.request.CreateProductOffersRequest;
import com.haiilo.kata.backend.model.http.request.ProductSelectionRequest;
import com.haiilo.kata.backend.model.http.request.UpdateProductOffersRequest;
import com.haiilo.kata.backend.model.mapper.ProductOfferMapper;
import com.haiilo.kata.backend.model.mapper.ProductOfferMapperImpl;
import com.haiilo.kata.backend.service.ProductOfferService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductOfferControllerV1Test extends BaseUnitTest {

    @InjectMocks
    private ProductOfferControllerV1 productOfferControllerV1;

    @Mock
    private ProductOfferService productOfferService;

    @Spy
    private ProductOfferMapper productOfferMapper = new ProductOfferMapperImpl();

    @Test
    void getProductOffers_Success() throws IOException {
        var productOfferDto = jsonTestUtils.loadObject("model/dto/v1/fixed_amount_product_offer_dto.json", ProductOfferDto.class);

        when(productOfferService.getProductOffers(any(), any())).thenReturn(List.of(productOfferDto));

        var response = productOfferControllerV1.getProductOffers(1L, 1L);

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void addProductOffer_Success() throws IOException {
        var productOfferDto = jsonTestUtils.loadObject("model/request/v1/new_product_offer_request.json", ProductSelectionRequest.class);
        var newProductOfferDto = jsonTestUtils.loadObject("model/dto/v1/fixed_amount_product_offer_dto.json", ProductOfferDto.class);

        when(productOfferService.addProductOffer(any())).thenReturn(newProductOfferDto);

        var response = productOfferControllerV1.addProductOffer(productOfferDto);

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void addProductOffers_Success() throws IOException {
        var productSelectionRequest = jsonTestUtils.loadObject("model/request/v1/create_product_offer_request.json", CreateProductOffersRequest.class);
        var newProductOfferDto = jsonTestUtils.loadObject("model/dto/v1/fixed_amount_product_offer_dto.json", ProductOfferDto.class);

        when(productOfferService.addProductOffers(any())).thenReturn(List.of(newProductOfferDto));

        var response = productOfferControllerV1.addProductOffers(productSelectionRequest);

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void updateProductOffer() throws IOException {
        var productOfferDto = jsonTestUtils.loadObject("model/dto/v1/fixed_amount_product_offer_dto.json", ProductOfferDto.class);

        var response = productOfferControllerV1.updateProductOffer(productOfferDto);

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(204, response.getStatusCode().value());

        verify(productOfferService, times(1)).updateProductOffer(any(ProductOfferDto.class));
    }

    @Test
    void updateProductOffers() throws IOException {
        var productOfferDto = jsonTestUtils.loadObject("model/dto/v1/fixed_amount_product_offer_dto.json", ProductOfferDto.class);
        var updateProductOffersRequest = new UpdateProductOffersRequest(List.of(productOfferDto));

        var response = productOfferControllerV1.updateProductOffers(updateProductOffersRequest);

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(204, response.getStatusCode().value());

        verify(productOfferService, times(1)).updateProductOffers(any());
    }
}