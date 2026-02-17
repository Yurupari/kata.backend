package com.haiilo.kata.backend.controller.v1;

import com.haiilo.kata.backend.BaseUnitTest;
import com.haiilo.kata.backend.model.dto.OfferDto;
import com.haiilo.kata.backend.model.http.request.CreateOfferRequest;
import com.haiilo.kata.backend.model.mapper.OfferMapperImpl;
import com.haiilo.kata.backend.model.mapper.ProductOfferMapper;
import com.haiilo.kata.backend.service.OfferService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OfferControllerV1Test extends BaseUnitTest {

    private OfferControllerV1 offerControllerV1;

    @Mock
    private OfferService offerService;

    @Mock
    private ProductOfferMapper productOfferMapper;

    @Spy
    @InjectMocks
    private OfferMapperImpl offerMapper;

    @BeforeEach
    void setup() {
        super.setUpBase();

        this.offerControllerV1 = new OfferControllerV1();

        ReflectionTestUtils.setField(offerControllerV1, "offerService", offerService);
        ReflectionTestUtils.setField(offerControllerV1, "offerMapper", offerMapper);
    }

    @Test
    void getOffers_Success() throws IOException {
        var pageable = PageRequest.of(0, 10);
        var offerDto = jsonTestUtils.loadObject("model/dto/v1/offer_dto.json", OfferDto.class);
        var offerDtoList = List.of(offerDto);
        var offerDtoPage = new PageImpl<>(offerDtoList, pageable, offerDtoList.size());

        when(offerService.getOffers(pageable)).thenReturn(offerDtoPage);

        var response = offerControllerV1.getOffers(pageable);

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void getOffer_Success() throws IOException {
        var offerDto = jsonTestUtils.loadObject("model/dto/v1/offer_dto.json", OfferDto.class);

        when(offerService.getOffer(any())).thenReturn(offerDto);

        var response = offerControllerV1.getOffer(1L);

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void addOffer_Success() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        try {
            var offerDto = jsonTestUtils.loadObject("model/request/v1/new_offer_request.json", CreateOfferRequest.class);
            var newOfferDto = jsonTestUtils.loadObject("model/dto/v1/offer_dto.json", OfferDto.class);

            when(offerService.addOffer(any())).thenReturn(newOfferDto);

            var response = offerControllerV1.addOffer(offerDto);

            assertNotNull(response);
            assertNotNull(response.getStatusCode());
            assertEquals(201, response.getStatusCode().value());
            assertNotNull(response.getBody());
            assertNotNull(response.getHeaders().getLocation());
        } finally {
            RequestContextHolder.resetRequestAttributes();
        }
    }

    @Test
    void updateOffer_Success() throws IOException {
        var offerDto = jsonTestUtils.loadObject("model/dto/v1/offer_dto.json", OfferDto.class);

        var response = offerControllerV1.updateOffer(offerDto);

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(204, response.getStatusCode().value());

        verify(offerService, times(1)).updateOffer(any(OfferDto.class));
    }
}