package com.ifenghui.storybookapi.app.app.controller;

/**
 * Created by wang
 * 问题提示
 */

import com.ifenghui.storybookapi.app.app.entity.Help;
import com.ifenghui.storybookapi.app.app.response.*;
import com.ifenghui.storybookapi.app.app.service.HelpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/help")
@Api(value = "问题提示",description = "问题提示")
public class HelpController {

    @Autowired
    HelpService questionService;

    @RequestMapping(value="/getQuestions",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "问题提示",notes = "问题提示")
    HelpsResponse getQuestions() {

        List<Help> helps=questionService.findAll();
        HelpsResponse response = new HelpsResponse();
        response.setHelps(helps);

        return response;
    }


}
