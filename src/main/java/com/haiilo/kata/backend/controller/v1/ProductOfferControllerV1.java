package com.haiilo.kata.backend.controller.v1;

import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.model.http.request.CreateProductOffersRequest;
import com.haiilo.kata.backend.model.http.request.ProductSelectionRequest;
import com.haiilo.kata.backend.model.http.request.UpdateProductOffersRequest;
import com.haiilo.kata.backend.model.mapper.ProductOfferMapper;
import com.haiilo.kata.backend.service.ProductOfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/kata/v1/product/offer")
public class ProductOfferControllerV1 {

    @Autowired
    private ProductOfferService productOfferService;

    @Operation(summary = "Get product's offers by offer ID or product ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the product's offers"),
            @ApiResponse(responseCode = "400", description = "Invalid product ID or offer ID provided")
    })
    @GetMapping
    public ResponseEntity<List<ProductOfferDto>> getProductOffers(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Long offerId
    ) {
        var productOfferDtos = productOfferService.getProductOffers(productId, offerId);

        return ResponseEntity.ok(productOfferDtos);
    }


    
    @Operation(summary = "Add product's offer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the product's offer"),
            @ApiResponse(responseCode = "400", description = "Invalid product's offer data provided")
    })
    @PostMapping
    public ResponseEntity<ProductOfferDto> addProductOffer(@Valid @RequestBody ProductSelectionRequest productSelectionRequest) {
        var newProductOfferDto = productOfferService.addProductOffer(productSelectionRequest);
        
        return ResponseEntity.ok(newProductOfferDto);
    }

    @Operation(summary = "Add several product's offers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the product's offers"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided")
    })
    @PostMapping("/multiple")
    public ResponseEntity<List<ProductOfferDto>> addProductOffers(@Valid @RequestBody CreateProductOffersRequest createProductOffersRequest) {
        var productOfferDtos = productOfferService.addProductOffers(createProductOffersRequest);

        return ResponseEntity.ok(productOfferDtos);
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

    @Operation(summary = "Update several product's offers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully updated the product's offers"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided")
    })
    @PutMapping("/multiple")
    public ResponseEntity<Void> updateProductOffers(@Valid @RequestBody UpdateProductOffersRequest updateProductOffersRequest) {
        productOfferService.updateProductOffers(updateProductOffersRequest);

        return ResponseEntity.noContent().build();
    }
}
