package com.haiilo.kata.backend.model.mapper;

import com.haiilo.kata.backend.config.CentralMapperConfig;
import com.haiilo.kata.backend.model.dto.OfferDto;
import com.haiilo.kata.backend.model.entity.Offer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        config = CentralMapperConfig.class,
        uses = {ProductOfferMapper.class}
)
public interface OfferMapper {
    @Mapping(target = "products", source = "products")
    OfferDto toDto(Offer entity);

    @Mapping(target = "products", ignore = true)
    Offer toEntity(OfferDto dto);
}
