package com.haiilo.kata.backend.controller.v1;

import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.service.ProductOfferService;
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
@RequestMapping("/api/kata/v1/product/offer")
public class ProductOfferControllerV1 {

    @Autowired
    private ProductOfferService productOfferService;
    
    @Operation(summary = "Add product's offer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the product's offer"),
            @ApiResponse(responseCode = "400", description = "Invalid product's offer data provided")
    })
    @PostMapping
    public ResponseEntity<ProductOfferDto> addProductOffer(@Valid @RequestBody ProductOfferDto productOfferDto) {
        var newProductOfferDto = productOfferService.addProductOffer(productOfferDto);
        
        return ResponseEntity.ok(newProductOfferDto);
    }

    @Operation(summary = "Update product's offer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully updated the product's offer"),
            @ApiResponse(responseCode = "400", description = "Invalid product's offer data provided"),
            @ApiResponse(responseCode = "404", description = "Product's offer not found")
    })
    @PutMapping
    public ResponseEntity<Void> updateProductOffer(@Valid @RequestBody ProductOfferDto productOfferDto) {
        productOfferService.updateProductOffer(productOfferDto);

        return ResponseEntity.noContent().build();
    }
}
