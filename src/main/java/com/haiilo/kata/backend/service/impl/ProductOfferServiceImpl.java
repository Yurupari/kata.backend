package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.exception.ProductOfferNotFoundException;
import com.haiilo.kata.backend.exception.ValidationException;
import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.model.mapper.ProductOfferMapper;
import com.haiilo.kata.backend.repository.ProductOfferRepository;
import com.haiilo.kata.backend.service.ProductOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductOfferServiceImpl implements ProductOfferService {

    private final ProductOfferRepository productOfferRepository;

    private final ProductOfferMapper productOfferMapper;

    @Override
    public List<ProductOfferDto> getProductOffers(Long productId, Long offerId) {
        validateProductAndOfferIds(productId, offerId);

        return productOfferRepository.findByProductIdOrOfferId(productId, offerId).stream()
                .map(productOfferMapper::toDto)
                .toList();
    }

    @Override
    public ProductOfferDto getProductOffer(Long id) {
        return productOfferRepository.findById(id)
                .map(productOfferMapper::toDto)
                .orElseThrow(() -> new ProductOfferNotFoundException(id));
    }

    @Override
    public ProductOfferDto addProductOffer(ProductOfferDto productOfferDto) {
        var productOffer = productOfferRepository.save(productOfferMapper.toEntity(productOfferDto));

        return productOfferMapper.toDto(productOffer);
    }

    @Override
    public void updateProductOffer(ProductOfferDto productOfferDto) {
        var existingProductOffer = productOfferRepository.findById(productOfferDto.id())
                .orElseThrow(() -> new ProductOfferNotFoundException(productOfferDto.id()));

        productOfferMapper.updateEntityFromDto(productOfferDto, existingProductOffer);

        productOfferRepository.save(existingProductOffer);
    }

    private void validateProductAndOfferIds(Long productId, Long offerId) {
        if (productId == null && offerId == null) {
            throw new ValidationException("Both productId and offerId cannot be null");
        }
    }
}
