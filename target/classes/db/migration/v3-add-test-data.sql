-- liquibase formatted sql

-- changeset author:3
-- Добавляем тестового админа (пароль: admin123)
INSERT INTO users (username, password, role)
VALUES ('admin', '$2a$10$XURPShQNCsLjp1ESc2laoObo9QZQE8TpOZR2W5XU1uJtS3nqYpYyS', 'ADMIN')
ON CONFLICT (username) DO NOTHING;

-- Добавляем тестового пользователя (пароль: user123)
INSERT INTO users (username, password, role)
VALUES ('user', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTTyU4T6GXq', 'USER')
ON CONFLICT (username) DO NOTHING;