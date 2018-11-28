package com.thoughtmechanix.confsvr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * A {@code {@link EnableConfigServer}} enables the service as a Spring Cloud Config service.
 * */
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

    //I stoppped 3.2.2
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }

}
