package com.jcso.springboot.app.item.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean("restItem")
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
