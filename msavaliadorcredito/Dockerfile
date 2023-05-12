FROM maven:3.8.5-openjdk-11 as build
WORKDIR /app
COPY . .
RUN mvn package -Dmaven.test.skip=true

FROM openjdk:11
WORKDIR /app
COPY --from=build ./app/target/*.jar ./app.jar

ARG RABBITMQ_SERVER=rabbitmq-host
ARG EUREKA_SERVER=localhost

ENTRYPOINT java -jar app.jar