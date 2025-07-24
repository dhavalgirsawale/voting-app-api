# Dockerfile

# Stage 1: Build the Spring Boot application using a Maven image with JDK 21
# We choose 'eclipse-temurin-21' to match your pom.xml's Java 21 version
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper files (mvnw, .mvn) to /app
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml and src directory
COPY pom.xml .
COPY src src

# Make the Maven wrapper executable
RUN chmod +x ./mvnw

# Build the Spring Boot application and package it into a JAR
RUN ./mvnw clean install -DskipTests

# Stage 2: Create the final lightweight image to run the application using JRE 21
FROM eclipse-temurin:21-jre-alpine

# Set the working directory for the runtime
WORKDIR /app

# Copy the built JAR file from the 'build' stage to the final image
# IMPORTANT: Ensure 'votingapp-0.0.1-SNAPSHOT.jar' matches your actual JAR name
COPY --from=build /app/target/votingapp-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app listens on (default 8080)
EXPOSE 8080

# Define the command to run your Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]