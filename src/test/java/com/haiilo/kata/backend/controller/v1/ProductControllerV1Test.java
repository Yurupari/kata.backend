package com.haiilo.kata.backend.controller.v1;

import com.haiilo.kata.backend.BaseUnitTest;
import com.haiilo.kata.backend.model.dto.ProductDto;
import com.haiilo.kata.backend.model.http.request.CreateProductRequest;
import com.haiilo.kata.backend.model.mapper.ProductMapper;
import com.haiilo.kata.backend.model.mapper.ProductMapperImpl;
import com.haiilo.kata.backend.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductControllerV1Test extends BaseUnitTest {

    @InjectMocks
    private ProductControllerV1 productControllerV1;

    @Mock
    private ProductService productService;

    @Spy
    private ProductMapper productMapper = new ProductMapperImpl();

    @Test
    void getProducts_Success() throws IOException {
        var productDto = jsonTestUtils.loadObject("model/dto/v1/product_dto.json", ProductDto.class);
        when(productService.getProducts()).thenReturn(List.of(productDto));

        var response = productControllerV1.getProducts();

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void getProduct_Success() throws IOException {
        var productDto = jsonTestUtils.loadObject("model/dto/v1/product_dto.json", ProductDto.class);
        when(productService.getProduct(any())).thenReturn(productDto);

        var response = productControllerV1.getProduct(1L);

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void addProduct_Success() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        try {
            var productDto = jsonTestUtils.loadObject("model/request/v1/new_product_request.json", CreateProductRequest.class);
            var newProductDto = jsonTestUtils.loadObject("model/dto/v1/product_dto.json", ProductDto.class);

            when(productService.addProduct(any())).thenReturn(newProductDto);

            var response = productControllerV1.addProduct(productDto);

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
    void updateProduct_Success() throws IOException {
        var productDto = jsonTestUtils.loadObject("model/dto/v1/product_dto.json", ProductDto.class);

        var response = productControllerV1.updateProduct(productDto);

        assertNotNull(response);
        assertNotNull(response.getStatusCode());
        assertEquals(204, response.getStatusCode().value());

        verify(productService, times(1)).updateProduct(any(ProductDto.class));
    }
}