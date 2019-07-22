package com.zhyyu.learn.springcloud.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@EnableCircuitBreaker
@SpringBootApplication
public class LearnSpringcloudConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnSpringcloudConsumerApplication.class, args);
    }

}
