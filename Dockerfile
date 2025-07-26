# Stage 1: Build with Maven and JDK
FROM maven:3.9.11-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml first to take advantage of Docker's cache of dependencies
COPY pom.xml .

# Download all project dependencies
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Compile the project and package it into a .jar (skipping local tests)
RUN mvn package -DskipTests

# Stage 2: Runtime with JRE
FROM eclipse-temurin:21-jre

# Install curl for healthcheck
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /app

# Copy ONLY the .jar from the build stage to our final image
COPY --from=build /app/target/url-shortener-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the Spring application uses
EXPOSE 8080

# Command to run the application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]