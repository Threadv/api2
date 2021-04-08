package com.ifenghui.storybookapi.app.app.controller;


import com.ifenghui.storybookapi.app.app.entity.Popup;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.app.response.popup.PopupsResponse;
import com.ifenghui.storybookapi.app.app.service.PopupService;
import com.ifenghui.storybookapi.app.app.service.PopupUserService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.user.service.UserTokenService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Component
@RequestMapping(value = "/api/popup", name = "弹窗")
public class PopupController {

    Logger logger = Logger.getLogger(PopupController.class);


    @Autowired
    PopupService popupService;

    @Autowired
    PopupUserService popupUserService;

//    @Autowired
//    Map<String, PopupTaskService> popupTask;

    @Autowired
    UserTokenService userTokenService;

    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest request;

//    @Autowired
//    List<PopupTaskService> popupTaskServices;

    @RequestMapping(value = "/get_popups", method = RequestMethod.GET)
    @ApiOperation(value = "获得首页弹窗", notes = "")
    @ResponseBody
    PopupsResponse getPopups(
//            @RequestHeader("ktxToken") String ktxToken
    ) {

        Integer userId=((Long)request.getAttribute("loginId")).intValue();

//        Integer userId = userTokenService.getUserIdByTokenAndCheck(ktxToken);

        PopupsResponse response = new PopupsResponse();

//        User user=userService.getUser(userId.longValue());


        //1，按时间获得允许弹窗的内容
        List<Popup> currentPopup= popupService.findCurrentAll(userId);

        response.setPopups(currentPopup);



        return response;
    }

    @RequestMapping(value = "/click_popup", method = RequestMethod.GET)
    @ApiOperation(value = "点击弹窗,获得用户已经知道的事件", notes = "数据量小，这里用get发送数据")
    @ResponseBody
    BaseResponse clickPopup(
            @ApiParam("popupId") @RequestParam Integer popupId,
            @ApiParam("pupupBtnType(0 关闭,1 领取)") @RequestParam(defaultValue = "1") Integer popupBtnType
    ) {
        Integer userId=((Long)request.getAttribute("loginId")).intValue();

        if(userId!=0){
            popupUserService.addPopupUser(popupId,userId);
        }
        return new BaseResponse();
    }
}
