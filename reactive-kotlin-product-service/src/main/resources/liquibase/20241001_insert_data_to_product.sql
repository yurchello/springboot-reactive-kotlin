--liquibase formatted sql
--changeset reactiveprod:create-tables

INSERT INTO reactive_prod.product(name, price) VALUES
('Test Description 1', 1.1),
('Test Description 2', 1.1),
('Test Description 3', 1.1),
('Test Description 4', 1.1),
('Test Description 5', 1.1),
('Test Description 6', 1.1),
('Test Description 7', 1.1),
('Test Description 8', 1.1);