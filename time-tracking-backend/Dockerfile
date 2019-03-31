FROM    openjdk:11

ENV DOCKERIZE_VERSION v0.6.0
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz

RUN     useradd --create-home time-tracking --shell /bin/bash --password kfwef5484$56@qQ
USER    time-tracking
WORKDIR /home/time-tracking/
COPY    build/libs/time-tracking-1.0.0-SNAPSHOT.jar /home/time-tracking

CMD dockerize -wait tcp://db:5432 -timeout 60s \
    java -jar time-tracking-1.0.0-SNAPSHOT.jar