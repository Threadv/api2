package com.ifenghui.storybookapi.active1902.controller;


import com.ifenghui.storybookapi.active1902.dao.ScheduleDao;
import com.ifenghui.storybookapi.active1902.dao.StatisticsDao;
import com.ifenghui.storybookapi.active1902.entity.*;
import com.ifenghui.storybookapi.active1902.response.*;
import com.ifenghui.storybookapi.active1902.service.*;
import com.ifenghui.storybookapi.active1902.style.AwardsStyle;
import com.ifenghui.storybookapi.active1902.style.ContStyle;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.story.dao.SerialStoryDao;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Date: 2019/2/19 10:50
 * @Description:
 */
@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@RestController
@RequestMapping(value = "/activity1902/index", name = "首页相关")
public class Index1902Controller {


    @Autowired
    UserAnswerService userAnswerService;
    @Autowired
    UserService userService;
    @Autowired
    UserAwardsService userAwardsService;
    @Autowired
    AnswerService answerService;
    @Autowired
    QuestionService questionService;
    @Autowired
    AwardsService awardsService;
    @Autowired
    StoryDao storyDao;
    @Autowired
    SerialStoryDao serialStoryDao;
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    ScheduleDao scheduleDao;
    @Autowired
    StatisticsDao statisticsDao;

    @CrossOrigin
    @ApiOperation(value = "首页入口")
    @RequestMapping(value = "index", name = "首页入口", method = RequestMethod.GET)
    @ResponseBody
    public IndexResponse index(
            @ApiParam(value = "token" ,required = false) @RequestParam(required = false) String token,
            @ApiParam(value = "scheduleId" ,required = false) @RequestParam(required = false) Integer scheduleId
    ) {

        IndexResponse response = new IndexResponse();
        Schedule schedule;
        if(scheduleId ==null || scheduleId.equals("") || scheduleId ==0){
            schedule = scheduleService.getLastSchedule();
        }else {
            schedule = scheduleDao.findOne(scheduleId);
        }
        response.setSchedule(schedule);
        if(token ==null || token.equals("") || token.equals("null")){
            return response;
        }

        User user = userService.findUserByToken(token);
        Integer userId = user.getId().intValue();
        //查看是否领取奖励故事，故事集
        Integer countStory = userAwardsService.countAwardsByType(schedule.getId(), userId, AwardsStyle.STORY);
        //是否领取过故事集
        Integer countSerialStory = userAwardsService.countAwardsByTypeAndUserId(userId, AwardsStyle.SERIAL_STORY);
        Integer count = userAwardsService.countAwardsByUserId(userId);
        response.setIsGetStory(countStory);
        response.setIsGetSerialStory(countSerialStory);
        if(count ==2){
            response.setAwardsStyle(2);
        }else {
            response.setAwardsStyle(1);
        }
        if(user.getIsAbilityPlan() ==1){
            response.setIsAbil(1);
        }else {
            response.setIsAbil(0);
        }
        return response;
    }

    @CrossOrigin
    @ApiOperation(value = "查看我的奖励")
    @RequestMapping(value = "awards", name = "查看我的奖励", method = RequestMethod.GET)
    @ResponseBody
    public UserAwardsListResponse awards(
            @ApiParam(value = "token") @RequestParam String token,
            @ApiParam(value = "status 1 已完成") @RequestParam Integer status
    ) {

        UserAwardsListResponse response = new UserAwardsListResponse();
        User user = userService.findUserByToken(token);
        Integer userId = user.getId().intValue();
        List<UserAwards> awardsList;
        List<Schedule> scheduleList;
        if (status == 1) {
            awardsList = userAwardsService.findAwardsByUserId(userId);
            for (UserAwards awards:awardsList) {
                Schedule schedule = scheduleDao.findOne(awards.getScheduleId());
                awards.setSchedule(schedule);
            }
            response.setAwardsList(awardsList);
        } else {
            scheduleList = userAwardsService.findUnGetAwardsByUserId(userId);
            response.setScheduleList(scheduleList);
        }
        return response;
    }

    @CrossOrigin
    @ApiOperation(value = "问题列表")
    @RequestMapping(value = "questions", name = "问题列表", method = RequestMethod.GET)
    @ResponseBody
    public QuestionListResponse questions(
            @ApiParam(value = "排期") @RequestParam Integer scheduleId
    ) {

        List<Question> questionList = questionService.findQuestionsByScheduleId(scheduleId);

        QuestionListResponse response = new QuestionListResponse();
        response.setQuestionList(questionList);
        return response;
    }

    @CrossOrigin
    @ApiOperation(value = "回答问题")
    @RequestMapping(value = "userAnswer", name = "回答问题", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse userAnswer(
            @ApiParam(value = "排期") @RequestParam Integer scheduleId,
            @ApiParam(value = "问题") @RequestParam Integer questionId,
            @ApiParam(value = "答案") @RequestParam Integer answerId,
            @ApiParam(value = "token") @RequestParam String token
    ) {
        User user = userService.findUserByToken(token);
        Integer userId = user.getId().intValue();
        userAnswerService.addRecord(userId,scheduleId,questionId,answerId);

        return new BaseResponse();

    }

    @CrossOrigin
    @ApiOperation(value = "单个问题")
    @RequestMapping(value = "question", name = "问题", method = RequestMethod.GET)
    @ResponseBody
    public QuestionResponse question(
            @ApiParam(value = "排期") @RequestParam Integer scheduleId,
            @ApiParam(value = "问题") @RequestParam Integer questionId
    ) {

        Question question = questionService.findQuestionsByScheduleIdAndQuestionId(scheduleId,questionId);

        QuestionResponse response = new QuestionResponse();
        response.setQuestion(question);
        return response;
    }


    @CrossOrigin
    @ApiOperation(value = "问题答案列表")
    @RequestMapping(value = "answers", name = "问题答案列表", method = RequestMethod.GET)
    @ResponseBody
    public AnswerListResponse awards(
            @ApiParam(value = "问题Id") @RequestParam Integer questionId
    ) {

        List<Answer> answerList = answerService.getAnswersByQuestionId(questionId);

        AnswerListResponse response = new AnswerListResponse();
        response.setAnswerList(answerList);
        return response;
    }


    @CrossOrigin
    @ApiOperation(value = "礼品故事列表")
    @RequestMapping(value = "storys", name = "礼品故事", method = RequestMethod.GET)
    @ResponseBody
    public AwardsListResponse storys(
            @ApiParam(value = "礼品类型 1 故事 2 故事集") @RequestParam Integer type,
            @ApiParam(value = "排期") @RequestParam Integer scheduleId
    ) {

        if(AwardsStyle.getById(type)==null){
            return new AwardsListResponse();
        }
        List<Awards> awardsList = awardsService.getAwardsByType(AwardsStyle.getById(type),scheduleId);
        List<Story> storyList = new ArrayList<>();
        SerialStory serialStory = null;
        for (Awards a : awardsList) {
            if(type ==1){
                Story story = storyDao.findOne(a.getStoryId().longValue());
                storyList.add(story);
            }else if(type ==2){
                serialStory = serialStoryDao.findOne(a.getSerialStoryId().longValue());
            }
        }
        AwardsListResponse response = new AwardsListResponse();
        response.setAwardsList(awardsList);
        response.setStoryList(storyList);
        response.setSerialStory(serialStory);
        return response;
    }

    @CrossOrigin
    @ApiOperation(value = "添加统计")
    @RequestMapping(value = "count", name = "添加统计", method = RequestMethod.POST)
    @ResponseBody
    public synchronized BaseResponse count(
            @ApiParam(value = "统计类型") @RequestParam ContStyle style,
            @ApiParam(value = "scheduleId") @RequestParam Integer scheduleId,
            @ApiParam(value = "token") @RequestParam String token
    ) throws ParseException {

        User user = userService.findUserByToken(token);
        Integer userId = user.getId().intValue();

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String today = sdf.format(new Date());
        Statistics statistics1 = statisticsDao.findStatisticsByUserIdAndTypeaAndCreateTime(userId, style.getId(), sdf.parse(today));
        if (statistics1 != null) {
            return new BaseResponse();
        }
        Statistics statistics = new Statistics();
        statistics.setType(style.getId());
        statistics.setName(style.getName());
        statistics.setScheduleId(scheduleId);
        statistics.setUserId(userId);
        statistics.setCreateTime(new Date());
        statisticsDao.save(statistics);

        if(style==ContStyle.LOGIN){
            statisticsDao.updateType1(scheduleId);
        }
        if(style==ContStyle.FINISH){
            statisticsDao.updateType4(scheduleId);
        }

        return new BaseResponse();
    }

//    @RequestMapping(value = "/get_wx_data", method = RequestMethod.GET)
//    @ResponseBody
//    @ApiOperation(value = "获得微信分享所需数据", notes = "获得微信分享所需数据")
//    WxJsonResponse getWxData(
//            @ApiParam(value = "当前页面url") @RequestParam String url
//    ) {
//
//        WxJsonResponse response = new WxJsonResponse();
//        Config.appid = MyEnv.env.getProperty("fenxiao.appId");
//        try {
//
//            String[] strings;
//            String uri;
//            if (url.contains("#")) {
//                strings = url.split("#");
//                uri = strings[0];
//            } else {
//                uri = url;
//            }
//            String ticket = Token.getTicket();
//            String noncestr = RandomStringGenerator.getRandomStringByLength(32);
//            long timestamp = System.currentTimeMillis() / 1000;
//            //请勿更换字符组装顺序
//            String sign = "jsapi_ticket=" + ticket + "&noncestr="
//                    + noncestr + "&timestamp=" + timestamp
//                    + "&url=" + uri;
//            String signature = new SHA1().getDigestOfString(sign.getBytes("utf-8"));
//            System.out.println(url);
//            System.out.println("url:" + uri);
//            System.out.println(sign);
//            System.out.println(signature);
//            response.setAppId(Config.appid);
//            response.setTimestamp(timestamp);
//            response.setNonceStr(noncestr);
//            response.setSignature(signature);
//            return response;
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.getStatus().setCode(2);
//            response.getStatus().setMsg(e.getMessage());
//            return response;
//        }
//    }
}
