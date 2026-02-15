package com.haiilo.kata.backend.controller.v1;

import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.service.ProductOfferService;
import com.haiilo.kata.backend.utils.UtilsTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductOfferControllerV1Test {

    @Autowired
    private ProductOfferControllerV1 productOfferControllerV1;

    @MockitoBean
    private ProductOfferService productOfferService;

    @Test
    void addProductOffer_Success() throws IOException {
        var productOfferDto = UtilsTest.loadObject("model/request/v1/new_product_offer_request.json", ProductOfferDto.class);
        var newProductOfferDto = UtilsTest.loadObject("model/dto/v1/product_offer_dto.json", ProductOfferDto.class);

        when(productOfferService.addProductOffer(any())).thenReturn(newProductOfferDto);

        var response = productOfferControllerV1.addProductOffer(productOfferDto);

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void updateProductOffer() throws IOException {
        var productOfferDto = UtilsTest.loadObject("model/dto/v1/product_offer_dto.json", ProductOfferDto.class);

        var response = productOfferControllerV1.updateProductOffer(productOfferDto);

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(204, response.getStatusCode().value());

        verify(productOfferService, times(1)).updateProductOffer(any(ProductOfferDto.class));
    }
}