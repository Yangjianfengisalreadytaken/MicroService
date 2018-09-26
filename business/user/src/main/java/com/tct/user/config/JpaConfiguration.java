package com.tct.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManagerFactory;

/**
 * @author 作者 E-mail: jianfeng.yang@tcl.com
 * @date 创建时间：八月 16, 2018 09:34
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.tct.user.repository")
public class JpaConfiguration  {


}
