# Use maven 3.6.0 with JDK 8 as the base image
FROM maven:4.11.0-jdk-17

# Copy your source code into the container
COPY src /home/FormicOF2Test/src

# Copy the pom.xml file into the working directory
COPY pom.xml /home/FormicOF2Test

# Running the actual command
RUN mvn -f /home/FormicOF2Test/pom.xml clean test -DskipTests=true
