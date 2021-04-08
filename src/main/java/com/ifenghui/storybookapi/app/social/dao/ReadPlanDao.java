package com.ifenghui.storybookapi.app.social.dao;

import com.ifenghui.storybookapi.app.social.entity.ReadPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadPlanDao extends JpaRepository<ReadPlan, Integer> {

    ReadPlan getReadPlanByPlanName(Integer planName);
}
