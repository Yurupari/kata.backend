package com.haiilo.kata.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haiilo.kata.backend.config.JacksonConfig;
import com.haiilo.kata.backend.utils.JsonTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class BaseUnitTest {

    protected JsonTestUtils jsonTestUtils;

    @BeforeEach
    protected void setUpBase() {
        JacksonConfig config = new JacksonConfig();
        ObjectMapper objectMapper = config.objectMapper();

        this.jsonTestUtils = new JsonTestUtils(objectMapper);
    }
}
