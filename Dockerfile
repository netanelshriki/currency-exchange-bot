#FROM amazoncorretto:11-alpine-jdk
#
#COPY target/MyTelegramBot-0.0.1-VERSION.jar first-telegrambot-0.0.1.jar
#
#ENTRYPOINT ["java","-jar","/first-telegrambot-0.0.1.jar"]

# Use an official Maven image as the base image for building the application
FROM maven:3.8.4-openjdk-11 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project file
COPY pom.xml .

# Download the project dependencies
RUN mvn dependency:go-offline -B

# Copy the application source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Create a new lightweight image with the application JAR file
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR file from the build stage
COPY --from=build target/MyTelegramBot-0.0.1-VERSION.jar first-telegrambot-0.0.1.jar

ENTRYPOINT ["java","-jar","/first-telegrambot-0.0.1.jar"]

