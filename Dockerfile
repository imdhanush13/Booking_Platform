# Step 1: Use an official OpenJDK runtime as base
FROM openjdk:17-jdk-slim

# Step 2: Set working directory inside container
WORKDIR /app

# Step 3: Copy built JAR file from target/ to container
COPY target/*.jar app.jar

# Step 4: Expose port (Spring Boot default is 8080)
EXPOSE 8080

# Step 5: Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
