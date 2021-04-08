package com.ifenghui.storybookapi.adminapi.controlleradmin;



import com.ifenghui.storybookapi.adminapi.manager.entity.ManagerLog;
import com.ifenghui.storybookapi.adminapi.manager.response.ManagerLogsResponse;
import com.ifenghui.storybookapi.adminapi.manager.service.ManagerLogService;
import com.ifenghui.storybookapi.style.ActionLogType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@Component
//@RestController
@RequestMapping(value = "/adminapi/managerlog", name = "后台管理员")
public class ManagerLogController {



    @Autowired
    ManagerLogService managerLogService;


    @RequestMapping(value = "/getManagerLogs", method = RequestMethod.GET)
    @ApiOperation("列表")
    @ResponseBody
    ManagerLogsResponse list(
            HttpServletRequest request,
            @ApiParam(value = "pageNo") @RequestParam Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam Integer pageSize,
            @ApiParam(value = "actionLogType") @RequestParam ActionLogType actionLogType,
            @ApiParam(value = "targetValue") @RequestParam Integer targetValue
    ) {
        ManagerLogsResponse response=new ManagerLogsResponse();
        Page<ManagerLog> managerPage=managerLogService.findByActionTypeAndTargetValue(actionLogType,targetValue,pageNo,pageSize);

//        response.setManagers(managerPage.getContent());
        response.setJpaPage(managerPage);
        response.setManagerLogs(managerPage.getContent());
        return response;
    }

}
