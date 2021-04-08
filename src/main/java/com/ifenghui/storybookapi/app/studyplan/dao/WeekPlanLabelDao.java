package com.ifenghui.storybookapi.app.studyplan.dao;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanLabel;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WeekPlanLabelDao extends JpaRepository<WeekPlanLabel, Integer> {

    @Cacheable(cacheNames = "WeekPlanLabelDaoByLabelType",key = "'WeekPlanLabelDaoByLabelType'+#p0")
    @Query("select w from WeekPlanLabel as w where w.labelType=:labelType order by w.orderBy desc")
    List<WeekPlanLabel> getListByLabelType(
        @Param("labelType") Integer labelType
    );


    @Cacheable(cacheNames = "WeekPlanLabelDao_findOne",key = "'WeekPlanLabelDao_findOne'+#p0")
    @Override
    WeekPlanLabel findOne(Integer id);
}
