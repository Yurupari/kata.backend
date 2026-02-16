package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.exception.CartConflictStatusException;
import com.haiilo.kata.backend.model.dto.CartDto;
import com.haiilo.kata.backend.model.dto.CartItemDto;
import com.haiilo.kata.backend.model.dto.PriceDto;
import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.model.enums.CartStatus;
import com.haiilo.kata.backend.model.http.request.CheckoutRequest;
import com.haiilo.kata.backend.service.CartService;
import com.haiilo.kata.backend.service.CheckoutService;
import com.haiilo.kata.backend.service.OfferService;
import com.haiilo.kata.backend.service.ProductOfferService;
import com.haiilo.kata.backend.service.ProductService;
import com.haiilo.kata.backend.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final CartService cartService;
    private final ProductService productService;
    private final ProductOfferService productOfferService;
    private final OfferService offerService;
    private final ReceiptService receiptService;

    @Override
    public ReceiptDto executeCheckout(CheckoutRequest checkoutRequest) {
        var cartDto = cartService.getCart(checkoutRequest.cartId());

        validateCart(cartDto); return null;

        /*var receiptDto = calculateBestProductPrice(cartDto.items());

        processCart(cartDto);

        return receiptService.addReceipt(receiptDto);*/
    }

    private void validateCart(CartDto cartDto) {
        if (!CartStatus.PENDING.equals(cartDto.cartStatus())) {
            throw new CartConflictStatusException(cartDto.cartStatus());
        }
    }

    private ReceiptDto generateReceipt(List<CartItemDto> cartItemDtos) {
        return null;
    }

    /*private PriceDto calculateBestProductPrice(CartItemDto cartItemDto) {
        int remainingQuantity = cartItemDto.quantity();
        double subTotal = cartItemDto.unitPrice() * cartItemDto.quantity();
        double totalPrice = 0.0;

        var availableOfferDtos = productOfferService.getProductOffers(cartItemDto.productId(), null).stream()
                .sorted(Comparator.comparing(ProductOfferDto::quantity).reversed())
                .toList();

        for (var productOfferDto : availableOfferDtos) {
            if (remainingQuantity >= productOfferDto.quantity()) {
                int timesToApply = remainingQuantity / productOfferDto.quantity();

                double offerPrice = cartItemDto.unitPrice() * timesToApply;

                totalPrice += offerPrice;

                remainingQuantity %= productOfferDto.quantity();
            }
        }

        if (remainingQuantity > 0) {
            double normalPrice = cartItemDto.unitPrice() * remainingQuantity;
            totalPrice += normalPrice;
        }

        double discount = subTotal - totalPrice;

        return new PriceDto(
                cartItemDto.unitPrice(),
                subTotal,
                totalPrice,
                discount
        );
    }*/

    private void processCart(CartDto cartDto) {
        var updatedCartDto = new CartDto(
                cartDto.id(),
                CartStatus.PROCESSED,
                cartDto.items(),
                cartDto.total(),
                cartDto.currency()
        );

        cartService.updateCart(updatedCartDto);
    }
}
