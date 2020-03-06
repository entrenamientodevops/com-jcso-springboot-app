FROM openjdk:8
VOLUME /tmp
EXPOSE 8088
ADD ./build/libs/springboot-api-zuul-server-0.0.1-SNAPSHOT.jar zuul-server.jar
ENTRYPOINT ["java","-jar","/zuul-server.jar"]