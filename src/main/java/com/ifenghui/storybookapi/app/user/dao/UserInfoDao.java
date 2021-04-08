package com.ifenghui.storybookapi.app.user.dao;

import com.ifenghui.storybookapi.app.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * Created by wml on 2017/4/10.
 */
@Transactional
public interface UserInfoDao extends JpaRepository<UserInfo, Long> {

    @Query("select info from UserInfo as info where info.userId =:userId")
    UserInfo getUserInfoByUserId(Long userId);

}