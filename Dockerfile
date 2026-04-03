# Stage 1: Build the JAR
FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /app

# Copy Maven wrapper and pom
COPY pom.xml mvnw ./
COPY .mvn .mvn
# Copy source code
COPY src src

# Make mvnw executable
RUN chmod +x mvnw
# Build the JAR
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the app
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Use Render dynamic port
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=$PORT"]