FROM openjdk:8u191-jdk-alpine3.9
MAINTAINER gailo22@gmail.com
EXPOSE 8080
VOLUME /tmp
ARG JAR_FILE=build/libs/functional-reactive-spring-demo-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=default","-XX:+UnlockExperimentalVMOptions","-XX:+UseCGroupMemoryLimitForHeap","-XX:MaxRAMFraction=2","/app.jar"]
