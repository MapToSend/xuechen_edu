package com.online.edu.ucservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableEurekaClient
@EnableTransactionManagement
@MapperScan("com.online.edu.ucservice.mapper")
public class UsServiceSpringboot {

    public static void main(String[] args) {
        SpringApplication.run(UsServiceSpringboot.class,args);
    }
}
