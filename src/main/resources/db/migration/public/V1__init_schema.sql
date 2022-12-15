CREATE TABLE IF NOT EXISTS data_source_config
(
    id  SERIAL PRIMARY KEY,
    name  character varying(250) NOT NULL,
    url   character varying(250) NOT NULL,
    username  character varying(250) NOT NULL,
    password  character varying(250) NOT NULL,
    driver_class_name  character varying(250) NOT NULL,
    initialize boolean
    );