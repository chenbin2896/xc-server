package com.xuecheng.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-07-14 11:11
 **/
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EntityScan(value = {"com.xuecheng.framework.domain.learning", "com.xuecheng.framework.domain.task"})
@ComponentScan(basePackages = {"com.xuecheng.api"})
@ComponentScan(basePackages = {"com.xuecheng.learning"})
@ComponentScan(basePackages = {"com.xuecheng.framework"})
public class LearningApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(LearningApplication.class, args);
    }
}