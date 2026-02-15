package com.haiilo.kata.backend.controller.v1;

import com.haiilo.kata.backend.model.dto.OfferDto;
import com.haiilo.kata.backend.service.OfferService;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/kata/v1/offer")
public class OfferControllerV1 {

    @Autowired
    private OfferService offerService;

    @Operation(summary = "Get offer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the offer"),
            @ApiResponse(responseCode = "404", description = "Offer not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OfferDto> getOffer(@PathVariable("id") Long id) {
        var offerDto = offerService.getOffer(id);

        return ResponseEntity.ok(offerDto);
    }

    @Operation(summary = "Add a new offer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the offer"),
            @ApiResponse(responseCode = "400", description = "Invalid offer data provided")
    })
    @PostMapping
    public ResponseEntity<OfferDto> addOffer(@Valid @RequestBody OfferDto offerDto) {
        var newOfferDto = offerService.addOffer(offerDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newOfferDto.id())
                .toUri();

        return ResponseEntity.created(location).body(newOfferDto);
    }

    @Operation(summary = "Update an existing offer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully updated the offer"),
            @ApiResponse(responseCode = "400", description = "Invalid offer data provided"),
            @ApiResponse(responseCode = "404", description = "Offer not found")
    })
    @PutMapping
    public ResponseEntity<Void> updateOffer(@Valid @RequestBody OfferDto offerDto) {
        offerService.updateOffer(offerDto);

        return ResponseEntity.noContent().build();
    }
}
