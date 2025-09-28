FROM openjdk:17-jdk-alpine
RUN apk --no-cache add curl
MAINTAINER practica.com.au
COPY target/browse-0.0.1-SNAPSHOT.jar browse-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/browse-0.0.1-SNAPSHOT.jar"]

