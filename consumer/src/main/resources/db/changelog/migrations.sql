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
    cost                       NUMERIC(10, 2) NOT NULL,
    created_at                 TIMESTAMP    NOT NULL
);

CREATE INDEX idx_transaction_type ON "transaction" (transaction_type_id);
CREATE INDEX idx_transaction_client ON "transaction" (client_id);
--rollback drop table transaction;

--changeset Hanna Yafremava:insert-data runInTransaction:false
INSERT INTO transaction_type (id, name)
VALUES (1, 'INCOME'),
       (2, 'OUTCOME');

INSERT INTO client (id, email, blank)
VALUES (1, 'test@test.com', false),
       (2, 'test2@test.com', false);

INSERT INTO transaction (id, client_id, bank, transaction_type_id, quantity, price, cost, created_at)
VALUES ('c27396ee-7562-4526-b707-832fc14cf391', 1, 'bank1', 2, 1, 10, 10, CURRENT_DATE - INTERVAL '1 day'),
       ('c27396ee-7562-4526-b707-832fc14cf392', 1, 'bank2', 2, 1, 100, 100, CURRENT_DATE - INTERVAL '1 day'),
       ('c27396ee-7562-4526-b707-832fc14cf393', 2, 'bank1', 2, 2, 100, 200, CURRENT_DATE - INTERVAL '1 day');
--rollback delete from transaction;
--rollback delete from client;
--rollback delete from transaction_type;