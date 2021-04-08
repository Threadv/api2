package com.ifenghui.storybookapi.app.app.controller;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.app.response.CheckPhoneCodeResponse;
import com.ifenghui.storybookapi.app.app.response.GetNoticeUserStatusResponse;
import com.ifenghui.storybookapi.app.app.service.PhoneCheckCodeService;
import com.ifenghui.storybookapi.style.CheckCodeStyle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/sms")
@Api(value = "发送短信接口", description = "发送短信接口")
public class SmsController {

    @Autowired
    PhoneCheckCodeService phoneCheckCodeService;

    @ApiOperation(value = "发送短信接口")
    @RequestMapping(value = "/send_register_sms",method = RequestMethod.POST)
    @ResponseBody
    public CheckPhoneCodeResponse sendRegisterSms(
            @ApiParam(value = "电话号码") @RequestParam String phone
    ) {
        CheckPhoneCodeResponse response = new CheckPhoneCodeResponse();
        phoneCheckCodeService.createPhoneCheckCode(phone, CheckCodeStyle.REGISTER_CODE);
        return response;
    }

    @ApiOperation(value = "发送物流短信验证")
    @RequestMapping(value = "/send_express_sms",method = RequestMethod.POST)
    @ResponseBody
    CheckPhoneCodeResponse sendCheckSms(
            @ApiParam(value = "电话号码") @RequestParam String phone
    ) {
        CheckPhoneCodeResponse response = new CheckPhoneCodeResponse();
        //TODO:需要物流短信模板
//        phoneCheckCodeService.createPhoneCheckCode(phone, CheckCodeStyle.UNENG);
        return response;
    }



    @ApiOperation(value = "验证验证码情况")
    @RequestMapping(value = "/check_phone_code",method = RequestMethod.GET)
    @ResponseBody
    public CheckPhoneCodeResponse checkPhoneCode(
            @ApiParam(value = "电话号码") @RequestParam String phone,
            @ApiParam(value = "验证码") @RequestParam String code
    ) {
        CheckPhoneCodeResponse response = new CheckPhoneCodeResponse();
        phoneCheckCodeService.checkCode(code, phone);
        return response;
    }



}
