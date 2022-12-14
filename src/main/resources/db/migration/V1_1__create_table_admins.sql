CREATE TABLE IF NOT EXISTS carwash.admins (
    id integer NOT NULL,
    name character varying NOT NULL UNIQUE,
    password character varying NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE carwash.admins ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME carwash.admins_id_seq
    START WITH 1
    INCREMENT BY 1
    CACHE 1
);