DROP TABLE IF EXISTS article_domain;
DROP TABLE IF EXISTS level;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS article;
DROP TABLE IF EXISTS domain;



CREATE TABLE domain
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE account
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100),
    email       VARCHAR(50),
    role        VARCHAR(10),
    description VARCHAR(100)
);

CREATE TABLE article
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50),
    posted_date DATE,
    description VARCHAR(100),
    visibility  VARCHAR(10),
    document    VARCHAR(100),
    writer_id   BIGINT
);

CREATE TABLE review
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    feedback   VARCHAR(500),
    grade      FLOAT,
    user_id    BIGINT,
    article_id BIGINT
);

