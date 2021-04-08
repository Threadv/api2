package com.ifenghui.storybookapi.adminapi.manager.service;


import com.ifenghui.storybookapi.adminapi.manager.entity.ManagerLog;
import com.ifenghui.storybookapi.style.ActionLogType;
import org.springframework.data.domain.Page;

public interface ManagerLogService {

    Page<ManagerLog> findAll(ActionLogType actionLogType, int pageNo, int pageSize);

    Page<ManagerLog> findByActionTypeAndTargetValue(ActionLogType actionLogType, Integer targetValue, int pageNo, int pageSize);

    ManagerLog save(ManagerLog managerLog);

    ManagerLog findOne(Integer id);

//    ManagerLog addManagerLog(int manager_id,Object ob)

    
}
