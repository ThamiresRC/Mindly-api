# Etapa 1: build com Maven e JDK 21
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -B -DskipTests clean package

# Etapa 2: imagem leve só com o JRE
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/mindly-api-0.0.1-SNAPSHOT.jar app.jar

# Render injeta PORT, e o Spring já está com server.port=${PORT:8080}
EXPOSE 8080

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
