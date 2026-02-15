package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.exception.ProductNotFoundException;
import com.haiilo.kata.backend.model.dto.ProductDto;
import com.haiilo.kata.backend.model.entity.Product;
import com.haiilo.kata.backend.repository.ProductRepository;
import com.haiilo.kata.backend.utils.JsonTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JsonTestUtils jsonTestUtils;

    @Test
    void getProducts_Success() throws IOException {
        var product = jsonTestUtils.loadObject("model/domain/v1/product.json", Product.class);

        when(productRepository.findByStatus(any())).thenReturn(List.of(product));

        var response = productService.getProducts();

        assertNotNull(response);
    }

    @Test
    void getProduct_Success() throws IOException {
        var product = jsonTestUtils.loadObject("model/domain/v1/product.json", Product.class);

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
    void addProduct_Success() throws IOException {
        var productDto = jsonTestUtils.loadObject("model/request/v1/new_product_request.json", ProductDto.class);
        var product = jsonTestUtils.loadObject("model/domain/v1/product.json", Product.class);

        when(productRepository.save(any())).thenReturn(product);

        var response = productService.addProduct(productDto);

        assertNotNull(response);
    }

    @Test
    void updateProduct_Success() throws IOException {
        var productDto = jsonTestUtils.loadObject("model/dto/v1/product_dto.json", ProductDto.class);
        var product = jsonTestUtils.loadObject("model/domain/v1/product.json", Product.class);

        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        when(productRepository.save(any())).thenReturn(new Product());

        assertDoesNotThrow(() -> productService.updateProduct(productDto));

        verify(productRepository, times(1)).findById(any(Long.class));
        verify(productRepository, times(1)).save(any(Product.class));
    }
}