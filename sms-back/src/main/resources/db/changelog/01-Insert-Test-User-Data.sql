 INSERT INTO sms.user_group VALUES (1, now(), now(), 0, 'a');
 ALTER SEQUENCE sms.sms_user_group_seq RESTART WITH 2;

 INSERT INTO sms.application_user VALUES (1, now(), now(), 0, 'nikoloz.kvaratskhelia@helmes.ee', 'EN', 'a', '$2a$10$EebXKqyOXfoYoz6bhcXyzeTrQqd3xXsvImcuFJIdIIbky0ihQOdEi', 'ACTIVE', 'a', 1, 'system', now(), now(), now());
 ALTER SEQUENCE sms.application_user_seq RESTART WITH 2;

 INSERT INTO sms.permission VALUES (1, now(), now(), 0, 'TEXT', 1),
   (2, now(), now(), 0, 'USER', 1),
   (3, now(), now(), 0, 'USER_GROUP', 1);
 ALTER SEQUENCE sms.sms_permission_seq RESTART WITH 4;

 INSERT INTO sms.permission_permission_types VALUES (1, 'ADD'),
   (1, 'DELETE'),
   (1, 'EDIT'),
   (1, 'VIEW'),
   (2, 'ADD'),
   (2, 'DELETE'),
   (2, 'EDIT'),
   (2, 'RESET_PASSWORD'),
   (2, 'VIEW'),
   (3, 'ADD'),
   (3, 'DELETE'),
   (3, 'EDIT'),
   (3, 'VIEW');