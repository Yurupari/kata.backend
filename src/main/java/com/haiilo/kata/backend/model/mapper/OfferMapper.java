package com.haiilo.kata.backend.model.mapper;

import com.haiilo.kata.backend.config.CentralMapperConfig;
import com.haiilo.kata.backend.model.dto.OfferDto;
import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.model.entity.Offer;
import com.haiilo.kata.backend.model.http.request.CreateOfferRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        config = CentralMapperConfig.class,
        uses = {ProductOfferMapper.class}
)
public interface OfferMapper {
    @Mapping(target = "id", ignore = true)
    OfferDto toDto(CreateOfferRequest request);

    OfferDto toDto(Offer entity);

    @Mapping(target = "products", source = "productOfferDtos")
    OfferDto toDto(Offer entity, List<ProductOfferDto> productOfferDtos);

    @Mapping(target = "status", defaultValue = "ACTIVE")
    Offer toEntity(OfferDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(OfferDto dto, @MappingTarget Offer entity);
}
