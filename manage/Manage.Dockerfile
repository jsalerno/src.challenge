FROM openjdk:17-jdk-alpine
RUN apk --no-cache add curl
MAINTAINER practica.com.au
COPY target/manage-0.0.1-SNAPSHOT.jar manage-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/manage-0.0.1-SNAPSHOT.jar"]

