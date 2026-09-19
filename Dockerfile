# ================================
# Build Stage
# ================================
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests


# ================================
# Production Stage
# ================================
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/*.jar rbme-api.jar

EXPOSE 9292

ENTRYPOINT ["java", "-jar", "rbme-api.jar"]