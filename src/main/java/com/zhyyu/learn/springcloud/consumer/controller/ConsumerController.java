package com.zhyyu.learn.springcloud.consumer.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    /**
     * provider 异常情况:
     * <pre>
     *     1. 未设置 hystrix, 先报
     *     {
     *     "timestamp": "2019-07-19T08:25:14.046+0000",
     *     "status": 500,
     *     "error": "Internal Server Error",
     *     "message": "I/O error on GET request for \"http://cloud-provider1/provider/hello\": Connection refused: connect; nested exception is java.net.ConnectException: Connection refused: connect",
     *     "path": "/consumer/hello-from-provider"
     *      }
     *      异常, 后reg 剔除异常服务, 返回正常结果"hello"
     *
     *      2. 设置 hystrix, 先报 fallbackMethod 返回结果"provider-error", 后reg 剔除异常服务, 返回正常结果"hello"
     * </pre>
     * @return
     */
    @RequestMapping("hello-from-provider")
    @HystrixCommand(fallbackMethod = "helloFromProviderFallback")
    public String helloFromProvider() {
        return restTemplate.getForEntity("http://cloud-provider1/provider/hello", String.class).getBody();
    }

    public String helloFromProviderFallback() {
        return "provider-error";
    }

    /**
     * exception: No instances available for baidu.com
     * <pre>
     *     若 @LoadBalanced 加在 restTemplate 上, 则搜索对应cloud 服务, 无则异常
     * </pre>
     */
    @RequestMapping("hello-from-other")
    public String helloFromOther() {
        return restTemplate.getForEntity("http://baidu.com", String.class).getBody();
    }

}
