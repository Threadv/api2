package com.ifenghui.storybookapi.app.social.dao;

import com.ifenghui.storybookapi.app.social.entity.PartsRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * Created by wml on 2017/4/10.
 */
@Transactional
public interface PartsRecordDao extends JpaRepository<PartsRecord, Long> {


    @Query("select  p from PartsRecord  as p where p.partId=:partId and  p.userId = :userId")
    PartsRecord getOneByPartIdAndUserId(@Param("partId") Long partId,@Param("userId") Long userId);



}