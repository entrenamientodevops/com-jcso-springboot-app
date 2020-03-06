package com.jcso.springboot.app.zuul.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;

@EnableEurekaClient
@EnableZuulProxy
@SpringBootApplication
public class SpringbootApiZuulServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApiZuulServerApplication.class, args);
    }

}
