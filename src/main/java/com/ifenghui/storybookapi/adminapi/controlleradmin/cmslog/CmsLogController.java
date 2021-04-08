package com.ifenghui.storybookapi.adminapi.controlleradmin.cmslog;


import com.ifenghui.storybookapi.adminapi.manager.entity.CmsLog;
import com.ifenghui.storybookapi.adminapi.manager.response.CmsLogsResponse;
import com.ifenghui.storybookapi.adminapi.manager.service.CmsLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@Component
@RequestMapping(value = "/adminapi/cmslog/", name = "操作日志")
public class CmsLogController {

    @Autowired
    CmsLogService cmsLogService;

    @RequestMapping(value = "/getCmsLog", method = RequestMethod.GET)
    @ApiOperation("操作日志")
    @ResponseBody
    CmsLogsResponse getmanager(
            String entityName,
            Integer contentId
    ) {
        CmsLogsResponse response=new CmsLogsResponse();
        Page<CmsLog> page = cmsLogService.findAll(entityName,contentId,0,20);
        response.setCmsLogs(page.getContent());
        return response;
    }


}
