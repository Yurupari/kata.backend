package com.haiilo.kata.backend.controller.v1;

import com.haiilo.kata.backend.model.dto.CartItemDto;
import com.haiilo.kata.backend.service.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kata/v1/cart/item")
public class CartItemControllerV1 {

    @Autowired
    private CartItemService cartItemService;

    @Operation(summary = "Add an item to a cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the cart's item"),
            @ApiResponse(responseCode = "400", description = "Invalid cart's item data provided")
    })
    @PostMapping
    public ResponseEntity<CartItemDto> addCartItem(@Valid @RequestBody CartItemDto cartItemDto) {
        var newCartItemDto = cartItemService.addCartItem(cartItemDto);

        return ResponseEntity.ok(newCartItemDto);
    }

    @Operation(summary = "Update cart's item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully updated the cart's item"),
            @ApiResponse(responseCode = "400", description = "Invalid cart's item data provided"),
            @ApiResponse(responseCode = "404", description = "Cart's item not found")
    })
    @PutMapping
    public ResponseEntity<Void> updateCartItem(@Valid @RequestBody CartItemDto cartItemDto) {
        cartItemService.updateCartItem(cartItemDto);

        return ResponseEntity.noContent().build();
    }
}
