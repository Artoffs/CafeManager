-- Очистка старых таблиц (порядок важен из-за внешних ключей)
DROP TABLE IF EXISTS feedback CASCADE;
DROP TABLE IF EXISTS dish_ingredient CASCADE;
DROP TABLE IF EXISTS ingredient CASCADE;
DROP TABLE IF EXISTS order_item CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS cafe_table CASCADE;
DROP TABLE IF EXISTS dish CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS customer CASCADE;

--- 1. Пользователи системы (Сотрудники)
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(100) NOT NULL,
                       role VARCHAR(20) NOT NULL -- ROLE_ADMIN, ROLE_WAITER, etc.
);

--- 2. Меню (Блюда)
CREATE TABLE dish (
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      description TEXT,
                      price DECIMAL(10, 2) NOT NULL,
                      category VARCHAR(50) NOT NULL, -- оставляем строкой для твоего удобства
                      is_available BOOLEAN DEFAULT TRUE,
                      is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

--- 3. Склад (Ингредиенты)
CREATE TABLE ingredient (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL UNIQUE,
                            quantity DECIMAL(10, 3) DEFAULT 0,
                            unit VARCHAR(10) NOT NULL -- кг, л, шт
);

--- 4. Рецепты (Связь Блюдо - Ингредиент)
CREATE TABLE dish_ingredient (
                                 dish_id BIGINT REFERENCES dish(id) ON DELETE CASCADE,
                                 ingredient_id BIGINT REFERENCES ingredient(id) ON DELETE CASCADE,
                                 amount DECIMAL(10, 3) NOT NULL,
                                 PRIMARY KEY (dish_id, ingredient_id)
);

--- 5. Столы в зале
CREATE TABLE cafe_table (
                            id BIGSERIAL PRIMARY KEY,
                            table_number VARCHAR(20) NOT NULL UNIQUE,
                            status VARCHAR(20) DEFAULT 'FREE' -- FREE, OCCUPIED, RESERVED
);

--- 6. Программа лояльности (Клиенты)
CREATE TABLE customer (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          phone VARCHAR(20) UNIQUE,
                          bonus_points INT DEFAULT 0
);

--- 7. Заказы
CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        table_id BIGINT REFERENCES cafe_table(id) ON DELETE CASCADE,
                        user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
                        customer_id BIGINT REFERENCES customer(id) ON DELETE SET NULL, -- связь с клиентом
                        status VARCHAR(20) DEFAULT 'CREATED', -- CREATED, CLOSED, CANCELLED
                        created_at TIMESTAMP DEFAULT NOW(),
                        closed_at TIMESTAMP
);

--- 8. Позиции заказа (Состав чека)
CREATE TABLE order_item (
                            id BIGSERIAL PRIMARY KEY,
                            order_id BIGINT REFERENCES orders(id) ON DELETE CASCADE,
                            dish_id BIGINT REFERENCES dish(id),
                            quantity INT NOT NULL DEFAULT 1,
                            comment TEXT
);

--- 9. Отзывы (Для крутости курсовой)
CREATE TABLE feedback (
                          id BIGSERIAL PRIMARY KEY,
                          order_id BIGINT REFERENCES orders(id) ON DELETE CASCADE,
                          rating INT CHECK (rating >= 1 AND rating <= 5),
                          comment TEXT,
                          created_at TIMESTAMP DEFAULT NOW()
);

-- ИНДЕКСЫ (Чтобы показать преподавателю, что думали о скорости)
CREATE INDEX idx_dish_category ON dish(category);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_order_item_order ON order_item(order_id);
