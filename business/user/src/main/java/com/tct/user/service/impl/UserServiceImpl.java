package com.tct.user.service.impl;

import com.tct.user.repository.UserRepository;
import com.tct.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tct.interfaces.user.dto.Logination;
import tct.interfaces.user.dto.Registrition;
import tct.interfaces.user.entity.UserEntity;

import java.util.Date;

/**
 * @author 作者 E-mail: jianfeng.yang@tcl.com
 * @date 创建时间：八月 15, 2018 17:52
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional(readOnly = false)
    public String register(Registrition registrition) {
        if(!registrition.getPassword().equals(registrition.getPasswordConfirm())){
            return "please correct your password!";
        }
        UserEntity entity = new UserEntity();
        entity.setCreateTime(new Date());
        entity.setEmail(registrition.getEmail());
        entity.setUserName(registrition.getUserName());
        entity.setLoginPassword(registrition.getPassword());
        userRepository.save(entity);
        return entity.getUserName() + " register success";
    }

    @Override
    @Transactional(readOnly = true)
    public String login(Logination logination) {
        UserEntity entity = userRepository.login(logination.getEmail(), logination.getPassword());
        return entity.getUserName() + " login success";
    }

    @Override
    @Transactional(readOnly = true)
    public String logout(Logination logination) {
        return "loogout success";
    }
}
