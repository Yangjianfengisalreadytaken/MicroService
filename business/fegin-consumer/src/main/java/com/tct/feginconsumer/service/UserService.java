package com.tct.feginconsumer.service;

import com.tct.feginconsumer.fallback.UserServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import tct.interfaces.user.RestUserService;

/**
 * @author 作者 E-mail: jianfeng.yang@tcl.com
 * @date 创建时间：九月 03, 2018 11:39
 */
@FeignClient(value = "userService", fallback = UserServiceFallBack.class)
public interface UserService extends RestUserService {
}
