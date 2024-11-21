# Use an OpenJDK base image
FROM openjdk:17

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/chatgpt-0.0.1-snapshot.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
