package com.ifenghui.storybookapi.app.social.controller;

/**
 * Created by jia on 2017/11/07.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifenghui.storybookapi.app.social.entity.CheckIn;
import com.ifenghui.storybookapi.app.social.response.*;
import com.ifenghui.storybookapi.app.social.response.StarRule;
import com.ifenghui.storybookapi.app.social.entity.UserReadDurationRecord;
import com.ifenghui.storybookapi.app.social.entity.UserReadRecord;
import com.ifenghui.storybookapi.app.social.service.CheckInService;
import com.ifenghui.storybookapi.app.social.service.DayTaskService;
import com.ifenghui.storybookapi.app.social.service.UserReadDurationRecordService;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanTaskFinishService;
import com.ifenghui.storybookapi.app.transaction.service.order.PayStoryOrderService;
import com.ifenghui.storybookapi.app.transaction.service.order.PaySubscriptionOrderService;
import com.ifenghui.storybookapi.app.wallet.entity.Wallet;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.app.social.service.UserReadRecordService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.service.UserStarRecordService;
import com.ifenghui.storybookapi.style.LabelTargetStyle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/userReadRecord")
@Api(value = "????????????",description = "?????????????????????")
public class UserReadRecordController {

    Logger logger = Logger.getLogger(UserReadRecordController.class);

    @Autowired
    UserReadRecordService userReadRecordService;

    @Autowired
    UserService userService;

    @Autowired
    StoryService storyService;

    @Autowired
    DayTaskService dayTaskService;

    @Autowired
    UserReadDurationRecordService userReadDurationRecordService;

    @Autowired
    PayStoryOrderService payStoryOrderService;

    @Autowired
    PaySubscriptionOrderService paySubscriptionOrderService;

    @Autowired
    WalletService walletService;

    @Autowired
    UserStarRecordService userStarRecordService;

    @Autowired
    CheckInService checkInService;

    @Autowired
    WeekPlanTaskFinishService weekPlanTaskFinishService;


    @RequestMapping(value="/getUserReadsRecords",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    GetUserReadRecordsResponse getUserReadsRecords(@ApiParam(value = "??????token")@RequestParam String  token,
                                                   @ApiParam(value = "??????")@RequestParam Integer pageNo,
                                                   @ApiParam(value = "??????")@RequestParam Integer pageSize
                                             ) {
        Long userId= null;
        try {
            userId = userService.checkAndGetCurrentUserId(token);
        } catch (ApiNotTokenException e) {
            e.setApimsg("??????token?????????");
        }


        Page<UserReadRecord> readRecords = userReadRecordService.getReadRecordsByUserIdAndPage(userId,1,pageNo,pageSize);

        GetUserReadRecordsResponse readRecordsResponse = new GetUserReadRecordsResponse();

        readRecordsResponse.setReadRecords(readRecords.getContent());
        readRecordsResponse.setJpaPage(readRecords);

        return readRecordsResponse;
    }




    @RequestMapping(value="/addReadRecord",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    AddUserReadRecordResponse addReadRecord(@ApiParam(value = "??????token")@RequestParam String  token,
                                            @ApiParam(value = "??????ID")@RequestParam Long storyId) throws ParseException {
        Long userId= null;
        try {
            userId = userService.checkAndGetCurrentUserId(token);
        } catch (ApiNotTokenException e) {
            e.setApimsg("??????token?????????");
        }

        AddUserReadRecordResponse readRecordResponse = userReadRecordService.addUserReadRecord(userId,storyId);
        if(userId != null) {
            weekPlanTaskFinishService.setWeekPlanTaskFinishEnter(storyId.intValue(), LabelTargetStyle.Story, userId.intValue());
        }

        return readRecordResponse;
    }

    @RequestMapping(value="/add_read_duration_record",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "????????????????????????", notes = "????????????????????????")
    AddUserReadDurationRecordResponse addUserReadDurationRecord(
            @ApiParam(value = "??????token")@RequestParam String  token,
            @ApiParam(value = "??????ID")@RequestParam Long storyId,
            @ApiParam(value = "???????????????")@RequestParam Integer duration
    ) {
        AddUserReadDurationRecordResponse response = new AddUserReadDurationRecordResponse();

        Long userId = null;
        try {
            userId = userService.checkAndGetCurrentUserId(token);
        } catch (ApiNotTokenException e) {
            e.setApimsg("??????token?????????");
        }
        if (duration < 86400){
            UserReadDurationRecord userReadDurationRecord = userReadDurationRecordService.addUserDurationRecord(storyId, userId, duration);
        }
        return response;
    }

    @RequestMapping(value="/user_total_statistic",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "??????????????????????????????", notes = "??????????????????????????????")
    GetTotalStatisticResponse getUserTotalStatistic(
            @ApiParam(value = "??????token")@RequestParam(required = false, defaultValue = "") String  token
    ) throws ParseException {
        GetTotalStatisticResponse response = new GetTotalStatisticResponse();
        Long userId = 0L;
        if(!token.equals("")){
            userId = userService.checkAndGetCurrentUserId(token);
        }
        response.setDayReadStatistic(userReadDurationRecordService.getReadStatistic(userId));
        response.setTotalReadStatistic(userReadDurationRecordService.getTotalReadStatistic(userId));
        if(!userId.equals(0L)){
            response.setFavoriteStory(storyService.getFavoriteReadStory(userId));
            response.setFirstStory(storyService.getFirstReadStory(userId));
        }
        return response;
    }

    @RequestMapping(value="/user_star_rule_statistic",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "??????????????????????????????", notes = "??????????????????????????????")
    GetUserStarRuleResponse getUserStarRuleStatistic(
            @ApiParam(value = "??????token")@RequestParam(required = false, defaultValue = "") String  token
    ) throws ParseException {
        GetUserStarRuleResponse response = new GetUserStarRuleResponse();

        Long userId = 0L;
        Integer totalStar = 0;
        Integer todayStar = 0;
        Integer continueCheckIn = 0;
        Integer hasCheckIn = 0;
        if(!token.equals("")){
            userId = userService.checkAndGetCurrentUserId(token);
            Wallet wallet = walletService.getWalletByUserId(userId);
            totalStar = wallet.getStarCount();

            logger.info("---- total -- star  --???" + totalStar + "---userid---???" + userId);
            todayStar = userStarRecordService.getTodayUserStarCount(userId);
            logger.info("todystar:" + todayStar + "userId:"+userId);
            CheckIn checkIn = checkInService.findOneByUserId(userId);
            hasCheckIn = checkInService.checkInValidate(userId);
            if(checkIn != null){
                continueCheckIn = checkIn.getContinueDays();
            }
        }
        response.setContinueCheckIn(continueCheckIn);
        response.setTotalStar(totalStar);
        response.setTodayStar(todayStar);
        response.setHasCheckIn(hasCheckIn);
        List<StarRule> starRuleList = new ArrayList<>();
        starRuleList.add(
            new StarRule(1,"????????????","??????????????????????????????1???", userStarRecordService.getDayTaskList(userId))
        );
        starRuleList.add(
            new StarRule(2,"????????????","?????????????????????1???",userStarRecordService.getRookieTaskList(userId))
        );
        /**
         * v 2.0.0 ??????????????????
         */
//        starRuleList.add(
//            new StarRule(3,"????????????","???????????????????????????",userStarRecordService.getMoneyTaskList(userId))
//        );
        response.setStarRuleList(starRuleList);
        return response;
    }


    @RequestMapping(value="/synchro_read_duration_record",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "????????????????????????", notes = "????????????????????????")
    SynchroViewRecordResponse synchroReadDurationRecord(
            @ApiParam(value = "??????token")@RequestParam String  token,
            @ApiParam(value = "??????????????????json??????")@RequestParam String jsonData
    )throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
//        {
//            "readDurationRecordList": [
//            {
//                "storyId": 1,
//                 "time": "2017-02-11",
//                 "duration":10
//            },
//            {
//                "storyId": 2,
//                "time": "2017-02-11"
//                "duration":10
//            }
//          ]
//        }
        //??????json??????
        //jackson json????????????
        ObjectMapper objectMapper = new ObjectMapper();
        SynchroReadDurationRecordJsonData synchroReadDurationRecordJsonData =new SynchroReadDurationRecordJsonData();
        SynchroViewRecordResponse response = new SynchroViewRecordResponse();
        try {
            SynchroReadDurationRecordJsonData resp = objectMapper.readValue( jsonData, SynchroReadDurationRecordJsonData.class);
            synchroReadDurationRecordJsonData.setReadDurationRecordList(resp.getReadDurationRecordList());
            userReadDurationRecordService.addReadDurationRecordList(resp.getReadDurationRecordList(),userId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


//    //????????????????????????
//    @RequestMapping(value="/delViewrecord",method = RequestMethod.DELETE)
//    @ResponseBody
//    @ApiOperation(value = "??????????????????", notes = "??????????????????")
//    Void delViewrecord(@ApiParam(value = "??????token")@RequestParam String  token,
//                                        @ApiParam(value = "ID")@RequestParam Long id
//    ) throws ApiNotTokenException {
//        Long userId = null;
//        userId = userService.checkAndGetCurrentUserId(token);
//
//        ViewRecord viewrecord=viewRecordService.getViewrecord(id);
//        if(viewrecord==null || userId!=viewrecord.getUser().getId()){
//            return null;
//        }
//        viewRecordService.delViewrecord(id);
//        return null;
//
//
//    }

}
