package com.ifenghui.storybookapi.app.social.dao;

import com.ifenghui.storybookapi.app.social.entity.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * Created by wml on 2017/4/10.
 */
@Transactional
public interface CheckInDao extends JpaRepository<CheckIn, Integer> {

    @Query("select c from CheckIn as c where c.userId=:userId")
    CheckIn findCheckInByUserId(
        @Param("userId") Integer userId
    );

}