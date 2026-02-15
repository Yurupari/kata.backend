package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.exception.ProductNotFoundException;
import com.haiilo.kata.backend.model.dto.ProductDto;
import com.haiilo.kata.backend.model.entity.Product;
import com.haiilo.kata.backend.repository.ProductRepository;
import com.haiilo.kata.backend.service.ProductOfferService;
import com.haiilo.kata.backend.utils.UtilsTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @MockitoBean
    private ProductRepository productRepository;

    @MockitoBean
    private ProductOfferService productOfferService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getProducts_Success() throws IOException {
        var product = UtilsTest.loadObject("model/domain/v1/product.json", Product.class);

        when(productRepository.findAll()).thenReturn(List.of(product));

        var response = productService.getProducts();

        assertNotNull(response);
    }

    @Test
    void getProduct_Success() throws IOException {
        var product = UtilsTest.loadObject("model/domain/v1/product.json", Product.class);

        when(productRepository.findById(any())).thenReturn(Optional.of(product));

        var response = productService.getProduct(1L);

        assertNotNull(response);
    }

    @Test
    void getProduct_NotFound() {
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProduct(1L));
    }

    @Test
    void getProduct_ConnectionRefused() {
        when(productRepository.findById(any())).thenThrow(new Exception("Connection refused"));

        assertThrows(Exception.class, () -> productService.getProduct(1L));
    }

    @Test
    void addProduct_Success() throws IOException {
        var productDto = UtilsTest.loadObject("model/request/v1/new_product_request.json", ProductDto.class);
        var product = UtilsTest.loadObject("model/domain/v1/product.json", Product.class);

        when(productRepository.save(product)).thenReturn(product);

        var response = productService.addProduct(productDto);

        assertNotNull(response);
    }

    @Test
    void updateProduct_Success() throws IOException {
        var productDto = UtilsTest.loadObject("model/dto/v1/product_dto.json", ProductDto.class);

        when(productRepository.save(any())).thenReturn(new Product());

        assertDoesNotThrow(() -> productService.updateProduct(productDto));

        verify(productRepository, times(1)).save(any(Product.class));
    }
}