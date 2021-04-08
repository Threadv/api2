package com.ifenghui.storybookapi.adminapi.manager.dao;


import com.ifenghui.storybookapi.adminapi.manager.entity.CmsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CmsLogDao extends JpaRepository<CmsLog, Integer>,JpaSpecificationExecutor {

}
