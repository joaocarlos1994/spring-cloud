package com.thoughtmechanix.licenses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * This {@code {@link SpringBootApplication}} tells the Spring Boot framework
 * that this is the bootstrap class for the project
 * */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableResourceServer
public class Application {
    
    public static void main(final String[] args) {
        //Call to start the entire Spring Boot service
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @LoadBalanced
    public OAuth2RestTemplate oauth2RestTemplate(
            final OAuth2ClientContext oauth2ClientContext,
            final OAuth2ProtectedResourceDetails details) {
        return new OAuth2RestTemplate(details, oauth2ClientContext);
    }
}
