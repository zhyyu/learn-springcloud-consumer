package com.zhyyu.learn.springcloud.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author juror
 * @datatime 2019/7/23 0:08
 */
@FeignClient("cloud-provider1")
public interface FeignService {

    @RequestMapping("provider/hello")
    String helloFromFeign();

}
