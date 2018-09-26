package com.tct.feginconsumer.controller;

import com.tct.feginconsumer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tct.interfaces.user.RestUserService;

/**
 * @author 作者 E-mail: jianfeng.yang@tcl.com
 * @date 创建时间：九月 03, 2018 11:49
 */
@RestController
public class ConsumerController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/fegin-cunsumer")
    public String helloConsumer(){
        return userService.hello();
    }

}
