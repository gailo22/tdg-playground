FROM gradle:jdk11 as build
USER gradle
WORKDIR /app
COPY --chown=gradle:gradle build.gradle .
COPY --chown=gradle:gradle src src
RUN gradle build

FROM openjdk:11.0.10-jre-slim
WORKDIR /app
COPY --from=build /app/build/libs/api.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
