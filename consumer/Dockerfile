
FROM eclipse-temurin:17-jdk-focal as builder1

WORKDIR /app

COPY ./consumer/ /app/.

RUN ./gradlew clean build -x test

RUN ls -la /app/build/libs

FROM eclipse-temurin:17-jdk-focal
WORKDIR /app
COPY --from=builder1 /app/build/libs /app

RUN ls -la /app

EXPOSE 9012

CMD ["java", "-jar", "consumer-0.0.1-SNAPSHOT.jar"]