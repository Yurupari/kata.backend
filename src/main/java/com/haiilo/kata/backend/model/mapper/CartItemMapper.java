package com.haiilo.kata.backend.model.mapper;

import com.haiilo.kata.backend.config.CentralMapperConfig;
import com.haiilo.kata.backend.model.dto.CartItemDto;
import com.haiilo.kata.backend.model.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        config = CentralMapperConfig.class
)
public interface CartItemMapper {
    @Mapping(target = "cartId", source = "cart.id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    CartItemDto toDto(CartItem entity);

    @Mapping(target = "cart.id", source = "cartId")
    @Mapping(target = "product.id", source = "productId")
    CartItem toEntity(CartItemDto dto);
}
