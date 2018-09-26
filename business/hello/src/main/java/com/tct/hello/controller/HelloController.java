package com.tct.hello.controller;

import com.tct.hello.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author 作者 E-mail: jianfeng.yang@tcl.com
 * @date 创建时间：八月 23, 2018 11:33
 */
@RestController
public class HelloController {
    public static final String url="http://userService/login-test";

    @Autowired
    public HelloService helloService;

    @RequestMapping(value = "/hello")
    public String hello(){
        return helloService.helloService();
    }


}
