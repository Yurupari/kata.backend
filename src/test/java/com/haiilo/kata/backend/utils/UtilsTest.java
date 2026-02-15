package com.haiilo.kata.backend.utils;

import org.springframework.core.io.ClassPathResource;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class UtilsTest {

    public static String loadRequest(String filePath) throws IOException {
        var resource = new ClassPathResource(filePath);
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

    public static <T> T loadObject(String resourcePath, Class<T> targetClass) throws IOException {
        var resource = new ClassPathResource(resourcePath);
        return new ObjectMapper().readValue(resource.getInputStream(), targetClass);
    }

    public static <T> List<T> loadListObjects(String resourcePath, Class<T> elementClass) throws IOException {
        var resource = new ClassPathResource(resourcePath);
        var objectMapper = new ObjectMapper();

        var listType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, elementClass);

        return objectMapper.readValue(resource.getInputStream(), listType);
    }
}
