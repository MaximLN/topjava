DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (userid, datetime, description, calories)
VALUES (100000, '2022-02-21 14:00:00 +02:00', 'descriptionuser1', 100),
       (100000, '2022-02-21 12:00:00 +02:00', 'descriptionuser2', 1000),
       (100000, '2022-02-20 11:00:00 +02:00', 'descriptionuser3', 2000),
       (100000, '2022-02-18 00:00:00 +02:00', 'descriptionuser4', 100),
       (100000, '2022-02-17 22:00:00 +02:00', 'descriptionuser5', 1200),
       (100000, '2022-02-21 21:00:00 +02:00', 'descriptionuser6', 200),
       (100000, '2022-02-21 23:00:00 +02:00', 'descriptionuser7', 1500),
       (100001, '2022-02-21 14:00:00 +02:00', 'description1Admin', 100),
       (100001, '2022-02-21 12:00:00 +02:00', 'description2Admin', 1000),
       (100001, '2022-02-20 11:00:00 +02:00', 'descriptio3Admin', 2000),
       (100001, '2022-02-18 9:00:00 +02:00', 'description4Admin', 100),
       (100001, '2022-02-17 22:00:00 +02:00', 'description5Admin', 1200),
       (100001, '2022-02-21 21:00:00 +02:00', 'description6Admin', 200),
       (100001, '2022-02-21 23:00:00 +02:00', 'description7Admin', 1500);