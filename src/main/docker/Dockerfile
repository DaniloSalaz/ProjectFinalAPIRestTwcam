FROM adoptopenjdk/openjdk8-openj9:alpine-jre

RUN mkdir /deployments \
    && chown 1001 /deployments \
    && chown 1001:root /deployments

COPY springboot-project-air-rest-1.1.jar /deployments/springboot-project-air-rest-0.0.1-SNAPSHOT.jar

EXPOSE 8080
USER 1001
WORKDIR /deployments

CMD ["java", "-jar", "springboot-project-air-rest-0.0.1-SNAPSHOT.jar"]
