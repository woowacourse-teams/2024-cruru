FROM openjdk:17-jdk-slim

# JAR 파일을 직접 COPY하는 대신 빌드 컨텍스트에서 jar 파일을 직접 지정
COPY /build/libs/*.jar /cruru.jar

# 환경 변수를 직접 사용하여 PROFILE 설정 존재하지 않다면 `default` 키워드가 할당됨
ENV PROFILE_ENV=${PROFILE:-default}

# Spring profile을 설정하여 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILE_ENV}", "/cruru.jar"]
