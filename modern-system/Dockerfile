# Multi-stage build for optimized image size
# Stage 1: Build the application
FROM maven:3.8-openjdk-17 AS build
WORKDIR /app

# Copy pom.xml first (for better layer caching)
COPY pom.xml .
# Download dependencies (cached unless pom.xml changes)
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the JAR (skip tests for faster builds during development)
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:17-jdk-slim
WORKDIR /app

# Create non-root user for security
RUN useradd -m -u 1000 appuser

# Copy JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Change ownership to appuser
RUN chown appuser:appuser app.jar

# Switch to non-root user
USER appuser

# Expose the port your app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
