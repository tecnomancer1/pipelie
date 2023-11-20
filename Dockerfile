# Use an official Maven runtime as a parent image
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy the project files into the container at /usr/src/app
COPY . .

# Build the application
RUN mvn clean install

# Use a smaller base image for the runtime
FROM openjdk:22-ea-22

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy the JAR file from the build stage to the runtime image
COPY --from=build /usr/src/app/target/pipeline.jar ./app.jar

# Expose the port the app runs on
EXPOSE 8080

# Define the command to run the application
CMD ["java", "-jar", "app.jar"]
