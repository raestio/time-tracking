FROM    openjdk:11

ENV DOCKERIZE_VERSION v0.6.0
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz

RUN     mkdir /home/time-tracking
WORKDIR /home/time-tracking/
COPY    . ./
RUN     ./gradlew clean build -x test

EXPOSE 8080
CMD dockerize -wait tcp://db:5432 -timeout 60s \
    java -jar /home/time-tracking/app/build/libs/time-tracking-app-1.0-SNAPSHOT.jar