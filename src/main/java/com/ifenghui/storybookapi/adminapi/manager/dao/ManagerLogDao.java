package com.ifenghui.storybookapi.adminapi.manager.dao;


import com.ifenghui.storybookapi.adminapi.manager.entity.ManagerLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ManagerLogDao extends JpaRepository<ManagerLog, Integer>,JpaSpecificationExecutor {

    @Query("select l from ManagerLog l where l.targetType=:targetType")
    public Page<ManagerLog> findAllByTargetType(@Param("targetType") Integer actionType, Pageable pageable);


    @Query("select l from ManagerLog l where l.targetType=:targetType and l.targetValue=:targetValue")
    public Page<ManagerLog> findAllByTargetTypeAndTargetValue(@Param("targetType") Integer actionType, @Param("targetValue") Integer targetValue, Pageable pageable);

}
