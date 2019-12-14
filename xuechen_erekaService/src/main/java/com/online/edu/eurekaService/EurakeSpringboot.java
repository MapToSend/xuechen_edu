package com.online.edu.eurekaService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurakeSpringboot {

    public static void main(String[] args) {

        SpringApplication.run(EurakeSpringboot.class,args);
    }
}
