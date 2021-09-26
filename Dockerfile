FROM adoptopenjdk/openjdk11:latest
VOLUME /tmp
EXPOSE 8080
ADD build/libs/*.jar reading-is-good-docker.jar
RUN bash -c 'touch reading-is-good-docker.jar'
ENTRYPOINT ["java","-jar","reading-is-good-docker.jar"]