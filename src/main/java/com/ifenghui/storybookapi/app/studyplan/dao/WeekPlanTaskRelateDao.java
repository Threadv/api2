package com.ifenghui.storybookapi.app.studyplan.dao;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanTaskRelate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WeekPlanTaskRelateDao extends JpaRepository<WeekPlanTaskRelate, Integer> {


    @Query("select w from WeekPlanTaskRelate as w where w.weekPlanId=:weekPlanId order by w.orderBy asc")
    List<WeekPlanTaskRelate> getWeekPlanTaskRelatesByWeekPlanId(
        @Param("weekPlanId") Integer weekPlanId
    );

    @Query("select w from WeekPlanTaskRelate as w where w.storyId=:storyId and w.targetType=:targetType order by w.orderBy asc")
    List<WeekPlanTaskRelate> getWeekPlanTaskRelatesByStoryIdAndTargetType(
            @Param("storyId") Integer storyId,
            @Param("targetType") Integer targetType
    );

    @Query("select count(w) from WeekPlanTaskRelate as w where w.storyId=:storyId ")
    Integer countByStoryId( @Param("storyId")Integer storyId);

    WeekPlanTaskRelate findByWeekPlanIdAndDayNum(@Param("weekPlanId")Integer weekPlanId, @Param("dayNum")Integer dayNum);

    WeekPlanTaskRelate findById(Integer id);

}
