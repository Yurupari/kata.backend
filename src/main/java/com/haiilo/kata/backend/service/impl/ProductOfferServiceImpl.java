package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.exception.ProductOfferNotFoundException;
import com.haiilo.kata.backend.exception.ValidationException;
import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.model.http.request.CreateProductOffersRequest;
import com.haiilo.kata.backend.model.http.request.ProductSelectionRequest;
import com.haiilo.kata.backend.model.http.request.UpdateProductOffersRequest;
import com.haiilo.kata.backend.model.mapper.ProductOfferMapper;
import com.haiilo.kata.backend.repository.ProductOfferRepository;
import com.haiilo.kata.backend.service.ProductOfferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
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
        Optional.ofNullable(id).orElseThrow(() -> new ValidationException("ID cannot be null"));

        return productOfferRepository.findById(id)
                .map(productOfferMapper::toDto)
                .orElseThrow(() -> new ProductOfferNotFoundException(id));
    }

    @Override
    public ProductOfferDto addProductOffer(ProductSelectionRequest productSelectionRequest) {
        log.info("Create product offer: {}", productSelectionRequest.toString());

        var productOfferDto = productOfferMapper.toDto(productSelectionRequest);

        validateProductOffer(productOfferDto, false);

        var productOffer = productOfferRepository.save(productOfferMapper.toEntity(productOfferDto));

        return productOfferMapper.toDto(productOffer);
    }

    @Override
    public List<ProductOfferDto> addProductOffers(CreateProductOffersRequest createProductOffersRequest) {
        log.info("Create product offers: {}", createProductOffersRequest.productOffers().size());

        var productOfferDtos = createProductOffersRequest.productOffers().stream()
                .map(productOfferMapper::toDto)
                .toList();

        productOfferDtos.forEach(po -> validateProductOffer(po, false));

        var productOffers = productOfferDtos.stream()
                .map(productOfferMapper::toEntity)
                .toList();

        var newProductOffers = productOfferRepository.saveAll(productOffers);

        return newProductOffers.stream()
                .map(productOfferMapper::toDto)
                .toList();
    }

    @Override
    public void updateProductOffer(ProductOfferDto productOfferDto) {
        log.info("Update product offer: {}", productOfferDto.toString());

        validateProductOffer(productOfferDto, true);

        var existingProductOffer = productOfferRepository.findById(productOfferDto.id())
                .orElseThrow(() -> new ProductOfferNotFoundException(productOfferDto.id()));

        productOfferMapper.updateEntityFromDto(productOfferDto, existingProductOffer);

        productOfferRepository.save(existingProductOffer);
    }

    @Override
    public void updateProductOffers(UpdateProductOffersRequest createProductOffersRequest) {
        log.info("Update product offers: {}", createProductOffersRequest.productOffers().size());

        var productOfferDtos = createProductOffersRequest.productOffers();

        productOfferDtos.forEach(po -> validateProductOffer(po, true));

        var existingProductOffers = productOfferRepository.findByIdIn(productOfferDtos.stream()
                .map(ProductOfferDto::id)
                .toList());

        existingProductOffers.forEach(epo -> {
            var productOfferDto = productOfferDtos.stream()
                    .filter(po -> po.id().equals(epo.getId()))
                    .findFirst().orElse(null);

            Optional.ofNullable(productOfferDto)
                    .ifPresent(pod -> productOfferMapper.updateEntityFromDto(pod, epo));
        });

        productOfferRepository.saveAll(existingProductOffers);
    }

    private void validateProductAndOfferIds(Long productId, Long offerId) {
        if (productId == null && offerId == null) {
            throw new ValidationException("Both productId and offerId cannot be null");
        }
    }

    private void validateProductOffer(ProductOfferDto productOfferDto, boolean isUpdate) {
        if (isUpdate) {
            Optional.ofNullable(productOfferDto.id())
                    .orElseThrow(() -> new ValidationException("ID cannot be null"));
        }

        Optional.ofNullable(productOfferDto.productId())
                .orElseThrow(() -> new ValidationException("Product ID cannot be null"));

        Optional.ofNullable(productOfferDto.offerDto())
                .orElseThrow(() -> new ValidationException("Offer cannot be null"));

        Optional.ofNullable(productOfferDto.offerDto().id())
                .orElseThrow(() -> new ValidationException("Offer ID cannot be null"));
    }
}
