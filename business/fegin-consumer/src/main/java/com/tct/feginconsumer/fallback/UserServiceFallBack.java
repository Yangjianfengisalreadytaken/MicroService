package com.tct.feginconsumer.fallback;

import com.tct.feginconsumer.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tct.interfaces.user.dto.Logination;
import tct.interfaces.user.dto.Registrition;

/**
 * @author 作者 E-mail: jianfeng.yang@tcl.com
 * @date 创建时间：九月 06, 2018 14:56
 */
@Component
public class UserServiceFallBack implements UserService {
    @Override
    public String hello() {
        return "fallback";
    }

    @Override
    public String hello(String name) {
        return "name fallback:"+name;
    }

    @Override
    public String hello(Registrition registrition) {
        return "regist fallback";
    }

    @Override
    public String hello(Logination logination) {
        return "login fallback";
    }
}
