FROM openjdk:8
VOLUME /tmp
ADD ./build/libs/springboot-product-api-service-0.0.1-SNAPSHOT.jar product-service.jar
ENTRYPOINT ["java","-jar","/product-service.jar"]