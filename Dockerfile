FROM openjdk:11
COPY ./target/elasticsearch-0.0.1-SNAPSHOT.jar /usr/src/app/
WORKDIR /usr/src/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "elasticsearch-0.0.1-SNAPSHOT.jar"]