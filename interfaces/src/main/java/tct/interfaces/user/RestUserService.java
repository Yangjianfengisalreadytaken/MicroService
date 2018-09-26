package tct.interfaces.user;

import org.springframework.web.bind.annotation.RequestMapping;
import tct.interfaces.user.dto.Logination;
import tct.interfaces.user.dto.Registrition;

/**
 * @author 作者 E-mail: jianfeng.yang@tcl.com
 * @date 创建时间：九月 03, 2018 11:39
 */
@RequestMapping("/user")
public interface RestUserService {
    @RequestMapping("/hello1")
    String hello();

    @RequestMapping("/hello2")
    String hello(String name);

    @RequestMapping("/hello3")
    String hello(Registrition registrition);

    @RequestMapping("/hello4")
    String hello(Logination logination);

}
