FROM openjdk:8
VOLUME /tmp
EXPOSE 9100
ADD ./build/libs/springboot-app-oauth-0.0.1-SNAPSHOT.jar oauth-server.jar
ENTRYPOINT ["java","-jar","/oauth-server.jar"]