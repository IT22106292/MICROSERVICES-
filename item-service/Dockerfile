FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the jar file (will be built during docker-compose build)
COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]