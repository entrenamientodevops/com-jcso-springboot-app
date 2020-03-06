package com.jcso.springboot.app.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class SpringbootApiEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApiEurekaServerApplication.class, args);
    }

}
