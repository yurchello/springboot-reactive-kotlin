--liquibase formatted sql
--changeset reactiveprod:create-tables

CREATE SCHEMA reactive_prod AUTHORIZATION postgres;

CREATE TABLE reactive_prod.product
(
    id BIGSERIAL,
    name VARCHAR,
    price float8,
    PRIMARY KEY (id)
);
