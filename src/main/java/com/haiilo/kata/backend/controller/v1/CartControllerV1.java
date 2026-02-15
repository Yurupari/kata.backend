package com.haiilo.kata.backend.controller.v1;

import com.haiilo.kata.backend.model.dto.CartDto;
import com.haiilo.kata.backend.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/kata/v1/cart")
public class CartControllerV1 {

    @Autowired
    private CartService cartService;

    @Operation(summary = "Get current active cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the current cart"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @GetMapping("/current")
    public ResponseEntity<CartDto> getCurrentCart() {
        var cartDto = cartService.getCurrentCart();

        return ResponseEntity.ok(cartDto);
    }

    @Operation(summary = "Get the cart by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the cart"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCart(@PathVariable("id") Long id) {
        var cartDto = cartService.getCart(id);

        return ResponseEntity.ok(cartDto);
    }

    @Operation(summary = "Update the cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully updated the cart"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @PutMapping
    public ResponseEntity<Void> updateCart(@Valid @RequestBody CartDto cartDto) {
        cartService.updateCart(cartDto);

        return ResponseEntity.noContent().build();
    }
}
