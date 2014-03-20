
--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: agrupacion; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE agrupacion (
    num_grupos integer,
    numero_alumnos integer,
    id_depar integer NOT NULL,
    cod_materia character varying(6) NOT NULL
);


ALTER TABLE public.agrupacion OWNER TO postgres;

--
-- Name: aulas; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE aulas (
    cod_aula character varying(4) NOT NULL,
    capacidad integer NOT NULL,
    hora_apertura time without time zone NOT NULL,
    hora_cierre time without time zone NOT NULL
);


ALTER TABLE public.aulas OWNER TO postgres;

--
-- Name: carreras; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE carreras (
    id_carrera character varying(6) NOT NULL,
    plan_estudio integer NOT NULL,
    nombre_carrera character varying(100) NOT NULL,
    id_depar integer
);


ALTER TABLE public.carreras OWNER TO postgres;

--
-- Name: carreras_materias; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE carreras_materias (
    carreras_plan_estudio integer NOT NULL,
    materias_cod_materia character varying(6) NOT NULL,
    ciclo integer NOT NULL,
    carreras_id_carrera character varying(6) NOT NULL,
    agrupacion_id_depar integer
);


ALTER TABLE public.carreras_materias OWNER TO postgres;

--
-- Name: departamentos; Type: TABLE; Schema: public; Owner: horarios_bd; Tablespace: 
--

CREATE TABLE departamentos (
    id_depar integer NOT NULL,
    nombre_depar character varying(100)
);


ALTER TABLE public.departamentos OWNER TO horarios_bd;

--
-- Name: dias; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE dias (
    nombre_dia character varying(10) NOT NULL,
    hora_entrada time without time zone NOT NULL,
    hora_salida time without time zone NOT NULL
);


ALTER TABLE public.dias OWNER TO postgres;

--
-- Name: horas; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE horas (
    id_hora integer NOT NULL,
    inicio time without time zone NOT NULL,
    fin time without time zone NOT NULL
);


ALTER TABLE public.horas OWNER TO postgres;

--
-- Name: materias; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE materias (
    cod_materia character varying(6) NOT NULL,
    nombre_materia character varying(100) NOT NULL,
    unidades_valorativas integer NOT NULL
);


ALTER TABLE public.materias OWNER TO postgres;

--
-- Data for Name: agrupacion; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY agrupacion (num_grupos, numero_alumnos, id_depar, cod_materia) FROM stdin;
5	30	1	CAR135
2	30	1	CAR235
10	70	1	CGR135
7	50	1	CGR235
5	40	2	DEH134
1	70	1	ESD135
2	50	1	ESR135
1	40	1	ESR235
4	60	1	FIR135
2	50	1	FIR235
10	70	1	COB135
7	50	1	COB235
2	25	1	FIR335
5	40	2	FPC134
4	40	2	FPC234
4	30	1	GES135
10	60	1	HSE135
10	60	1	IAI135
4	50	1	IEC135
5	40	2	ING134
5	40	2	INP134
4	40	2	INP234
11	60	1	MAT135
8	50	1	MAT235
5	50	1	MAT335
4	40	1	MAT435
4	40	2	MDP134
4	40	2	MDP234
4	40	1	MEP135
5	40	2	MGI134
5	40	2	MGI234
4	40	2	MGI334
10	70	1	MSA135
8	60	1	MSA235
9	60	1	MSM135
3	40	1	MSO135
2	40	1	MSO235
11	70	1	MTE135
4	40	2	PGC134
8	60	1	PPR135
10	70	1	PRN135
8	70	1	PRN235
6	70	1	PRN335
3	60	1	FDE135
4	40	2	PSI134
10	70	1	PSI135
4	40	2	PSI234
5	40	2	PSO134
4	40	2	PSP134
4	40	2	PSR134
4	40	2	PSR234
4	60	1	PYE135
7	50	1	QTR135
4	40	2	SCO134
5	50	2	SOG134
4	40	1	TAP135
4	40	1	TAP235
4	40	1	TDC135
4	50	1	THI135
3	45	1	THI235
4	40	1	URN135
4	40	1	URN235
4	40	2	DIG134
4	40	2	ECU134
5	40	2	FYF134
4	40	2	HDI134
6	50	2	HIL134
3	40	2	ICL134
2	40	2	IDV134
3	40	2	ING234
3	40	2	INL134
2	40	2	LIC134
3	40	2	MDI134
3	40	2	MOR134
2	40	2	MOR234
3	40	2	PSC134
3	40	2	PSC234
3	40	2	RTA134
3	40	2	TLI134
\.


--
-- Data for Name: aulas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY aulas (cod_aula, capacidad, hora_apertura, hora_cierre) FROM stdin;
A2	50	06:45:00	20:25:00
A3	130	06:45:00	20:25:00
A4	75	06:45:00	20:25:00
A5	75	06:45:00	20:25:00
A6	90	06:45:00	20:25:00
A7	105	06:45:00	20:25:00
A8	80	06:45:00	20:25:00
A9	30	06:45:00	20:25:00
A10	65	06:45:00	20:25:00
A11	65	06:45:00	20:25:00
A12	80	06:45:00	20:25:00
M3	50	06:45:00	20:25:00
E1	110	06:45:00	20:25:00
E2	50	06:45:00	20:25:00
E3	70	06:45:00	20:25:00
E0	70	06:45:00	20:25:00
1N	100	06:45:00	20:25:00
2N	50	06:45:00	20:25:00
3N	50	06:45:00	20:25:00
4N	30	06:45:00	20:25:00
5N	30	06:45:00	20:25:00
1A	105	06:45:00	20:25:00
1B	105	06:45:00	20:25:00
2A	105	06:45:00	20:25:00
2B	105	06:45:00	20:25:00
3A	105	06:45:00	20:25:00
3B	105	06:45:00	20:25:00
S1A	100	06:45:00	20:25:00
S1B	100	06:45:00	20:25:00
S1C	100	06:45:00	20:25:00
S2A	50	06:45:00	20:25:00
S2B	50	06:45:00	20:25:00
S2C	50	06:45:00	20:25:00
S2D	60	06:45:00	20:25:00
S2E	60	06:45:00	20:25:00
S2F	80	06:45:00	20:25:00
HD	40	06:45:00	20:25:00
T1A	40	06:45:00	18:00:00
T1B	40	06:45:00	18:00:00
T1C	40	06:45:00	18:00:00
T1D	40	06:45:00	18:00:00
\.


--
-- Data for Name: carreras; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY carreras (id_carrera, plan_estudio, nombre_carrera, id_depar) FROM stdin;
A30507	1998	Arquitectura	1
I30501	1998	Ingenieria Civil	1
I30515	1998	Ingenieria en Sistemas Informaticos	1
L30407	1998	Licenciatura en Psicologia	2
L30414	2007	Licenciatura en Ciencias del Lenguaje y Literatura	2
\.


--
-- Data for Name: carreras_materias; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY carreras_materias (carreras_plan_estudio, materias_cod_materia, ciclo, carreras_id_carrera, agrupacion_id_depar) FROM stdin;
1998	CGR135	1	I30501	1
1998	CGR235	2	I30501	1
1998	IAI135	1	I30515	1
1998	MGI334	3	L30407	2
1998	MSA135	1	A30507	1
1998	IEC135	4	I30501	1
1998	ING134	1	L30407	2
1998	INP134	1	L30407	2
1998	INP234	2	L30407	2
1998	MAT135	1	A30507	1
1998	MAT135	1	I30501	1
1998	MAT135	1	I30515	1
1998	MAT235	2	A30507	1
1998	MAT235	2	I30501	1
1998	MAT235	2	I30515	1
1998	MAT335	3	I30501	1
1998	MAT335	3	I30515	1
1998	MAT435	4	I30501	1
1998	MAT435	4	I30515	1
1998	MDP134	3	L30407	2
1998	MDP234	4	L30407	2
1998	MEP135	4	I30515	1
1998	MGI134	1	L30407	2
1998	MSA235	2	A30507	1
1998	MSM135	4	I30501	1
1998	MGI234	2	L30407	2
1998	MSM135	2	I30515	1
1998	MSO135	3	I30501	1
1998	MSO235	4	I30501	1
1998	MTE135	1	A30507	1
1998	MTE135	1	I30501	1
1998	MTE135	1	I30515	1
1998	PSO134	2	L30407	2
1998	PGC134	4	L30407	2
1998	PPR135	2	A30507	1
1998	PRN135	2	I30515	1
1998	PRN235	3	I30515	1
1998	PRN335	4	I30515	1
1998	PSI134	3	L30407	2
1998	PSI135	1	I30501	1
1998	PSI135	1	I30515	1
1998	PSI234	4	L30407	2
1998	PSP134	3	L30407	2
1998	PSR134	3	L30407	2
1998	PSR234	4	L30407	2
2007	HDI134	3	L30414	2
1998	PYE135	3	I30501	1
1998	PYE135	3	I30515	1
1998	COB135	1	A30507	1
2007	HIL134	1	L30414	2
1998	QTR135	2	I30501	1
1998	SCO134	4	L30407	2
1998	SOG134	1	L30407	2
1998	TAP135	3	A30507	1
1998	TAP235	4	A30507	1
1998	TDC135	4	A30507	1
1998	THI135	3	A30507	1
1998	THI235	4	A30507	1
1998	URN135	3	A30507	1
2007	ICL134	2	L30414	2
1998	URN235	4	A30507	1
2007	DIG134	3	L30414	2
2007	ECU134	4	L30414	2
2007	FYF134	2	L30414	2
2007	IDV134	4	L30414	2
2007	ING134	1	L30414	2
2007	ING234	2	L30414	2
2007	INL134	1	L30414	2
2007	LIC134	4	L30414	2
2007	MDI134	1	L30414	2
2007	MOR134	3	L30414	2
1998	COB235	2	A30507	1
2007	MOR234	4	L30414	2
2007	PSC134	1	L30414	2
2007	PSC234	2	L30414	2
1998	DEH134	2	L30407	2
2007	RTA134	2	L30414	2
2007	TLI134	3	L30414	2
1998	ESD135	4	I30515	1
1998	ESR135	3	A30507	1
1998	ESR235	4	A30507	1
1998	FDE135	3	I30515	1
1998	FIR135	2	I30501	1
1998	CAR135	3	A30507	1
1998	CAR235	5	A30507	1
1998	FIR135	2	I30515	1
1998	FIR235	3	I30501	1
1998	FIR235	3	I30515	1
1998	FIR335	4	I30501	1
1998	FIR335	4	I30515	1
1998	FPC134	1	L30407	2
1998	FPC234	2	L30407	2
1998	GES135	3	A30507	1
1998	HSE135	2	I30501	1
1998	HSE135	2	I30515	1
1998	IAI135	3	I30501	1
\.


--
-- Data for Name: departamentos; Type: TABLE DATA; Schema: public; Owner: horarios_bd
--

COPY departamentos (id_depar, nombre_depar) FROM stdin;
1	Ingenieria y Arquitectura
3	Matematicas
4	Medicina
5	Fisica
2	Ciencias Sociales, Filosofia y Letras
6	Quimica
7	Economia
8	Biologia
\.


--
-- Data for Name: dias; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY dias (nombre_dia, hora_entrada, hora_salida) FROM stdin;
Lunes	06:45:00	08:25:00
Martes	06:45:00	08:25:00
Miercoles	06:45:00	08:25:00
Jueves	06:45:00	08:25:00
Viernes	06:45:00	08:25:00
Sabado	06:45:00	06:00:00
\.


--
-- Data for Name: horas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY horas (id_hora, inicio, fin) FROM stdin;
1	06:45:00	07:35:00
2	07:35:00	08:25:00
5	10:05:00	10:55:00
6	10:55:00	11:45:00
7	11:45:00	12:35:00
8	13:30:00	14:20:00
9	14:20:00	15:10:00
10	15:10:00	16:00:00
3	08:25:00	09:15:00
4	09:15:00	10:05:00
11	16:00:00	16:50:00
12	16:50:00	17:40:00
13	17:40:00	18:30:00
14	18:30:00	19:20:00
15	19:20:00	20:10:00
\.


--
-- Data for Name: materias; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY materias (cod_materia, nombre_materia, unidades_valorativas) FROM stdin;
MTE135	Metodos Experimentales	4
MAT135	Matematica 1	4
MSA135	Metodos Sociales	3
COB235	Comunicacion Basica 2	5
COB135	Comunicacion Basica 1	5
PPR135	Principios de la Proyectacion Arquitectonica	5
MAT235	Matematica 2	4
MSA235	Metodos Sociales 2	3
CAR135	Comunicacion Arquitectonica 1	3
TAP135	Taller de Proyectacion 1	5
ESR135	Estructuras 1	4
THI135	Teoria e Historia 1	3
URN135	Urbanismo 1	3
GES135	Geometria Descriptiva	3
CAR235	Comunicacion Arquitectonica 2	3
TAP235	Taller de Proyectacion 2	5
ESR235	Estructuras 2	4
THI235	Teoria e Historia 2	3
URN235	Urbanismo 2	3
MDI134	Metodos de Investigacion	4
DEH134	Desarrollo Humano	4
FPC134	Fundamentos Psico-Fisiologicos de la Conducta Normal y Anormal	4
FPC234	Fundamentos Psico-Fisiologicos de la Conducta Normal y Anormal 2	4
FYF134	Fonetica y Fonologia	4
HIL134	Histografia Literaria	4
ICL134	Introduccion a la Ciencia Literaria	4
ING134	Ingles 1	4
INL134	Introduccion a la Linguistica	4
INP134	Introduccion a la Psicologia	4
INP234	Introduccion a la Psicologia 2	4
MDP134	Metodos Diagnostico y Psicoterapeuticos 1	4
MDP234	Metodos Diagnostico y Psicoterapeuticos 2	4
MGI134	Metodologia de la Investigacion	4
MGI234	Metodologia de la Investigacion 2	4
MGI334	Metodologia de la Investigacion 3	4
PGC134	Psicologia Clinica	4
PSC134	Psicopedagogia 1	5
PSI134	Personalidad Social e Individual	4
PSI234	Personalidad Social e Individual 2	4
PSO134	Psicologia Social	4
PSP134	Psicologia Pedagogica	4
PSI135	Psicologia Social	4
PSR134	Psicologia Organizacional	4
PSR234	Psicologia Organizacional 2	4
SCO134	Salud Mental Comunitaria	4
SOG134	Sociologia General	4
TDC135	Tecnologia de la Construccion 1	3
CGR135	Comunicacion Espacial Grafica 1	3
FIR135	Fisica 1	4
CGR235	Comunicacion Espacial Grafica 2	3
QTR135	Quimica Tecnica	4
HSE135	Historia Social y Economica de El Salvador y Centroamerica	4
FIR235	Fisica 2	4
MSO135	Mecanica de los Solidos 1	4
MAT335	Matematica 3	4
PYE135	Probabilidad y Estadistica	4
IAI135	Introduccion a la Informatica	4
FIR335	Fisica 3	4
MSO235	Mecanica de los Solidos 2	4
MAT435	Matematica 4	4
IEC135	Ingenieria Economica	4
MSM135	Manejo de Software para Microcomputadoras	4
PRN135	Programacion 1	4
PRN235	Programacion 2	4
FDE135	Fundamentos de Economia	4
ESD135	Estructura de Datos	4
PRN335	Programacion 3	4
MEP135	Metodos Probabilisticos	4
ING234	Ingles 2	4
PSC234	Psicopedagogia 2	5
RTA134	Redaccion de Textos Academicos	4
DIG134	Didactica General	5
HDI134	Historia de las Ideas Esteticas	4
MOR134	Morfosintaxis	4
TLI134	Teoria Literaria 1	4
ECU134	Estudios Culturales 1	4
IDV134	Informatica Educativa	5
LIC134	Literatura Clasica 1	4
MOR234	Morfosintaxis 2	4
\.


--
-- Name: agrupacion_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY agrupacion
    ADD CONSTRAINT agrupacion_pk PRIMARY KEY (id_depar, cod_materia);


--
-- Name: aulas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY aulas
    ADD CONSTRAINT aulas_pkey PRIMARY KEY (cod_aula);


--
-- Name: carreras_materias_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY carreras_materias
    ADD CONSTRAINT carreras_materias_pk PRIMARY KEY (carreras_plan_estudio, materias_cod_materia, carreras_id_carrera);


--
-- Name: carreras_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY carreras
    ADD CONSTRAINT carreras_pk PRIMARY KEY (id_carrera, plan_estudio);


--
-- Name: departamentos_pk; Type: CONSTRAINT; Schema: public; Owner: horarios_bd; Tablespace: 
--

ALTER TABLE ONLY departamentos
    ADD CONSTRAINT departamentos_pk PRIMARY KEY (id_depar);


--
-- Name: dias_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY dias
    ADD CONSTRAINT dias_pkey PRIMARY KEY (nombre_dia);


--
-- Name: horas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY horas
    ADD CONSTRAINT horas_pkey PRIMARY KEY (id_hora);


--
-- Name: materias_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY materias
    ADD CONSTRAINT materias_pk PRIMARY KEY (cod_materia);


--
-- Name: agrupacion_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY carreras_materias
    ADD CONSTRAINT agrupacion_fk FOREIGN KEY (materias_cod_materia, agrupacion_id_depar) REFERENCES agrupacion(cod_materia, id_depar) ON DELETE CASCADE;


--
-- Name: carreras_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY carreras_materias
    ADD CONSTRAINT carreras_fk FOREIGN KEY (carreras_id_carrera, carreras_plan_estudio) REFERENCES carreras(id_carrera, plan_estudio);


--
-- Name: depar_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY agrupacion
    ADD CONSTRAINT depar_fk FOREIGN KEY (id_depar) REFERENCES departamentos(id_depar);


--
-- Name: departamentos_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY carreras
    ADD CONSTRAINT departamentos_fk FOREIGN KEY (id_depar) REFERENCES departamentos(id_depar);


--
-- Name: materia_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY agrupacion
    ADD CONSTRAINT materia_fk FOREIGN KEY (cod_materia) REFERENCES materias(cod_materia);


--
-- Name: materias_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY carreras_materias
    ADD CONSTRAINT materias_fk FOREIGN KEY (materias_cod_materia) REFERENCES materias(cod_materia);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- Name: aulas; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE aulas FROM PUBLIC;
REVOKE ALL ON TABLE aulas FROM postgres;
GRANT ALL ON TABLE aulas TO postgres;
GRANT ALL ON TABLE aulas TO horarios_bd;


--
-- Name: dias; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE dias FROM PUBLIC;
REVOKE ALL ON TABLE dias FROM postgres;
GRANT ALL ON TABLE dias TO postgres;
GRANT ALL ON TABLE dias TO horarios_bd;


--
-- Name: horas; Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON TABLE horas FROM PUBLIC;
REVOKE ALL ON TABLE horas FROM postgres;
GRANT ALL ON TABLE horas TO postgres;
GRANT ALL ON TABLE horas TO horarios_bd;


--
-- PostgreSQL database dump complete
--
