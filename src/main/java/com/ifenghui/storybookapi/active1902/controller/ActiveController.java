package com.ifenghui.storybookapi.active1902.controller;


import com.ifenghui.storybookapi.active1902.dao.ScheduleDao;
import com.ifenghui.storybookapi.active1902.entity.Schedule;
import com.ifenghui.storybookapi.active1902.entity.UserAwards;
import com.ifenghui.storybookapi.active1902.response.IsGetAllResponse;
import com.ifenghui.storybookapi.active1902.response.IsGetAwardsResponse;
import com.ifenghui.storybookapi.active1902.service.ActiveService;
import com.ifenghui.storybookapi.active1902.service.UserAwardsService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.user.service.UserTokenService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：
 * @Date: 2019/2/19 10:41
 * @Description:
 */
@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@RequestMapping(value = "/activity1902/active", name = "故事/集激活相关")
public class ActiveController {


    @Autowired
    UserService userService;
    @Autowired
    ActiveService activeService;
    @Autowired
    ScheduleDao scheduleDao;
    @Autowired
    UserAwardsService userAwardsService;

    @Autowired
    UserTokenService userTokenService;

    @CrossOrigin
    @ApiOperation(value = "查看本期是否领取")
    @RequestMapping(value = "isGet", name = "查看本期是否领取", method = RequestMethod.GET)
    @ResponseBody
    public IsGetAwardsResponse isGet(
            @ApiParam(value = "token") @RequestParam String token,
            @ApiParam(value = "排期") @RequestParam Integer scheduleId
    ){
        User user = userService.findUserByToken(token);
        Integer userId = user.getId().intValue();
        UserAwards userAwards = userAwardsService.getAwardsByUserIdAndScheduleId(userId, scheduleId);

        IsGetAwardsResponse response = new IsGetAwardsResponse();
        if(userAwards != null){
            response.setIsGetAwards(1);
        }else {
            response.setIsGetAwards(0);
        }

        return response;
    }


    @CrossOrigin
    @ApiOperation(value = "激活故事")
    @RequestMapping(value = "buyStoryRecord", name = "激活故事", method = RequestMethod.POST)
    @ResponseBody

    public synchronized IsGetAllResponse buyStoryRecord(
            @ApiParam(value = "token") @RequestParam String token,
            @ApiParam(value = "故事Id") @RequestParam Integer storyId,
            @ApiParam(value = "排期") @RequestParam Integer scheduleId
    ) {

        User user = userService.findUserByToken(token);
        Integer userId = user.getId().intValue();
        activeService.addBuyStoryRecord(token,userId, storyId,scheduleId);
        userAwardsService.addRecord(userId,scheduleId,1,storyId,0);
        //查看是否有未领取奖励
        List<Schedule> schedules = scheduleDao.findAll();
        boolean b = userAwardsService.isGetAllAwards(userId, schedules);
        IsGetAllResponse response = new IsGetAllResponse();
        if(b){
            response.setIsGetAll(1);
        }else {
            response.setIsGetAll(0);
        }
        return response;
    }

    @CrossOrigin
    @ApiOperation(value = "激活故事集")
    @RequestMapping(value = "buySerialStoryRecord", name = "激活故事集", method = RequestMethod.POST)
    @ResponseBody
    public synchronized IsGetAllResponse buySerialStoryRecord(
            @ApiParam(value = "token") @RequestParam String token,
            @ApiParam(value = "故事Id") @RequestParam Integer serialStoryId,
              @ApiParam(value = "排期") @RequestParam Integer scheduleId
    ) {

        User user = userService.findUserByToken(token);
        Integer userId = user.getId().intValue();
        activeService.addBuySerialStoryRecord(token,userId, serialStoryId,scheduleId);
        userAwardsService.addRecord(userId,scheduleId,2,0,serialStoryId);
        //查看是否有未领取奖励
        List<Schedule> schedules = scheduleDao.findAll();
        boolean b = userAwardsService.isGetAllAwards(userId, schedules);
        IsGetAllResponse response = new IsGetAllResponse();
        if(b){
            response.setIsGetAll(1);
        }else {
            response.setIsGetAll(0);
        }

        return response;
    }

}
