package com.ifenghui.storybookapi.app.social.controller;

import com.ifenghui.storybookapi.app.social.response.*;
import com.ifenghui.storybookapi.app.story.entity.Paster;
import com.ifenghui.storybookapi.app.story.service.PasterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 日程表
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/api/schedule")
@Api(value="每日任务",description = "每日任务")
public class ScheduleController {


    @Autowired
    PasterService pasterService;


    @RequestMapping(value="/all_paster",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获得所有相机贴图", notes = "获得所有相机贴图")
    GetAllPasterPageResponse getAllPasterPage(
            @ApiParam(value = "pageSize")@RequestParam Integer pageSize,
            @ApiParam(value = "pageNo")@RequestParam Integer pageNo
    ) {
        GetAllPasterPageResponse response = new GetAllPasterPageResponse();
        Page<Paster> pasterPage = pasterService.getAllPasterPage(pageSize,pageNo);
        response.setPasterList(pasterPage.getContent());
        response.setJpaPage(pasterPage);
        return response;
    }

}
