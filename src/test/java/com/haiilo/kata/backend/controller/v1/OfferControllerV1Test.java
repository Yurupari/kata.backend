package com.haiilo.kata.backend.controller.v1;

import com.haiilo.kata.backend.model.dto.OfferDto;
import com.haiilo.kata.backend.service.OfferService;
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
class OfferControllerV1Test {

    @Autowired
    private OfferControllerV1 offerControllerV1;

    @MockitoBean
    private OfferService offerService;

    @Test
    void getOffer_Success() throws IOException {
        var offerDto = UtilsTest.loadObject("model/dto/v1/offer_dto.json", OfferDto.class);

        when(offerService.getOffer(any())).thenReturn(offerDto);

        var response = offerControllerV1.getOffer(1L);

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void addOffer_Success() throws IOException {
        var offerDto = UtilsTest.loadObject("model/request/v1/new_offer_request.json", OfferDto.class);
        var newOfferDto = UtilsTest.loadObject("model/dto/v1/offer_dto.json", OfferDto.class);

        when(offerService.addOffer(any())).thenReturn(newOfferDto);

        var response = offerControllerV1.addOffer(offerDto);

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void updateOffer_Success() throws IOException {
        var offerDto = UtilsTest.loadObject("model/dto/v1/offer_dto.json", OfferDto.class);

        var response = offerControllerV1.updateOffer(offerDto);

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(204, response.getStatusCode().value());

        verify(offerService, times(1)).updateOffer(any(OfferDto.class));
    }
}