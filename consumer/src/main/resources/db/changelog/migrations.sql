--liquibase formatted sql

--changeset Hanna Yafremava:create-initial-tables runInTransaction:false
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE client
(
    id                         INT8         NOT NULL PRIMARY KEY,
    email                      VARCHAR,
    blank                      BOOLEAN
);
--rollback drop table client;

CREATE TABLE transaction_type
(
    id                         INT4         NOT NULL PRIMARY KEY,
    name                       VARCHAR      NOT NULL
);
--rollback drop table transaction_type;

CREATE TABLE "transaction"
(
    id                         UUID         NOT NULL DEFAULT uuid_generate_v4() PRIMARY KEY,
    client_id                  INT8         NOT NULL REFERENCES client(id),
    bank                       VARCHAR      NOT NULL,
    transaction_type_id        INT8         NOT NULL REFERENCES transaction_type(id),
    quantity                   int4         NOT NULL,
    price                      FLOAT        NOT NULL,
    cost                       NUMERIC(19, 2) NOT NULL,
    created_at                 TIMESTAMP    NOT NULL
);

CREATE INDEX idx_transaction_type ON "transaction" (transaction_type_id);
CREATE INDEX idx_transaction_client ON "transaction" (client_id);
--rollback drop table transaction;

--changeset Hanna Yafremava:insert-data runInTransaction:false
INSERT INTO transaction_type (id, name)
VALUES (1, 'INCOME'),
       (2, 'OUTCOME');
--rollback delete from transaction_type;