package com.haiilo.kata.backend.service;

import com.haiilo.kata.backend.model.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getProducts();
    ProductDto getProduct(Long id);
    ProductDto addProduct(ProductDto productDto);
    void updateProduct(ProductDto productDto);
}
