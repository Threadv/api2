package com.ifenghui.storybookapi.app.user.dao;

import com.ifenghui.storybookapi.app.user.entity.UserInfo;
import com.ifenghui.storybookapi.app.user.entity.UserUmengToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserUmengTokenDao extends JpaRepository<UserUmengToken, Integer> {

    UserUmengToken findUserUmengTokenByUmengToken(String umengToken);

}
