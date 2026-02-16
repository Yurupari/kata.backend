package com.haiilo.kata.backend.model.converter;

import com.haiilo.kata.backend.model.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class StatusConverterTest {

    private StatusConverter statusConverter;

    @BeforeEach
    void setup() {
        statusConverter = new StatusConverter();
    }

    @Test
    void convertToDatabaseColumn_Success() {
        var response = statusConverter.convertToDatabaseColumn(Status.ACTIVE);

        assertNotNull(response);
        assertEquals("ACTIVE", response);
    }

    @Test
    void convertToDatabaseColumn_Null() {
        var response = statusConverter.convertToDatabaseColumn(null);

        assertNull(response);
    }

    @Test
    void convertToEntityAttribute_Success() {
        var response = statusConverter.convertToEntityAttribute("ACTIVE");

        assertNotNull(response);
        assertEquals(Status.ACTIVE, response);
    }

    @Test
    void convertToEntityAttribute_Null() {
        var response = statusConverter.convertToEntityAttribute(null);

        assertNull(response);
    }
}