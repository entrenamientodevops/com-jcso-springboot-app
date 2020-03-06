FROM openjdk:8
VOLUME /tmp
ADD ./build/libs/springboot-app-users-0.0.1-SNAPSHOT.jar users-service.jar
ENTRYPOINT ["java","-jar","/users-service.jar"]