CREATE TABLE tbl_users
(
    id     SERIAL PRIMARY KEY,
    fullName character varying(250),
    email character varying(250),
    phone character varying(250),
    tenantId character varying(250)
);