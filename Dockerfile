FROM openjdk:20
EXPOSE 8080
COPY api/target/api.jar /app/api.jar
COPY persistence/target/persistence.jar /app/persistence.jar
COPY service/target/service.jar /app/service.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "api.jar"]
