FROM openjdk:13-jdk-alpine

ARG version
ENV version=$version

COPY target/authority-$version.jar /usr/spring/authority.jar

WORKDIR /usr/spring

RUN sh -c 'touch authority.jar'

ENTRYPOINT java -jar -Dspring.profiles.active=prod authority.jar