package com.ifenghui.storybookapi.app.activity.dao;

import com.ifenghui.storybookapi.app.activity.entity.Activity180116;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Created by wml on 2017/4/10.
 */
@Deprecated
@Transactional
public interface Activity180116Dao extends JpaRepository<Activity180116, Long> {


}