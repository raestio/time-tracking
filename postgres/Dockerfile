FROM postgres:11-alpine

ENV POSTGRES_PASSWORD a63$%a<sdAZ@95(p))>
ENV POSTGRES_USER postgres
ENV POSTGRES_DB time_tracking
EXPOSE 5432
COPY sql/ /docker-entrypoint-initdb.d/
