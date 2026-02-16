CREATE SCHEMA IF NOT EXISTS kata;
SET SCHEMA kata;

-- Product
CREATE TABLE IF NOT EXISTS product (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    unit_price DECIMAL(19,4),
    currency VARCHAR(10) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Offer
CREATE TABLE IF NOT EXISTS offer (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    discount DECIMAL(19,4) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    discount_type VARCHAR(50) NOT NULL,
    from_date TIMESTAMP NOT NULL,
    until_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Product_Offer (Join Table)
CREATE TABLE IF NOT EXISTS product_offer (
    id BIGINT PRIMARY KEY,
    product_id BIGINT REFERENCES product(id),
    offer_id BIGINT REFERENCES offer(id),
    quantity INTEGER NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Cart
CREATE TABLE IF NOT EXISTS cart (
    id BIGINT PRIMARY KEY,
    cart_status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Cart_Item
CREATE TABLE IF NOT EXISTS cart_item (
    id BIGINT PRIMARY KEY,
    cart_id BIGINT REFERENCES cart(id),
    product_id BIGINT REFERENCES product(id),
    quantity INTEGER NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Receipt
CREATE TABLE IF NOT EXISTS receipt (
    id BIGINT PRIMARY KEY,
    sub_total DECIMAL(19,4),
    discount DECIMAL(19,4),
    total DECIMAL(19,4),
    currency VARCHAR(10),
    transaction_details JSON, -- H2 supports JSON type
    created_at TIMESTAMP NOT NULL,
    cart_id BIGINT UNIQUE REFERENCES cart(id)
);

-- Add these to the end of your schema.sql
CREATE SEQUENCE IF NOT EXISTS cart_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS product_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS offer_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS product_offer_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS cart_item_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS receipt_seq START WITH 1 INCREMENT BY 1;

-- Sequence (Hibernate AUTO default)
CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;