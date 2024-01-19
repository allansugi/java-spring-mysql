FROM eclipse-temurin:17-jdk-jammy as base

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:resolve

COPY src ./src

FROM base as test
CMD ["./mvnw", "test"]

FROM base as dev
CMD ["./mvnw", "spring-boot:run"]