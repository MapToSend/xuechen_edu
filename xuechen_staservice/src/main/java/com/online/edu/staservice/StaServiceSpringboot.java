package com.online.edu.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableEurekaClient
@EnableTransactionManagement
@EnableFeignClients
@EnableScheduling
@MapperScan("com.online.edu.staservice.mapper")
public class StaServiceSpringboot {

    public static void main(String[] args) {
        SpringApplication.run(StaServiceSpringboot.class,args);
    }
}
