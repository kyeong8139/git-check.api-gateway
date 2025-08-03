# Build stage
FROM gradle:8.5-jdk21 AS build

WORKDIR /app

COPY gradle/ gradle/
COPY gradlew gradlew.bat build.gradle settings.gradle ./

COPY src/ src/

RUN ./gradlew build -x test

# Runtime stage
FROM openjdk:21-slim

WORKDIR /app

RUN groupadd -r gateway && useradd -r -g gateway gateway

COPY --from=build /app/build/libs/*.jar gateway.jar

RUN chown gateway:gateway gateway.jar

USER gateway

ARG GATEWAY_PORT
EXPOSE ${GATEWAY_PORT}

ENTRYPOINT ["java", "-jar", "gateway.jar"]
