package com.ifenghui.storybookapi.app.studyplan.dao;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanLabelStory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WeekPlanLabelStoryDao extends JpaRepository<WeekPlanLabelStory, Integer> {

    @Cacheable(cacheNames = "getWeekPlanLabelStoriesByTargetTypeAndTargetValue",key = "'getWeekPlanLabelStoriesByTargetTypeAndTargetValue'+#p0+'_'+#p1")
    @Query("select w from WeekPlanLabelStory as w where w.targetType=:targetType and w.targetValue=:targetValue")
    public List<WeekPlanLabelStory> getWeekPlanLabelStoriesByTargetTypeAndTargetValue(
        @Param("targetType") Integer targetType,
        @Param("targetValue") Integer targetValue
    );

    @Query("select w from WeekPlanLabelStory as w where w.targetType=:targetType and w.targetValue=:targetValue and w.labelType=:labelType")
    public List<WeekPlanLabelStory> getWeekPlanLabelStoriesByTargetTypeAndTargetValueAndLabelType(
            @Param("targetType") Integer targetType,
            @Param("targetValue") Integer targetValue,
            @Param("labelType") Integer labelType
    );

}
