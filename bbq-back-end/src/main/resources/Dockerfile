FROM openjdk:8-jre

EXPOSE 8888

ADD bbq-1.0.0-SNAPSHOT.jar bbq.jar
RUN bash -c 'touch /bbq.jar'

ENTRYPOINT ["java","-jar","bbq.jar","--spring.profiles.active=pro"]