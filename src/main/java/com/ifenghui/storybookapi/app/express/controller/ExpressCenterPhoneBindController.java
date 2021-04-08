package com.ifenghui.storybookapi.app.express.controller;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterPhoneBind;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterPhoneBindService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.remote.RemoteFenxiaoApiService;
import com.ifenghui.storybookapi.remote.resp.FenxiaoUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)//支持跨域
@Controller
//@EnableAutoConfiguration
@Api(value = "物流中心，用户绑定", description = "物流中心，用户绑定")
@RequestMapping("/api/expresscenter/user")
public class ExpressCenterPhoneBindController {

    @Autowired
    ExpressCenterPhoneBindService phoneBindService;

    @Autowired
    UserService userService;

    @Autowired
    RemoteFenxiaoApiService remoteFenxiaoApiService;

    @ApiOperation(value = "发送验证码",notes = "")
    @RequestMapping(value = "/send_verify_code",method = RequestMethod.POST)
    @ResponseBody
    BaseResponse sendVerifyCode(String phone){
        BaseResponse response = new BaseResponse();
        phoneBindService.sendSmsVerification(phone);
        return response;
    }

    @ApiOperation(value = "绑定手机号",notes = "")
    @RequestMapping(value = "/bind_phone",method = RequestMethod.POST)
    @ResponseBody
    BaseResponse expressCenterBindPhone(@RequestHeader(value = "ssToken") String ssToken,
                                        @RequestHeader(value = "userType") Integer userType,
                                        String phone, String code){
        BaseResponse response = new BaseResponse();
        //根据token查询用户id
        Integer userOutId=null;
        if(userType==1){
            Long userId = userService.checkAndGetCurrentUserId(ssToken);
            userOutId = userId.intValue();
        }else if(userType==2){
            FenxiaoUser fenxiaoUser= remoteFenxiaoApiService.getUserByUnionId(ssToken).getUser();
            userOutId = fenxiaoUser.getId();
        }

        boolean flag=phoneBindService.checkSmsVerification(phone,code);
        if(flag==false){
            response.getStatus().setCode(0);
            response.getStatus().setMsg("验证码错误或已过期");
        }
        ExpressCenterPhoneBind phoneBind=new ExpressCenterPhoneBind();

        phoneBind.setPhone(phone);
        phoneBind.setUserOutId(userOutId);
        phoneBind.setUserType(userType);

        phoneBindService.addExpressCenterPhoneBind(phoneBind);

        return response;
    }



}
