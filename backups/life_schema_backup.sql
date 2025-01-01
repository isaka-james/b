--
-- PostgreSQL database dump
--

-- Dumped from database version 17.2 (Debian 17.2-1.pgdg120+1)
-- Dumped by pg_dump version 17.2 (Debian 17.2-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
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
-- Name: behavior_logs; Type: TABLE; Schema: public; Owner: lifesupport
--

CREATE TABLE public.behavior_logs (
    id integer NOT NULL,
    behavior_id integer,
    log_date date NOT NULL,
    notes text,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.behavior_logs OWNER TO lifesupport;

--
-- Name: behavior_logs_id_seq; Type: SEQUENCE; Schema: public; Owner: lifesupport
--

CREATE SEQUENCE public.behavior_logs_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.behavior_logs_id_seq OWNER TO lifesupport;

--
-- Name: behavior_logs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: lifesupport
--

ALTER SEQUENCE public.behavior_logs_id_seq OWNED BY public.behavior_logs.id;


--
-- Name: behaviors; Type: TABLE; Schema: public; Owner: lifesupport
--

CREATE TABLE public.behaviors (
    id integer NOT NULL,
    behavior character varying(255) NOT NULL,
    target_date date,
    frequency character varying(50),
    status character varying(50) DEFAULT 'not started'::character varying,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.behaviors OWNER TO lifesupport;

--
-- Name: behaviors_id_seq; Type: SEQUENCE; Schema: public; Owner: lifesupport
--

CREATE SEQUENCE public.behaviors_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.behaviors_id_seq OWNER TO lifesupport;

--
-- Name: behaviors_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: lifesupport
--

ALTER SEQUENCE public.behaviors_id_seq OWNED BY public.behaviors.id;


--
-- Name: book_readings; Type: TABLE; Schema: public; Owner: lifesupport
--

CREATE TABLE public.book_readings (
    id integer NOT NULL,
    title character varying(255) NOT NULL,
    author character varying(255),
    genre character varying(100),
    start_date date,
    finish_date date,
    status character varying(50),
    rating integer,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT book_readings_rating_check CHECK (((rating >= 1) AND (rating <= 5))),
    CONSTRAINT book_readings_status_check CHECK (((status)::text = ANY (ARRAY[('not started'::character varying)::text, ('in progress'::character varying)::text, ('completed'::character varying)::text])))
);


ALTER TABLE public.book_readings OWNER TO lifesupport;

--
-- Name: book_readings_id_seq; Type: SEQUENCE; Schema: public; Owner: lifesupport
--

CREATE SEQUENCE public.book_readings_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.book_readings_id_seq OWNER TO lifesupport;

--
-- Name: book_readings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: lifesupport
--

ALTER SEQUENCE public.book_readings_id_seq OWNED BY public.book_readings.id;


--
-- Name: contacts; Type: TABLE; Schema: public; Owner: lifesupport
--

CREATE TABLE public.contacts (
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    phone character varying(20),
    email character varying(255),
    address text,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.contacts OWNER TO lifesupport;

--
-- Name: contacts_id_seq; Type: SEQUENCE; Schema: public; Owner: lifesupport
--

CREATE SEQUENCE public.contacts_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.contacts_id_seq OWNER TO lifesupport;

--
-- Name: contacts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: lifesupport
--

ALTER SEQUENCE public.contacts_id_seq OWNED BY public.contacts.id;


--
-- Name: events; Type: TABLE; Schema: public; Owner: lifesupport
--

CREATE TABLE public.events (
    id integer NOT NULL,
    title character varying(255) NOT NULL,
    description text,
    event_date timestamp without time zone NOT NULL,
    location character varying(255),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.events OWNER TO lifesupport;

--
-- Name: events_id_seq; Type: SEQUENCE; Schema: public; Owner: lifesupport
--

CREATE SEQUENCE public.events_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.events_id_seq OWNER TO lifesupport;

--
-- Name: events_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: lifesupport
--

ALTER SEQUENCE public.events_id_seq OWNED BY public.events.id;


--
-- Name: financials; Type: TABLE; Schema: public; Owner: lifesupport
--

CREATE TABLE public.financials (
    id integer NOT NULL,
    description character varying(255),
    amount numeric(12,2) NOT NULL,
    transaction_date date NOT NULL,
    category character varying(100),
    type character varying(50),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    user_id integer,
    CONSTRAINT financials_type_check CHECK (((type)::text = ANY (ARRAY[('income'::character varying)::text, ('expense'::character varying)::text])))
);


ALTER TABLE public.financials OWNER TO lifesupport;

--
-- Name: financials_id_seq; Type: SEQUENCE; Schema: public; Owner: lifesupport
--

CREATE SEQUENCE public.financials_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.financials_id_seq OWNER TO lifesupport;

--
-- Name: financials_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: lifesupport
--

ALTER SEQUENCE public.financials_id_seq OWNED BY public.financials.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: lifesupport
--

CREATE TABLE public.users (
    id integer NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(255),
    last_login timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    photo character varying(255) DEFAULT NULL::character varying,
    born date,
    first_name character varying(50) DEFAULT NULL::character varying,
    middle_name character varying(50) DEFAULT NULL::character varying,
    last_name character varying(50) DEFAULT NULL::character varying,
    phone_number character varying(50),
    role character varying(50),
    email character varying(50),
    gender character varying(50),
    nida character varying(50),
    CONSTRAINT role_check CHECK (((role)::text = ANY (ARRAY[('owner'::character varying)::text, ('admin'::character varying)::text, ('friend'::character varying)::text])))
);


ALTER TABLE public.users OWNER TO lifesupport;

--
-- Name: me_id_seq; Type: SEQUENCE; Schema: public; Owner: lifesupport
--

CREATE SEQUENCE public.me_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.me_id_seq OWNER TO lifesupport;

--
-- Name: me_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: lifesupport
--

ALTER SEQUENCE public.me_id_seq OWNED BY public.users.id;


--
-- Name: notes; Type: TABLE; Schema: public; Owner: lifesupport
--

CREATE TABLE public.notes (
    id integer NOT NULL,
    title character varying(255),
    content text,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.notes OWNER TO lifesupport;

--
-- Name: notes_id_seq; Type: SEQUENCE; Schema: public; Owner: lifesupport
--

CREATE SEQUENCE public.notes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.notes_id_seq OWNER TO lifesupport;

--
-- Name: notes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: lifesupport
--

ALTER SEQUENCE public.notes_id_seq OWNED BY public.notes.id;


--
-- Name: todos; Type: TABLE; Schema: public; Owner: lifesupport
--

CREATE TABLE public.todos (
    id integer NOT NULL,
    title character varying(255) NOT NULL,
    description text,
    due_date date,
    status character varying(50) DEFAULT 'pending'::character varying,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    user_id integer,
    completed_at timestamp without time zone,
    CONSTRAINT status_check CHECK (((status)::text = ANY ((ARRAY['completed'::character varying, 'pending'::character varying, 'cancelled'::character varying])::text[])))
);


ALTER TABLE public.todos OWNER TO lifesupport;

--
-- Name: todo_items_id_seq; Type: SEQUENCE; Schema: public; Owner: lifesupport
--

CREATE SEQUENCE public.todo_items_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.todo_items_id_seq OWNER TO lifesupport;

--
-- Name: todo_items_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: lifesupport
--

ALTER SEQUENCE public.todo_items_id_seq OWNED BY public.todos.id;


--
-- Name: behavior_logs id; Type: DEFAULT; Schema: public; Owner: lifesupport
--

ALTER TABLE ONLY public.behavior_logs ALTER COLUMN id SET DEFAULT nextval('public.behavior_logs_id_seq'::regclass);


--
-- Name: behaviors id; Type: DEFAULT; Schema: public; Owner: lifesupport
--

ALTER TABLE ONLY public.behaviors ALTER COLUMN id SET DEFAULT nextval('public.behaviors_id_seq'::regclass);


--
-- Name: book_readings id; Type: DEFAULT; Schema: public; Owner: lifesupport
--

ALTER TABLE ONLY public.book_readings ALTER COLUMN id SET DEFAULT nextval('public.book_readings_id_seq'::regclass);


--
-- Name: contacts id; Type: DEFAULT; Schema: public; Owner: lifesupport
--

ALTER TABLE ONLY public.contacts ALTER COLUMN id SET DEFAULT nextval('public.contacts_id_seq'::regclass);


--
-- Name: events id; Type: DEFAULT; Schema: public; Owner: lifesupport
--

ALTER TABLE ONLY public.events ALTER COLUMN id SET DEFAULT nextval('public.events_id_seq'::regclass);


--
-- Name: financials id; Type: DEFAULT; Schema: public; Owner: lifesupport
--

ALTER TABLE ONLY public.financials ALTER COLUMN id SET DEFAULT nextval('public.financials_id_seq'::regclass);


--
-- Name: notes id; Type: DEFAULT; Schema: public; Owner: lifesupport
--

ALTER TABLE ONLY public.notes ALTER COLUMN id SET DEFAULT nextval('public.notes_id_seq'::regclass);


--
-- Name: todos id; Type: DEFAULT; Schema: public; Owner: lifesupport
--

ALTER TABLE ONLY public.todos ALTER COLUMN id SET DEFAULT nextval('public.todo_items_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: lifesupport
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.me_id_seq'::regclass);


--
-- Name: users pk_user_id; Type: CONSTRAINT; Schema: public; Owner: lifesupport
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT pk_user_id PRIMARY KEY (id);


--
-- Name: financials fk_user; Type: FK CONSTRAINT; Schema: public; Owner: lifesupport
--

ALTER TABLE ONLY public.financials
    ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: todos todos_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lifesupport
--

ALTER TABLE ONLY public.todos
    ADD CONSTRAINT todos_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- PostgreSQL database dump complete
--

