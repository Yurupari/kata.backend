package com.haiilo.kata.backend.model.mapper;

import com.haiilo.kata.backend.config.CentralMapperConfig;
import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.model.entity.Receipt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        config = CentralMapperConfig.class
)
public interface ReceiptMapper {
    @Mapping(target = "cartId", source = "cart.id")
    ReceiptDto toDto(Receipt entity);

    @Mapping(target = "cart", ignore = true)
    Receipt toEntity(ReceiptDto dto);
}
