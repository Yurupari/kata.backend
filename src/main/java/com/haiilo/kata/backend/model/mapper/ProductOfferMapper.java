package com.haiilo.kata.backend.model.mapper;

import com.haiilo.kata.backend.config.CentralMapperConfig;
import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.model.entity.ProductOffer;
import com.haiilo.kata.backend.model.http.request.ProductSelectionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring",
        config = CentralMapperConfig.class
)
public interface ProductOfferMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "offerDto.id", source = "offerId")
    ProductOfferDto toDto(ProductSelectionRequest request);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "offerDto", source = "offer")
    ProductOfferDto toDto(ProductOffer entity);

    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "offer", source = "offerDto")
    ProductOffer toEntity(ProductOfferDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "offer", ignore = true)
    void updateEntityFromDto(ProductOfferDto dto, @MappingTarget ProductOffer entity);
}
