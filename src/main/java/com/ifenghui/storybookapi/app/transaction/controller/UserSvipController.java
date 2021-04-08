package com.ifenghui.storybookapi.app.transaction.controller;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.transaction.service.UserSvipService;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/usersvip")
@Api(value="超级用户相关",description = "超级用户相关")
public class UserSvipController {

    @Autowired
    UserSvipService userSvipService;




}
