package com.haiilo.kata.backend.error;

import com.haiilo.kata.backend.exception.CartConflictStatusException;
import com.haiilo.kata.backend.exception.CartItemNotFoundException;
import com.haiilo.kata.backend.exception.CartNotFoundException;
import com.haiilo.kata.backend.exception.OfferNotFoundException;
import com.haiilo.kata.backend.exception.ProductNotFoundException;
import com.haiilo.kata.backend.exception.ProductOfferNotFoundException;
import com.haiilo.kata.backend.exception.ReceiptNotFoundException;
import com.haiilo.kata.backend.exception.ValidationException;
import com.haiilo.kata.backend.model.enums.CartStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ErrorHandlerTest {

    @Autowired
    private ErrorHandler errorHandler;

    @Test
    void handleProductNotFoundException() {
        var response = errorHandler.handleProductNotFoundException(new ProductNotFoundException(1L));

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void handleCartNotFoundException() {
        var response = errorHandler.handleCartNotFoundException(new CartNotFoundException(1L));

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void handleCartItemNotFoundException() {
        var response = errorHandler.handleCartItemNotFoundException(new CartItemNotFoundException(1L));

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void handleOfferNotFoundException() {
        var response = errorHandler.handleOfferNotFoundException(new OfferNotFoundException(1L));

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void handleProductOfferNotFoundException() {
        var response = errorHandler.handleProductOfferNotFoundException(new ProductOfferNotFoundException(1L));

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void handleReceiptNotFoundException() {
        var response = errorHandler.handleReceiptNotFoundException(new ReceiptNotFoundException(1L, 1L));

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void handleValidationException() {
        var response = errorHandler.handleValidationException(new ValidationException("Invalid parameters"));

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void handleIllegalArgumentException() {
        var response = errorHandler.handleIllegalArgumentException(new IllegalArgumentException("Invalid parameters"));

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void handleCartConflictStatusException() {
        var response = errorHandler.handleCartConflictStatusException(new CartConflictStatusException(CartStatus.OPEN));

        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void handleException() {
        var response = errorHandler.handleException(new Exception("Something went wrong"));

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}