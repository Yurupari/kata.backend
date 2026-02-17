package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.exception.ProductNotFoundException;
import com.haiilo.kata.backend.model.dto.ProductDto;
import com.haiilo.kata.backend.model.enums.Status;
import com.haiilo.kata.backend.model.mapper.ProductMapper;
import com.haiilo.kata.backend.repository.ProductRepository;
import com.haiilo.kata.backend.service.ProductOfferService;
import com.haiilo.kata.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Override
    public List<ProductDto> getProducts() {
        return productRepository.findByStatus(Status.ACTIVE).stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public ProductDto getProduct(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        log.info("Create product: {}", productDto.toString());
        var product = productRepository.save(productMapper.toEntity(productDto));

        return productMapper.toDto(product);
    }

    @Override
    public void updateProduct(ProductDto productDto) {
        log.info("Update product: {}", productDto.toString());
        var existingProduct = productRepository.findById(productDto.id())
                        .orElseThrow(() -> new ProductNotFoundException(productDto.id()));

        productMapper.updateEntityFromDto(productDto, existingProduct);

        productRepository.save(existingProduct);
    }
}
