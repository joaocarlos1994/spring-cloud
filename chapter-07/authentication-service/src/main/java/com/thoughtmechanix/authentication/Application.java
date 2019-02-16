package com.thoughtmechanix.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableAuthorizationServer //tell Spring Cloud that this service is going to act as an OAuth2 service
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
