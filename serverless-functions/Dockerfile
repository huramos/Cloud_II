# Etapa de build
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Compila y empaqueta
RUN mvn clean package -DskipTests

# Etapa final: usa imagen oficial de Azure Functions Java 17
FROM mcr.microsoft.com/azure-functions/java:4-java17

# Variables requeridas por Azure Functions
ENV AzureWebJobsScriptRoot=/home/site/wwwroot \
    AzureFunctionsJobHost__Logging__Console__IsEnabled=true

# Copia los archivos de funciones ya compilados (reemplaza la ruta según tu estructura)
COPY --from=build /app/target/azure-functions/functions-bff/ /home/site/wwwroot/
