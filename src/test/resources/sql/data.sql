INSERT INTO news (id, date, title, text)
VALUES (1, '2022-09-30T17:57:55.955', 'Soccer World Cup', 'Championship final'),
       (2, '2022-09-30T19:37:55.945', 'Day of the city', 'The concert will take place on November 21'),
       (3, '2022-10-30T08:27:55.955', 'Weather', 'It will be colder next week'),
       (4, '2022-11-01T13:17:35.932', 'Supermarket opening', 'Supermarket open this weekend'),
       (5, '2022-11-16T16:36:55.696', 'Fair', 'A week-long fair will be held in our city'),
       (6, '2022-12-29T11:10:55.345', 'New Year', 'New Year');
SELECT SETVAL('news_id_seq', (SELECT max(id) FROM news));

INSERT INTO users (id, username, firstname, lastname, role)
VALUES (1, 'ruslan@mail.ru', 'Ruslan', 'Niyazou', 'ADMIN'),
       (2, 'ivan@mail.ru', 'Ivan', 'Ivanov', 'USER'),
       (3, 'petr@mail.ru', 'Petr', 'Petrov', 'USER');
SELECT SETVAL('users_id_seq', (SELECT max(id) FROM users));

INSERT INTO comment (id, date, text, user_id, news_id)
VALUES (1, '2022-09-30T19:57:55.955', 'Cool', 1, 1),
       (2, '2022-09-30T21:26:55.955', 'I can not wait', 2, 2),
       (3, '2022-10-30T21:26:55.955', 'It is very bad', 3, 3),
       (4, '2022-11-02T11:26:55.955', 'This is wonderful', 2, 4),
       (5, '2022-12-31T21:26:55.955', 'Happy New Year', 1, 5);
SELECT SETVAL('comment_id_seq', (SELECT max(id) FROM comment));