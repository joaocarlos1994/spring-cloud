package com.thoughtmechanix.licenses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * This {@code {@link SpringBootApplication}} tells the Spring Boot framework
 * that this is the bootstrap class for the project
 * */
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class Application {
    
    public static void main(final String[] args) {
        //Call to start the entire Spring Boot service
        SpringApplication.run(Application.class, args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
