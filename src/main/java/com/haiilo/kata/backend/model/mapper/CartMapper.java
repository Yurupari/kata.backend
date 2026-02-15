package com.haiilo.kata.backend.model.mapper;

import com.haiilo.kata.backend.config.CentralMapperConfig;
import com.haiilo.kata.backend.model.dto.CartDto;
import com.haiilo.kata.backend.model.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        config = CentralMapperConfig.class,
        uses = {CartItemMapper.class}
)
public interface CartMapper {

    CartDto toDto(Cart entity);

    @Mapping(target = "items", ignore = true)
    Cart toEntity(CartDto dto);
}
