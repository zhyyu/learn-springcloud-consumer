package com.zhyyu.learn.springcloud.consumer.service;

import com.zhyyu.learn.learn.springcloud.provider.api.config.FormFeignConfig;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ws.rs.core.MediaType;

/**
 * @author juror
 * @datatime 2019/7/23 0:08
 */
//@FeignClient("cloud-provider1")
//@FeignClient(name = "cloud-provider1", configuration = FeignService.Configuration.class)
//    使用和 FeignApiService 相同config, 防止配置覆盖问题
@FeignClient(name = "cloud-provider1", configuration = FormFeignConfig.class)
public interface FeignService {

    @RequestMapping("provider/hello")
    String helloFromFeign();


    /**
     * 若配置 @RequestMapping("provider/helloObj")
     * 则请求为 Content-Type: application/json;charset=UTF-8
     *
     * 若仅配置 @RequestMapping(value = "provider/helloObj", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED), 则如下异常:
     * feign.codec.EncodeException: Could not write request: no suitable HttpMessageConverter found for request type [com.zhyyu.learn.springcloud.consumer.service.FeignService$MyObj1] and content type [application/x-www-form-urlencoded]
     *
     * 增加 @FeignClient(name = "cloud-provider1", configuration = FeignService.Configuration.class) 配置
     * 则请求 Content-Type: application/x-www-form-urlencoded; charset=UTF-8
     */
    // @RequestMapping("provider/helloObj")
    @RequestMapping(value = "provider/helloObj", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED)
    String helloObjFromFeign(MyObj1 myObj1);

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    class MyObj1 {
        private String key1;
        private String key2;
    }

    class Configuration {
        @Bean
        Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> converters) {
            return new SpringFormEncoder(new SpringEncoder(converters));
        }
    }


}
