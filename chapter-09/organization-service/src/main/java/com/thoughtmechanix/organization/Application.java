package com.thoughtmechanix.organization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


/**
 * {@link EnableBinding} annotation tells Spring Cloud Stream that you
 * want to bind the service to a message broker. The use of {@link Source}
 * in the {@link EnableBinding} annotation tells Spring Cloud Stream that
 * this service will communicate with the message broker via a set of
 * channels defined on the {@link Source}.
 *
 * */
@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
@EnableResourceServer
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Sampler defaultSampler() {
        return new AlwaysSampler();
    }
}
