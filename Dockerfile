# ===== Build stage =====
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app

# Copy your project files into the build container
COPY . .

# Build your Spring Boot application (skip tests if desired)
RUN mvn clean package -DskipTests

# ===== Runtime stage =====
FROM eclipse-temurin:17-jre-alpine AS runtime
WORKDIR /app

# Copy the generated JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your Spring Boot app runs on (change if you use 8082)
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
