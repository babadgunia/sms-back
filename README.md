**SMS (Student management system)**
SMS-back is monolith web application backend to manage university students, faculties, exams, courses etc.

**Technology stack**
Java 8
Spring boot
Docker
Postgresql
Liquibase

**Travis CI**
[![Build Status](https://travis-ci.org/babadgunia/sms-back.svg?branch=master)](https://travis-ci.org/babadgunia/sms-back)

**To build APP Docker image use this command**

docker build -t IMAGE_NAME .

**To build Database Docker image use this command from project home directory**

 docker build -t IMAGE_NAME -f src/main/resources/db/Dockerfile .
 
**Database setup**
Once docker container is running liquibase will automatically insert initial values into the database.