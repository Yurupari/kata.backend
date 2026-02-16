package com.haiilo.kata.backend.model.converter;

import com.haiilo.kata.backend.model.enums.DiscountType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class DiscountTypeConverterTest {

    private DiscountTypeConverter discountTypeConverter;

    @BeforeEach
    void setup() {
        discountTypeConverter = new DiscountTypeConverter();
    }

    @Test
    void convertToDatabaseColumn_Success() {
        var response = discountTypeConverter.convertToDatabaseColumn(DiscountType.PERCENTAGE);

        assertNotNull(response);
        assertEquals("PERCENTAGE", response);
    }

    @Test
    void convertToDatabaseColumn_Null() {
        var response = discountTypeConverter.convertToDatabaseColumn(null);

        assertNull(response);
    }

    @Test
    void convertToEntityAttribute_Success() {
        var response = discountTypeConverter.convertToEntityAttribute("PERCENTAGE");

        assertNotNull(response);
        assertEquals(DiscountType.PERCENTAGE, response);
    }

    @Test
    void convertToEntityAttribute_Null() {
        var response = discountTypeConverter.convertToEntityAttribute(null);

        assertNull(response);
    }
}