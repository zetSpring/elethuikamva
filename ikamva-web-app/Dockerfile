FROM maven:3.9.3 AS build
WORKDIR /app
ARG SERVER_PORT
COPY pom.xml /app
RUN mvn dependency:resolve
COPY . /app
RUN mvn clean
RUN mvn package -DskipTests -X


FROM openjdk:20
WORKDIR --from=build /app/target/*.jar elethuikamva.jar
EXPOSE ${SERVER_PORT}
CMD ["java", "-jar", "elethuikamva.jar"]