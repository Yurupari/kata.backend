package com.haiilo.kata.backend.model.mapper;

import com.haiilo.kata.backend.config.CentralMapperConfig;
import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.model.entity.ProductOffer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        config = CentralMapperConfig.class
)
public interface ProductOfferMapper {
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "offerId", source = "offer.id")
    ProductOfferDto toDto(ProductOffer entity);

    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "offer.id", source = "offerId")
    ProductOffer toEntity(ProductOfferDto dto);
}
