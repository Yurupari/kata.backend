package com.haiilo.kata.backend.controller.v1;

import com.haiilo.kata.backend.model.dto.ProductDto;
import com.haiilo.kata.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/kata/v1/product")
public class ProductControllerV1 {

    @Autowired
    ProductService productService;

    @Operation(summary = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all products")
    })
    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts() {
        var products = productService.getProducts();

        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Get product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the product"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long id) {
        var productDto = productService.getProduct(id);

        return ResponseEntity.ok(productDto);
    }

    @Operation(summary = "Add a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the product"),
            @ApiResponse(responseCode = "400", description = "Invalid product data provided")
    })
    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductDto productDto) {
        var newProductDto = productService.addProduct(productDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newProductDto.id())
                .toUri();

        return ResponseEntity.created(location).body(newProductDto);
    }

    @Operation(summary = "Update an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully updated the product"),
            @ApiResponse(responseCode = "400", description = "Invalid product data provided"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping
    public ResponseEntity<Void> updateProduct(@Valid @RequestBody ProductDto productDto) {
        productService.updateProduct(productDto);

        return ResponseEntity.noContent().build();
    }
}
