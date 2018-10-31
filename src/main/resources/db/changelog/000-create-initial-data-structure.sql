-- action log

CREATE TABLE action_log (
  id            BIGINT PRIMARY KEY,
  created       TIMESTAMP NOT NULL,
  last_modified TIMESTAMP NOT NULL,
  version       BIGINT    NOT NULL,
  type          VARCHAR(255),
  info          TEXT,
  username      VARCHAR(255),
  ip_address    VARCHAR(255)
);

CREATE SEQUENCE sq_action_log
  START WITH 1
  INCREMENT BY 1;

-- exception log

CREATE TABLE exception_log (
  id            BIGINT PRIMARY KEY,
  created       TIMESTAMP NOT NULL,
  last_modified TIMESTAMP NOT NULL,
  version       BIGINT    NOT NULL,
  type          VARCHAR(255),
  info          TEXT,
  stack_trace   TEXT
);

CREATE SEQUENCE sq_exception_log
  START WITH 1
  INCREMENT BY 1;

-- system parameter

CREATE TABLE system_parameter (
  id            BIGINT PRIMARY KEY,
  created       TIMESTAMP NOT NULL,
  last_modified TIMESTAMP NOT NULL,
  version       BIGINT    NOT NULL,
  group_type    VARCHAR(255),
  key           VARCHAR(255),
  value         VARCHAR(255),
  type          VARCHAR(255),
  unit          VARCHAR(255)
);

CREATE SEQUENCE sq_system_parameter
  START WITH 1
  INCREMENT BY 1;

-- text

CREATE TABLE text (
  id            BIGINT PRIMARY KEY,
  created       TIMESTAMP NOT NULL,
  last_modified TIMESTAMP NOT NULL,
  version       BIGINT    NOT NULL,
  key           VARCHAR(255)
);

CREATE SEQUENCE sq_text
  START WITH 1
  INCREMENT BY 1;

-- i18n text

CREATE TABLE i18n_text (
  id            BIGINT PRIMARY KEY,
  created       TIMESTAMP NOT NULL,
  last_modified TIMESTAMP NOT NULL,
  version       BIGINT    NOT NULL,
  value         VARCHAR(255),
  language      VARCHAR(255),
  text_id       BIGINT    NOT NULL REFERENCES text ON DELETE CASCADE
);

CREATE SEQUENCE sq_i18n_text
  START WITH 1
  INCREMENT BY 1;

-- user group

CREATE TABLE user_group (
  id            BIGINT PRIMARY KEY,
  created       TIMESTAMP NOT NULL,
  last_modified TIMESTAMP NOT NULL,
  version       BIGINT    NOT NULL,
  name          VARCHAR(255)
);

CREATE SEQUENCE sq_user_group
  START WITH 1
  INCREMENT BY 1;

-- permission

CREATE TABLE permission (
  id            BIGINT PRIMARY KEY,
  created       TIMESTAMP NOT NULL,
  last_modified TIMESTAMP NOT NULL,
  version       BIGINT    NOT NULL,
  group_type    VARCHAR(255),
  user_group_id BIGINT    NOT NULL REFERENCES user_group ON DELETE CASCADE
);

CREATE SEQUENCE sq_permission
  START WITH 1
  INCREMENT BY 1;

CREATE TABLE permission_permission_type (
  permission_type VARCHAR(255),
  permission_id   BIGINT NOT NULL REFERENCES permission ON DELETE CASCADE
);

-- user

CREATE TABLE app_user (
  id            BIGINT PRIMARY KEY,
  created       TIMESTAMP NOT NULL,
  last_modified TIMESTAMP NOT NULL,
  version       BIGINT    NOT NULL,
  username      VARCHAR(255),
  password      VARCHAR(255),
  name          VARCHAR(255),
  email         VARCHAR(255),
  status        VARCHAR(255),
  language      VARCHAR(255),
  user_group_id BIGINT    NOT NULL REFERENCES user_group
);

CREATE SEQUENCE sq_app_user
  START WITH 1
  INCREMENT BY 1;

-- password reset token

CREATE TABLE password_reset_token (
  id            BIGINT PRIMARY KEY,
  created       TIMESTAMP NOT NULL,
  last_modified TIMESTAMP NOT NULL,
  version       BIGINT    NOT NULL,
  token         VARCHAR(255),
  expiry_date   TIMESTAMP,
  user_id       BIGINT    NOT NULL REFERENCES app_user ON DELETE CASCADE
);

CREATE SEQUENCE sq_password_reset_token
  START WITH 1
  INCREMENT BY 1;