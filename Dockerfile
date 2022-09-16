#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM adoptopenjdk/openjdk11:latest
COPY --from=build /app/target/*.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
