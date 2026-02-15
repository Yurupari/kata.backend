package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.model.dto.ProductDto;
import com.haiilo.kata.backend.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public List<ProductDto> getProducts() {
        return List.of();
    }

    @Override
    public ProductDto getProduct(Long id) {
        return null;
    }

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        return null;
    }

    @Override
    public void updateProduct(ProductDto productDto) {

    }
}
