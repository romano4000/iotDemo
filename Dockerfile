FROM adoptopenjdk/openjdk11:latest
MAINTAINER Ruben Romano Poveda
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]