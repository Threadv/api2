package com.ifenghui.storybookapi.app.story.dao;

import com.ifenghui.storybookapi.app.story.entity.VipSerialGetRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VipSerialGetRecordDao extends JpaRepository<VipSerialGetRecord, Integer> {

    @Query("select v from VipSerialGetRecord as v where v.userId=:userId and v.serialStoryId=:serialStoryId")
    VipSerialGetRecord getDistinctByUserIdAndAndSerialStoryId(
            @Param("userId") Integer userId,
            @Param("serialStoryId") Integer serialStoryId
    );

}
