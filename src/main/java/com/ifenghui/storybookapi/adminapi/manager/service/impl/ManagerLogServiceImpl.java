package com.ifenghui.storybookapi.adminapi.manager.service.impl;


import com.ifenghui.storybookapi.adminapi.manager.dao.ManagerLogDao;
import com.ifenghui.storybookapi.adminapi.manager.entity.ManagerLog;
import com.ifenghui.storybookapi.adminapi.manager.service.ManagerLogService;
import com.ifenghui.storybookapi.style.ActionLogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class ManagerLogServiceImpl implements ManagerLogService {

    @Autowired
    ManagerLogDao managerLogDao;

    @Override
    public Page<ManagerLog> findAll(ActionLogType actionLogType, int pageNo, int pageSize) {
        return managerLogDao.findAllByTargetType(actionLogType.getId(),new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));
    }

    @Override
    public Page<ManagerLog> findByActionTypeAndTargetValue(ActionLogType actionLogType, Integer targetValue, int pageNo, int pageSize) {
        return managerLogDao.findAllByTargetTypeAndTargetValue(actionLogType.getId(),targetValue,new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));
    }

    @Override
    public ManagerLog save(ManagerLog managerLog) {
        return managerLogDao.save(managerLog);
    }

    @Override
    public ManagerLog findOne(Integer id) {
        return managerLogDao.findOne(id);
    }
}
