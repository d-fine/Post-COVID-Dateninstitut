FROM gradle:8.10.2 AS builder
WORKDIR /app
COPY settings.gradle.kts ./settings.gradle.kts
COPY gradle ./gradle
COPY gradlew ./gradlew

RUN ./gradlew --version # cache download
COPY ./clientapp-backend/ ./clientapp-backend/
RUN ./gradlew dependencies --no-daemon
RUN  ./gradlew bootJar --no-daemon --stacktrace

FROM eclipse-temurin:21.0.4_7-jre-alpine@sha256:848653d62f2fe03f2ef6d0527236fbdedd296ee44bfeb7a9836662dc2f0f58d3
COPY --from=builder /app/clientapp-backend/build/libs/clientapp-backend-0.0.1-SNAPSHOT.jar /jar/clientapp-backend.jar
COPY --from=builder /app/clientapp-backend/src/main/resources/keystore.jks /src/main/resources/keystore.jks


CMD ["java","-jar","/jar/clientapp-backend.jar"]