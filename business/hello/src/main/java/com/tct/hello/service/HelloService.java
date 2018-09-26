package com.tct.hello.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author 作者 E-mail: jianfeng.yang@tcl.com
 * @date 创建时间：八月 28, 2018 10:49
 */
@Service
public class HelloService {
    public static final String url="http://userService/login-test";

    @Autowired
    private RestTemplate restTemplate;

//    @CacheResult(cacheKeyMethod = "getCacheKey")
    @HystrixCommand(commandKey = "helloKey", fallbackMethod = "helloFallBack")
    public String helloService(){
        return restTemplate.getForObject(url, String.class);
    }

//    @CacheRemove(commandKey = "helloService", cacheKeyMethod = "getCacheKey")
    @HystrixCommand
    public void updateHello(){

    }

    public String helloFallBack(){
        return "my-error";
    }

    public String getCacheKey(){
        return "1";
    }


}
