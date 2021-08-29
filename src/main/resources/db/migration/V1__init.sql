create table dishes
(
    id    bigint not null generated by default as identity ,
    name  character varying(255) unique ,
    price double precision,
    primary key (id)
);

CREATE TABLE drinks
(
    id    bigint NOT NULL generated by default as identity ,
    name  character varying(255),
    price double precision,
    primary key (id)
);

CREATE TABLE ingredients
(
    id   bigint NOT NULL generated by default as identity,
    name character varying(255) unique ,
    primary key (id)
);

CREATE TABLE users
(
    id         bigint NOT NULL generated by default as identity ,
    email      character varying(255) unique ,
    first_name character varying(255),
    last_name  character varying(255),
    password   character varying(255),
    username   character varying(255),
    primary key (id)
);

CREATE TABLE orders
(
    id     bigint NOT NULL generated by default as identity ,
    status integer,
    user_id bigint references users(id),
    price double precision,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    primary key (id)
);

create table dish_ingredients
(
    dish_id       bigint not null references dishes(id),
    ingredient_id bigint not null references ingredients(id)
);

create table dish_orders
(
    id      bigint not null generated by default as identity ,
    amount  bigint,
    dish_id bigint references dishes(id),
    primary key (id)
);



create table drink_orders
(
    id       bigint not null generated by default as identity,
    amount   bigint,
    drink_id bigint references drinks(id),
    primary key (id)
);


CREATE TABLE excluded_dish_ingredients
(
    dish_order_id bigint NOT NULL references dish_orders(id),
    ingredient_id bigint NOT NULL references ingredients(id)
);


CREATE TABLE orders_dishes
(
    order_id  bigint NOT NULL references orders(id),
    dishes_id bigint NOT NULL unique references dish_orders(id)
);

CREATE TABLE orders_drinks
(
    order_id  bigint NOT NULL references orders(id),
    drinks_id bigint NOT NULL unique references drink_orders(id)
);

CREATE TABLE roles
(
    id         bigint NOT NULL generated by default as identity ,
    name       character varying(255),
    primary key (id)
);


CREATE TABLE user_roles
(
    user_id bigint NOT NULL references users(id),
    role_id bigint NOT NULL references roles(id)
);

COPY roles (id, name) FROM stdin;
1	ROLE_USER
2	ROLE_ADMIN
3	ROLE_COOK
\.

COPY users (id, email, first_name, last_name, password, username) FROM stdin;
1	test@email.com	\N	\N	$2a$10$3jHQnfbFomLLndXbfbyq3.Jim/teSU9.E1fRp/rl5XEmPK.lETn0e	k4rnaj1k
\.

COPY user_roles (user_id, role_id) FROM stdin;
1	1
1	2
1	3
\.

