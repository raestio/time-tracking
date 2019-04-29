FROM    docker.elastic.co/logstash/logstash:7.0.0

#Remove default pipeline conf
RUN     rm -f /usr/share/logstash/pipeline/logstash.conf

#Copy config
ADD     pipeline/ /usr/share/logstash/pipeline/
ADD     config/ /usr/share/logstash/config/

#Add jdbc for jdbc input plugin
COPY    jdbc /usr/share/jdbc

#Set environment variables
ENV     JDBC_DRIVER_LIBRARY=/usr/share/jdbc/postgresql-42.2.5.jar
ENV     JDBC_DRIVER_CLASS=org.postgresql.Driver
ENV     JDBC_CONNECTION_STRING=jdbc:postgresql://db:5432/time_tracking
ENV     JDBC_USER=postgres
ENV     JDBC_PASSWORD=a63$%a<sdAZ@95(p))>
ENV     ELASTICSEARCH_HOST=localhost:9200
ENV     ELASTICSEARCH_PROJECTS_INDEX_NAME=projects
ENV     ELASTICSEARCH_USERS_INDEX_NAME=users
ENV     ELASTICSEARCH_WORK_RECORDS_INDEX_NAME=work_records