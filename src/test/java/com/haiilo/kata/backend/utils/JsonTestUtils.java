package com.haiilo.kata.backend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JsonTestUtils {

    private final ObjectMapper objectMapper;

    public JsonTestUtils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String loadRequest(String filePath) throws IOException {
        var resource = new ClassPathResource(filePath);
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

    public <T> T loadObject(String resourcePath, Class<T> targetClass) throws IOException {
        var resource = new ClassPathResource(resourcePath);
        return objectMapper.readValue(resource.getInputStream(), targetClass);
    }

    public <T> List<T> loadListObjects(String resourcePath, Class<T> elementClass) throws IOException {
        var resource = new ClassPathResource(resourcePath);
        var listType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, elementClass);

        return objectMapper.readValue(resource.getInputStream(), listType);
    }
}
