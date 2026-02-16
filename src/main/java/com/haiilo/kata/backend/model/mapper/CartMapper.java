package com.haiilo.kata.backend.model.mapper;

import com.haiilo.kata.backend.config.CentralMapperConfig;
import com.haiilo.kata.backend.model.dto.CartDto;
import com.haiilo.kata.backend.model.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring",
        config = CentralMapperConfig.class,
        uses = {CartItemMapper.class}
)
public interface CartMapper {

    CartDto toDto(Cart entity);

    @Mapping(target = "items", ignore = true)
    @Mapping(target = "cartStatus", defaultValue = "OPEN")
    Cart toEntity(CartDto dto);

    @Mapping(target = "items", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(CartDto dto, @MappingTarget Cart entity);
}
