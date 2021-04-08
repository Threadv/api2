package com.ifenghui.storybookapi.app.social.dao;

import com.ifenghui.storybookapi.app.social.entity.MedalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * Created by wml on 2017/4/10.
 */
@Transactional
public interface MedalRecordDao extends JpaRepository<MedalRecord, Long> {

    @Query("SELECT m FROM  MedalRecord  as m where m.medalId = :medalId and  m.userId =:userId")
    MedalRecord getOneByMedalIdAndUserId(@Param("medalId") Long medalId,@Param("userId")  long userId);

}