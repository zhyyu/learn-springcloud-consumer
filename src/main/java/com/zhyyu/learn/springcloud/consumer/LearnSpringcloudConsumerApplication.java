package com.zhyyu.learn.springcloud.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"com.zhyyu.learn.springcloud.consumer.service", "com.zhyyu.learn.learn.springcloud.provider.api.service"})
@EnableDiscoveryClient
@EnableCircuitBreaker
@SpringBootApplication
public class LearnSpringcloudConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnSpringcloudConsumerApplication.class, args);
    }

}
