INSERT INTO user_group
VALUES (1, NOW(), NOW(), 0, 'a');

ALTER SEQUENCE sq_user_group
  RESTART WITH 2;

INSERT INTO permission
VALUES (1, now(), now(), 0, 'TEXT', 1),
       (2, now(), now(), 0, 'USER', 1),
       (3, now(), now(), 0, 'USER_GROUP', 1);

ALTER SEQUENCE sq_permission
  RESTART WITH 4;

INSERT INTO permission_permission_type
VALUES ('ADD', 1),
       ('DELETE', 1),
       ('EDIT', 1),
       ('VIEW', 1),
       ('ADD', 2),
       ('DELETE', 2),
       ('EDIT', 2),
       ('RESET_PASSWORD', 2),
       ('VIEW', 2),
       ('ADD', 3),
       ('DELETE', 3),
       ('EDIT', 3),
       ('VIEW', 3);

INSERT INTO app_user
VALUES (1, now(), now(), 0, 'a', '$2a$10$EebXKqyOXfoYoz6bhcXyzeTrQqd3xXsvImcuFJIdIIbky0ihQOdEi', 'a', 'nikoloz.kvaratskhelia@helmes.ee', 'ACTIVE', 'EN', 1);

ALTER SEQUENCE sq_app_user
  RESTART WITH 2;