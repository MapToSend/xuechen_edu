package com.online.edu.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableTransactionManagement
public class EduServiceSpringboot {

    public static void main(String[] args) {

        SpringApplication.run(EduServiceSpringboot.class,args);

    }
}
