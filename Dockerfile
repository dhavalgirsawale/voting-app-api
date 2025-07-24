FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# Copy everything except what's in .dockerignore
COPY . .

# Set permissions and build
RUN chmod +x mvnw && \
    ./mvnw dependency:go-offline && \
    ./mvnw package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
