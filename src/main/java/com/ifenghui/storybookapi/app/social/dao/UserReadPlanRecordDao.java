package com.ifenghui.storybookapi.app.social.dao;

import com.ifenghui.storybookapi.app.social.entity.UserReadPlanRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserReadPlanRecordDao extends JpaRepository<UserReadPlanRecord, Integer> {

    List<UserReadPlanRecord> getAllByPlanNameAndUserIdOrderByFinishRecordAsc(Integer planName, Integer userId);

    UserReadPlanRecord getUserReadPlanRecordByFinishRecordAndAndUserId(java.sql.Date finishRecord, Integer userId);
}
