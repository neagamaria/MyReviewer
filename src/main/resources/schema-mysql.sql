drop table if exists article_domain;
drop table if exists review;
drop table if exists article;
drop table if exists level;
drop table if exists domain;
drop table if exists account;




create table domain
(
    id   bigint auto_increment primary key,
    name varchar(50)
);

create table account
(
    id          bigint auto_increment primary key,
    name        varchar(100),
    email       varchar(50),
    role        varchar(10),
    description varchar(100)
);

create table article
(
    id          bigint auto_increment primary key,
    name        varchar(50),
    posted_date date,
    description varchar(100),
    visibility  varchar(10),
    document    varchar(100),
    writer_id   bigint
);

create table review
(
    id         bigint auto_increment primary key,
    feedback   varchar(500),
    grade      float,
    reviewer_id    bigint,
    article_id bigint
);

alter table article add foreign key (writer_id) references account(id);

alter table review add foreign key (reviewer_id) references account(id);

alter table review add foreign key (article_id) references article(id);


create table article_domain (
        article_id bigint,
        domain_id bigint,
        foreign key (article_id) references article(id),
        foreign key (domain_id) references domain(id),
        primary key (article_id, domain_id)
);