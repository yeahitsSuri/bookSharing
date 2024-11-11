#Build the backend image
FROM maven:3.8.6-openjdk-18 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline // add all dependencies to the local repository
COPY src ./src
RUN mvn clean package -DskipTests

#Run the backend image
FROM amazoncorretto:17

WORKDIR /app