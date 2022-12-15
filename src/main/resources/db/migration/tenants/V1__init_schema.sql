CREATE TABLE tbl_users
(
    id     SERIAL PRIMARY KEY,
    full_name character varying(250),
    email character varying(250),
    phone character varying(250),
    tenant_id character varying(250)
);