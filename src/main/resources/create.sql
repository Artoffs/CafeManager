CREATE TABLE IF NOT EXISTS public."user"
(
    id bigint NOT NULL PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(100),
    createdAt TIMESTAMP DEFAULT NOW(),
);

CREATE TABLE IF NOT EXISTS public.dish
(
    id bigint NOT NULL PRIMARY KEY,
    name VARCHAR(255),
    price numeric,
    description VARCHAR(255),
    "isAvailable" boolean,
)

create table IF NOT EXISTS "order" (
id bigserial primary key,
user_id int,
order_status varchar(255),
createdAt TIMESTAMP default now(),
totalPrice numeric,
foreign key (user_id) references "user"(id)
);