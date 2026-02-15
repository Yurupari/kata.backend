package com.haiilo.kata.backend.model.mapper;

import com.haiilo.kata.backend.config.CentralMapperConfig;
import com.haiilo.kata.backend.model.dto.ProductDto;
import com.haiilo.kata.backend.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        config = CentralMapperConfig.class,
        uses = {ProductOfferMapper.class}
)
public interface ProductMapper {
    ProductDto toDto(Product entity);

    @Mapping(target = "offers", ignore = true)
    Product toEntity(ProductDto dto);
}
