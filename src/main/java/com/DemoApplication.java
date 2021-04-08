package com;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;


import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;




@EnableTransactionManagement
//@EnableAutoConfiguration
@SpringBootApplication
@EnableCaching
@ServletComponentScan
//@EnableDiscoveryClient
@EnableFeignClients
//@EnableHystrix
//@EnableHystrixDashboard


public class DemoApplication {

    public static void main(String[] args) {
        System.setProperty("user.timezone","Asia/Shanghai");
//        System.setProperty("user.timezone","America/Los_Angeles");
        SpringApplication.run(DemoApplication.class, args);


    }
}
