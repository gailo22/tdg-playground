FROM openjdk:11.0.10-jre-slim
RUN mkdir /app
WORKDIR /app
COPY api.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]