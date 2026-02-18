package com.haiilo.kata.backend.service;

import com.haiilo.kata.backend.model.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductDto> getProducts(Pageable pageable);
    Page<ProductDto> searchProducts(String searchQuery, Pageable pageable);
    ProductDto getProduct(Long id);
    ProductDto addProduct(ProductDto productDto);
    void updateProduct(ProductDto productDto);
}

