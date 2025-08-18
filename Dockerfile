# Utilise une image de base OpenJDK pour Java 17
FROM openjdk:17-jdk-slim

# Définit le répertoire de travail dans le conteneur
WORKDIR /app

# Copie le fichier JAR de l'application (le résultat de votre build Maven)
COPY target/*.jar echeancier-0.0.1-SNAPSHOT.jar

# Expose le port par défaut de Spring Boot
EXPOSE 8080

# Lance l'application
ENTRYPOINT ["java", "-jar", "echeancier-0.0.1-SNAPSHOT.jar"]