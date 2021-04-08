package com.ifenghui.storybookapi.app.studyplan.controller;

import com.ifenghui.storybookapi.aop.ApiConfigAop;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.app.service.ConfigService;
import com.ifenghui.storybookapi.app.social.dao.UserReadRecordDao;
import com.ifenghui.storybookapi.app.social.entity.SignRecord;
import com.ifenghui.storybookapi.app.social.entity.UserReadRecord;
import com.ifenghui.storybookapi.app.social.service.SignRecordService;
import com.ifenghui.storybookapi.app.social.service.TotalReadStatistic;
import com.ifenghui.storybookapi.app.social.service.UserReadDurationRecordService;
import com.ifenghui.storybookapi.app.social.service.UserReadRecordService;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.story.entity.LessonStudyVideo;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.story.service.LessonStudyVideoService;
import com.ifenghui.storybookapi.app.story.service.PromptService;
import com.ifenghui.storybookapi.app.studyplan.dao.WeekPlanReadRecordDao;
import com.ifenghui.storybookapi.app.studyplan.entity.*;
import com.ifenghui.storybookapi.app.studyplan.response.*;
import com.ifenghui.storybookapi.app.studyplan.service.*;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.UserAbilityPlanRelate;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.BuyLessonItemRecord;
import com.ifenghui.storybookapi.app.transaction.service.AbilityPlanOrderService;
import com.ifenghui.storybookapi.app.transaction.service.UserAbilityPlanRelateService;
import com.ifenghui.storybookapi.app.transaction.service.lesson.BuyLessonItemRecordService;
import com.ifenghui.storybookapi.app.transaction.service.lesson.PayLessonOrderService;
import com.ifenghui.storybookapi.app.user.dao.UserAccountDao;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.entity.UserAccount;
import com.ifenghui.storybookapi.app.user.entity.UserExtend;
import com.ifenghui.storybookapi.app.user.entity.UserToken;
import com.ifenghui.storybookapi.app.user.service.UserExtendService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import com.ifenghui.storybookapi.config.StarConfig;
import com.ifenghui.storybookapi.config.StoryConfig;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.style.*;
import com.ifenghui.storybookapi.util.VersionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@EnableAutoConfiguration
@Api(value = "成长计划相关接口", description = "成长计划相关接口")
@RequestMapping("/api/study/plan")
public class StudyPlanController {

    @Autowired
    UserService userService;

    @Autowired
    WeekPlanIntroService weekPlanIntroService;

    @Autowired
    LessonStudyVideoService lessonStudyVideoService;

    @Autowired
    WeekPlanUserRecordService weekPlanUserRecordService;
    @Autowired
    WeekPlanJoinService weekPlanJoinService;

    @Autowired
    WeekPlanTaskRelateService weekPlanTaskRelateService;

    @Autowired
    WeekPlanTaskFinishService weekPlanTaskFinishService;

    @Autowired
    WeekPlanFinishMagazineService weekPlanFinishMagazineService;

    @Autowired
    WeekPlanLabelService weekPlanLabelService;

    @Autowired
    WeekPlanMagazineService weekPlanMagazineService;

    @Autowired
    UserExtendService userExtendService;

    @Autowired
    WalletService walletService;

    @Autowired
    WeekPlanReadRecordService weekPlanReadRecordService;

    @Autowired
    StatisticLabelService statisticLabelService;

    @Autowired
    SignRecordService signRecordService;

    @Autowired
    PromptService promptService;

    @Autowired
    UserReadRecordService userReadRecordService;

    @Autowired
    UserReadDurationRecordService userReadDurationRecordService;

    @Autowired
    UserAbilityPlanRelateService userAbilityPlanRelateService;

    @Autowired
    ConfigService configService;

    @Autowired
    UserAccountDao userAccountDao;

    @Autowired
    HttpServletRequest request;

    @Autowired
    PayLessonOrderService payLessonOrderService;

    @Autowired
    BuyLessonItemRecordService buyLessonItemRecordService;

    @RequestMapping(value = "/get_week_plan_type", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获得当前用户的计划类型")
    public GetWeekPlanTypeResponse getAbilityPlanPrice(

    ) {

        Long userId=(Long)request.getAttribute("loginId");
        GetWeekPlanTypeResponse response = new GetWeekPlanTypeResponse();
        response.setWeekPlanType(0);
        response.setWeekPlanStyle(WeekPlanStyle.DEFAULT);
        if(userId!=0L){
            UserExtend userExtend= userExtendService.findUserExtendByUserId(userId);
            response.setWeekPlanType(userExtend.getWeekPlanType());
            response.setWeekPlanStyle(WeekPlanStyle.getById(userExtend.getWeekPlanType()));
        }

        return response;
    }

    @RequestMapping(value = "/add_one_week", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "试用活动添加一周")
    public BaseResponse getAbilityPlanPrice(
            @ApiParam(value = "计划类型 1 2-4  2 4-6") @RequestParam Integer planStyle,
            @ApiParam(value = "token") @RequestParam(required = false) String token,
            @ApiParam(value = "电话") @RequestParam(required = false) String phone,
            @ApiParam(value = "unionid") @RequestParam(required = false) String unionid
    ) {

        BaseResponse response = new BaseResponse();
        Integer userId = 0;
        if (unionid != null) {
            List<UserAccount> userAccountList = userAccountDao.findUserAccountsBySrcId(unionid);
            if (userAccountList != null && userAccountList.size() > 0) {
                userId = userAccountList.get(0).getUserId().intValue();
            }
        } else if (token != null) {
            UserToken userToken = userService.getUserIdByToken(token);
            userId = userToken.getUserId().intValue();
        } else if (phone != null) {
            User user = userService.getUserByPhone(phone);
            userId = user.getId().intValue();
        }

        weekPlanJoinService.addOneWeekPlanByUserId(userId, WeekPlanStyle.getById(planStyle));
        return response;
    }


    @ApiOperation(value = "参加阅读成长周计划")
    @RequestMapping(value = "/add_week_plan", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse addWeekPlan(
            @RequestHeader(value = "ssToken") String ssToken,
            @ApiParam(value = "planStyle") @RequestParam() WeekPlanStyle planStyle

    ) {
        Long userId = userService.checkAndGetCurrentUserId(ssToken);
        weekPlanJoinService.createWeekPlanJoin(userId.intValue(), planStyle);
        userExtendService.changeUserWeekPlanType(userId.intValue(), planStyle);
        return new BaseResponse();
    }

    @ApiOperation(value = "周计划入口页 v2.11.0")
    @RequestMapping(value = "/week_plan_intro_page_2110", method = RequestMethod.GET)
    @ResponseBody
    GetWeekPlanIntroPageResponse getWeekPlanIntroPage2110(
            @RequestHeader(value = "ssToken") String ssToken,
            @ApiParam(value = "iosPay") @RequestParam(required = false) Integer iosPay
    ) {
        Long userId = userService.checkAndGetCurrentUserId(ssToken);
        UserExtend userExtend = userExtendService.findUserExtendByUserId(userId);
        WeekPlanStyle weekPlanStyle = WeekPlanStyle.getById(userExtend.getWeekPlanType());
        GetWeekPlanIntroPageResponse res = this.getWeekPlanIntroPage(ssToken, weekPlanStyle, 0, 60);

        //冗余测试服会出现大于52周的情况
        if(res.getPage().getRsCount()>52){
            res.getPage().setRsCount(52);
        }
//        res24.setContent("2～4岁 启蒙版");
        //已完成数量和总数
        Integer count = weekPlanUserRecordService.countFinishWeekNum(weekPlanStyle, userId.intValue());
        res.setFinishWeekNum(count);
        res.setAllWeekNum(52);
        res.setWeekPlanStyle(weekPlanStyle);
        //是否领取全部免费课程 0 显示
        res.setIsGetFreeAll(1);
        try {
            //是否达到一周
            Integer isAchieve = promptService.isAchieveByPlanStyle(userId.intValue(), weekPlanStyle);
            res.setIsAchieve(isAchieve);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (iosPay != null && iosPay == 1) {
            //ios审核版本使用
            res.setIsNeedShow(0);
            for (WeekPlanIntro weekPlanIntro : res.getWeekPlanIntroList()) {
                weekPlanIntro.setIsBuy(1);
                weekPlanIntro.setIsHasPlan(1);
            }
        }
        return res;

    }

    @ApiOperation(value = "周计划入口页 v2.10.0")
    @RequestMapping(value = "/week_plan_intro_page_210", method = RequestMethod.GET)
    @ResponseBody
    GetAllWeekPlanIntroResponse getWeekPlanIntroPage210(
            @RequestHeader(value = "ssToken") String ssToken,
            @ApiParam(value = "iosPay") @RequestParam(required = false) Integer iosPay
    ) {

        GetAllWeekPlanIntroResponse response = new GetAllWeekPlanIntroResponse();
        Long userId = userService.checkAndGetCurrentUserId(ssToken);

        GetWeekPlanIntroPageResponse res24 = this.getWeekPlanIntroPage(ssToken, WeekPlanStyle.TWO_FOUR, 0, 60);
        res24.setContent("2～4岁 启蒙版");
        //已完成数量和总数
        Integer count24 = weekPlanUserRecordService.countFinishWeekNum(WeekPlanStyle.TWO_FOUR, userId.intValue());
        res24.setFinishWeekNum(count24);
        res24.setAllWeekNum(52);
        res24.setWeekPlanStyle(WeekPlanStyle.TWO_FOUR);
        //是否领取全部免费课程 0 显示
        res24.setIsGetFreeAll(1);
        try {
            //是否达到一周
            Integer isAchieve = promptService.isAchieveByPlanStyle(userId.intValue(), WeekPlanStyle.TWO_FOUR);
            res24.setIsAchieve(isAchieve);
        } catch (Exception e) {
            e.printStackTrace();
        }


        GetWeekPlanIntroPageResponse res46 = this.getWeekPlanIntroPage(ssToken, WeekPlanStyle.FOUR_SIX, 0, 60);
        res46.setContent("4～6岁 成长版");
        //已完成数量和总数
        Integer count46 = weekPlanUserRecordService.countFinishWeekNum(WeekPlanStyle.FOUR_SIX, userId.intValue());
        res46.setFinishWeekNum(count46);
        res46.setAllWeekNum(52);
        res46.setWeekPlanStyle(WeekPlanStyle.FOUR_SIX);
        //是否领取全部免费课程 0 显示
        res46.setIsGetFreeAll(1);
        try {
            //是否达到一周
            Integer isAchieve = promptService.isAchieveByPlanStyle(userId.intValue(), WeekPlanStyle.FOUR_SIX);
            res46.setIsAchieve(isAchieve);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<GetWeekPlanIntroPageResponse> list = new ArrayList<>();
        list.add(res24);
        list.add(res46);
        response.setWeekPlanIntroPageResponseList(list);

        UserExtend userExtendFirst = userExtendService.findUserExtendByUserId(userId);
        WeekPlanStyle weekPlanStyle = WeekPlanStyle.getById(userExtendFirst.getWeekPlanType());
        if (weekPlanStyle == WeekPlanStyle.DEFAULT) {
            //未参加默认参加2-4 岁周计划
            response.setWeekPlanStyle(WeekPlanStyle.TWO_FOUR);
        }

        response.setWeekPlanStyle(WeekPlanStyle.TWO_FOUR);

        if (iosPay != null && iosPay == 1) {
            //ios审核版本使用
            for (GetWeekPlanIntroPageResponse getWeekPlanIntroPageResponse : response.getWeekPlanIntroPageResponseList()) {
                getWeekPlanIntroPageResponse.setIsNeedShow(0);
                for (WeekPlanIntro weekPlanIntro : getWeekPlanIntroPageResponse.getWeekPlanIntroList()) {
                    weekPlanIntro.setIsBuy(1);
                    weekPlanIntro.setIsHasPlan(1);

                }
            }
        }

        return response;
    }

    @ApiOperation(value = "周计划入口页(新) v2.9.0")
    @RequestMapping(value = "/week_plan_intro_page_new", method = RequestMethod.GET)
    @ResponseBody
    GetWeekPlanIntroPageResponse getWeekPlanIntroPageNew(
            @RequestHeader(value = "ssToken") String ssToken,
            @ApiParam(value = "pageNo  1开始") @RequestParam() Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam() Integer pageSize
    ) {

        //最近参加的计划
        Long userId = userService.checkAndGetCurrentUserId(ssToken);
        UserExtend userExtendFirst = userExtendService.findUserExtendByUserId(userId);
        WeekPlanStyle weekPlanStyle = WeekPlanStyle.getById(userExtendFirst.getWeekPlanType());
        if (weekPlanStyle == WeekPlanStyle.DEFAULT) {
            //未参加默认参加2-4 岁周计划
            this.addWeekPlan(ssToken, WeekPlanStyle.TWO_FOUR);
        }
        UserExtend userExtend = userExtendService.findUserExtendByUserId(userId);
        Integer planChangeCount = userExtend.getPlanChangeCount();
        WeekPlanStyle planStyle = WeekPlanStyle.getById(userExtend.getWeekPlanType());
        GetWeekPlanIntroPageResponse response = this.getWeekPlanIntroPage(ssToken, planStyle, pageNo, pageSize);

        Integer record24 = weekPlanUserRecordService.getCountByUserIdAndWeekPlanId(userId.intValue(), 2);
        Integer record46 = weekPlanUserRecordService.getCountByUserIdAndWeekPlanId(userId.intValue(), 54);
        int cont = record24 + record46;
        if (cont > 0) {
            response.setIsHasSecond(1);
        } else {
            response.setIsHasSecond(0);
        }
        response.setPlanChangeCount(planChangeCount);
        response.setWeekPlanStyle(planStyle);

        UserAbilityPlanRelate lastestUserAbilityPlanRelateRecord = userAbilityPlanRelateService.getLastestUserAbilityPlanRelateRecord(userId);
        if (lastestUserAbilityPlanRelateRecord != null) {
            response.setWeekPlanEndTime(lastestUserAbilityPlanRelateRecord.getEndTime());
        }

        if (planStyle == WeekPlanStyle.TWO_FOUR) {
            response.setContent("2～4岁 启蒙版");
        } else if (planStyle == WeekPlanStyle.FOUR_SIX) {
            response.setContent("4～6岁 成长版");
        }


//        if(VersionUtil.getPlatform(request)== StoryConfig.Platfrom.IOS){
//            //审核版本判断
//            String vver=configService.getConfigByKey("version").getVal();
//            if(vver.equals(VersionUtil.getVerionInfo(request))){
//               for(WeekPlanIntro weekPlanIntro:response.getWeekPlanIntroList()){
//                   weekPlanIntro.setIsBuy(1);
//                   weekPlanIntro.setIsHasPlan(1);
//               }
//            }
//        }
        return response;
    }


    @ApiOperation(value = "周计划入口页")
    @RequestMapping(value = "/week_plan_intro_page", method = RequestMethod.GET)
    @ResponseBody
    GetWeekPlanIntroPageResponse getWeekPlanIntroPage(
            @RequestHeader(value = "ssToken") String ssToken,
            @ApiParam(value = "planStyle") @RequestParam() WeekPlanStyle planStyle,
            @ApiParam(value = "pageNo") @RequestParam() Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam() Integer pageSize
    ) {

        if (planStyle.getId() == 0) {
            throw new ApiNotFoundException("年龄段未选择！");
        }
        Long userId = userService.checkAndGetCurrentUserId(ssToken);
        WeekPlanJoin weekPlanJoin = weekPlanJoinService.getWeekPlanJoinByUserIdAndType(userId.intValue(), planStyle);

        if (weekPlanJoin == null) {
            this.addWeekPlan(ssToken, planStyle);
        } else {
            userExtendService.changeUserWeekPlanType(userId.intValue(), planStyle);
        }
        Page<WeekPlanIntro> weekPlanIntroPage = weekPlanIntroService.getWeekPlanIntroPage(pageNo, pageSize, planStyle, userId.intValue());
        GetWeekPlanIntroPageResponse response = new GetWeekPlanIntroPageResponse();
        response.setWeekPlanIntroList(weekPlanIntroPage.getContent());
        response.setJpaPage(weekPlanIntroPage);

        try {
            Integer isAchieve = promptService.isAchieve(userId.intValue());
            response.setIsAchieve(isAchieve);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /***v2.10.0*/
//        WeekPlanJoin weekPlanJoin2 = weekPlanJoinService.getWeekPlanJoinByUserIdAndType(userId.intValue(), planStyle);
//        if (weekPlanJoin2.getBuyNum() < 52) {
//            response.setIsNeedShow(1);
//        } else {
//            response.setIsNeedShow(0);
//        }

        /**v2.11.0 */
        User user = userService.getUser(userId);
        if (user.isAbilityPlan()) {
            response.setIsNeedShow(0);
        } else {
            response.setIsNeedShow(1);
        }

        return response;
    }

    @ApiOperation(value = "查看最近参加的周计划")
    @RequestMapping(value = "/recent_join_week_plan", method = RequestMethod.GET)
    @ResponseBody
    GetUserJoinWeekPlanConditionResponse getUserJoinWeekPlanCondition(
            @RequestHeader(value = "ssToken") String ssToken
    ) {
        GetUserJoinWeekPlanConditionResponse response = new GetUserJoinWeekPlanConditionResponse();
        Long userId = userService.checkAndGetCurrentUserId(ssToken);
        UserExtend userExtend = userExtendService.findUserExtendByUserId(userId);
        WeekPlanStyle weekPlanStyle = WeekPlanStyle.getById(userExtend.getWeekPlanType());
        response.setWeekPlanStyle(weekPlanStyle);
        response.setPlanChangeCount(userExtend.getPlanChangeCount());
        return response;
    }

    @ApiOperation(value = "某一周详情")
    @RequestMapping(value = "/week_plan_task_list", method = RequestMethod.GET)
    @ResponseBody
    GetWeekPlanTaskListResponse getWeekPlanTaskList(
            @RequestHeader(value = "ssToken") String ssToken,
            @ApiParam(value = "weekPlanId") @RequestParam() Integer weekPlanId,
            @ApiParam(value = "planStyle") @RequestParam() WeekPlanStyle planStyle
    ) {
        Long userId = userService.checkAndGetCurrentUserId(ssToken);
        List<WeekPlanTaskRelate> weekPlanTaskRelateList = weekPlanTaskRelateService.getWeekPlanTaskRelateListByWeekPlanId(weekPlanId, userId.intValue());
        GetWeekPlanTaskListResponse response = new GetWeekPlanTaskListResponse();
        response.setWeekPlanTaskRelateList(weekPlanTaskRelateList);
        WeekPlanUserRecord weekPlanUserRecord = weekPlanUserRecordService.isHasWeekPlanUserRecord(weekPlanId, userId.intValue());
        if (weekPlanUserRecord == null) {
            throw new ApiNotFoundException("没有该计划！");
        }
        if (weekPlanUserRecord.getShowStar().equals(0) && weekPlanUserRecord.getIsFinish().equals(1)) {
            response.setIsNeedShowStar(1);
        } else {
            response.setIsNeedShowStar(0);
        }
        response.setFinishNum(weekPlanUserRecord.getFinishNum());
        if (!weekPlanUserRecord.getMagTaskId().equals(0)) {
            response.setWeekPlanMagazineList(weekPlanMagazineService.getWeekPlanMagazineByDate(weekPlanUserRecord.getMagTaskId(), userId.intValue(), planStyle));
        }
        return response;
    }

    @ApiOperation(value = "展示完成星星")
    @RequestMapping(value = "/finish_show_star", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse setFinishShowStar(
            @RequestHeader(value = "ssToken") String ssToken,
            @ApiParam(value = "weekPlanId") @RequestParam() Integer weekPlanId
    ) {
        Long userId = userService.checkAndGetCurrentUserId(ssToken);
        weekPlanUserRecordService.setFinishShowStar(weekPlanId, userId.intValue());
        walletService.addStarToWallet(userId.intValue(), StarRechargeStyle.WEEK_PLAN_FINISH, StarConfig.WEEK_PLAN_FINISH, StarRechargeStyle.WEEK_PLAN_FINISH.getName());
        return new BaseResponse();
    }

    @ApiOperation(value = "设置完成杂志任务")
    @RequestMapping(value = "/set_finish_magazine_task", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse setFinishMagazineTask(
            @RequestHeader(value = "ssToken") String ssToken,
            @ApiParam(value = "magPlanId") @RequestParam() Integer magPlanId,
            @ApiParam(value = "planStyle") @RequestParam() WeekPlanStyle planStyle
    ) {
        Long userId = userService.checkAndGetCurrentUserId(ssToken);
        weekPlanFinishMagazineService.createWeekPlanFinishMagazine(magPlanId, userId.intValue(), planStyle);
        return new BaseResponse();
    }

    @ApiOperation(value = "周报告")
    @RequestMapping(value = "/week_study_report", method = RequestMethod.GET)
    @ResponseBody
    WeekStudyReportResponse weekStudyReport(
            @RequestHeader(value = "ssToken") String ssToken,
            @ApiParam(value = "weekPlanId") @RequestParam() Integer weekPlanId,
            @ApiParam(value = "planStyle") @RequestParam() WeekPlanStyle planStyle
    ) {
        WeekStudyReportResponse response = new WeekStudyReportResponse();
        Long userId = userService.checkAndGetCurrentUserId(ssToken);
        WeekPlanLabelStyle weekPlanLabelStyle = WeekPlanLabelStyle.COGNITION_TYPE;
        if (planStyle.equals(WeekPlanStyle.FOUR_SIX)) {
            weekPlanLabelStyle = WeekPlanLabelStyle.LITERACY_TYPE;
        }
        WeekPlanUserRecord weekPlanUserRecord = weekPlanUserRecordService.isHasWeekPlanUserRecord(weekPlanId, userId.intValue());
        if (weekPlanUserRecord == null || weekPlanUserRecord.getFinishNum().equals(0)) {
            response.setIsHasContent(0);
            return response;
        } else {
            response.setIsHasContent(1);
        }
        weekPlanTaskRelateService.setDataToWeekStudyReportResponse(response, weekPlanId, userId.intValue());
        response.setScore(weekPlanTaskFinishService.getScoreCountByWeekPlanIdAndUserId(weekPlanId, userId.intValue()));
        response.setLiteracyLabelList(weekPlanLabelService.findListByWeekPlanIdAndUserId(weekPlanId, userId.intValue(), weekPlanLabelStyle));

        List<WeekPlanLabel> readTypeList = weekPlanLabelService.findListByWeekPlanIdAndUserId(weekPlanId, userId.intValue(), WeekPlanLabelStyle.READ_TYPE);
        response.setReadLabelList(readTypeList);
        response.setReadLabelListNew(readTypeList);
        return response;
    }

    @ApiOperation(value = "成长记录报告")
    @RequestMapping(value = "/week_plan_record_report", method = RequestMethod.GET)
    @ResponseBody
    StaticLabelListResponse getStatisticWeekPlanLabel(
            @RequestHeader(value = "ssToken") String ssToken,
            @ApiParam(value = "planStyle") @RequestParam() WeekPlanStyle planStyle
    ) throws ParseException {
        Long userId = userService.checkAndGetCurrentUserId(ssToken);
        StaticLabelListResponse response = new StaticLabelListResponse();
        WeekPlanReadRecord weekPlanReadRecord = weekPlanReadRecordService.getFirstWeekPlanReadRecord(userId.intValue(), planStyle);
        if (weekPlanReadRecord != null) {
            int dayNumber = statisticLabelService.differentDaysByMillisecond(weekPlanReadRecord.getCreateTime(), new Date());
            response.setDayNumber(dayNumber);
        } else {
            response.setDayNumber(0);
        }
        GetStatisticWeekPlanLabelResponse dataResponse = weekPlanReadRecordService.getStatisticWeekPlanLabel(userId.intValue(), planStyle);

        //是否分享过周计划阅读
//        Long signRecord = signRecordService.countSignRecordByUserIdAndType(userId.intValue(), SignRecordStyle.SHARE_TYPE);
//        //未分享过
//        if (signRecord == 0) {
//            response.setGetShare(new GetShare(PromptStyle.SHARE.getId(), PromptStyle.SHARE.getName(),
//                    PromptStyle.SHARE.getUrl(), PromptStyle.SHARE.getContent(), PromptStyle.SHARE.getImgPath(), 750, 140));
//        }

        Integer storyCount = weekPlanReadRecordService.getCountByPlanTypeAndTargetTypeAndUserIdAndReadType(planStyle, LabelTargetStyle.Story, userId.intValue(), ReadRecordTypeStyle.STORY, 1);
        Integer storyWordCount = weekPlanReadRecordService.getSumWordCountByPlanTypeAndTargetTypeAndUserIdAndReadType(planStyle, LabelTargetStyle.Story, userId.intValue(), ReadRecordTypeStyle.STORY, 1);
        Integer magCount = weekPlanReadRecordService.getCountByPlanTypeAndTargetTypeAndUserIdAndReadType(planStyle, LabelTargetStyle.Magazine, userId.intValue(), ReadRecordTypeStyle.MAGAZINE, 0);
        Integer magWordCount = weekPlanReadRecordService.getSumWordCountByPlanTypeAndTargetTypeAndUserIdAndReadType(planStyle, LabelTargetStyle.Magazine, userId.intValue(), ReadRecordTypeStyle.MAGAZINE, 0);
        //一本杂志三个故事
        response.setStoryCount(storyCount);
        response.setWordCount(storyWordCount + magWordCount);

        //获得时长类
        TotalReadStatistic totalReadStatistic = userReadDurationRecordService.getTotalReadStatistic(userId);
        //在线阅读时长
        response.setReadStoryDuration(totalReadStatistic.getTotalReadStoryDuration());
        //收听音频时长
        response.setListenStoryDuration(totalReadStatistic.getTotalListenStoryDuration());
        //益智训练时长
        response.setGameStoryDuration(totalReadStatistic.getTotalGameStoryDuration());

        response.setReadWordCount(statisticLabelService.getStaticLabelOne(userId.intValue(), planStyle, storyCount, storyWordCount, magCount, magWordCount));
        response.setReadDuration(statisticLabelService.getStaticLabelTwo(userId.intValue(), totalReadStatistic));
        response.setReadType(statisticLabelService.getStaticLabelThree(dataResponse));
        response.setCognitionType(statisticLabelService.getStaticLabelFour(dataResponse));
        response.setLiteracyType(statisticLabelService.getStaticLabelFive(dataResponse));
        response.setFiveAreaType(statisticLabelService.getStaticLabelSix(dataResponse));
        return response;
    }



    @ApiOperation(value = "(修复）每周一定时任务推送用户周计划任务")
    @RequestMapping(value = "/recoverDateSendData", method = RequestMethod.GET)
    @Deprecated
    void recoverDateSendData() throws ApiNotTokenException {
        try {
//            weekPlanUserRecordService.recoverDayTaskSend();
              weekPlanUserRecordService.dayTaskSendWeekPlanUserRecord();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "手动给指定用户推送一周学习计划")
    @RequestMapping(value = "/sendWeekPlanToSomeone", method = RequestMethod.GET)
    @ResponseBody
    BaseResponse sendWeekPlanToSomeone(
            @ApiParam(value = "userId") @RequestParam() Integer userId,
            @ApiParam(value = "planStyle") @RequestParam() WeekPlanStyle planStyle
    ) throws ApiNotTokenException {
        //每次推送一周
        try {
            weekPlanUserRecordService.sendWeekPlanUserRecordByUserIdAndPlanTypeToSomeone(userId, planStyle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        BaseResponse response = new BaseResponse();
        return response;
    }


    @ApiOperation(value = "视频任务详情")
    @RequestMapping(value = "/study_video_detail", method = RequestMethod.GET)
    @ResponseBody
    StudyVideoDetailResponse studyVideoDetail(
            @ApiParam(value = "videoId") @RequestParam() Integer videoId
    ) {
        StudyVideoDetailResponse response = new StudyVideoDetailResponse();
        LessonStudyVideo lessonStudyVideo = lessonStudyVideoService.getLessonStudyVideoById(videoId);
        response.setLessonStudyVideo(lessonStudyVideo);
        return response;
    }


    @ApiOperation(value = "添加记录")
    @RequestMapping(value = "/addSignRecord", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse addSignRecord(
//            @RequestHeader(value = "ssToken") String ssToken,
            @ApiParam(value = "类型 SHARE-添加分享  READ - 添加阅读报告") @RequestParam() SignRecordStyle signRecordStyle
    ) {

        Long userId = ApiConfigAop.getCurrentUserId(request);
        if (userId == 0L) {
            return new BaseResponse();
        }

        BaseResponse response = new BaseResponse();
        signRecordService.createSignRecord(userId.intValue(), signRecordStyle);
        return response;
    }


    @ApiOperation(value = "导入数据from-user_read_record")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse update(
            @ApiParam(value = "循环次数") @RequestParam() Integer count,
            @ApiParam(value = "起始id") @RequestParam() Integer id1,
            @ApiParam(value = "sign") @RequestParam() String sign
    ) {

        BaseResponse response = new BaseResponse();

        if (sign.equals("vista688")) {
            weekPlanReadRecordService.updateDate(count, id1);
        }
        return response;
    }

    @ApiOperation(value = "修改week_plan_read_record数据先执行")
    @RequestMapping(value = "/updateWeekPlan", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse updateWeekPlan(
            @ApiParam(value = "循环次数") @RequestParam() Integer count
    ) {

        BaseResponse response = new BaseResponse();

        weekPlanReadRecordService.updateWeekPlanDate(count);
        return response;
    }

    @ApiOperation(value = "家长导读页面")
    @RequestMapping(value = "/getParentsGuide", method = RequestMethod.GET)
    public ModelAndView getParentsGuide(
            @ApiParam(value = "story_week_plan_task_relate表的id") @RequestParam(value = "id") Integer id
    ) {
        ModelAndView modelAndView = new ModelAndView();
        WeekPlanTaskRelate weekPlanTaskRelate = weekPlanTaskRelateService.getWeekPlanTaskRelateById(id);

        modelAndView.setViewName("parentsGuide/parents_guide");

        String content = weekPlanTaskRelate.getContent();
        Integer type = weekPlanTaskRelate.getType();

        modelAndView.addObject("content", content);
        modelAndView.addObject("type", type);

        return modelAndView;
    }

//    @ApiOperation(value = "获取家长导读信息")
//    @RequestMapping(value = "/getParentsGuide", method = RequestMethod.GET)
//    @ResponseBody
//    GetParentsGuideResponse getParentsGuide(
//            @ApiParam(value = "周计划id") @RequestParam() Integer weekPlanId,
//            @ApiParam(value = "星期(1,2,3,4,5,6,7,0表示复习任务)") @RequestParam() Integer dayNum
//    ){
//        GetParentsGuideResponse response = new GetParentsGuideResponse();
//
//        ParentsGuide parentsGuide = weekPlanTaskRelateService.getParentsGuideByWeekPlanIdAndDayNum(weekPlanId,dayNum);
//
//        response.setParentsGuide(parentsGuide);
//        return response;
//    }

    @ApiOperation(value = "补充历史记录，如果已经是宝宝会读用户，用于保存阅读进度")
    @ResponseBody
    @RequestMapping(value = "/addByBaobaoUser", method = RequestMethod.GET)
    public BaseResponse addByBaobaoUser(
            @ApiParam(value = "userId") @RequestParam(value = "userId") Integer userId
    ) {
        payLessonOrderService.addBuyLessonItemRecordAndOrderLesson(userId,1,0,0,50,1);
        payLessonOrderService.addBuyLessonItemRecordAndOrderLesson(userId,2,0,0,50,1);
        return new BaseResponse();
    }

    @ApiOperation(value = "补充历史记录，如果已经是宝宝会读用户，用于保存阅读进度")
    @ResponseBody
    @RequestMapping(value = "/addByBaobaoAllUser", method = RequestMethod.GET)
    public BaseResponse addByBaobaoAllUser(
    ) {
        User userFind=new User();
        userFind.setIsAbilityPlan(1);

        PageRequest page=new PageRequest(0,20,new Sort(Sort.Direction.ASC,"id"));
        Page<User> userPage= userService.findAll(userFind,page);
        forAddBaobaoLesson(userPage.getContent());

        for(int i=1;i<userPage.getTotalPages();i++){
            page=new PageRequest(i,20,new Sort(Sort.Direction.ASC,"id"));
            userPage= userService.findAll(userFind,page);
            forAddBaobaoLesson(userPage.getContent());

        }

        return new BaseResponse();
    }
    BuyLessonItemRecord buyLessonItemRecord;
    private void forAddBaobaoLesson(List<User> users){
        for(User user:users){
           BuyLessonItemRecord buyLessonItemRecord= buyLessonItemRecordService.getBuyLessonItemRecordByUserIdAndLessonItemId(user.getId().intValue(),3);
           if(buyLessonItemRecord!=null){
               continue;
           }

            this.addByBaobaoUser(user.getId().intValue());
        }
    }
}
