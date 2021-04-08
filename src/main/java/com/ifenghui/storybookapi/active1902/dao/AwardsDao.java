package com.ifenghui.storybookapi.active1902.dao;


import com.ifenghui.storybookapi.active1902.entity.Awards;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Date: 2019/2/19 14:10
 * @Description:
 */
public interface AwardsDao extends JpaRepository<Awards,Integer> {


    List<Awards> findAwardsByTypeAndScheduleId(Integer type, Integer scheduleId, Pageable pageable);
    List<Awards> findAwardsByType(Integer type, Pageable pageable);
}
