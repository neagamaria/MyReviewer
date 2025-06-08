drop table if exists article_domain;
drop table if exists review;
drop table if exists level;
drop table if exists article;
drop table if exists domain;
drop table if exists acocunt;


CREATE TABLE account (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         email VARCHAR(255) NOT NULL UNIQUE,
                         role VARCHAR(50) NOT NULL,
                         description VARCHAR(1000)
);

CREATE TABLE article (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         posted_date DATE,
                         description VARCHAR(1000),
                         visibility VARCHAR(50),
                         document VARCHAR(255),
                         writer_id BIGINT,
                         level VARCHAR(50),
                         FOREIGN KEY (writer_id) REFERENCES account(id)
);

CREATE TABLE domain (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        image VARCHAR(255)
);

CREATE TABLE article_domain (
                                domain_id BIGINT NOT NULL,
                                article_id BIGINT NOT NULL,
                                PRIMARY KEY (domain_id, article_id),
                                FOREIGN KEY (domain_id) REFERENCES domain(id),
                                FOREIGN KEY (article_id) REFERENCES article(id)
);


CREATE TABLE review (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        feedback VARCHAR(1000),
                        grade FLOAT NOT NULL,
                        posted_date TIMESTAMP,
                        reviewer_id BIGINT,
                        article_id BIGINT,
                        FOREIGN KEY (reviewer_id) REFERENCES account(id),
                        FOREIGN KEY (article_id) REFERENCES article(id)
);


