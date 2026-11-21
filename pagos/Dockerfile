# ---- Etapa 1: compilar con Maven (JDK 24) ----
FROM eclipse-temurin:24-jdk-alpine AS build
WORKDIR /app

# Primero solo el pom: Docker reutiliza esta capa y no vuelve a bajar dependencias si no cambia
COPY .mvn .mvn
COPY mvnw pom.xml ./
# mvnw con saltos de línea de Windows (CRLF) falla en Linux; se normaliza por si acaso
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw && ./mvnw -B -q dependency:go-offline || true

COPY src src
RUN ./mvnw -B clean package -DskipTests

# ---- Etapa 2: imagen final liviana (solo JRE) ----
FROM eclipse-temurin:24-jre-alpine
WORKDIR /app

# No ejecutar como root
RUN addgroup -S app && adduser -S app -G app
COPY --from=build /app/target/*.jar app.jar
USER app

EXPOSE 8085
# JAVA_OPTS permite ajustar memoria desde docker-compose (ej. -Xmx256m)
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]
