package com.ifenghui.storybookapi.app.system.controller;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.style.CheckCodeStyle;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ifenghui.storybookapi.app.system.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date: 2019/1/2 13:45
 * @Description:
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/api/message")
@Api(value="信息",description = "信息相关接口")
public class MessageController {

    Logger logger=Logger.getLogger(MessageController.class);

    @Autowired
    MessageService messageService;

    @ResponseBody
    @RequestMapping(value = "/send_message", method = RequestMethod.POST)
    public BaseResponse sendMsg(
            @ApiParam(value = "电话号码") @RequestParam String phone,
            @ApiParam(value = "模板code") @RequestParam String templateCode,
            @ApiParam(value = "code") @RequestParam String code
//            @ApiParam(value = "字段信息") @RequestParam Map<String,String> map
    ) {

        Map<String,String> map = new HashMap<>();

        map.put("code",code);
        messageService.sendSms(phone,templateCode,map);
//        for(CheckCodeStyle checkCodeStyle:CheckCodeStyle.values()){
//            if(checkCodeStyle.getMode().equals(templateCode)){
//                messageService.sendSms(phone,checkCodeStyle,map);
//            }
//        }


        return new BaseResponse();
    }


}
