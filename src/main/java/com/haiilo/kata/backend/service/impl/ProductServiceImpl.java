package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.model.dto.ProductDto;
import com.haiilo.kata.backend.repository.ProductRepository;
import com.haiilo.kata.backend.service.ProductOfferService;
import com.haiilo.kata.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductOfferService productOfferService;

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
