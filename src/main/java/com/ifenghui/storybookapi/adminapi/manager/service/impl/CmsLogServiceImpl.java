package com.ifenghui.storybookapi.adminapi.manager.service.impl;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ifenghui.storybookapi.adminapi.manager.dao.CmsLogDao;
import com.ifenghui.storybookapi.adminapi.manager.entity.CmsLog;
import com.ifenghui.storybookapi.adminapi.manager.entity.Manager;
import com.ifenghui.storybookapi.adminapi.manager.entity.ManagerToken;
import com.ifenghui.storybookapi.adminapi.manager.service.CmsLogService;
import com.ifenghui.storybookapi.adminapi.manager.service.ManagerService;
import com.ifenghui.storybookapi.adminapi.manager.service.ManagerTokenService;
import com.ifenghui.storybookapi.util.HeadManagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class CmsLogServiceImpl implements CmsLogService {

    @Autowired
    CmsLogDao cmsLogDao;

    @Autowired
    ManagerTokenService managerTokenService;

    @Autowired
    ManagerService managerService;

    @Override
    public Page<CmsLog> findAll(String entityName, Integer contentId, int pageNo, int pageSize) {
        CmsLog cmsLog = new CmsLog();
        cmsLog.setEntityName(entityName);
        cmsLog.setContentId(contentId);
        Pageable pageable = new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id"));
        return cmsLogDao.findAll(Example.of(cmsLog),pageable);
    }

    @Override
    public CmsLog save(String entityName, Integer contentId,Integer actionType,HttpServletRequest request) {

        CmsLog cmsLog = new CmsLog();
        String token= HeadManagerUtil.getManagerToken(request);
        ManagerToken managerToken=managerTokenService.findOneByToken(token);
        Manager manager=managerService.findOne(managerToken.getManagerId());
        cmsLog.setUserId(manager.getId());
        cmsLog.setNick(manager.getNick());

        Gson gson = new Gson();
        String msg = gson.toJson( request.getParameterMap());
        cmsLog.setMsg(msg);

        cmsLog.setCreateTime(new Date());
        cmsLog.setEntityName(entityName);
        cmsLog.setContentId(contentId);
        cmsLog.setActionType(actionType);

        return cmsLogDao.save(cmsLog);
    }
}
