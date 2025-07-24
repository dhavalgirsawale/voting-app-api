FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# First copy only wrapper files
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Set permissions and download dependencies
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Copy remaining files and build
COPY src ./src
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]