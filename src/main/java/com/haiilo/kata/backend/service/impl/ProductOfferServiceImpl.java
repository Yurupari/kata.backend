package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.exception.ProductOfferNotFoundException;
import com.haiilo.kata.backend.exception.ValidationException;
import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.model.http.request.CreateProductOffersRequest;
import com.haiilo.kata.backend.model.http.request.CreateProductSelectionRequest;
import com.haiilo.kata.backend.model.http.request.UpdateProductOffersRequest;
import com.haiilo.kata.backend.model.http.request.UpdateProductSelectionRequest;
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
    public ProductOfferDto addProductOffer(CreateProductSelectionRequest createProductSelectionRequest) {
        log.info("Create product offer: {}", createProductSelectionRequest.toString());

        validateCreateProductOffer(createProductSelectionRequest);

        var productOfferDto = productOfferMapper.toDto(createProductSelectionRequest);

        var productOffer = productOfferRepository.save(productOfferMapper.toEntity(productOfferDto));

        return productOfferMapper.toDto(productOffer);
    }

    @Override
    public List<ProductOfferDto> addProductOffers(CreateProductOffersRequest createProductOffersRequest) {
        log.info("Create product offers: {}", createProductOffersRequest.productOffers().size());

        createProductOffersRequest.productOffers().forEach(this::validateCreateProductOffer);

        var productOfferDtos = createProductOffersRequest.productOffers().stream()
                .map(productOfferMapper::toDto)
                .toList();

        var productOffers = productOfferDtos.stream()
                .map(productOfferMapper::toEntity)
                .toList();

        var newProductOffers = productOfferRepository.saveAll(productOffers);

        return newProductOffers.stream()
                .map(productOfferMapper::toDto)
                .toList();
    }

    @Override
    public void updateProductOffer(UpdateProductSelectionRequest updateProductSelectionRequest) {
        log.info("Update product offer: {}", updateProductSelectionRequest.toString());

        validateUpdateProductOffer(updateProductSelectionRequest);

        var existingProductOffer = productOfferRepository.findById(updateProductSelectionRequest.id())
                .orElseThrow(() -> new ProductOfferNotFoundException(updateProductSelectionRequest.id()));

        productOfferMapper.updateEntityFromRequest(updateProductSelectionRequest, existingProductOffer);

        productOfferRepository.save(existingProductOffer);
    }

    @Override
    public void updateProductOffers(UpdateProductOffersRequest updateProductOffersRequest) {
        log.info("Update product offers: {}", updateProductOffersRequest.productOffers().size());

        var selections = updateProductOffersRequest.productOffers();

        selections.forEach(this::validateUpdateProductOffer);

        var existingProductOffers = productOfferRepository.findByIdIn(selections.stream()
                .map(UpdateProductSelectionRequest::id)
                .toList());

        existingProductOffers.forEach(epo -> {
            var selection = selections.stream()
                    .filter(po -> po.id().equals(epo.getId()))
                    .findFirst().orElse(null);

            Optional.ofNullable(selection)
                    .ifPresent(s -> productOfferMapper.updateEntityFromRequest(s, epo));
        });

        productOfferRepository.saveAll(existingProductOffers);
    }

    private void validateProductAndOfferIds(Long productId, Long offerId) {
        if (productId == null && offerId == null) {
            throw new ValidationException("Both productId and offerId cannot be null");
        }
    }

    private void validateCreateProductOffer(CreateProductSelectionRequest updateProductSelectionRequest) {
        Optional.ofNullable(updateProductSelectionRequest.productId())
                .orElseThrow(() -> new ValidationException("Product ID cannot be null"));

        Optional.ofNullable(updateProductSelectionRequest.offerId())
                .orElseThrow(() -> new ValidationException("Offer cannot be null"));
    }

    private void validateUpdateProductOffer(UpdateProductSelectionRequest updateProductSelectionRequest) {
        Optional.ofNullable(updateProductSelectionRequest.id())
                .orElseThrow(() -> new ValidationException("ID cannot be null"));

        Optional.ofNullable(updateProductSelectionRequest.productId())
                .orElseThrow(() -> new ValidationException("Product ID cannot be null"));

        Optional.ofNullable(updateProductSelectionRequest.offerId())
                .orElseThrow(() -> new ValidationException("Offer cannot be null"));
    }
}
