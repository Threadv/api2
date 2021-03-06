package com.ifenghui.storybookapi.app.story.controller;

import com.ifenghui.storybookapi.app.analysis.service.GroupRelevanceService;
import com.ifenghui.storybookapi.app.app.entity.Ads;
import com.ifenghui.storybookapi.app.app.entity.Config;
import com.ifenghui.storybookapi.app.app.entity.CustomGroup;
import com.ifenghui.storybookapi.app.app.entity.DisplayGroup;
import com.ifenghui.storybookapi.app.app.service.AdService;
import com.ifenghui.storybookapi.app.app.service.ConfigService;
import com.ifenghui.storybookapi.app.app.service.DisplayGroupService;
import com.ifenghui.storybookapi.app.social.entity.ViewRecord;
import com.ifenghui.storybookapi.app.social.service.UserReadRecordService;
import com.ifenghui.storybookapi.app.social.service.ViewRecordService;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.story.response.GetIndex280Response;
import com.ifenghui.storybookapi.app.story.response.lesson.LessonIndex;
import com.ifenghui.storybookapi.app.story.service.*;
import com.ifenghui.storybookapi.app.story.thread.GroupAdThread;
import com.ifenghui.storybookapi.app.story.thread.GroupPromptThread;
import com.ifenghui.storybookapi.app.story.thread.GroupSerialThread;
import com.ifenghui.storybookapi.app.story.thread.GroupStoryThread;
import com.ifenghui.storybookapi.app.system.service.GeoipService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.style.SerialStoryStyle;
import com.ifenghui.storybookapi.style.StoryGroupStyle;
import com.ifenghui.storybookapi.style.SvipStyle;
import com.ifenghui.storybookapi.util.HttpRequest;
import com.ifenghui.storybookapi.util.VersionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Date: 2018/12/26 17:10
 * @Description:
 */

@Controller
@EnableAutoConfiguration
@Api(value = "IndexController", description = "??????api")
@RequestMapping("/api/index")
public class IndexController {

    Logger logger=Logger.getLogger(IndexController.class);

    @Autowired
    HttpServletRequest request;
    @Autowired
    ConfigService configService;
    @Autowired
    GeoipService geoipService;
    @Autowired
    UserService userService;
    @Autowired
    PromptService promptService;
    @Autowired
    UserReadRecordService userReadRecordService;
    @Autowired
    ViewRecordService viewRecordService;
    @Autowired
    SerialStoryService serialStoryService;
    @Autowired
    LessonService lessonService;
    @Autowired
    StoryService storyService;
    @Autowired
    AdService adsService;
    @Autowired
    DisplayGroupService displayGroupService;
    @Autowired
    GroupRelevanceService groupRelevanceService;

    @Autowired
    IpBrandService ipBrandService;


    class ThreadResult{
        public List<GroupStoryThread> storyThreads=new ArrayList<>();
        public List<GroupSerialThread> serialThreads=new ArrayList<>();
        public GroupAdThread groupAdThread=null;
        public GroupPromptThread groupPromptThread=null;
    }

    @RequestMapping(value = "getIndex2_12_0", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "?????? 2.12.0 ????????????")
    GetIndex280Response getIndex2120(
    ){

        //????????????????????????
        final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(15);

        /**
         * ????????????
         */
        ThreadResult threadResult = new ThreadResult();

        Long loginId=(Long)request.getAttribute("loginId");
        User user=userService.getUser(loginId.intValue());
        logger.info("-------------begin");
        GetIndex280Response response = this.getIndex2100Response(2120,user, 3,threadResult);
        logger.info("-------------3");

        String userAgent = request.getHeader("User-Agent");
        if (userAgent!=null&&userAgent.contains("ios")) {
            String ver210 = "2.10.0";
            /**2.10.0 ?????????????????????*/
            if (!VersionUtil.isAllow(request,ver210)) {
                threadResult.groupPromptThread=new GroupPromptThread(promptService,user);
            }
        }else {
            threadResult.groupPromptThread=new GroupPromptThread(promptService,user);
        }

        response=this.responseSetThreadValue2100(response,executor,threadResult);
        logger.info("-------------end resp");
        return response;
    }

    @RequestMapping(value = "getIndex2_11_0", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "?????? 2.11.0 ????????????")
    public GetIndex280Response getIndex2110(
            @RequestHeader(value = "ssToken", required = false) String ssToken,
            @ApiParam(value = "?????????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String ver
    ){

        //????????????????????????
        final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(15);

        /**
         * ????????????
         */
        ThreadResult threadResult = new ThreadResult();

        Long loginId=(Long)request.getAttribute("loginId");
        User user=userService.getUser(loginId.intValue());
        logger.info("-------------begin");
        GetIndex280Response response = this.getIndex2100Response(2110,user, 3,threadResult);
        logger.info("-------------3");

        String userAgent = request.getHeader("User-Agent");
        if (userAgent.contains("ios")) {
            String ver210 = "2.10.0";
            /**2.10.0 ?????????????????????*/
            if (VersionUtil.isAllow(request,ver210)) {
                threadResult.groupPromptThread = null;
            }else {
                threadResult.groupPromptThread=new GroupPromptThread(promptService,user);
            }
        }else {
            threadResult.groupPromptThread=new GroupPromptThread(promptService,user);
        }
        response=this.responseSetThreadValue2100(response,executor,threadResult);
        logger.info("-------------end resp");
        return response;
    }


    @RequestMapping(value = "getIndex2_10_0", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "?????? 2.10.0 ????????????")
    GetIndex280Response getIndex2100(
            @RequestHeader(value = "ssToken", required = false) String ssToken,
            @ApiParam(value = "?????????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String ver
    ){

        //????????????????????????
        final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(15);

        /**
         * ????????????
         */
        ThreadResult threadResult = new ThreadResult();

        Long loginId=(Long)request.getAttribute("loginId");
        User user=userService.getUser(loginId.intValue());
        logger.info("-------------begin");
        GetIndex280Response response = this.getIndex2100Response(2100,user, 3,threadResult);
        logger.info("-------------3");

        String userAgent = request.getHeader("User-Agent");
        if (userAgent.contains("ios")) {
            //???????????????????????????????????????config???ver????????????????????????
            String ver210 = "2.10.0";
            /**2.10.0 ???????????????*/
            if (VersionUtil.isAllow(request,ver210)) {
                threadResult.groupPromptThread = null;
            }else {
                threadResult.groupPromptThread=new GroupPromptThread(promptService,user);
            }
        }else {
            threadResult.groupPromptThread=new GroupPromptThread(promptService,user);
        }
        response=this.responseSetThreadValue2100(response,executor,threadResult);
        logger.info("-------------end resp");
        return response;
    }


    /**
     * ??????????????????????????????????????????????????? 2101
     * ?????????responseSetThreadValue ?????????
     * @param user ??????
     * @param serialPageSize ????????????
     * @param threadResult ??????????????????
     * @return
     */
    private GetIndex280Response getIndex2100Response(Integer index,User user, Integer serialPageSize,IndexController.ThreadResult threadResult) {

        GetIndex280Response response = new GetIndex280Response();
        //??????svip
        if(user != null &&(user.getSvip().equals(SvipStyle.LEVEL_FOUR.getId()) || user.getSvip().equals(SvipStyle.LEVEL_THREE.getId()))){
            response.setIsSvip(1);
        } else {
            response.setIsSvip(0);
        }
        logger.info("-------------001");
        //????????????
        Integer isCheckVer = 0;
        String userAgent = request.getHeader("User-Agent");
        if (userAgent!=null&&userAgent.contains("ios")) {
            //???????????????????????????????????????config???ver????????????????????????
            String key = "version";
            if(userAgent.contains("appname:zhijianStory")){
                key = "zhijianStory"+"_version";
            }
            Config config=this.configService.getConfigByKey(key);
            String ver = VersionUtil.getVerionInfo(request);
            //????????????????????????
            Integer iosIsCheck = VersionUtil.getIosIsCheck(request, configService,geoipService);
            if(iosIsCheck ==1){
                isCheckVer = 1;
            }
        }
        logger.info("-------------0011");
        //????????????
        String channel = request.getHeader("channel");
        String platform= VersionUtil.getPlatformStr(request);
        String ver=VersionUtil.getVerionInfo(request);
        threadResult.groupAdThread=new GroupAdThread(adsService,channel,platform,isCheckVer,ver,user);

        logger.info("-------------0016");
        //????????????
        Ads ads = new Ads();
        ads.setTitle("????????????");
        ads.setContent("????????????");
        ads.setUrl("http://storybook.oss-cn-hangzhou.aliyuncs.com/web_project/younengjihua/index.html");
        ads.setImgPath("recommend.png");
        ads.setIcon("recommend.png");
        ads.setPadImg("http://storybook.oss-cn-hangzhou.aliyuncs.com/banner/padRecommend.png");
        ads.setId(0L);
        ads.setTargetValue(0);
        ads.setTargetType(0);
        ads.setStatus(0);
        ads.setOrderBy(0);
        ads.setCreateTime(new Date());
        ads.setIsIosVisual(0);
        ads.setAdsPosition(0);

        CustomGroup recommendGroup = new CustomGroup();
        if(user != null && user.getIsAbilityPlan()==1) {
            recommendGroup.setIsAbilityPlan(1);
        } else {
            recommendGroup.setIsAbilityPlan(0);
        }
        recommendGroup.setId(1006L);
        recommendGroup.setName("????????????");
        recommendGroup.setContent("????????????");
        recommendGroup.setIcon("");
        recommendGroup.setPadIcon("");
        recommendGroup.setTargetType(StoryGroupStyle.RECOMMEND.getTargetType());
        recommendGroup.setTargetValue(0);
        recommendGroup.setIsSubscribe(0);
        recommendGroup.setAds(ads);
        response.setRecommendGroup(recommendGroup);

        logger.info("-------------00113");
        CustomGroup viewRecordGroup = new CustomGroup();
        viewRecordGroup.setId(1005L);
        viewRecordGroup.setContent("????????????");
        viewRecordGroup.setName("????????????");
        viewRecordGroup.setIcon("");
        viewRecordGroup.setPadIcon("");
        viewRecordGroup.setIsSubscribe(0);
        viewRecordGroup.setTargetType(StoryGroupStyle.RECORD.getTargetType());
        viewRecordGroup.setTargetValue(0);
        if(user!=null && user.getId()!=0){
            Page<ViewRecord> viewRecordPage = viewRecordService.getViewrecordByUserIdAndType(user.getId(), 0, 4);
            viewRecordGroup.setViewRecordList(viewRecordPage.getContent());
            List<Story> storys = new ArrayList<>();
            for (ViewRecord s:viewRecordPage.getContent()) {
                storys.add(s.getStory());
            }
            viewRecordGroup.setStorys(storys);
            response.setUserReadRecordGroup(viewRecordGroup);
        }else{
            viewRecordGroup.setStorys(new ArrayList<>());
            response.setUserReadRecordGroup(viewRecordGroup);
        }

        //???????????? 2.9.0 ??????????????????
        if(index==280){
            Long userId = 0L;
            if(user!=null){
                userId=user.getId();
            }
            List<Story> radioList = storyService.getRadioList(userId);
            CustomGroup radioGroup = new CustomGroup();
            radioGroup.setId(1004L);
            radioGroup.setName("????????????");
            radioGroup.setContent("????????????");
            radioGroup.setIcon("");
            radioGroup.setPadIcon("");
            radioGroup.setTargetType(StoryGroupStyle.AUDIO.getTargetType());
            radioGroup.setTargetValue(0);
            radioGroup.setIsSubscribe(0);
            radioGroup.setStorys(radioList);
            response.setRadioGroup(radioGroup);
        }

        logger.info("-------------0010");
        LessonIndex lessonIndex = new LessonIndex();
        lessonIndex.setName("???????????????");
        lessonIndex.setContent("???????????????");
        lessonIndex.setIntro("");
        lessonIndex.setTargetType(StoryGroupStyle.LESSON.getTargetType());
        lessonIndex.setLessonList(lessonService.getLessonList());
        response.setLessonIndex(lessonIndex);

        List<CustomGroup> cgroups = new ArrayList<>();
        //???????????????????????????
        if(index>=2100){
            logger.info("-------??????????????? 1 ???------0013");
            CustomGroup parentLissonGroup = new CustomGroup();
            parentLissonGroup.setId(1007L);
            parentLissonGroup.setName("????????????");
            parentLissonGroup.setContent("????????????");
            parentLissonGroup.setIcon("");
            parentLissonGroup.setPadIcon("");
            parentLissonGroup.setIsSubscribe(0);
            parentLissonGroup.setTargetType(StoryGroupStyle.SERIAL.getTargetType());
            parentLissonGroup.setTargetValue(0);
            cgroups.add(parentLissonGroup);
            GroupSerialThread groupSerialThread4=new GroupSerialThread(serialStoryService, SerialStoryStyle.PARENT_LESSON_SERIAL,user,0,1);
            threadResult.serialThreads.add(groupSerialThread4);
            response.setParentLessonGroup(parentLissonGroup);
        }

        logger.info("-------?????????????????? 4 ???------003");
        CustomGroup gameSerialGroup = new CustomGroup();
        gameSerialGroup.setId(1003L);
        gameSerialGroup.setName("??????????????????");
        gameSerialGroup.setContent("??????????????????");
        gameSerialGroup.setIcon("");
        gameSerialGroup.setPadIcon("");
        gameSerialGroup.setIsSubscribe(0);
        gameSerialGroup.setTargetType(StoryGroupStyle.SERIAL.getTargetType());
        gameSerialGroup.setTargetValue(0);
        cgroups.add(gameSerialGroup);
        GroupSerialThread groupSerialThread3=new GroupSerialThread(serialStoryService,SerialStoryStyle.SMART_GAME_SERIAL,user,0,4);
        threadResult.serialThreads.add(groupSerialThread3);

        if(index<=2110) {
            logger.info("-------ip??????------002");
            CustomGroup ipSerialGroup = new CustomGroup();
            ipSerialGroup.setId(1002L);
            ipSerialGroup.setName("????????????");
            ipSerialGroup.setContent("");
            ipSerialGroup.setIcon("");
            ipSerialGroup.setPadIcon("");
            ipSerialGroup.setIsSubscribe(0);
            ipSerialGroup.setTargetType(StoryGroupStyle.SERIAL.getTargetType());
            ipSerialGroup.setTargetValue(0);
            cgroups.add(ipSerialGroup);
            GroupSerialThread groupSerialThread2 = new GroupSerialThread(serialStoryService, SerialStoryStyle.IP_STORY_SERIAL, user, 0, serialPageSize);
            threadResult.serialThreads.add(groupSerialThread2);
            response.setIpStoryGroup(ipSerialGroup);
        }
        if(index>=2120) {
            logger.info("-------ipBrand??????------004");
            CustomGroup ipSerialGroup = new CustomGroup();
            ipSerialGroup.setId(1009L);
            ipSerialGroup.setName("????????????");
            ipSerialGroup.setContent("");
            ipSerialGroup.setIcon("");
            ipSerialGroup.setPadIcon("");
            ipSerialGroup.setIsSubscribe(0);
            ipSerialGroup.setTargetType(StoryGroupStyle.SERIAL.getTargetType());
            ipSerialGroup.setTargetValue(0);
            ipSerialGroup.setIpBrands(ipBrandService.getAllIpBrand());
            cgroups.add(ipSerialGroup);
            response.setIpStoryGroup(ipSerialGroup);
//            GroupSerialThread groupSerialThread2 = new GroupSerialThread(serialStoryService, SerialStoryStyle.IP_STORY_SERIAL, user, 0, serialPageSize);
//            threadResult.serialThreads.add(groupSerialThread2);


        }

        if(index<=2110) {
            //????????????2120???????????????????????????ip?????????????????????????????????
            logger.info("-------????????????------000");
            CustomGroup huiBenGroup = new CustomGroup();
            huiBenGroup.setId(1000L);
            huiBenGroup.setName("????????????");
            huiBenGroup.setContent("????????????");
            huiBenGroup.setIcon("");
            huiBenGroup.setPadIcon("");
            huiBenGroup.setIsSubscribe(0);
            huiBenGroup.setTargetType(StoryGroupStyle.SERIAL.getTargetType());
            huiBenGroup.setTargetValue(0);
            cgroups.add(huiBenGroup);
            GroupSerialThread groupSerialThread = new GroupSerialThread(serialStoryService, SerialStoryStyle.INDEX_SERIAL, user, 0, serialPageSize);
            threadResult.serialThreads.add(groupSerialThread);
            response.setSerialGroup(huiBenGroup);
            response.setSerialStoryCount(3);
        }

        if(index>=2120) {
            //2120????????????????????????
            logger.info("-------????????????------000");
            CustomGroup huiBenGroup = new CustomGroup();
            huiBenGroup.setId(1008L);
            huiBenGroup.setName("??????????????????");
            huiBenGroup.setContent("??????????????????");
            huiBenGroup.setIcon("");
            huiBenGroup.setPadIcon("");
            huiBenGroup.setIsSubscribe(0);
            huiBenGroup.setTargetType(StoryGroupStyle.SERIAL.getTargetType());
            huiBenGroup.setTargetValue(0);
            cgroups.add(huiBenGroup);
            List<Integer> ids=new ArrayList<Integer>();
            ids.add(2);
            GroupSerialThread groupSerialThread = new GroupSerialThread(serialStoryService, SerialStoryStyle.INDEX_SERIAL, user, ids,0, serialPageSize);
            threadResult.serialThreads.add(groupSerialThread);
            response.setAudioSerialGroup(huiBenGroup);
            response.setSerialStoryCount(1);
        }
//
        //??????

        response.setGameSerialGroup(gameSerialGroup);


        //??????????????????
        DisplayGroup group9 = displayGroupService.getGroupById(9L);
        DisplayGroup group12 = displayGroupService.getGroupById(12L);
        DisplayGroup group10 = displayGroupService.getGroupById(10L);
        DisplayGroup group11 = displayGroupService.getGroupById(11L);
        DisplayGroup group17 = displayGroupService.getGroupById(17L);
        DisplayGroup group14 = displayGroupService.getGroupById(14L);

        List<DisplayGroup> classicAndCreateGroup = new ArrayList<>();
        classicAndCreateGroup.add(group9);
        classicAndCreateGroup.add(group12);
        List<DisplayGroup> emotionAndHumourGroup = new ArrayList<>();
        emotionAndHumourGroup.add(group10);
        emotionAndHumourGroup.add(group11);
        List<DisplayGroup> newStoryGroup = new ArrayList<>();
        newStoryGroup.add(group17);
        List<DisplayGroup> traditionCultureGroup = new ArrayList<>();
        traditionCultureGroup.add(group14);

        //??????
        response.setNewStoryGroup(newStoryGroup);
        response.setClassicAndCreateGroup(classicAndCreateGroup);
        response.setEmotionAndHumourGroup(emotionAndHumourGroup);
        response.setTraditionCultureGroup(traditionCultureGroup);

        logger.info("-------------003");
        //???????????? groupId 9 ???????????? 10 ????????????  11 ????????????  12 ???????????? 13 ???????????????  14 ???????????? 16 ????????????  17 ????????????
        //9  12
        GroupStoryThread groupStoryThread9=new GroupStoryThread(groupRelevanceService,user,9,0,6);
        threadResult.storyThreads.add(groupStoryThread9);
        GroupStoryThread groupStoryThread12=new GroupStoryThread(groupRelevanceService,user,12,0,6);
        threadResult.storyThreads.add(groupStoryThread12);
        // 10  11
        GroupStoryThread groupStoryThread10=new GroupStoryThread(groupRelevanceService,user,10,0,6);
        threadResult.storyThreads.add(groupStoryThread10);
        GroupStoryThread groupStoryThread11=new GroupStoryThread(groupRelevanceService,user,11,0,6);
        threadResult.storyThreads.add(groupStoryThread11);
        // 17
        GroupStoryThread groupStoryThread17=new GroupStoryThread(groupRelevanceService,user,17,0,6);
        threadResult.storyThreads.add(groupStoryThread17);
        // 14
        GroupStoryThread groupStoryThread14=new GroupStoryThread(groupRelevanceService,user,14,0,3);
        threadResult.storyThreads.add(groupStoryThread14);

        logger.info("-------------004");

        return response;
    }


    /**
     * ????????????????????????response2100
     * @param response
     * @param executor
     * @param threadResult
     * @return
     */
    private GetIndex280Response responseSetThreadValue2100(GetIndex280Response response, ExecutorService executor, IndexController.ThreadResult threadResult){
        logger.info("=====1");
        for(GroupStoryThread groupStoryThreadItem:threadResult.storyThreads){
            executor.execute(groupStoryThreadItem);
        }

        executor.execute(threadResult.groupAdThread);
        for(GroupSerialThread groupSerialThread:threadResult.serialThreads){
            executor.execute(groupSerialThread);
        }
        executor.execute(threadResult.groupAdThread);
        if(threadResult.groupPromptThread!=null){
            executor.execute(threadResult.groupPromptThread);
        }

        logger.info("=====2");
        try {
            executor.shutdown();
            executor.awaitTermination(9, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {
            ignored.printStackTrace();
        }
        logger.info("=====3");
        //???????????? groupId 9 ???????????? 10 ????????????  11 ????????????  12 ???????????? 13 ???????????????  14 ???????????? 16 ????????????  17 ????????????
        //??????
        for(GroupStoryThread groupStoryThread1:threadResult.storyThreads){
            // 9????????????  12????????????
            if(groupStoryThread1.getGroupId()==9){
                response.getClassicAndCreateGroup().get(0).setStorys(groupStoryThread1.getStoryPage().getContent());
            }
            if(groupStoryThread1.getGroupId()==12){
                response.getClassicAndCreateGroup().get(1).setStorys(groupStoryThread1.getStoryPage().getContent());
            }
            //10 ????????????  11 ????????????
            if(groupStoryThread1.getGroupId()==10){
                response.getEmotionAndHumourGroup().get(0).setStorys(groupStoryThread1.getStoryPage().getContent());
            }
            if(groupStoryThread1.getGroupId()==11){
                response.getEmotionAndHumourGroup().get(1).setStorys(groupStoryThread1.getStoryPage().getContent());
            }
            // 17????????????
            if(groupStoryThread1.getGroupId()==17){
                response.getNewStoryGroup().get(0).setStorys(groupStoryThread1.getStoryPage().getContent());
            }
            // 14????????????
            if(groupStoryThread1.getGroupId()==14){
                response.getTraditionCultureGroup().get(0).setStorys(groupStoryThread1.getStoryPage().getContent());
            }
        }
        for(GroupSerialThread groupSerialThread:threadResult.serialThreads){
            //??????????????????

            if(response.getSerialGroup()!=null&&response.getSerialGroup().getId()==1000L&&groupSerialThread.getSerialStoryStyle()==SerialStoryStyle.INDEX_SERIAL){
                response.getSerialGroup().setSerialStories(groupSerialThread.getSerialStoryList());
            }
            //IP??????
            if(response.getIpStoryGroup()!=null&&response.getIpStoryGroup().getId()==1002L&&groupSerialThread.getSerialStoryStyle()==SerialStoryStyle.IP_STORY_SERIAL){
                response.getIpStoryGroup().setSerialStories(groupSerialThread.getSerialStoryList());
            }
            //????????????
            if(response.getGameSerialGroup().getId()==1003L&&groupSerialThread.getSerialStoryStyle()==SerialStoryStyle.SMART_GAME_SERIAL){
                response.getGameSerialGroup().setSerialStories(groupSerialThread.getSerialStoryList());
            }
            //???????????????2.10.1
            if(response.getParentLessonGroup()!=null && response.getParentLessonGroup().getId()==1007L&&groupSerialThread.getSerialStoryStyle()==SerialStoryStyle.PARENT_LESSON_SERIAL){
                response.getParentLessonGroup().setSerialStories(groupSerialThread.getSerialStoryList());
            }

            //??????????????????
            if(response.getAudioSerialGroup()!=null&&response.getAudioSerialGroup().getId()==1008L&&groupSerialThread.getSerialStoryStyle()==SerialStoryStyle.INDEX_SERIAL){
                response.getAudioSerialGroup().setSerialStories(groupSerialThread.getSerialStoryList());
            }

            if(response.getIpStoryGroup().getId()==1009L){
                //ip ??????????????????????????????????????????
//                response.getIpStoryGroup().setIpBrands(ipBrandService.getAllIpBrand());
            }

//            1009L
        }
        response.setAds(threadResult.groupAdThread.getAdsList());
        if(threadResult.groupPromptThread!=null){
            response.setPrompt(threadResult.groupPromptThread.getPrompt());
        }
        logger.info("=====4");
        return response;
    }
}
