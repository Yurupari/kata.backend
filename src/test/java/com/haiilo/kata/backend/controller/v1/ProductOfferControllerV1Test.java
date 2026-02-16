package com.haiilo.kata.backend.controller.v1;

import com.haiilo.kata.backend.BaseUnitTest;
import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.model.http.request.ProductSelectionRequest;
import com.haiilo.kata.backend.model.mapper.ProductOfferMapper;
import com.haiilo.kata.backend.model.mapper.ProductOfferMapperImpl;
import com.haiilo.kata.backend.service.ProductOfferService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.io.IOException;

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
    void updateProductOffer() throws IOException {
        var productOfferDto = jsonTestUtils.loadObject("model/dto/v1/fixed_amount_product_offer_dto.json", ProductOfferDto.class);

        var response = productOfferControllerV1.updateProductOffer(productOfferDto);

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(204, response.getStatusCode().value());

        verify(productOfferService, times(1)).updateProductOffer(any(ProductOfferDto.class));
    }
}