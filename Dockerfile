FROM openjdk:8-alpine
VOLUME /tmp
RUN chmod +x gradlew
RUN gradlew build
ADD build/libs/xorvey-api-0.0.1-SNAPSHOT.jar xorvey-api.jar
EXPOSE 8080
RUN sh -c 'touch /xorvey-api.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=stage", "-jar" , "xorvey-api.jar"]