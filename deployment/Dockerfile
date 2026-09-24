#FROM eclipse-temurin:25-jdk-alpine
#VOLUME /tmp
#COPY *.jar franquicia-api.jar
#ENV JAVA_OPTS=" -Xshareclasses:name=cacheapp,cacheDir=/cache,nonfatal -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"
## Replace with a non-root user to avoid running the container with excessive privileges
#USER appuser
#ENTRYPOINT [ "/bin/sh", "-c", "/opt/java/openjdk/bin/java $JAVA_OPTS  -jar /franquicia-api.jar" ]

# ==============================
# Etapa 1: Compilación
# ==============================
FROM gradle:9.7.1-jdk25 AS builder
WORKDIR /workspace
COPY . .
RUN gradle clean :app-service:bootJar --no-daemon

# ==============================
# Etapa 2: Imagen final
# ==============================
FROM eclipse-temurin:25-jre-alpine AS runtime
WORKDIR /app
COPY --from=builder /workspace/applications/app-service/build/libs/*.jar franquicia-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75", "-jar", "/app/franquicia-api.jar"]
