package com.ifenghui.storybookapi.app.social.controller;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.social.dao.CheckInDao;
import com.ifenghui.storybookapi.app.social.dao.CheckInRecordDao;
import com.ifenghui.storybookapi.app.social.entity.CheckIn;
import com.ifenghui.storybookapi.app.social.response.CheckInResponse;
import com.ifenghui.storybookapi.app.social.service.CheckInService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import com.ifenghui.storybookapi.style.StarRechargeStyle;
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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 签到领星星
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/api/checkin")
@Api(value = "签到", description = "签到任务")
public class CheckInController {

    @Autowired
    WalletService walletService;

    @Autowired
    UserService userService;

    @Autowired
    CheckInDao checkInDao;

    @Autowired
    CheckInRecordDao checkInRecordDao;

    @Autowired
    CheckInService checkInService;

    /**
     * 七天为一周期
     * 获得签到奖励
     */
    @RequestMapping(value = "/check_in", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "签到", notes = "每日签到")
    CheckInResponse checkIn(
            @ApiParam(value = "用户token") @RequestParam String token
    ) {

        CheckInResponse response = new CheckInResponse();
        //通过token获取userId
        Long userId;
        if (token == null || token.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }
        CheckIn checkIn = checkInService.updateCheckInAndSaveRecord(userId);
        if(checkIn.getContinueDays().equals(7)){
            response.setAllStar(35);
            response.setTodayStar(5);
            response.setContinueStar(30);
        } else {
            response.setAllStar(5);
            response.setTodayStar(5);
            response.setContinueStar(0);
        }
        return response;
    }
}
