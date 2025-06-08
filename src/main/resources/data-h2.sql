insert into domain(id, name) values (1, 'Mathematics');
insert into account(id, name, email, role) values (1, 'User1', 'user1@test.com', 'writer');
insert into article(id, name, posted_date, visibility, writer_id, level) values (1, 'Article1', '2025-04-01', 'public', 1, 'BEGINNER');
INSERT INTO article_domain (domain_id, article_id) VALUES (1, 1);
insert into account(id, name, email, role) values (2, 'User2', 'user2@test.com', 'reviewer');
insert into review(id, grade, posted_date, reviewer_id, article_id) values (1, 7.50, '2025-04-02 12:00:00', 2, 1);

