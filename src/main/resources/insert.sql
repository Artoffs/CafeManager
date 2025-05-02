TRUNCATE TABLE users;
TRUNCATE TABLE cafe_table;
TRUNCATE TABLE orders;
TRUNCATE TABLE dish;
TRUNCATE TABLE order_item;


INSERT INTO public.users(
	id, username, password, role)
	VALUES
	 (1, 'Username1', '12312312', 'WAITER'),
	 (2, 'Username2', '12312312', 'COOK'),
	 (3, 'Username3', '12312312', 'COOK'),
	 (4, 'Username4', '12312312', 'ADMIN');

INSERT INTO public.cafe_table(
	id, table_number, status)
	VALUES
	(1, 'Стол 1', 'FREE'),
	(2, 'Стол 2', 'FREE'),
	(3, 'Стол 3', 'RESERVED'),
	(4, 'Барная стойка', 'FREE');

INSERT INTO public.orders(
	id, table_id, user_id, status)
	VALUES
	(1, 1, 1, 'CREATED'),
	(2, 2, 1, 'CREATED'),
	(3, 3, 3, 'CREATED'),
	(4, 4, 4, 'CREATED');

INSERT INTO public.dish(
	id, name, description, price, category, is_available)
	VALUES
	(1, 'Блюдо 1', 'Какое-то типичное блюдо', '10.0', 'Горячее', TRUE),
	(2, 'Блюдо 2', 'Какое-то типичное блюдо', '12.0', 'Горячее', TRUE),
	(3, 'Блюдо 3', 'Какое-то типичное блюдо', '13.0', 'Горячее', FALSE),
	(4, 'Блюдо 4', 'Какое-то типичное блюдо', '14.0', 'Салат', FALSE);

INSERT INTO public.order_item(
	id, order_id, dish_id, quantity, comment)
	VALUES
	 (1, 1, 1, 1, 'Какой-то комментарий'),
	 (2, 2, 2, 2, 'Какой-то комментарий'),
	 (3, 3, 3, 3, 'Какой-то комментарий'),
	 (4, 4, 4, 4, 'Какой-то комментарий');