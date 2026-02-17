package com.haiilo.kata.backend.model.converter;

import com.haiilo.kata.backend.model.enums.CartStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CartStatusConverterTest {

    private CartStatusConverter cartStatusConverter;

    @BeforeEach
    void setup() {
        cartStatusConverter = new CartStatusConverter();
    }

    @Test
    void convertToDatabaseColumn_Success() {
        var response = cartStatusConverter.convertToDatabaseColumn(CartStatus.OPEN);

        assertNotNull(response);
        assertEquals("OPEN", response);
    }

    @Test
    void convertToDatabaseColumn_Null() {
        var response = cartStatusConverter.convertToDatabaseColumn(null);

        assertNull(response);
    }

    @Test
    void convertToEntityAttribute_Success() {
        var response = cartStatusConverter.convertToEntityAttribute("OPEN");

        assertNotNull(response);
        assertEquals(CartStatus.OPEN, response);
    }

    @Test
    void convertToEntityAttribute_Null() {
        var response = cartStatusConverter.convertToEntityAttribute(null);

        assertNull(response);
    }
}