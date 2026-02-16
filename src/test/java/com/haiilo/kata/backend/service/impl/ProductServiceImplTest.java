package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.BaseUnitTest;
import com.haiilo.kata.backend.exception.ProductNotFoundException;
import com.haiilo.kata.backend.model.dto.ProductDto;
import com.haiilo.kata.backend.model.entity.Product;
import com.haiilo.kata.backend.model.mapper.ProductMapper;
import com.haiilo.kata.backend.model.mapper.ProductMapperImpl;
import com.haiilo.kata.backend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

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

class ProductServiceImplTest extends BaseUnitTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Spy
    private ProductMapper productMapper = new ProductMapperImpl();

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