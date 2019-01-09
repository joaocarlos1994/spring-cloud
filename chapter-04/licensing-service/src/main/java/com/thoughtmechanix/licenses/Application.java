package com.thoughtmechanix.licenses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * This {@code {@link SpringBootApplication}} tells the Spring Boot framework
 * that this is the bootstrap class for the project
 * */
@EnableEurekaClient
@SpringBootApplication
public class Application {
    
    public static void main(final String[] args) {
        //Call to start the entire Spring Boot service
        SpringApplication.run(Application.class, args);
    }
}
