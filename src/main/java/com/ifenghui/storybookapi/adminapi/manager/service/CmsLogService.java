package com.ifenghui.storybookapi.adminapi.manager.service;

import com.ifenghui.storybookapi.adminapi.manager.entity.CmsLog;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;

public interface CmsLogService {

    Page<CmsLog> findAll(String entityName, Integer contentId, int pageNo, int pageSize);

    CmsLog save(String entityName, Integer contentId,Integer actionType,HttpServletRequest request);





    
}
