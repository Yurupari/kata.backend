package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.exception.CartConflictStatusException;
import com.haiilo.kata.backend.exception.CheckoutException;
import com.haiilo.kata.backend.model.dto.CartDto;
import com.haiilo.kata.backend.model.dto.CartItemDto;
import com.haiilo.kata.backend.model.dto.PriceDto;
import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.model.enums.CartStatus;
import com.haiilo.kata.backend.model.enums.Status;
import com.haiilo.kata.backend.model.http.request.CheckoutRequest;
import com.haiilo.kata.backend.model.json.AppliedOffer;
import com.haiilo.kata.backend.model.json.TransactionDetail;
import com.haiilo.kata.backend.service.CartService;
import com.haiilo.kata.backend.service.CheckoutService;
import com.haiilo.kata.backend.service.ProductOfferService;
import com.haiilo.kata.backend.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final CartService cartService;
    private final ProductOfferService productOfferService;
    private final ReceiptService receiptService;

    @Override
    public ReceiptDto executeCheckout(CheckoutRequest checkoutRequest) {
        var cartDto = cartService.getCart(checkoutRequest.cartId());

        validateCart(cartDto);

        var receiptDto = generateReceipt(cartDto);

        processCart(cartDto);

        return receiptService.addReceipt(receiptDto);
    }

    private void validateCart(CartDto cartDto) {
        if (!CartStatus.PENDING.equals(cartDto.cartStatus())) {
            throw new CartConflictStatusException(cartDto.cartStatus());
        }
    }

    private ReceiptDto generateReceipt(CartDto cartDto) {
        var cartItems = cartDto.items().stream()
                .filter(item -> Status.ACTIVE.equals(item.status()))
                .toList();

        var prices = cartItems.stream()
                .map(this::calculateBestProductPrice)
                .toList();

        var subTotal = BigDecimal.ZERO;
        var discount = BigDecimal.ZERO;
        var total = BigDecimal.ZERO;

        for (var price : prices) {
            subTotal = subTotal.add(price.subTotal());
            discount = discount.add(price.discount());
            total = total.add(price.total());
        }

        var transactionDetails = generateTransactionDetails(prices, cartItems);

        return new ReceiptDto(
                null,
                cartDto.id(),
                subTotal,
                discount,
                total,
                cartDto.currency(),
                transactionDetails
        );
    }

    private PriceDto calculateBestProductPrice(CartItemDto cartItemDto) {
        int remainingQuantity = cartItemDto.quantity();
        var subTotal = cartItemDto.unitPrice().multiply(BigDecimal.valueOf(cartItemDto.quantity()));
        var totalPrice = BigDecimal.ZERO;

        var availableOfferDtos = productOfferService.getProductOffers(cartItemDto.productId(), null).stream()
                .filter(offer -> Status.ACTIVE.equals(offer.status()))
                .sorted(Comparator.comparing(ProductOfferDto::quantity).reversed())
                .toList();

        for (var productOfferDto : availableOfferDtos) {
            if (remainingQuantity >= productOfferDto.quantity()) {
                int timesToApply = remainingQuantity / productOfferDto.quantity();

                var offerPrice = cartItemDto.unitPrice().multiply(BigDecimal.valueOf(timesToApply));

                totalPrice = totalPrice.add(offerPrice);

                remainingQuantity %= productOfferDto.quantity();
            }
        }

        if (remainingQuantity > 0) {
            var normalPrice = cartItemDto.unitPrice().multiply(BigDecimal.valueOf(remainingQuantity));
            totalPrice = totalPrice.add(normalPrice);
        }

        var discount = subTotal.subtract(totalPrice);

        return new PriceDto(
                cartItemDto.productId(),
                availableOfferDtos,
                cartItemDto.unitPrice(),
                subTotal,
                totalPrice,
                discount
        );
    }

    private List<TransactionDetail> generateTransactionDetails(List<PriceDto> prices, List<CartItemDto> cartItems) {
        List<TransactionDetail> transactionDetails = new ArrayList<>();

        for (var cartItem : cartItems) {
            var price = prices.stream()
                    .filter(p -> p.productId().equals(cartItem.productId()))
                    .findFirst()
                    .orElseThrow(() -> new CheckoutException(String.format(
                            "Product not found during the checkout calculation: productId=%s",
                            cartItem.productId())));

            var transactionDetail = new TransactionDetail(
                    cartItem.productId(),
                    cartItem.productName(),
                    cartItem.quantity(),
                    cartItem.unitPrice(),
                    generateAppliedOffers(price),
                    price.subTotal()
            );

            transactionDetails.add(transactionDetail
            );
        }

        return transactionDetails;
    }

    private List<AppliedOffer> generateAppliedOffers(PriceDto price) {
        return price.offers().stream()
                .map(offer -> new AppliedOffer(
                        offer.offerDto().name(),
                        offer.offerDto().discount(),
                        offer.offerDto().discountType()
                        ))
                .toList();
    }

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
