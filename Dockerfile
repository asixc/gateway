# -----------------------------
# Stage 1: Build the application
# -----------------------------
FROM maven:3.9.9-eclipse-temurin-23-alpine AS build
WORKDIR /app

# Copiar el pom.xml y descargar dependencias (para aprovechar el caché)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente
COPY src ./src

# Se espera que el JAR se genere como gateway-${VERSION}.jar.
# Debes pasar el build-arg VERSION (por ejemplo, 0.0.1-SNAPSHOT).
ARG VERSION
RUN mvn clean package -DskipTests && \
    cp target/gateway-$(echo $VERSION | sed 's/-SNAPSHOT//').jar app.jar

# -----------------------------
# Stage 2: Crear la imagen runtime
# -----------------------------
FROM eclipse-temurin:23.0.1_11-jre-alpine
WORKDIR /app

# Copiar el JAR compilado de la etapa anterior (con nombre fijo app.jar)
COPY --from=build /app/app.jar app.jar

# Exponer el puerto en el que la aplicación escucha (ajustado a 8085)
EXPOSE 8085

# Definir el ENTRYPOINT para ejecutar la aplicación
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]

# Establecer la zona horaria (opcional)
ENV TZ="Europe/Madrid"
