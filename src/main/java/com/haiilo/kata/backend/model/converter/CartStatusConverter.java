package com.haiilo.kata.backend.model.converter;

import com.haiilo.kata.backend.model.enums.CartStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CartStatusConverter implements AttributeConverter<CartStatus, String> {
    @Override
    public String convertToDatabaseColumn(CartStatus cartStatus) {
        return cartStatus == null ? null : cartStatus.name();
    }

    @Override
    public CartStatus convertToEntityAttribute(String s) {
        return s == null ? null : CartStatus.valueOf(s);
    }
}
