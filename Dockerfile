FROM eclipse-temurin:23.0.1_11-jre-alpine
COPY ./target/gateway-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
ENV TZ="Europe/Madrid"