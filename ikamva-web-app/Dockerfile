FROM maven:3.9.3 AS build
WORKDIR /app
ARG SERVER_PORT
COPY pom.xml /app
COPY ../ikamva-web-app/pom.xml /app
COPY ../ikamva-web-app/src /app/src
COPY ../ikamva-payment-bulk-processing/pom.xml /app
RUN mvn dependency:resolve
COPY . /app
RUN mvn clean
RUN mvn package -DskipTests -X

FROM openjdk:20
COPY --from=build /app/target/*.jar elethuikamva.jar
EXPOSE ${SERVER_PORT}
CMD ["java", "-jar", "elethuikamva.jar"]