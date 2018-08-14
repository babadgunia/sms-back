CREATE DATABASE sms;
\connect sms;
CREATE SCHEMA sms;

CREATE USER sms WITH PASSWORD 'sms';

GRANT ALL PRIVILEGES ON DATABASE sms TO sms;
GRANT ALL PRIVILEGES ON SCHEMA sms TO sms;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA sms TO sms;
CREATE TABLE sms.user_group
(
    id bigint NOT NULL,
    creation_time timestamp,
    last_modified_time timestamp,
    version integer NOT NULL,
    name character varying(255),
    CONSTRAINT user_group_pkey PRIMARY KEY (id)
);

CREATE TABLE sms.permission
(
    id bigint NOT NULL,
    creation_time timestamp,
    last_modified_time timestamp,
    version integer NOT NULL,
    permission_group character varying(255),
    user_group_id bigint,
    CONSTRAINT permission_pkey PRIMARY KEY (id),
    CONSTRAINT fk_permission_user_group_id FOREIGN KEY (user_group_id)
        REFERENCES sms.user_group (id) MATCH SIMPLE
);

CREATE TABLE sms.permission_permission_types
(
    permission_id bigint NOT NULL,
    permission_types character varying(255),
    CONSTRAINT fk_permission_id FOREIGN KEY (permission_id)
        REFERENCES sms.permission (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE sms.application_user
(
    id bigint NOT NULL,
    creation_time timestamp,
    last_modified_time timestamp,
    version integer NOT NULL,
    email character varying(255),
    language character varying(255),
    name character varying(255),
    password character varying(255),
    status character varying(255),
    username character varying(255),
    user_group_id bigint,
    created_by character varying(50),
    created_date timestamp NOT NULL,
    last_modified_by character varying(50),
    last_modified_date timestamp,
    CONSTRAINT application_user_pkey PRIMARY KEY (id),
    CONSTRAINT fk_application_user_user_group FOREIGN KEY (user_group_id)
        REFERENCES sms.user_group (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE SEQUENCE sms.sms_user_group_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE sms.application_user_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE sms.sms_permission_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- Data Insertion

 INSERT INTO sms.user_group VALUES (1, now(), now(), 0, 'a');
 COMMIT;
 ALTER SEQUENCE sms.sms_user_group_seq RESTART WITH 2;
 INSERT INTO sms.application_user VALUES (1, now(), now(), 0, 'nikoloz.kvaratskhelia@helmes.ee', 'EN', 'a', '$2a$10$EebXKqyOXfoYoz6bhcXyzeTrQqd3xXsvImcuFJIdIIbky0ihQOdEi', 'ACTIVE', 'a', 1, 'system', now(), now(), now());
 COMMIT;
 ALTER SEQUENCE sms.application_user_seq RESTART WITH 2;
 INSERT INTO sms.permission VALUES (1, now(), now(), 0, 'TEXT', 1),
   (2, now(), now(), 0, 'USER', 1),
   (3, now(), now(), 0, 'USER_GROUP', 1);
 COMMIT;
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
 COMMIT;