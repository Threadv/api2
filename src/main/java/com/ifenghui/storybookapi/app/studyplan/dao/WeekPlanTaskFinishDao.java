package com.ifenghui.storybookapi.app.studyplan.dao;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanTaskFinish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WeekPlanTaskFinishDao extends JpaRepository<WeekPlanTaskFinish, Integer> {

    WeekPlanTaskFinish getDistinctByUserIdAndWeekPlanIdAndPlanTaskId(
            Integer userId, Integer weekPlanId, Integer planTaskId
    );

    @Query("select count(w) from WeekPlanTaskFinish as w where w.userId=:userId and w.weekPlanId=:weekPlanId and w.planTaskId=:planTaskId")
    Long getCountByUserIdAndWeekPlanIdAndPlanTaskId(
            @Param("userId") Integer userId,
            @Param("weekPlanId") Integer weekPlanId,
            @Param("planTaskId") Integer planTaskId
    );

    @Query("select sum(w.score) from WeekPlanTaskFinish as w where w.weekPlanId=:weekPlanId and w.userId=:userId")
    Integer getScoreCountByWeekPlanIdAndUserId(
        @Param("weekPlanId") Integer weekPlanId,
        @Param("userId") Integer userId
    );

}
