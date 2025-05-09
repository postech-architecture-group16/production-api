# Stage 1: Build the application
FROM maven:latest as builder
WORKDIR /app

# Copy only the necessary files to leverage Docker layer caching
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src/ ./src/

RUN mvn clean package -DskipTests=true

# Stage 2: Create the runtime image
FROM eclipse-temurin:21-jdk-alpine

RUN mkdir /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/*.jar /app/production-api.jar

WORKDIR /app
# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/production-api.jar"]