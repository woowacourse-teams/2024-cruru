FROM openjdk:17-jdk-slim

ARG JAR_FILE=/build/libs/cruru-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /cruru.jar

# 환경 변수를 직접 사용하여 PROFILE 설정 존재하지 않다면 `default` 키워드가 할당됨
ARG PROFILE
ENV PROFILE_ENV=${PROFILE:-default}

# Spring profile을 설정하여 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILE_ENV}", "/cruru.jar"]
