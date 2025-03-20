FROM openjdk:17-jdk-slim
LABEL authors="hatrongvu"
ENV APPLICATION_PORT:8080

WORKDIR /opt/service

COPY /target/*.jar /opt/service/app.jar

EXPOSE $APPLICATION_PORT

RUN chgrp -R 0 ./ && chmod -R g=u ./
ENTRYPOINT ["java", "-jar", "app.jar"]