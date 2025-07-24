# Build stage
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# 1. Copy build files first
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# 2. Set permissions and download dependencies
RUN chmod +x mvnw && \
    ./mvnw dependency:go-offline

# 3. Copy source and build
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy built artifact
COPY --from=builder /app/target/*.jar app.jar

# Port configuration
EXPOSE 8080
ENV PORT=8080

ENTRYPOINT ["java","-jar","app.jar"]
