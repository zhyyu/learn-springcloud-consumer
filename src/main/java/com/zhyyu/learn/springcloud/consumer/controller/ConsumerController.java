package com.zhyyu.learn.springcloud.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author juror
 * @datatime 2019/7/18 18:14
 */
@RequestMapping("consumer")
@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("hello-from-provider")
    public String helloFromProvider() {
        return restTemplate.getForEntity("http://cloud-provider1/provider/hello", String.class).getBody();
    }

}
