--liquibase formatted sql

--changeset rniyazov:1
CREATE TABLE IF NOT EXISTS news
(
    id    SERIAL PRIMARY KEY,
    date  TIMESTAMP          NOT NULL,
    title VARCHAR(32) UNIQUE NOT NULL,
    text  VARCHAR(128)       NOT NULL
    );

--changeset rniyazov:2
CREATE TABLE IF NOT EXISTS users
(
    id        SERIAL PRIMARY KEY,
    username  VARCHAR(32) UNIQUE NOT NULL,
    firstname VARCHAR(32)        NOT NULL,
    lastname  VARCHAR(32)        NOT NULL
    );

--changeset rniyazov:3
CREATE TABLE IF NOT EXISTS comment
(
    id    SERIAL PRIMARY KEY,
    date  TIMESTAMP          NOT NULL,
    text  VARCHAR(128)       NOT NULL,
    username  VARCHAR(32) REFERENCES users (username) ON DELETE CASCADE,
    news_id INT REFERENCES news (id) ON DELETE CASCADE
    );
