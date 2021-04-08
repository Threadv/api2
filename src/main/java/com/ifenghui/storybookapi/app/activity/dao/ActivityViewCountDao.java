package com.ifenghui.storybookapi.app.activity.dao;

import com.ifenghui.storybookapi.app.activity.entity.ActivityViewCount;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Created by wml on 2017/4/10.
 */
@Transactional
@Deprecated
public interface ActivityViewCountDao extends JpaRepository<ActivityViewCount, Integer> {


}