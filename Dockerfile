# ----------- Build Stage -----------
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy Maven wrapper and config for caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Preload dependencies (improves build caching)
RUN ./mvnw dependency:go-offline

# Copy source code and build the app
COPY src src
RUN ./mvnw clean package -DskipTests

# ----------- Runtime Stage -----------
FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app

# Copy built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Default command
ENTRYPOINT ["java", "-jar", "app.jar"]
