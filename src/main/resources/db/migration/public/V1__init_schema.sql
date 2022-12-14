CREATE TABLE IF NOT EXISTS datasourceconfig
(
    id  SERIAL PRIMARY KEY,
    name  character varying(250) NOT NULL,
    url   character varying(250) NOT NULL,
    username  character varying(250) NOT NULL,
    password  character varying(250) NOT NULL,
    driverClassName  character varying(250) NOT NULL,
    initialize boolean
    );