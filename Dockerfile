# ETAPE 1: Build de l'application Java avec Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ETAPE 2: Création de l'image finale pour l'exécution
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar echeancier-0.0.1-SNAPSHOT.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "echeancier-0.0.1-SNAPSHOT.jar"]