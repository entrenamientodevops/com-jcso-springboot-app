FROM openjdk:8
VOLUME /tmp
EXPOSE 8186
ADD ./build/libs/springboot-item-api-service-0.0.1-SNAPSHOT.jar item-service.jar
ENTRYPOINT ["java","-jar","/item-service.jar"]