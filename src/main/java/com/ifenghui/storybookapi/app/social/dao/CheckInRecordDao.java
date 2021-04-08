package com.ifenghui.storybookapi.app.social.dao;

import com.ifenghui.storybookapi.app.social.entity.CheckInRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Created by wml on 2017/4/10.
 */
@Transactional
public interface CheckInRecordDao extends JpaRepository<CheckInRecord, Integer> {


}