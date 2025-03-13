FROM gradle:latest AS build

WORKDIR /app

COPY . .

RUN gradle clean build -x test

FROM openjdk:21-jdk

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
