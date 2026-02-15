package com.haiilo.kata.backend.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UtilsTest {

    public static String loadRequest(String filePath) throws IOException {
        var resource = new ClassPathResource(filePath);
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
}
