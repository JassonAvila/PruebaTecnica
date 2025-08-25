FROM eclipse-temurin:21-jre

RUN useradd -ms /bin/bash appuser
WORKDIR /app

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080
USER appuser

ENTRYPOINT ["java","-jar","/app/app.jar"]
