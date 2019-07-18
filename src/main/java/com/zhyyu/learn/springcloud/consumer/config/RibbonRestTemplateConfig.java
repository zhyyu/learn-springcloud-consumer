package com.zhyyu.learn.springcloud.consumer.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author juror
 * @datatime 2019/7/18 18:23
 */
@SpringBootConfiguration
public class RibbonRestTemplateConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
