package com.haiilo.kata.backend.controller.v1;

import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.service.ReceiptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kata/v1/receipt")
public class ReceiptControllerV1 {

    @Autowired
    private ReceiptService receiptService;

    @Operation(summary = "Get receipt by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the receipt"),
            @ApiResponse(responseCode = "400", description = "ID and cartId are empty"),
            @ApiResponse(responseCode = "404", description = "Receipt not found")
    })
    @GetMapping
    public ResponseEntity<ReceiptDto> getReceipt(
            @RequestParam Long id,
            @RequestParam Long cartId
    ) {
        var receiptDto = receiptService.getReceipt(id, cartId);

        return ResponseEntity.ok(receiptDto);
    }
}
