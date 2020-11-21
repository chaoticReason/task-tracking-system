CREATE DATABASE dbts;

USE dbts;

CREATE TABLE users
(
    id              INT unsigned NOT NULL AUTO_INCREMENT,
    email           VARCHAR(150) NOT NULL,
    name            VARCHAR(150) NOT NULL,
    surname         VARCHAR(150),
    password        VARCHAR(150) NOT NULL,
    PRIMARY KEY     (id)
);

INSERT INTO users ( email, name, surname, password) VALUES
  ( 'nastya@gmail.com', 'Анастасия', 'Сибирская', 'Sibir1sky' );