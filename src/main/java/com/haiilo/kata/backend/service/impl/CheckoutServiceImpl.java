package com.haiilo.kata.backend.service.impl;

import com.haiilo.kata.backend.exception.CheckoutProcessException;
import com.haiilo.kata.backend.exception.CheckoutException;
import com.haiilo.kata.backend.model.dto.CartDto;
import com.haiilo.kata.backend.model.dto.CartItemDto;
import com.haiilo.kata.backend.model.dto.PriceDto;
import com.haiilo.kata.backend.model.dto.ProductOfferDto;
import com.haiilo.kata.backend.model.dto.ReceiptDto;
import com.haiilo.kata.backend.model.enums.CartStatus;
import com.haiilo.kata.backend.model.enums.DiscountType;
import com.haiilo.kata.backend.model.enums.Status;
import com.haiilo.kata.backend.model.http.request.CheckoutRequest;
import com.haiilo.kata.backend.model.json.AppliedOffer;
import com.haiilo.kata.backend.model.json.TransactionDetail;
import com.haiilo.kata.backend.service.CartService;
import com.haiilo.kata.backend.service.CheckoutService;
import com.haiilo.kata.backend.service.ProductOfferService;
import com.haiilo.kata.backend.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CheckoutServiceImpl implements CheckoutService {

    private final CartService cartService;
    private final ProductOfferService productOfferService;
    private final ReceiptService receiptService;

    @Override
    public PriceDto precalculatePrice(Long cartId) {
        log.info("Precalculate price: cartId={}", cartId);

        var cartDto = cartService.getCart(cartId);

        var filteredCartItems = cartDto.items().stream()
                .filter(item ->
                        Status.ACTIVE.equals(item.status())
                                && Status.ACTIVE.equals(item.productStatus()))
                .toList();

        var prices = filteredCartItems.stream()
                .map(this::calculateBestProductPrice)
                .toList();

        return sumPrices(prices);
    }

    @Override
    public ReceiptDto executeCheckout(CheckoutRequest checkoutRequest) {
        log.info("Execute checkout: cartId={}", checkoutRequest.cartId());

        var cartDto = cartService.getCart(checkoutRequest.cartId());

        var receiptDto = generateReceipt(cartDto);

        processCart(cartDto);

        return receiptService.addReceipt(receiptDto);
    }

    private void validateCart(CartDto cartDto, List<CartItemDto> filteredCartItems) {
        if (!CartStatus.PENDING.equals(cartDto.cartStatus())) {
            throw new CheckoutProcessException(String.format("With the current status of the cart is not possible to continue: %s", cartDto.cartStatus()));
        }

        if (filteredCartItems.isEmpty()) {
            throw new CheckoutProcessException("Cart is empty or with not active products");
        }
    }

    private ReceiptDto generateReceipt(CartDto cartDto) {
        var validatedCartItems = getValidatedCartItems(cartDto);

        var prices = validatedCartItems.stream()
                .map(this::calculateBestProductPrice)
                .toList();

        var totals = sumPrices(prices);

        var currency = validatedCartItems.getFirst().currency();

        var transactionDetails = generateTransactionDetails(prices, validatedCartItems);

        return new ReceiptDto(
                null,
                cartDto.id(),
                totals.subTotal(),
                totals.discount(),
                totals.total(),
                currency,
                transactionDetails
        );
    }

    private List<CartItemDto> getValidatedCartItems(CartDto cartDto) {
        var cartItems = cartDto.items().stream()
                .filter(item ->
                        Status.ACTIVE.equals(item.status())
                                && Status.ACTIVE.equals(item.productStatus()))
                .toList();

        validateCart(cartDto, cartItems);

        return cartItems;
    }

    private PriceDto sumPrices(List<PriceDto> prices) {
        var subTotal = BigDecimal.ZERO;
        var discount = BigDecimal.ZERO;
        var total = BigDecimal.ZERO;

        for (var price : prices) {
            subTotal = subTotal.add(price.subTotal());
            discount = discount.add(price.discount());
            total = total.add(price.total());
        }

        return new PriceDto(
                null,
                null,
                null,
                subTotal,
                total,
                discount
        );
    }

    private PriceDto calculateBestProductPrice(CartItemDto cartItemDto) {
        int remainingQuantity = cartItemDto.quantity();
        var subTotal = cartItemDto.unitPrice().multiply(BigDecimal.valueOf(cartItemDto.quantity()));
        var totalPrice = BigDecimal.ZERO;

        var nowLocalDateTime = LocalDateTime.now();

        log.info("Getting available offers: productId={}", cartItemDto.productId());
        var availableOfferDtos = productOfferService.getProductOffers(cartItemDto.productId(), null).stream()
                .filter(offer -> {
                    var from = offer.offerDto().fromDate();
                    var until = offer.offerDto().untilDate();

                    return Status.ACTIVE.equals(offer.status())
                            && Status.ACTIVE.equals(offer.offerDto().status())
                            && !nowLocalDateTime.isBefore(from)
                            && !nowLocalDateTime.isAfter(until);
                })
                .sorted(Comparator.comparing(ProductOfferDto::quantity).reversed())
                .toList();

        log.info("Calculating the price: availableOffers={}", availableOfferDtos.size());
        for (var productOfferDto : availableOfferDtos) {
            if (remainingQuantity >= productOfferDto.quantity()) {
                log.info("Offer applicable: discountType={}", productOfferDto.offerDto().discountType());

                int timesToApply = remainingQuantity / productOfferDto.quantity();

                var offerPrice = calculatePrice(
                        productOfferDto.offerDto().discountType(),
                        productOfferDto.offerDto().discount(),
                        cartItemDto.unitPrice(),
                        productOfferDto.quantity(),
                        timesToApply
                );

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

    private BigDecimal calculatePrice(
            DiscountType discountType,
            BigDecimal discountValue,
            BigDecimal unitPrice,
            int quantity,
            int timesToApply
    ) {
        BigDecimal offerPrice = BigDecimal.ZERO;
        switch (discountType) {
            case PERCENTAGE -> {
                BigDecimal discountMultiplier = BigDecimal.ONE.subtract(
                        discountValue.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP)
                );
                var normalPriceForBundle = unitPrice.multiply(BigDecimal.valueOf(quantity));
                offerPrice = normalPriceForBundle.multiply(discountMultiplier);
            }
            case FIXED_AMOUNT -> offerPrice = discountValue.multiply(BigDecimal.valueOf(timesToApply));
        }

        return offerPrice;
    }

    private List<TransactionDetail> generateTransactionDetails(List<PriceDto> prices, List<CartItemDto> cartItems) {
        log.info("Calculating transaction details");
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

            transactionDetails.add(transactionDetail);
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
