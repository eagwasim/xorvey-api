FROM gradle:jdk8 as builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM openjdk:8-alpine
VOLUME /tmp
COPY --from=builder /home/gradle/src/build/libs/xorvey-api-0.0.1-SNAPSHOT.jar /xorvey-api.jar
EXPOSE 8080
RUN sh -c 'touch /xorvey-api.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=stage", "-jar" , "/xorvey-api.jar"]