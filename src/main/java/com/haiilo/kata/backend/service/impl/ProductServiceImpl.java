package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.exception.ProductNotFoundException;
import com.haiilo.kata.backend.model.dto.ProductDto;
import com.haiilo.kata.backend.model.enums.Status;
import com.haiilo.kata.backend.model.mapper.ProductMapper;
import com.haiilo.kata.backend.repository.ProductRepository;
import com.haiilo.kata.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<ProductDto> getProducts(Pageable pageable) {
        var productPage = productRepository.findByStatus(Status.ACTIVE, pageable);

        return productPage.map(productMapper::toDto);
    }

    @Override
    public Page<ProductDto> searchProducts(String searchQuery, Pageable pageable) {
        log.info("Get products by query: searchQuery={}", searchQuery);

        if (Optional.ofNullable(searchQuery).isPresent() && !searchQuery.isBlank()) {
            if (NumberUtils.isParsable(searchQuery)) {
                return productRepository.findById(Long.valueOf(searchQuery))
                        .map(productMapper::toDto)
                        .map(productDto -> new PageImpl<>(List.of(productDto), pageable, 1))
                        .orElseGet(() -> new PageImpl<>(List.of(), pageable, 0));
            }

            return productRepository.findByNameContainingIgnoreCase(searchQuery, pageable)
                    .map(productMapper::toDto);
        }

        return new PageImpl<>(List.of(), pageable, 0);
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
