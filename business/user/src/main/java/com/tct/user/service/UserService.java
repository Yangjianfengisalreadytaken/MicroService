package com.tct.user.service;

import tct.interfaces.user.dto.Logination;
import tct.interfaces.user.dto.Registrition;

/**
 * @author 作者 E-mail: jianfeng.yang@tcl.com
 * @date 创建时间：八月 15, 2018 17:51
 */
public interface UserService {
    String register(Registrition registrition);
    String login(Logination logination);
    String logout(Logination logination);
}
