package com.ifenghui.storybookapi.app.studyplan.dao;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanIntro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WeekPlanIntroDao extends JpaRepository<WeekPlanIntro, Integer> {

    @Query("select w from WeekPlanIntro as w where w.planType=:planType and w.weekNum <:weekNum")
    List<WeekPlanIntro> getWeekPlanIntrosByWeekNumAndAndPlanType(
        @Param("weekNum") Integer weekNum,
        @Param("planType") Integer planType
    );

    @Query("select w from WeekPlanIntro as w where w.planType=:planType and w.weekNum=:weekNum")
    WeekPlanIntro getDistinctByPlanTypeAndWeekNum(
        @Param("weekNum") Integer weekNum,
        @Param("planType") Integer planType
    );

    @Query("select w from WeekPlanIntro as w where w.planType=:planType")
    Page<WeekPlanIntro> getWeekPlanIntrosByPlanType(
        @Param("planType") Integer planType,
        Pageable pageable
    );

}
