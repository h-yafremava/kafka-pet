
FROM eclipse-temurin:17-jdk-focal as builder2

WORKDIR /app

COPY ./producer/ /app/.

RUN ./gradlew clean build -x test

RUN ls -la /app/build/libs

FROM eclipse-temurin:17-jdk-focal
WORKDIR /app
COPY --from=builder2 /app/build/libs /app

RUN ls -la /app

EXPOSE 9011

CMD ["java", "-jar", "/app/producer-0.0.1-SNAPSHOT.jar"]