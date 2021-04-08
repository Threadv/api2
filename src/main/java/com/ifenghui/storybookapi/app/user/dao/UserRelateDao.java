package com.ifenghui.storybookapi.app.user.dao;

import com.ifenghui.storybookapi.app.user.entity.UserRelate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRelateDao extends JpaRepository<UserRelate,Integer> {

    UserRelate findByPhone(String phone);

    UserRelate findByUnionid(String unionid);
}
