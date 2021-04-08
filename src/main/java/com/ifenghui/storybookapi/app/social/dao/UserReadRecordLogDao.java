package com.ifenghui.storybookapi.app.social.dao;

import com.ifenghui.storybookapi.app.social.entity.UserReadRecordLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReadRecordLogDao extends JpaRepository<UserReadRecordLog, Integer> {
}
