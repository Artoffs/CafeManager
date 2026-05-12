-- Очищаем старые данные (CASCADE удалит зависимые записи в order_item)
TRUNCATE TABLE order_item, orders, dish, cafe_table, users, ingredient, customer CASCADE;

-- 1. Пользователи (Сотрудники)
-- Пароли стоит оставить такими же для тестов, но имена сделаем реальными
INSERT INTO users (id, username, password, role) VALUES
                                                     (1, 'alex_waiter', '12312312', 'WAITER'),
                                                     (2, 'mary_waiter', '12312312', 'WAITER'),
                                                     (3, 'chef_mario',  '12312312', 'COOK'),
                                                     (4, 'admin_boss',  '12312312', 'ADMIN');

-- 2. Столы (Разная вместимость и статусы)
INSERT INTO cafe_table (id, table_number, status) VALUES
                                                      (1, 'Окно: Стол 1', 'OCCUPIED'),
                                                      (2, 'Центр: Стол 2', 'OCCUPIED'),
                                                      (3, 'Терраса: Стол 3', 'FREE'),
                                                      (4, 'VIP-ложа',      'RESERVED'),
                                                      (5, 'Барная стойка', 'FREE');

-- 3. Меню (Разнообразные категории и цены)
INSERT INTO dish (id, name, description, price, category, is_available) VALUES
                                                                            (1, 'Паста Карбонара', 'Классическая паста с беконом и пармезаном', 45.00, 'Горячее', TRUE),
                                                                            (2, 'Том Ям', 'Острый тайский суп с креветками', 55.50, 'Супы', TRUE),
                                                                            (3, 'Цезарь с курицей', 'Салат ромэн, гренки, соус цезарь', 38.00, 'Салаты', TRUE),
                                                                            (4, 'Стейк Рибай', 'Говядина прожарки Medium', 120.00, 'Горячее', TRUE),
                                                                            (5, 'Тирамису', 'Итальянский десерт с маскарпоне', 25.00, 'Десерты', TRUE),
                                                                            (6, 'Капучино', 'Кофе с молочной пеной 300мл', 12.00, 'Напитки', TRUE);

-- 4. Клиенты (Для программы лояльности)
INSERT INTO customer (id, name, phone, bonus_points) VALUES
                                                         (1, 'Иван Иванов', '+375291112233', 150),
                                                         (2, 'Анна Смирнова', '+375334445566', 0);

-- 5. Заказы (Связываем со столами, официантами и клиентами)
INSERT INTO orders (id, table_id, user_id, customer_id, status, created_at) VALUES
                                                                                (1, 1, 1, 1, 'CREATED', NOW() - interval '30 minutes'),
                                                                                (2, 2, 2, 2, 'CREATED', NOW() - interval '15 minutes'),
                                                                                (3, 3, 1, NULL, 'CLOSED',  NOW() - interval '2 hours');

-- 6. Состав заказов (По несколько позиций на один чек)
INSERT INTO order_item (id, order_id, dish_id, quantity, comment) VALUES
-- Заказ №1 (Стол 1)
(1, 1, 1, 1, 'Без лука'),
(2, 1, 6, 2, 'Один с корицей'),
-- Заказ №2 (Стол 2)
(3, 2, 2, 1, 'Очень остро'),
(4, 2, 3, 1, NULL),
-- Заказ №3 (Архивный)
(5, 3, 4, 1, 'Medium Well'),
(6, 3, 5, 1, NULL);

-- 7. Склад (Ингредиенты)
INSERT INTO ingredient (id, name, quantity, unit) VALUES
                                                      (1, 'Спагетти', 10.5, 'кг'),
                                                      (2, 'Бекон', 5.0, 'кг'),
                                                      (3, 'Креветки', 3.2, 'кг'),
                                                      (4, 'Кофе зерно', 2.0, 'кг');