FROM postgres:10.4
VOLUME postgres-data:/var/lib/postgresql/data
COPY src/main/resources/db/init_db.sql /docker-entrypoint-initdb.d/