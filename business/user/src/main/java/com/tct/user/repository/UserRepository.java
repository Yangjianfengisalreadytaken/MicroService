package com.tct.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tct.interfaces.user.entity.UserEntity;

/**
 * @author 作者 E-mail: jianfeng.yang@tcl.com
 * @date 创建时间：八月 16, 2018 09:36
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select u from tct.interfaces.user.entity.UserEntity u where u.email=:email and u.loginPassword=:password")
    UserEntity login(@Param("email")String email, @Param("password")String password);
}
