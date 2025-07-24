FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# 1. First copy only build files
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# 2. Set permissions (MUST be done before running)
RUN chmod +x mvnw && \
    ./mvnw dependency:go-offline

# 3. Copy source and build
COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]