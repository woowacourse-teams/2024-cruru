FROM openjdk:17-jdk-slim

ARG JAR_FILE=/build/libs/cruru-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /cruru.jar

ARG PROFILE
ENV PROFILE_ENV=${PROFILE}
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILE_ENV}", "/cruru.jar"]
