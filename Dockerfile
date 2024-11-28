FROM maven:3.9.4-amazoncorretto-21 AS build

WORKDIR /app

COPY . .

RUN mvn clean install package -DskipTests

FROM amazoncorretto:21-alpine

WORKDIR /app

COPY --from=build /app/target/hotel-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
