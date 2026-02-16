package com.haiilo.kata.backend.model.mapper;

import com.haiilo.kata.backend.config.CentralMapperConfig;
import com.haiilo.kata.backend.model.dto.ProductDto;
import com.haiilo.kata.backend.model.entity.Product;
import com.haiilo.kata.backend.model.http.request.CreateProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring",
        config = CentralMapperConfig.class,
        uses = {ProductOfferMapper.class}
)
public interface ProductMapper {
    @Mapping(target = "offers", ignore = true)
    ProductDto toDto(CreateProductRequest request);

    ProductDto toDto(Product entity);

    @Mapping(target = "offers", ignore = true)
    @Mapping(target = "status", defaultValue = "ACTIVE")
    Product toEntity(ProductDto dto);

    @Mapping(target = "offers", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(ProductDto dto, @MappingTarget Product entity);
}
