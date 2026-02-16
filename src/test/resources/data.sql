-- Products
INSERT INTO kata.product (id, name, description, unit_price, currency, status, created_at, updated_at)
VALUES
    (1, 'Green Onion', 'Vegetable', 0.10, 'EUR', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'Lulo', 'Green fruit from Latin-America', 10.00, 'EUR', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Offers
INSERT INTO kata.offer (id, name, discount, currency, discount_type, from_date, until_date, status, created_at, updated_at)
VALUES
    (1, 'Summer Sale', 0.15, 'EUR', 'FIXED_AMOUNT', '2026-01-01 00:00:00', '2026-12-31 23:59:59', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'Summer Sale', 0.25, 'EUR', 'FIXED_AMOUNT', '2026-01-01 00:00:00', '2026-12-31 23:59:59', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Product Offers
INSERT INTO kata.product_offer (id, product_id, offer_id, quantity, status, created_at, updated_at)
VALUES
    (1, 1, 1, 2, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 1, 2, 3, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Cart
INSERT INTO kata.cart (id, cart_status, created_at, updated_at)
VALUES
    (1, 'OPEN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 'PROCESSED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 'PENDING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Cart Items
INSERT INTO kata.cart_item (id, cart_id, product_id, quantity, status, created_at, updated_at)
VALUES
    (1, 1, 1, 2, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 1, 2, 1, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 3, 1, 5, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 3, 2, 1, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Receipt
INSERT INTO kata.receipt (id, cart_id, currency, sub_total, discount, total, transaction_details, created_at)
VALUES (1, 2, 'EUR', 100.00, 10.00, 90.00,
        '[{"productId": 1, "productName": "Milk", "quantity": 2, "unitPrice": 1.50, "subtotal": 3.00, "appliedOffers": []}]',
        CURRENT_TIMESTAMP);
