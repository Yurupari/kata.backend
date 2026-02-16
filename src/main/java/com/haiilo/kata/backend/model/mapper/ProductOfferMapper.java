package com.haiilo.kata.backend.model.mapper;

import com.haiilo.kata.backend.config.CentralMapperConfig;
import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.model.entity.ProductOffer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring",
        config = CentralMapperConfig.class
)
public interface ProductOfferMapper {
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "offerId", source = "offer.id")
    @Mapping(target = "discount", source = "offer.discount")
    @Mapping(target = "currency", source = "offer.currency")
    @Mapping(target = "discountType", source = "offer.discountType")
    @Mapping(target = "from", source = "offer.from")
    @Mapping(target = "until", source = "offer.until")
    ProductOfferDto toDto(ProductOffer entity);

    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "offer.id", source = "offerId")
    @Mapping(target = "offer.discount", ignore = true)
    @Mapping(target = "offer.currency", ignore = true)
    @Mapping(target = "offer.discountType", ignore = true)
    @Mapping(target = "offer.from", ignore = true)
    @Mapping(target = "offer.until", ignore = true)
    ProductOffer toEntity(ProductOfferDto dto);

    @Mapping(target = "product.id", ignore = true)
    @Mapping(target = "offer.id", ignore = true)
    @Mapping(target = "offer.discount", ignore = true)
    @Mapping(target = "offer.currency", ignore = true)
    @Mapping(target = "offer.discountType", ignore = true)
    @Mapping(target = "offer.from", ignore = true)
    @Mapping(target = "offer.until", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(ProductOfferDto dto, @MappingTarget ProductOffer entity);
}
