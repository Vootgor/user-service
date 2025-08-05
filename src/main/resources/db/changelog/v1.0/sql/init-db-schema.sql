CREATE TYPE Role AS ENUM('CUSTOMER', 'EMPLOYEE', 'ADMIN');

CREATE TABLE users
(
    id              UUID PRIMARY KEY,
    email           VARCHAR(255)     NOT NULL UNIQUE,
    password        VARCHAR(255)     NOT NULL,
    name            VARCHAR(100),
    last_name       VARCHAR(100),
    phone_number    VARCHAR(20),
    role            Role        NOT NULL DEFAULT 'ADMIN',
    created         TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated         TIMESTAMPTZ,
    email_verified  BOOLEAN     NOT NULL DEFAULT FALSE,
    is_active       BOOLEAN     NOT NULL DEFAULT TRUE
);