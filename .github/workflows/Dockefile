FROM openjdk:21-jdk

WORKDIR  /app

COPY target/pipeline.jar /app/pipeline.jar

EXPOSE 8080

CMD ("java", "-jar", "pipeline.jar")

