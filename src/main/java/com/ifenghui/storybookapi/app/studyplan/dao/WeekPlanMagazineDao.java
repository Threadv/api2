package com.ifenghui.storybookapi.app.studyplan.dao;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanMagazine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WeekPlanMagazineDao extends JpaRepository<WeekPlanMagazine, Integer> {

    @Query("select w from WeekPlanMagazine as w where w.date=:date")
    List<WeekPlanMagazine> getWeekPlanMagazinesByDate(
        @Param("date") Integer date
    );

}
