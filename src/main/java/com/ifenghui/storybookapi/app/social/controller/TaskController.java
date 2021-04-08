package com.ifenghui.storybookapi.app.social.controller;

import com.ifenghui.storybookapi.app.social.response.*;
import com.ifenghui.storybookapi.config.StarConfig;

import com.ifenghui.storybookapi.app.story.entity.Medal;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.app.social.service.DayTaskService;
import com.ifenghui.storybookapi.app.user.service.UserService;
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

import java.util.ArrayList;
import java.util.List;

@Controller
@EnableAutoConfiguration
@Api(value = "任务勋章道具管理", description = "任务勋章道具管理")
@RequestMapping("/api/task")
public class TaskController {


    @Autowired
    DayTaskService dayTaskService;
    @Autowired
    UserService userService;


    @Deprecated
    @ApiOperation(value = "领取任务",notes = "")
    @RequestMapping(value = "/addDayTaskRecord",method = RequestMethod.GET)
    @ResponseBody
    AddDayTaskRecordResponse addDayTaskRecord(
            @ApiParam(value = "token")@RequestParam String token,
            @ApiParam(value = "taskId（任务id）")@RequestParam Long taskId
    ) {
        Long userId;
        if(token == null || token.length() <= 0){
            userId = 0L;
        }else{
            userId = userService.checkAndGetCurrentUserId(token);
        }
        AddDayTaskRecordResponse response = new AddDayTaskRecordResponse();
        response.getStatus().setCode(0);
        response.getStatus().setMsg("请升级到故事飞船最新版本！");
//        return response;
//        dayTaskService.addDayTaskRecord(userId,taskId);

        return response;
    }

    @ApiOperation(value = "成就手册",notes = "")
    @RequestMapping(value = "/getAchievementHandbook",method = RequestMethod.GET)
    @ResponseBody
    GetAchievementHandbookResponse getAchievementHandbook(
            @ApiParam(value = "token")@RequestParam String token,
            @ApiParam(value = "pageNo")@RequestParam Integer pageNo,
            @ApiParam(value = "pageSize")@RequestParam Integer pageSize
    ) {
        Long userId= null;
        try {
            userId = userService.checkAndGetCurrentUserId(token);
        } catch (ApiNotTokenException e) {
//            e.setApimsg("用户token不存在");
            userId = 0L;
        }
        GetAchievementHandbookResponse response=new GetAchievementHandbookResponse();
        Page<Medal> medalPage = dayTaskService.getAchievementHandbook(userId,pageNo,pageSize);//阅读故事

        Integer userCollectMedalCount = dayTaskService.getUserCollectMedalCount(userId);

        response.setMedals(medalPage.getContent());
        response.setJpaPage(medalPage);
        String title = "成就手册";
        String intro = "欢迎来到你的成就手册，每篇故事都有一个独特的成就。在这里，你可以查看每个故事的密保手机状况。达成你的成就。";
        response.setFinishCount(userCollectMedalCount);
        response.setTitle(title);
        response.setIntro(intro);

        return response;
    }
    @ApiOperation(value = "收集道具",notes = "")
    @RequestMapping(value = "/collectParts",method = RequestMethod.POST)
    @ResponseBody
    CollectPartsResponse collectParts(
            @ApiParam(value = "token")@RequestParam String token,
            @ApiParam(value = "storyId（故事id）")@RequestParam Long storyId,
            @ApiParam(value = "keyName（道具）")@RequestParam String keyName
    ) {
        Long userId;
        if(token == null || token.length() <= 0){
            userId = 0L;
        }else{
            userId = userService.checkAndGetCurrentUserId(token);
        }
        CollectPartsResponse response = new CollectPartsResponse();
        dayTaskService.collectParts(userId,storyId,keyName);
        response.setKeyName(keyName);

        return response;
    }
    @RequestMapping(value="/synchroPartsRecord",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "同步收集道具记录", notes = "同步收集道具记录")
    SynchroPartsRecordResponse synchroPartsRecord(@ApiParam(value = "token")@RequestParam String token,
                                                  @ApiParam(value = "收集记录json数据")@RequestParam String jsonData
    )throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);


        SynchroPartsRecordResponse response = dayTaskService.synchroPartsRecord(userId,jsonData);


        return response;
    }
    @ApiOperation(value = "分享邀请任务",notes = "")
    @RequestMapping(value = "/shareInviteTask",method = RequestMethod.POST)
    @ResponseBody
    ShareTaskInviteResponse shareTask(
            @ApiParam(value = "token")@RequestParam String token
    ) {
        Long userId;
        if(token == null || token.length() <= 0){
            userId = 0L;
        }else{
            userId = userService.checkAndGetCurrentUserId(token);
        }
        ShareTaskInviteResponse response = new ShareTaskInviteResponse();
        dayTaskService.shareInviteTask(userId);

        List<TaskFinish> taskFinishes = new ArrayList<>();

        TaskFinish taskFinish = new TaskFinish();
        String intro ="恭喜你完成分享任务\n获得"+ StarConfig.STAR_SHARE_INVITE+"颗小星星";
        taskFinish.setIntro(intro);
        taskFinishes.add(taskFinish);

        response.setTaskFinishInfo(taskFinishes);

        return response;
    }


    @ApiOperation(value = "获取不同任务星星值获取提示",notes = "")
    @RequestMapping(value = "/getTaskPrompt",method = RequestMethod.GET)
    @ResponseBody
    GetTaskPromptResponse getTaskPrompt(
            @ApiParam(value = "token")@RequestParam String token,
            @ApiParam(value = "type(1,购买2订阅3注册4购买故事集5购买课程6总订单)")@RequestParam Integer type,
            @ApiParam(value = "value(1购买订单id；2订阅订单id；3注册；4购买故事集订单id；5购买课程订单id")@RequestParam(required = false) String value
    ) {

        Long userId = userService.checkAndGetCurrentUserId(token);

        GetTaskPromptResponse response = new GetTaskPromptResponse();
        response = dayTaskService.getTaskPrompt(userId,type,value);

        return response;
    }


    @ApiOperation(value = "获取故事勋章道具收集情况",notes = "")
    @RequestMapping(value = "/getMedalByStoryId",method = RequestMethod.GET)
    @ResponseBody
    GetMedalByStoryIdResponse getMedalByStoryId(
            @ApiParam(value = "token")@RequestParam(required = false) String token,
            @ApiParam(value = "storyId（故事id）")@RequestParam Long storyId
    ) {
        Long userId;
        if(token == null || token.length() <= 0){
            userId = 0L;
        }else{
            userId = userService.checkAndGetCurrentUserId(token);
        }

        GetMedalByStoryIdResponse response = new GetMedalByStoryIdResponse();
        Medal medal = dayTaskService.getMedalByStoryId(storyId,userId);
        response.setMedal(medal);
        return response;
    }
}
