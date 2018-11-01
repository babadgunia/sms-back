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
  permission_type VARCHAR(255) NOT NULL,
  permission_id   BIGINT       NOT NULL REFERENCES permission ON DELETE CASCADE
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
  user_group_id BIGINT    NOT NULL REFERENCES user_group ON DELETE CASCADE
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

-- building

CREATE TABLE building (
  id            BIGINT PRIMARY KEY,
  created       TIMESTAMP NOT NULL,
  last_modified TIMESTAMP NOT NULL,
  version       BIGINT    NOT NULL,
  name          VARCHAR(255),
  address       VARCHAR(255),
  lat           DOUBLE PRECISION,
  lon           DOUBLE PRECISION
);

CREATE SEQUENCE sq_building
  START WITH 1
  INCREMENT BY 1;

-- auditorium

CREATE TABLE auditorium (
  id            BIGINT PRIMARY KEY,
  created       TIMESTAMP NOT NULL,
  last_modified TIMESTAMP NOT NULL,
  version       BIGINT    NOT NULL,
  name          VARCHAR(255),
  building_id   BIGINT    NOT NULL REFERENCES building ON DELETE CASCADE
);

CREATE SEQUENCE sq_auditorium
  START WITH 1
  INCREMENT BY 1;

CREATE TABLE auditorium_seat (
  seat          INTEGER NOT NULL,
  vacant        BOOLEAN NOT NULL,
  auditorium_id BIGINT  NOT NULL REFERENCES auditorium ON DELETE CASCADE
);

-- faculty

CREATE TABLE faculty (
  id            BIGINT PRIMARY KEY,
  created       TIMESTAMP NOT NULL,
  last_modified TIMESTAMP NOT NULL,
  version       BIGINT    NOT NULL,
  name          VARCHAR(255)
);

CREATE SEQUENCE sq_faculty
  START WITH 1
  INCREMENT BY 1;

-- course

CREATE TABLE course (
  id            BIGINT PRIMARY KEY,
  created       TIMESTAMP NOT NULL,
  last_modified TIMESTAMP NOT NULL,
  version       BIGINT    NOT NULL,
  name          VARCHAR(255),
  num_credits   INTEGER,
  max_students  INTEGER,
  syllabus      OID,
  faculty_id    BIGINT    NOT NULL REFERENCES faculty ON DELETE CASCADE
);

CREATE SEQUENCE sq_course
  START WITH 1
  INCREMENT BY 1;

CREATE TABLE course_semester (
  semester  VARCHAR(255) NOT NULL,
  course_id BIGINT       NOT NULL REFERENCES course ON DELETE CASCADE
);

CREATE TABLE course_prerequisite (
  course_id       BIGINT NOT NULL REFERENCES course ON DELETE CASCADE,
  prerequisite_id BIGINT NOT NULL REFERENCES course ON DELETE CASCADE
);

-- exam

CREATE TABLE exam (
  id            BIGINT PRIMARY KEY,
  created       TIMESTAMP NOT NULL,
  last_modified TIMESTAMP NOT NULL,
  version       BIGINT    NOT NULL,
  type          VARCHAR(255),
  max_grade     INTEGER,
  start_date    TIMESTAMP,
  end_date      TIMESTAMP,
  num_students  INTEGER,
  course_id     BIGINT    NOT NULL REFERENCES course ON DELETE CASCADE,
  auditorium_id BIGINT    NOT NULL REFERENCES auditorium ON DELETE CASCADE
);

CREATE SEQUENCE sq_exam
  START WITH 1
  INCREMENT BY 1;

-- module

CREATE TABLE module (
  id            BIGINT PRIMARY KEY,
  created       TIMESTAMP NOT NULL,
  last_modified TIMESTAMP NOT NULL,
  version       BIGINT    NOT NULL,
  name          VARCHAR(255),
  max_grade     INTEGER,
  course_id     BIGINT    NOT NULL REFERENCES course ON DELETE CASCADE
);

CREATE SEQUENCE sq_module
  START WITH 1
  INCREMENT BY 1;

-- lecturer

CREATE TABLE lecturer (
  id              BIGINT PRIMARY KEY,
  created         TIMESTAMP NOT NULL,
  last_modified   TIMESTAMP NOT NULL,
  version         BIGINT    NOT NULL,
  first_name      VARCHAR(255),
  last_name       VARCHAR(255),
  gender          VARCHAR(255),
  birth_date      DATE,
  personal_number VARCHAR(255),
  phone_number    VARCHAR(255),
  address         VARCHAR(255),
  email           VARCHAR(255),
  uni_email       VARCHAR(255),
  photo           OID,
  user_id         BIGINT    NOT NULL REFERENCES app_user ON DELETE CASCADE
);

CREATE SEQUENCE sq_lecturer
  START WITH 1
  INCREMENT BY 1;

-- student

CREATE TABLE student (
  id              BIGINT PRIMARY KEY,
  created         TIMESTAMP NOT NULL,
  last_modified   TIMESTAMP NOT NULL,
  version         BIGINT    NOT NULL,
  first_name      VARCHAR(255),
  last_name       VARCHAR(255),
  gender          VARCHAR(255),
  birth_date      DATE,
  personal_number VARCHAR(255),
  phone_number    VARCHAR(255),
  address         VARCHAR(255),
  email           VARCHAR(255),
  uni_email       VARCHAR(255),
  photo           OID,
  semester        INTEGER,
  scholarship     INTEGER,
  major_id        BIGINT    NOT NULL REFERENCES faculty ON DELETE CASCADE,
  minor_id        BIGINT    NOT NULL REFERENCES faculty ON DELETE CASCADE,
  user_id         BIGINT    NOT NULL REFERENCES app_user ON DELETE CASCADE
);

CREATE SEQUENCE sq_student
  START WITH 1
  INCREMENT BY 1;

-- group

CREATE TABLE uni_group (
  id            BIGINT PRIMARY KEY,
  created       TIMESTAMP NOT NULL,
  last_modified TIMESTAMP NOT NULL,
  version       BIGINT    NOT NULL,
  number        INTEGER,
  day_of_week   VARCHAR(255),
  start_time    TIME,
  end_time      TIME,
  module_id     BIGINT    NOT NULL REFERENCES module ON DELETE CASCADE,
  auditorium_id BIGINT    NOT NULL REFERENCES auditorium,
  lecturer_id   BIGINT    NOT NULL REFERENCES lecturer
);

CREATE SEQUENCE sq_uni_group
  START WITH 1
  INCREMENT BY 1;

CREATE TABLE uni_group_student (
  group_id   BIGINT NOT NULL REFERENCES uni_group ON DELETE CASCADE,
  student_id BIGINT NOT NULL REFERENCES student ON DELETE CASCADE
);

-- student course

CREATE TABLE student_course (
  id               BIGINT PRIMARY KEY,
  created          TIMESTAMP NOT NULL,
  last_modified    TIMESTAMP NOT NULL,
  version          BIGINT    NOT NULL,
  semester         VARCHAR(255),
  year             VARCHAR(255),
  attendance_grade INTEGER,
  student_id       BIGINT    NOT NULL REFERENCES student ON DELETE CASCADE,
  course_id        BIGINT    NOT NULL REFERENCES course ON DELETE CASCADE
);

CREATE SEQUENCE sq_student_course
  START WITH 1
  INCREMENT BY 1;

CREATE TABLE student_course_module_grade (
  module_type       VARCHAR(255),
  grade             INTEGER NOT NULL,
  student_course_id BIGINT  NOT NULL REFERENCES student_course ON DELETE CASCADE
);

CREATE TABLE student_course_exam_grade (
  exam_type         VARCHAR(255),
  grade             INTEGER NOT NULL,
  student_course_id BIGINT  NOT NULL REFERENCES student_course ON DELETE CASCADE
);

CREATE TABLE student_course_module_group (
  student_course_id BIGINT NOT NULL REFERENCES student_course ON DELETE CASCADE,
  module_id         BIGINT NOT NULL REFERENCES module ON DELETE CASCADE,
  group_id          BIGINT NOT NULL REFERENCES uni_group ON DELETE CASCADE
);