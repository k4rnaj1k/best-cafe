--
-- PostgreSQL database dump
--

-- Dumped from database version 13.3
-- Dumped by pg_dump version 13.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: dish_ingredients; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.dish_ingredients (
    dish_id bigint NOT NULL,
    ingredient_id bigint NOT NULL
);


ALTER TABLE public.dish_ingredients OWNER TO test;

--
-- Name: dish_orders; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.dish_orders (
    id bigint NOT NULL,
    amount bigint,
    dish_id bigint
);


ALTER TABLE public.dish_orders OWNER TO test;

--
-- Name: dish_orders_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

ALTER TABLE public.dish_orders ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.dish_orders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: dishes; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.dishes (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.dishes OWNER TO test;

--
-- Name: dishes_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

ALTER TABLE public.dishes ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.dishes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: drink_orders; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.drink_orders (
    id bigint NOT NULL,
    amount bigint,
    drink_id bigint
);


ALTER TABLE public.drink_orders OWNER TO test;

--
-- Name: drink_orders_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

ALTER TABLE public.drink_orders ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.drink_orders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: drinks; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.drinks (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.drinks OWNER TO test;

--
-- Name: drinks_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

ALTER TABLE public.drinks ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.drinks_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: excluded_dish_ingredients; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.excluded_dish_ingredients (
    dish_order_id bigint NOT NULL,
    ingredient_id bigint NOT NULL
);


ALTER TABLE public.excluded_dish_ingredients OWNER TO test;

--
-- Name: ingredients; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.ingredients (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.ingredients OWNER TO test;

--
-- Name: ingredients_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

ALTER TABLE public.ingredients ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.ingredients_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: orders; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.orders (
    id bigint NOT NULL,
    status integer
);


ALTER TABLE public.orders OWNER TO test;

--
-- Name: orders_dishes; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.orders_dishes (
    order_id bigint NOT NULL,
    dishes_id bigint NOT NULL
);


ALTER TABLE public.orders_dishes OWNER TO test;

--
-- Name: orders_drinks; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.orders_drinks (
    order_id bigint NOT NULL,
    drinks_id bigint NOT NULL
);


ALTER TABLE public.orders_drinks OWNER TO test;

--
-- Name: orders_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

ALTER TABLE public.orders ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.orders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: roles; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.roles (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    status character varying(255),
    updated_at timestamp without time zone,
    name character varying(255)
);


ALTER TABLE public.roles OWNER TO test;

--
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

ALTER TABLE public.roles ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: user_roles; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.user_roles (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE public.user_roles OWNER TO test;

--
-- Name: users; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    created_at timestamp without time zone,
    status character varying(255),
    updated_at timestamp without time zone,
    email character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    password character varying(255),
    username character varying(255)
);


ALTER TABLE public.users OWNER TO test;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

ALTER TABLE public.users ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: dish_ingredients; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.dish_ingredients (dish_id, ingredient_id) FROM stdin;
\.


--
-- Data for Name: dish_orders; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.dish_orders (id, amount, dish_id) FROM stdin;
\.


--
-- Data for Name: dishes; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.dishes (id, name) FROM stdin;
\.


--
-- Data for Name: drink_orders; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.drink_orders (id, amount, drink_id) FROM stdin;
\.


--
-- Data for Name: drinks; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.drinks (id, name) FROM stdin;
\.


--
-- Data for Name: excluded_dish_ingredients; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.excluded_dish_ingredients (dish_order_id, ingredient_id) FROM stdin;
\.


--
-- Data for Name: ingredients; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.ingredients (id, name) FROM stdin;
\.


--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.orders (id, status) FROM stdin;
\.


--
-- Data for Name: orders_dishes; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.orders_dishes (order_id, dishes_id) FROM stdin;
\.


--
-- Data for Name: orders_drinks; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.orders_drinks (order_id, drinks_id) FROM stdin;
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.roles (id, created_at, status, updated_at, name) FROM stdin;
1	2021-08-28 20:43:35	ACTIVE	2021-08-28 20:43:41	ROLE_USER
2	2021-08-28 20:43:35	ACTIVE	2021-08-28 20:43:41	ROLE_ADMIN
3	2021-08-28 20:43:35	ACTIVE	2021-08-28 20:43:41	ROLE_COOK
\.


--
-- Data for Name: user_roles; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.user_roles (user_id, role_id) FROM stdin;
1	1
1	2
1	3
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.users (id, created_at, status, updated_at, email, first_name, last_name, password, username) FROM stdin;
1	2021-08-28 20:44:56.942104	ACTIVE	2021-08-28 20:44:56.942104	test@email.com	\N	\N	$2a$10$3jHQnfbFomLLndXbfbyq3.Jim/teSU9.E1fRp/rl5XEmPK.lETn0e	k4rnaj1k
\.


--
-- Name: dish_orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.dish_orders_id_seq', 1, false);


--
-- Name: dishes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.dishes_id_seq', 1, false);


--
-- Name: drink_orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.drink_orders_id_seq', 1, false);


--
-- Name: drinks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.drinks_id_seq', 1, false);


--
-- Name: ingredients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.ingredients_id_seq', 1, false);


--
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.orders_id_seq', 1, false);


--
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.roles_id_seq', 1, false);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.users_id_seq', 1, true);


--
-- Name: dish_orders dish_orders_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.dish_orders
    ADD CONSTRAINT dish_orders_pkey PRIMARY KEY (id);


--
-- Name: dishes dishes_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.dishes
    ADD CONSTRAINT dishes_pkey PRIMARY KEY (id);


--
-- Name: drink_orders drink_orders_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.drink_orders
    ADD CONSTRAINT drink_orders_pkey PRIMARY KEY (id);


--
-- Name: drinks drinks_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.drinks
    ADD CONSTRAINT drinks_pkey PRIMARY KEY (id);


--
-- Name: ingredients ingredients_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.ingredients
    ADD CONSTRAINT ingredients_pkey PRIMARY KEY (id);


--
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: users uk_6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- Name: dishes uk_g9v3f8f18je2t2ou8fvwse3kq; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.dishes
    ADD CONSTRAINT uk_g9v3f8f18je2t2ou8fvwse3kq UNIQUE (name);


--
-- Name: ingredients uk_j6tsl15xx76y4kv41yxr4uxab; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.ingredients
    ADD CONSTRAINT uk_j6tsl15xx76y4kv41yxr4uxab UNIQUE (name);


--
-- Name: orders_dishes uk_oitt9mabpc2xbkej23i2owy3r; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.orders_dishes
    ADD CONSTRAINT uk_oitt9mabpc2xbkej23i2owy3r UNIQUE (dishes_id);


--
-- Name: orders_drinks uk_rruwulfvy93epnmyt4dfj4w6a; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.orders_drinks
    ADD CONSTRAINT uk_rruwulfvy93epnmyt4dfj4w6a UNIQUE (drinks_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: drink_orders fk33tesw3w9gv4dxac2p6kv92va; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.drink_orders
    ADD CONSTRAINT fk33tesw3w9gv4dxac2p6kv92va FOREIGN KEY (drink_id) REFERENCES public.drinks(id);


--
-- Name: orders_dishes fk6on9jka58uq379lr0ai2o7c6w; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.orders_dishes
    ADD CONSTRAINT fk6on9jka58uq379lr0ai2o7c6w FOREIGN KEY (order_id) REFERENCES public.orders(id);


--
-- Name: orders_drinks fkantxuiaylxrsfibbv0nip31d8; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.orders_drinks
    ADD CONSTRAINT fkantxuiaylxrsfibbv0nip31d8 FOREIGN KEY (drinks_id) REFERENCES public.drink_orders(id);


--
-- Name: orders_drinks fkbdju48fj4ljc4s5lgqdktfu7j; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.orders_drinks
    ADD CONSTRAINT fkbdju48fj4ljc4s5lgqdktfu7j FOREIGN KEY (order_id) REFERENCES public.orders(id);


--
-- Name: dish_ingredients fkbhivhxrdibnjx2unpalmvc0e8; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.dish_ingredients
    ADD CONSTRAINT fkbhivhxrdibnjx2unpalmvc0e8 FOREIGN KEY (dish_id) REFERENCES public.dishes(id);


--
-- Name: dish_ingredients fkcu67of27o6282rbfi19w1sa88; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.dish_ingredients
    ADD CONSTRAINT fkcu67of27o6282rbfi19w1sa88 FOREIGN KEY (ingredient_id) REFERENCES public.ingredients(id);


--
-- Name: user_roles fkh8ciramu9cc9q3qcqiv4ue8a6; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- Name: user_roles fkhfh9dx7w3ubf1co1vdev94g3f; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: excluded_dish_ingredients fkjrbjobpi9nbgarg9b4tc0auwn; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.excluded_dish_ingredients
    ADD CONSTRAINT fkjrbjobpi9nbgarg9b4tc0auwn FOREIGN KEY (dish_order_id) REFERENCES public.dish_orders(id);


--
-- Name: excluded_dish_ingredients fkn7ulv2037pgroto47y1tesh2; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.excluded_dish_ingredients
    ADD CONSTRAINT fkn7ulv2037pgroto47y1tesh2 FOREIGN KEY (ingredient_id) REFERENCES public.ingredients(id);


--
-- Name: dish_orders fkq56o2eb9urpavcu9rr4o7ubs0; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.dish_orders
    ADD CONSTRAINT fkq56o2eb9urpavcu9rr4o7ubs0 FOREIGN KEY (dish_id) REFERENCES public.dishes(id);


--
-- Name: orders_dishes fkrl70hyhk0vpkjlflgp7bnq2gb; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.orders_dishes
    ADD CONSTRAINT fkrl70hyhk0vpkjlflgp7bnq2gb FOREIGN KEY (dishes_id) REFERENCES public.dish_orders(id);


--
-- PostgreSQL database dump complete
--

