FROM gradle:jdk11 AS builder
WORKDIR /my-app
COPY app app
RUN gradle build

FROM openjdk:11
WORKDIR /my-app
COPY --from=builder build/libs/app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]