FROM postgres:15.10-alpine

#ENV POSTGRES_DB=mydb
#ENV POSTGRES_USER=postgres
#ENV POSTGRES_PASSWORD=password

ENV POSTGRES_DB=${DB_NAME}
ENV POSTGRES_USER=${DB_USER}
ENV POSTGRES_PASSWORD=${DB_PASSWORD}

COPY ./create_tables.sql /docker-entrypoint-initdb.d/
COPY ./init_database.sql /docker-entrypoint-initdb.d/

CMD ["postgres"]
