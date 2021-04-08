package com.ifenghui.storybookapi.app.social.dao;

import com.ifenghui.storybookapi.app.social.entity.UserJoinReadPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserJoinReadPlanDao extends JpaRepository<UserJoinReadPlan, Integer> {

    UserJoinReadPlan getUserJoinReadPlanByUserIdAndPlanName(Integer userId, Integer planName);

    @Query("select count(c) from UserJoinReadPlan as c where c.planName=:planName")
    Integer getUserJoinReadPlanNumberByPlanName(@Param("planName") Integer planName);
}
