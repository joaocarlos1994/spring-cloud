package com.thoughtmechanix.licenses;

import com.thoughtmechanix.licenses.utils.UserContextInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * This {@code {@link SpringBootApplication}} tells the Spring Boot framework
 * that this is the bootstrap class for the project
 * */
@EnableFeignClients
@EnableEurekaClient
@EnableCircuitBreaker
@SpringBootApplication
public class Application {
    
    public static void main(final String[] args) {
        //Call to start the entire Spring Boot service
        SpringApplication.run(Application.class, args);
    }


    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        final RestTemplate template = new RestTemplate();
        final List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();

        if (interceptors == null){
            template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
        } else {
            interceptors.add(new UserContextInterceptor());
            template.setInterceptors(interceptors);
        }
        return template;
    }
}
