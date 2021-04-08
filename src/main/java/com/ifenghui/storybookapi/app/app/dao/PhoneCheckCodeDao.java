package com.ifenghui.storybookapi.app.app.dao;

import com.ifenghui.storybookapi.app.app.entity.PhoneCheckCode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Date;
import java.util.List;

public interface PhoneCheckCodeDao extends JpaRepository<PhoneCheckCode, Integer> {

    @Query("select count(p) from PhoneCheckCode as p where p.phone=:phone and p.createTime >=:startTime and p.createTime<=:endTime")
    Integer getCountPhoneCheckCodeByPhone(
        @Param("phone") String phone,
        @Param("startTime") Date startTime,
        @Param("endTime") Date endTime
    );

    @Query("select p from PhoneCheckCode as p where p.phone=:phone")
    List<PhoneCheckCode> getPhoneCheckCodeListByPhone(
        @Param("phone") String phone,
        Pageable pageable
    );

}
