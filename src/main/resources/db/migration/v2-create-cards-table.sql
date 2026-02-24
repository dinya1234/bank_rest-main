-- liquibase formatted sql

-- changeset author:2
CREATE TABLE IF NOT EXISTS cards (
                                     id BIGSERIAL PRIMARY KEY,
                                     card_number VARCHAR(255) NOT NULL UNIQUE,
    owner_name VARCHAR(255) NOT NULL,
    expiry_date VARCHAR(7) NOT NULL,
    status VARCHAR(20) NOT NULL,
    balance DECIMAL(10,2) DEFAULT 0.00 NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_cards_users FOREIGN KEY (user_id) REFERENCES users(id)
    );