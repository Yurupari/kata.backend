package com.haiilo.kata.backend.model.mapper;

import com.haiilo.kata.backend.config.CentralMapperConfig;
import com.haiilo.kata.backend.model.dto.CartItemDto;
import com.haiilo.kata.backend.model.entity.CartItem;
import com.haiilo.kata.backend.model.http.request.CreateCartItemRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        config = CentralMapperConfig.class
)
public interface CartItemMapper {

    CartItemDto toDto(CreateCartItemRequest request);

    @Mapping(target = "cartId", source = "cart.id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "unitPrice", source = "product.unitPrice")
    @Mapping(target = "currency", source = "product.currency")
    CartItemDto toDto(CartItem entity);

    @Mapping(target = "cart.id", source = "cartId")
    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "status", defaultValue = "ACTIVE")
    CartItem toEntity(CartItemDto dto);

    @Mapping(target = "cart.id", ignore = true)
    @Mapping(target = "product.id", ignore = true)
    @Mapping(target = "product.name", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(CartItemDto dto, @MappingTarget CartItem entity);
}
