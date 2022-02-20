DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;
ALTER SEQUENCE mealsid_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (userid, datetime, description, calories)
VALUES (100000, '2012-08-24 14:00:00 +02:00', 'description1', 100),
       (100000, '2012-08-25 14:00:00 +02:00', 'description11', 1200),
       (100002, '2012-08-27 14:00:00 +02:00', 'description2', 500);