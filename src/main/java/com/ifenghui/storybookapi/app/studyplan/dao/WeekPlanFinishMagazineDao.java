package com.ifenghui.storybookapi.app.studyplan.dao;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanFinishMagazine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WeekPlanFinishMagazineDao extends JpaRepository<WeekPlanFinishMagazine, Integer> {

    @Query("select w from WeekPlanFinishMagazine as w where w.planType=:planType and w.userId=:userId and w.magPlanId=:magPlanId")
    WeekPlanFinishMagazine getDistinctByMagPlanIdAndUserIdAndPlanType(
        @Param("magPlanId") Integer magPlanId,
        @Param("userId") Integer userId,
        @Param("planType") Integer planType
    );

    @Query("select count(w) from WeekPlanFinishMagazine as w where w.planType=:planType and w.userId=:userId and w.magPlanId=:magPlanId")
    Long getCountByMagPlanIdAndUserIdAndPlanType(
            @Param("magPlanId") Integer magPlanId,
            @Param("userId") Integer userId,
            @Param("planType") Integer planType
    );



}
