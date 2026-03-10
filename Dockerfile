FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY target/*.jar app.jar

# Copy the .env file
COPY .env /app/.env

EXPOSE 9090

ENTRYPOINT ["java","-jar","app.jar"]