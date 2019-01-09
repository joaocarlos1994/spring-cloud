package com.thoughtmechanix.licenses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * This {@code {@link SpringBootApplication}} tells the Spring Boot framework
 * that this is the bootstrap class for the project
 * */
@SpringBootApplication
public class Application {
    
    public static void main(final String[] args) {
        //Call to start the entire Spring Boot service
        SpringApplication.run(Application.class, args);
    }
}
