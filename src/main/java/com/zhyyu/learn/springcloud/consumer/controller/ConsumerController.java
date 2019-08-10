package com.zhyyu.learn.springcloud.consumer.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zhyyu.learn.learn.springcloud.provider.api.dto.MyDTO1;
import com.zhyyu.learn.learn.springcloud.provider.api.service.FeignApiService;
import com.zhyyu.learn.springcloud.consumer.service.FeignService;
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

    @Autowired
    private FeignService feignService;

    @Autowired
    private FeignApiService feignApiService;

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

    @RequestMapping("hello-from-feign")
    public String helloFromFeign() {
        return feignService.helloFromFeign();
    }

    /**
     * feign 入参对象
     * <pre>
     *     为post json 提交
     *      POST /provider/helloObj HTTP/1.1
     *      Content-Type: application/json;charset=UTF-8
     * </pre>
     */
    @RequestMapping("helloObj-from-feign")
    public String helloObjFromFeign() {
        FeignService.MyObj1 myObj1 = FeignService.MyObj1.builder().key1("value1").key2("value2").build();

        return feignService.helloObjFromFeign(myObj1);
    }

    /**
     * Field feignApiService in com.zhyyu.learn.springcloud.consumer.controller.ConsumerController required a bean of type 'com.zhyyu.learn.learn.springcloud.provider.api.service.FeignApiService' that could not be found.
     * 解决:
     * LearnSpringcloudConsumerApplication
     * @EnableFeignClients(basePackages = {"com.zhyyu.learn.springcloud.consumer.service", "com.zhyyu.learn.learn.springcloud.provider.api.service"})
     *
     * The bean 'cloud-provider1.FeignClientSpecification', defined in null, could not be registered. A bean with that name has already been defined in null and overriding is disabled.
     * 解决:
     * spring.main.allow-bean-definition-overriding=true
     */
    @RequestMapping("helloFromFeignApi")
    public String feignApiService() {
        return feignApiService.helloFromFeignApi(MyDTO1.builder().key1("value1").key2("value2").build());
    }

}
