package com.jcso.springboot.app.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.jcso.springboot.api.users.commons.models.entity"})
public class SpringbootAppUsersApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAppUsersApplication.class, args);
    }

}
