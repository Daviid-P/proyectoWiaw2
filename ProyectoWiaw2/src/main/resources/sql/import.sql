--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.6
-- Dumped by pg_dump version 9.5.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = public, pg_catalog;

ALTER TABLE ONLY public.director DROP CONSTRAINT fkstxmla51x0gjvwu5ksbjvxln7;
ALTER TABLE ONLY public.prueba DROP CONSTRAINT fks16f2s5hminfcefn5ut6ibsav;
ALTER TABLE ONLY public.paciente DROP CONSTRAINT fknk7alk2a1iathi4h7mqftlbn;
ALTER TABLE ONLY public.resultado DROP CONSTRAINT fknf49iogpdxorqa6rc8n80qlao;
ALTER TABLE ONLY public.empleado DROP CONSTRAINT fkmp3e7ustd9kqeqvi166ig12ky;
ALTER TABLE ONLY public.doctor DROP CONSTRAINT fkk22swcaqu7wslbrrjhtyathd0;
ALTER TABLE ONLY public.visita DROP CONSTRAINT fkh3d4lgbqqma56gjq29ko187q3;
ALTER TABLE ONLY public.visita DROP CONSTRAINT fkgwfc02fcxdg6qrq29leehipwp;
ALTER TABLE ONLY public.prueba DROP CONSTRAINT fkg5hv6akuh0fcey5dhp500fshd;
ALTER TABLE ONLY public.paciente DROP CONSTRAINT fk9g6q28l9tfssrgragbpru6csd;
ALTER TABLE ONLY public.visita DROP CONSTRAINT fk8im74y09xrpunu7r98f8mbuhq;
ALTER TABLE ONLY public.recepcionista DROP CONSTRAINT fk72lijrdamnbnn88xy89qy3er6;
ALTER TABLE ONLY public.autoridad DROP CONSTRAINT fk5fd97m39ir5wnqy3k353gvrwf;
ALTER TABLE ONLY public.paciente DROP CONSTRAINT fk3yn2eov5cnuor3o8d8sh9ehyt;
ALTER TABLE ONLY public.empleado DROP CONSTRAINT fk21ddfrduanruae8nw4y1gbfw;
ALTER TABLE ONLY public.visita DROP CONSTRAINT visita_pkey;
ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_pkey;
ALTER TABLE ONLY public.usuario DROP CONSTRAINT uk_ma71x4n4tydibsd9qt0m71le7;
ALTER TABLE ONLY public.tipoprueba DROP CONSTRAINT uk_ivt8bos8892xld03epnlf8r65;
ALTER TABLE ONLY public.tipoprueba DROP CONSTRAINT tipoprueba_pkey;
ALTER TABLE ONLY public.resultado DROP CONSTRAINT resultado_pkey;
ALTER TABLE ONLY public.resettoken DROP CONSTRAINT resettoken_pkey;
ALTER TABLE ONLY public.recepcionista DROP CONSTRAINT recepcionista_pkey;
ALTER TABLE ONLY public.prueba DROP CONSTRAINT prueba_pkey;
ALTER TABLE ONLY public.paciente DROP CONSTRAINT paciente_pkey;
ALTER TABLE ONLY public.expediente DROP CONSTRAINT expediente_pkey;
ALTER TABLE ONLY public.empleado DROP CONSTRAINT empleado_pkey;
ALTER TABLE ONLY public.doctor DROP CONSTRAINT doctor_pkey;
ALTER TABLE ONLY public.director DROP CONSTRAINT director_pkey;
ALTER TABLE ONLY public.contrato DROP CONSTRAINT contrato_pkey;
ALTER TABLE ONLY public.autoridad DROP CONSTRAINT autoridad_pkey;
ALTER TABLE public.visita ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.usuario ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.tipoprueba ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.resultado ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.prueba ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.expediente ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.contrato ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.autoridad ALTER COLUMN id DROP DEFAULT;
DROP SEQUENCE public.visita_id_seq;
DROP TABLE public.visita;
DROP SEQUENCE public.usuario_id_seq;
DROP TABLE public.usuario;
DROP SEQUENCE public.tipoprueba_id_seq;
DROP TABLE public.tipoprueba;
DROP SEQUENCE public.resultado_id_seq;
DROP TABLE public.resultado;
DROP TABLE public.resettoken;
DROP TABLE public.recepcionista;
DROP SEQUENCE public.prueba_id_seq;
DROP TABLE public.prueba;
DROP TABLE public.paciente;
DROP SEQUENCE public.hibernate_sequence;
DROP SEQUENCE public.expediente_id_seq;
DROP TABLE public.expediente;
DROP TABLE public.empleado;
DROP TABLE public.doctor;
DROP TABLE public.director;
DROP SEQUENCE public.contrato_id_seq;
DROP TABLE public.contrato;
DROP SEQUENCE public.autoridad_id_seq;
DROP TABLE public.autoridad;
DROP EXTENSION unaccent;
DROP EXTENSION plpgsql;
DROP SCHEMA public;
--
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: unaccent; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS unaccent WITH SCHEMA public;


--
-- Name: EXTENSION unaccent; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION unaccent IS 'text search dictionary that removes accents';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: autoridad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE autoridad (
    id bigint NOT NULL,
    autoridad character varying(255) NOT NULL,
    usuario_id bigint
);


ALTER TABLE autoridad OWNER TO postgres;

--
-- Name: autoridad_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE autoridad_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE autoridad_id_seq OWNER TO postgres;

--
-- Name: autoridad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE autoridad_id_seq OWNED BY autoridad.id;


--
-- Name: contrato; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE contrato (
    id bigint NOT NULL,
    dias_vacaciones integer NOT NULL,
    fin date,
    fincontrato date,
    inicio date NOT NULL,
    sueldo integer NOT NULL,
    tipo character varying(255) NOT NULL
);


ALTER TABLE contrato OWNER TO postgres;

--
-- Name: contrato_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE contrato_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE contrato_id_seq OWNER TO postgres;

--
-- Name: contrato_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE contrato_id_seq OWNED BY contrato.id;


--
-- Name: director; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE director (
    director_id bigint NOT NULL
);


ALTER TABLE director OWNER TO postgres;

--
-- Name: doctor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE doctor (
    especialidad character varying(255) NOT NULL,
    id bigint NOT NULL
);


ALTER TABLE doctor OWNER TO postgres;

--
-- Name: empleado; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE empleado (
    id bigint NOT NULL,
    contrato_id bigint
);


ALTER TABLE empleado OWNER TO postgres;

--
-- Name: expediente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE expediente (
    id bigint NOT NULL,
    alergias character varying(255),
    alimentacion character varying(255) NOT NULL,
    antecedentesmedicos character varying(4112) NOT NULL,
    genero character(1) NOT NULL,
    habitos character varying(255),
    hospitalizaciones integer NOT NULL,
    medicacion character varying(1024) NOT NULL,
    ocupacion character varying(255) NOT NULL,
    peso integer NOT NULL
);


ALTER TABLE expediente OWNER TO postgres;

--
-- Name: expediente_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE expediente_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE expediente_id_seq OWNER TO postgres;

--
-- Name: expediente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE expediente_id_seq OWNED BY expediente.id;


--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hibernate_sequence OWNER TO postgres;

--
-- Name: paciente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE paciente (
    habitacion integer,
    id bigint NOT NULL,
    doctorcabecera_id bigint,
    expediente_id bigint
);


ALTER TABLE paciente OWNER TO postgres;

--
-- Name: prueba; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE prueba (
    id bigint NOT NULL,
    fecha date NOT NULL,
    observaciones character varying(512) NOT NULL,
    paciente_id bigint,
    tipo_id bigint
);


ALTER TABLE prueba OWNER TO postgres;

--
-- Name: prueba_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE prueba_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE prueba_id_seq OWNER TO postgres;

--
-- Name: prueba_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE prueba_id_seq OWNED BY prueba.id;


--
-- Name: recepcionista; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE recepcionista (
    id bigint NOT NULL
);


ALTER TABLE recepcionista OWNER TO postgres;

--
-- Name: resettoken; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE resettoken (
    id bigint NOT NULL,
    expirydate timestamp without time zone,
    token character varying(255)
);


ALTER TABLE resettoken OWNER TO postgres;

--
-- Name: resultado; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE resultado (
    id bigint NOT NULL,
    comentarios character varying(255) NOT NULL,
    resultado integer NOT NULL,
    prueba_id bigint
);


ALTER TABLE resultado OWNER TO postgres;

--
-- Name: resultado_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE resultado_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE resultado_id_seq OWNER TO postgres;

--
-- Name: resultado_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE resultado_id_seq OWNED BY resultado.id;


--
-- Name: tipoprueba; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tipoprueba (
    id bigint NOT NULL,
    descripcion character varying(512) NOT NULL,
    disponible boolean NOT NULL,
    nombre character varying(32) NOT NULL
);


ALTER TABLE tipoprueba OWNER TO postgres;

--
-- Name: tipoprueba_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE tipoprueba_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tipoprueba_id_seq OWNER TO postgres;

--
-- Name: tipoprueba_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE tipoprueba_id_seq OWNED BY tipoprueba.id;


--
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE usuario (
    id bigint NOT NULL,
    activo boolean NOT NULL,
    apellidos character varying(255) NOT NULL,
    codigopostal character varying(255) NOT NULL,
    direccion character varying(255) NOT NULL,
    dni character varying(9) NOT NULL,
    email character varying(255),
    fecha_nacimiento date NOT NULL,
    nombre character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    telefono bigint
);


ALTER TABLE usuario OWNER TO postgres;

--
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE usuario_id_seq OWNER TO postgres;

--
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE usuario_id_seq OWNED BY usuario.id;


--
-- Name: visita; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE visita (
    id bigint NOT NULL,
    estado character varying(255),
    fecha timestamp without time zone,
    habitacion integer,
    observaciones character varying(255),
    doctor_id bigint,
    paciente_id bigint,
    recepcionista_id bigint
);


ALTER TABLE visita OWNER TO postgres;

--
-- Name: visita_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE visita_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE visita_id_seq OWNER TO postgres;

--
-- Name: visita_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE visita_id_seq OWNED BY visita.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY autoridad ALTER COLUMN id SET DEFAULT nextval('autoridad_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY contrato ALTER COLUMN id SET DEFAULT nextval('contrato_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY expediente ALTER COLUMN id SET DEFAULT nextval('expediente_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY prueba ALTER COLUMN id SET DEFAULT nextval('prueba_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY resultado ALTER COLUMN id SET DEFAULT nextval('resultado_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tipoprueba ALTER COLUMN id SET DEFAULT nextval('tipoprueba_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario ALTER COLUMN id SET DEFAULT nextval('usuario_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY visita ALTER COLUMN id SET DEFAULT nextval('visita_id_seq'::regclass);


--
-- Data for Name: autoridad; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY autoridad (id, autoridad, usuario_id) FROM stdin;
1	ROLE_DIRECTOR	1
2	ROLE_DOCTOR	5
3	ROLE_DOCTOR	6
4	ROLE_DOCTOR	7
5	ROLE_DOCTOR	8
6	ROLE_RECEPCIONISTA	9
7	ROLE_RECEPCIONISTA	10
8	ROLE_PACIENTE	13
9	ROLE_PACIENTE	14
10	ROLE_PACIENTE	15
\.


--
-- Name: autoridad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('autoridad_id_seq', 10, true);


--
-- Data for Name: contrato; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY contrato (id, dias_vacaciones, fin, fincontrato, inicio, sueldo, tipo) FROM stdin;
1	30	\N	\N	2017-05-19	3000	indefinido
2	30	\N	2019-06-04	2017-05-20	3030	temporal
3	29	\N	\N	2017-05-19	3001	indefinido
4	0	2017-05-19	2017-11-16	2017-05-19	600	practicas
5	30	\N	\N	2017-05-19	2100	indefinido
6	25	\N	2019-07-11	2017-05-20	2100	temporal
\.


--
-- Name: contrato_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('contrato_id_seq', 6, true);


--
-- Data for Name: director; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY director (director_id) FROM stdin;
1
\.


--
-- Data for Name: doctor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY doctor (especialidad, id) FROM stdin;
cardiologia	5
cabecera	6
cabecera	7
neurologia	8
\.


--
-- Data for Name: empleado; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY empleado (id, contrato_id) FROM stdin;
5	1
6	2
7	3
8	4
9	5
10	6
\.


--
-- Data for Name: expediente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY expediente (id, alergias, alimentacion, antecedentesmedicos, genero, habitos, hospitalizaciones, medicacion, ocupacion, peso) FROM stdin;
1	Polen	Poca verdura, poca fruta, muchas grasas	Rotura del ligamento cruzado. ExtirpaciÃ³n de amigdalas. Diabetes de tipo 2.	H	Fumador, bebedor ocasional	2	Insulina y Lantus.	Parado	84
2	No	Dieta normal, muchos vegetales, poca fruta	No.	H	Fumador	0	No.	Contable	90
3	Penicilina	Vegana	Rotura de muÃ±eca izquierda	M	No	1	Aspirina.	Encargada de comedor	71
\.


--
-- Name: expediente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('expediente_id_seq', 3, true);


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('hibernate_sequence', 1, false);


--
-- Data for Name: paciente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY paciente (habitacion, id, doctorcabecera_id, expediente_id) FROM stdin;
10	13	6	1
23	14	7	2
\N	15	6	3
\.


--
-- Data for Name: prueba; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY prueba (id, fecha, observaciones, paciente_id, tipo_id) FROM stdin;
1	2017-05-19	300 ml de sange extraida para su posterior estudio en laboratorio.	13	1
2	2017-05-19	Radiografia de la pierna derecha debido a una posible rotura del femur.	13	3
3	2017-05-19	Tomada muestra de 200 ml de orina para su posterior estudi en laboratorio.	13	2
4	2017-05-19	Radiografia muÃ±eca izquierda debido a una posible fisura.	13	3
\.


--
-- Name: prueba_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('prueba_id_seq', 4, true);


--
-- Data for Name: recepcionista; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY recepcionista (id) FROM stdin;
9
10
\.


--
-- Data for Name: resettoken; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY resettoken (id, expirydate, token) FROM stdin;
\.


--
-- Data for Name: resultado; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY resultado (id, comentarios, resultado, prueba_id) FROM stdin;
1	Finalmente el femur no se encuentra roto, pero si que ha recibido un leve traumatismo, por lo cual hemos procedido a aplicar una venda para asegurar una curacion rapida.	2	2
2	Negativo en Test1.	1	1
3	Positivo leve en test2.	2	1
4	Positivo grave en test3.	3	1
5	Positivo muy grave en test4,	4	1
6	MuÃ±eca gravemente fisurada, posible roura en caso de que no se inmovilize el antebrazo. Se aplica una escayola al paciente.	3	4
\.


--
-- Name: resultado_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('resultado_id_seq', 6, true);


--
-- Data for Name: tipoprueba; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY tipoprueba (id, descripcion, disponible, nombre) FROM stdin;
1	Un analisis de laboratorio realizado en una muestra de sangre. Usualmente es extraida de una vena del brazo usando una jeringa, via pinchazo de dedo, tambien se puede hacer con sangre arterial.	t	Sangre
2	Un examen general de orina, tambien llamado analisis de orina o uroanalisis, consiste en una serie de examenes efectuados sobre la orina, constituyendo uno de los metodos mas comunes de diagnostico medico.	t	Orina
3	Es una tecnica diagnostica radiologica de forma digital (radiologÃ­a digital directa o indirecta) en una base de datos. La imagen se obtiene al exponer al receptor de imagen radiografica a una fuente de radiacion de alta energÃ­a, comunmente rayos X o radiacion gamma	t	Radiografia
4	Se realiza mediante un pinchazo con una fina aguja en un hueso superficial, la biopsia de medula es una prueba que sirve para estudiar con profundidad patologias hematologicas.	t	Biopsia de medula osea
5	Ejemplo de prueba que ya no se realiza en la clinica debido a que ha quedado obsoleta.	f	Obsoleta
\.


--
-- Name: tipoprueba_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('tipoprueba_id_seq', 5, true);


--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY usuario (id, activo, apellidos, codigopostal, direccion, dni, email, fecha_nacimiento, nombre, password, telefono) FROM stdin;
1	t	Ramos Lopez	08770	c/ triola 9 11 2-2	47752902A	kevinramos456@gmail.com	1993-10-18	Kevin	$2a$10$e0mUqoxaG7AKjoip/9SqruDN4zggxmB9wOzUfGjGcLbLOFuvyaa22	669618162
5	t	Jimenez Barber	08880	calle nombre 32 2-2	93110748D	manolo@gmail.com	1991-09-18	Sergi	$2a$10$S9NqS0/.L8s1TUa1ccbn7OIqNzvO8RBIuu27ODSZmIcagR8BQjLcK	666777888
6	t	Alier Ala	07654	calle numero 45	94995707A	marina@gmail.com	1995-07-18	Marina	$2a$10$noKMhMhPLsHDU3W..aUuv.XNDKSh6eHDLUBZR7CzKVNUUsRK5KydW	654321987
7	t	Sorinas Acon	09000	Calle triana 31 2-2	89799454W	dani@gmail.com	1992-04-22	Dani	$2a$10$3O7xKam2L6FitdRL.Ce3BeNtnoLgCQJQBXC05Av8lkcsbNbDZ7Qz6	607767473
8	f	Jimenez Batber	08770	calle falsa 123	55264303H	marc@gmail.com	1992-04-22	Marc	$2a$10$7VNRD2cACl995//Gt6rzNe9H7s/QPQswWnTmBqevqoMognKy7Mck.	123456789
9	t	Pons Gallart	08770	calle barraquer 345 2-3	58232996D	ari@gmail.com	1999-01-06	Ari	$2a$10$/D.QEd6VXzt9W9YvnXxKzefLpSpRGIJ3UyLEEGaVlQwortzi9trEi	623123123
10	t	Paz Leguizamon	09000	calle falsa 456	Y6419635G	maria@gmail.com	1992-04-22	Maria	$2a$10$TOJbiWeCV2iGCs8/a6upb.OkqkQUgNezum2io8Z7e7D5rSS5eHiRi	666555444
14	t	Escobar Rodriguez	09000	calle paralel 234 2-3	Z2385818X	gabri@gmail.com	1992-04-22	Gabriel	$2a$10$rrOMthC.XFh6Cqh7c1tpXuxqc4CakwGW/VUzV16MgBjHm463JG0eG	654321654
15	t	Onda Vidal	09000	calle numero 344	36019780D	marta@gmail.com	1996-06-05	Marta	$2a$10$AzAGmwnQg4gMHwTpm5tsHuAifDIV75Oqqf/PSPebwYjG8U3GfxcLC	654555444
13	t	Guerrero Alvarez	09000	calle piruleta 67 2-2	74638850D	guerre@gmail.com	1992-04-22	Marc	$2a$10$RH2QHNlaUJi1VDsz3uTgfex3fujKyx2/1hBYMrfUyKYjontBy268m	612345672
\.


--
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('usuario_id_seq', 19, true);


--
-- Data for Name: visita; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY visita (id, estado, fecha, habitacion, observaciones, doctor_id, paciente_id, recepcionista_id) FROM stdin;
2	Pendiente	2017-05-22 19:30:00	3	\N	5	14	9
3	Pendiente	2017-05-22 20:00:00	3	\N	5	15	9
4	Pendiente	2017-05-22 19:00:00	2	\N	6	15	9
5	Pendiente	2017-05-22 20:30:00	4	\N	7	14	9
1	Cancelada	2017-05-19 19:30:00	1	\N	5	14	9
\.


--
-- Name: visita_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('visita_id_seq', 5, true);


--
-- Name: autoridad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY autoridad
    ADD CONSTRAINT autoridad_pkey PRIMARY KEY (id);


--
-- Name: contrato_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY contrato
    ADD CONSTRAINT contrato_pkey PRIMARY KEY (id);


--
-- Name: director_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY director
    ADD CONSTRAINT director_pkey PRIMARY KEY (director_id);


--
-- Name: doctor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY doctor
    ADD CONSTRAINT doctor_pkey PRIMARY KEY (id);


--
-- Name: empleado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY empleado
    ADD CONSTRAINT empleado_pkey PRIMARY KEY (id);


--
-- Name: expediente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY expediente
    ADD CONSTRAINT expediente_pkey PRIMARY KEY (id);


--
-- Name: paciente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY paciente
    ADD CONSTRAINT paciente_pkey PRIMARY KEY (id);


--
-- Name: prueba_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY prueba
    ADD CONSTRAINT prueba_pkey PRIMARY KEY (id);


--
-- Name: recepcionista_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY recepcionista
    ADD CONSTRAINT recepcionista_pkey PRIMARY KEY (id);


--
-- Name: resettoken_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY resettoken
    ADD CONSTRAINT resettoken_pkey PRIMARY KEY (id);


--
-- Name: resultado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY resultado
    ADD CONSTRAINT resultado_pkey PRIMARY KEY (id);


--
-- Name: tipoprueba_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tipoprueba
    ADD CONSTRAINT tipoprueba_pkey PRIMARY KEY (id);


--
-- Name: uk_ivt8bos8892xld03epnlf8r65; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tipoprueba
    ADD CONSTRAINT uk_ivt8bos8892xld03epnlf8r65 UNIQUE (nombre);


--
-- Name: uk_ma71x4n4tydibsd9qt0m71le7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT uk_ma71x4n4tydibsd9qt0m71le7 UNIQUE (dni);


--
-- Name: usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- Name: visita_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY visita
    ADD CONSTRAINT visita_pkey PRIMARY KEY (id);


--
-- Name: fk21ddfrduanruae8nw4y1gbfw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY empleado
    ADD CONSTRAINT fk21ddfrduanruae8nw4y1gbfw FOREIGN KEY (id) REFERENCES usuario(id);


--
-- Name: fk3yn2eov5cnuor3o8d8sh9ehyt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY paciente
    ADD CONSTRAINT fk3yn2eov5cnuor3o8d8sh9ehyt FOREIGN KEY (doctorcabecera_id) REFERENCES doctor(id);


--
-- Name: fk5fd97m39ir5wnqy3k353gvrwf; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY autoridad
    ADD CONSTRAINT fk5fd97m39ir5wnqy3k353gvrwf FOREIGN KEY (usuario_id) REFERENCES usuario(id);


--
-- Name: fk72lijrdamnbnn88xy89qy3er6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY recepcionista
    ADD CONSTRAINT fk72lijrdamnbnn88xy89qy3er6 FOREIGN KEY (id) REFERENCES empleado(id);


--
-- Name: fk8im74y09xrpunu7r98f8mbuhq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY visita
    ADD CONSTRAINT fk8im74y09xrpunu7r98f8mbuhq FOREIGN KEY (paciente_id) REFERENCES paciente(id);


--
-- Name: fk9g6q28l9tfssrgragbpru6csd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY paciente
    ADD CONSTRAINT fk9g6q28l9tfssrgragbpru6csd FOREIGN KEY (expediente_id) REFERENCES expediente(id);


--
-- Name: fkg5hv6akuh0fcey5dhp500fshd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY prueba
    ADD CONSTRAINT fkg5hv6akuh0fcey5dhp500fshd FOREIGN KEY (paciente_id) REFERENCES paciente(id);


--
-- Name: fkgwfc02fcxdg6qrq29leehipwp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY visita
    ADD CONSTRAINT fkgwfc02fcxdg6qrq29leehipwp FOREIGN KEY (recepcionista_id) REFERENCES recepcionista(id);


--
-- Name: fkh3d4lgbqqma56gjq29ko187q3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY visita
    ADD CONSTRAINT fkh3d4lgbqqma56gjq29ko187q3 FOREIGN KEY (doctor_id) REFERENCES doctor(id);


--
-- Name: fkk22swcaqu7wslbrrjhtyathd0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY doctor
    ADD CONSTRAINT fkk22swcaqu7wslbrrjhtyathd0 FOREIGN KEY (id) REFERENCES empleado(id);


--
-- Name: fkmp3e7ustd9kqeqvi166ig12ky; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY empleado
    ADD CONSTRAINT fkmp3e7ustd9kqeqvi166ig12ky FOREIGN KEY (contrato_id) REFERENCES contrato(id);


--
-- Name: fknf49iogpdxorqa6rc8n80qlao; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY resultado
    ADD CONSTRAINT fknf49iogpdxorqa6rc8n80qlao FOREIGN KEY (prueba_id) REFERENCES prueba(id);


--
-- Name: fknk7alk2a1iathi4h7mqftlbn; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY paciente
    ADD CONSTRAINT fknk7alk2a1iathi4h7mqftlbn FOREIGN KEY (id) REFERENCES usuario(id);


--
-- Name: fks16f2s5hminfcefn5ut6ibsav; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY prueba
    ADD CONSTRAINT fks16f2s5hminfcefn5ut6ibsav FOREIGN KEY (tipo_id) REFERENCES tipoprueba(id);


--
-- Name: fkstxmla51x0gjvwu5ksbjvxln7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY director
    ADD CONSTRAINT fkstxmla51x0gjvwu5ksbjvxln7 FOREIGN KEY (director_id) REFERENCES usuario(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

