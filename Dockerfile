# =========================
# BUILD STAGE
# =========================
FROM gradle:8.14.3-jdk24 AS build

WORKDIR /app

# Copie uniquement les fichiers nécessaires d'abord
# pour améliorer le cache Docker
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle

# Télécharge les dépendances
RUN gradle dependencies --no-daemon

# Copie le reste du projet
COPY . .

# Build
RUN gradle clean bootJar -x test --no-daemon


# =========================
# RUNTIME STAGE
# =========================
FROM eclipse-temurin:24-jre

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

#ENTRYPOINT ["java", "-jar", "app.jar"]
ENTRYPOINT ["java", "--add-opens", "java.base/java.util=ALL-UNNAMED", "-jar", "app.jar"]