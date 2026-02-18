package com.haiilo.kata.backend.controller.v1;

import com.haiilo.kata.backend.model.dto.PriceDto;
import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.model.http.request.CheckoutRequest;
import com.haiilo.kata.backend.service.CheckoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kata/v1/checkout")
public class CheckoutControllerV1 {

    @Autowired
    private CheckoutService checkoutService;

    @Operation(summary = "Precalculate the price for the cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully precalculated the price"),
            @ApiResponse(responseCode = "400", description = "Invalid checkout data provided"),
            @ApiResponse(responseCode = "404", description = "Entity not found")
    })
    @GetMapping("/precalculate/{cartId}")
    public ResponseEntity<PriceDto> getPrecalculatedPrice(@PathVariable Long cartId) {
        var price = checkoutService.precalculatePrice(cartId);

        return ResponseEntity.ok(price);
    }

    @Operation(summary = "Execute checkout logic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully executed checkout"),
            @ApiResponse(responseCode = "400", description = "Invalid checkout data provided"),
            @ApiResponse(responseCode = "404", description = "Entity not found")
    })
    @PostMapping
    public ResponseEntity<ReceiptDto> executeCheckout(@Valid @RequestBody CheckoutRequest request) {
        var receiptDto = checkoutService.executeCheckout(request);

        return ResponseEntity.ok(receiptDto);
    }
}
