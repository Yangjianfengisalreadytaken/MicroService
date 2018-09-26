package com.tct.user.controller;

import com.tct.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tct.interfaces.user.RestUserService;
import tct.interfaces.user.dto.Logination;
import tct.interfaces.user.dto.Registrition;

/**
 * @author 作者 E-mail: jianfeng.yang@tcl.com
 * @date 创建时间：八月 15, 2018 11:13
 */
@RestController
public class UserController implements RestUserService {
    @Autowired
    private UserService userService;

    @RequestMapping("/regist")
    public String regist(Registrition registrition){
        return userService.register(registrition);
    }

    @RequestMapping("/login")
    public String login(Logination logination){
        return userService.login(logination);
    }

    @RequestMapping("/logout")
    public String logout(Logination logination){
        return userService.logout(logination);
    }

    @RequestMapping("/login-test")
    public String loginTest(){return "login-test success";}

    @Override
    public String hello() {
        return "hello";
    }

    @Override
    public String hello(String name) {
        return "hello:"+name;
    }

    @Override
    public String hello(Registrition registrition) {
        return "hello:"+registrition.getEmail();
    }

    @Override
    public String hello(Logination logination) {
        return "hello:"+logination.getEmail()+"-"+logination.getPassword();
    }
}
