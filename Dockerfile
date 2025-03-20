FROM openjdk:17-jdk-slim
LABEL authors="hatrongvu"
ENV APPLICATION_PORT:8080

WORKDIR /opt/service

COPY /target/*.jar /opt/service/app.jar

COPY /src/main/resources /opt/service/resources_default

COPY entrypoint.sh /entrypoint.sh

RUN chmod +x /entrypoint.sh

EXPOSE $APPLICATION_PORT

RUN chgrp -R 0 ./ && chmod -R g=u ./
ENTRYPOINT ["/entrypoint.sh"]