package com.haiilo.kata.backend.model.converter;

import com.haiilo.kata.backend.model.enums.DiscountType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DiscountTypeConverter implements AttributeConverter<DiscountType, String> {
    @Override
    public String convertToDatabaseColumn(DiscountType discountType) {
        return discountType == null ? null : discountType.name();
    }

    @Override
    public DiscountType convertToEntityAttribute(String s) {
        return s == null ? null : DiscountType.valueOf(s);
    }
}
