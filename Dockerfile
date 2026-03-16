FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app


COPY target/*.jar app.jar


EXPOSE 9090


ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=$PORT"]