package com.ifenghui.storybookapi.app.story.controller;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.ifenghui.storybookapi.api.response.base.*;
import com.ifenghui.storybookapi.api.response.exception.ExceptionResponse;
import com.ifenghui.storybookapi.app.analysis.entity.StoryRecommend;
import com.ifenghui.storybookapi.app.analysis.service.GroupRelevanceService;
import com.ifenghui.storybookapi.app.app.entity.Ads;
import com.ifenghui.storybookapi.app.app.entity.Config;
import com.ifenghui.storybookapi.app.app.entity.CustomGroup;
import com.ifenghui.storybookapi.app.app.entity.DisplayGroup;
import com.ifenghui.storybookapi.app.app.service.AdService;
import com.ifenghui.storybookapi.app.app.service.ConfigService;
import com.ifenghui.storybookapi.app.app.service.DisplayGroupService;
import com.ifenghui.storybookapi.app.analysis.entity.GroupRelevance;
import com.ifenghui.storybookapi.app.social.entity.UserReadRecord;
import com.ifenghui.storybookapi.app.social.entity.ViewRecord;
import com.ifenghui.storybookapi.app.social.response.SubscriptionSchedule;
import com.ifenghui.storybookapi.app.social.service.SignRecordService;
import com.ifenghui.storybookapi.app.social.service.ViewRecordService;
import com.ifenghui.storybookapi.app.story.entity.*;
import com.ifenghui.storybookapi.app.story.response.*;
import com.ifenghui.storybookapi.app.story.response.lesson.LessonIndex;
import com.ifenghui.storybookapi.app.story.service.*;
import com.ifenghui.storybookapi.app.story.thread.GroupAdThread;
import com.ifenghui.storybookapi.app.story.thread.GroupPromptThread;
import com.ifenghui.storybookapi.app.story.thread.GroupSerialThread;
import com.ifenghui.storybookapi.app.story.thread.GroupStoryThread;
import com.ifenghui.storybookapi.app.system.service.ElasticService;
import com.ifenghui.storybookapi.app.system.service.GeoipService;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.UserAbilityPlanRelate;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.BuyMagazineRecord;
import com.ifenghui.storybookapi.app.transaction.entity.BuyStoryRecord;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.SubscriptionRecord;

import com.ifenghui.storybookapi.app.transaction.service.*;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.config.StoryConfig;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiInputFormatException;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.app.social.service.UserReadRecordService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.style.*;
import com.ifenghui.storybookapi.util.HttpRequest;
import com.ifenghui.storybookapi.util.VersionUtil;
import io.swagger.annotations.*;
import io.swagger.annotations.ApiResponse;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.asn1.teletrust.TeleTrusTNamedCurves;
import org.elasticsearch.client.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Controller
@EnableAutoConfiguration
//@EnableAutoConfiguration
//@SpringBootApplication
//@ConfigurationProperties(prefix = "spring")
@Api(value = "StoryController", description = "????????????api")
@RequestMapping("/api/story")
public class StoryController {
    //    @JsonBackReference
    Logger logger = Logger.getLogger(StoryController.class);

    @Autowired
    StoryService storyService;
    @Autowired
    AdService adsService;
    @Autowired
    DisplayGroupService displayGroupService;
    @Autowired
    GroupRelevanceService groupRelevanceService;
    @Autowired
    UserService userService;
    @Autowired
    UserAbilityPlanRelateService userAbilityPlanRelateService;
    @Autowired
    MagazineService magazineService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    BuyMagazineRecordService buyMagazineRecordService;
    @Autowired
    ConfigService configService;
    @Autowired
    GeoipService geoipService;
    @Autowired
    UserReadRecordService userReadRecordService;

    @Autowired
    ViewRecordService viewRecordService;

    @Autowired
    SerialStoryService serialStoryService;

    @Autowired
    LessonService lessonService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    GroupRelevanceService getGroupRelevanceService;

    @Autowired
    BuyStorySubscriptionRecordService buyStorySubscriptionRecordService;

    @Autowired
    PromptService promptService;

    @Autowired
    SignRecordService signRecordService;

    @Autowired
    ElasticService elasticService;

    @Autowired
    BuyStoryRecordService buyStoryRecordService;
    @Autowired
    BuySerialStoryRecordService buySerialStoryRecordService;
    @Autowired
    SerialBannerService serialBannerService;

    class ThreadResult {
        public List<GroupStoryThread> storyThreads = new ArrayList<>();
        public List<GroupSerialThread> serialThreads = new ArrayList<>();
        public GroupAdThread groupAdThread = null;
        public GroupPromptThread groupPromptThread = null;
    }


    @RequestMapping(value = "/save_story", method = RequestMethod.POST)
    @ApiOperation(value = "????????????", notes = "")
    void saveStory(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "??????ID") @RequestParam Long storyId
    ) throws ApiException {

        Long userId;
        if (token == null || token.length() <= 0) {
            return;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }
        buyStoryRecordService.addBuyStoryRecord(userId,storyId,4);

    }

    @RequestMapping(value = "/save_serialstory", method = RequestMethod.POST)
    @ApiOperation(value = "???????????????", notes = "")
    void saveSerialStory(
            @ApiParam(value = "??????token")@RequestParam String token,
            @ApiParam(value = "?????????ID") @RequestParam Long serialStoryId
    ) throws ApiException {

        Long userId;
        if (token == null || token.length() <= 0) {
            return;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }
        User user = userService.getUser(userId);
        buySerialStoryRecordService.addBuySerialStoryRecord(user,serialStoryId.intValue());
    }


    @ApiOperation(value = "??????????????????????????????", notes = "")
    @RequestMapping(value = "/getStorysByCategoryId", method = RequestMethod.GET)
    @ResponseBody
    GetStorysByCategoryResponse getStorysByCategoryId(@ApiParam(value = "??????token", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String token,
                                                      @ApiParam(value = "??????id") @RequestParam Integer categoryId,
                                                      @RequestParam int pageNo, @RequestParam int pageSize) throws ApiNotTokenException {
        Long userId;
        if (token == null || token.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }
//        pageNo = pageNo - 1;//?????????0????????????????????????1

        GetStorysByCategoryResponse getStorysByCategoryResponse = new GetStorysByCategoryResponse();
        Page<Story> pageStory = this.storyService.getStorysByCategoryId(categoryId, userId, pageNo, pageSize, request);
        getStorysByCategoryResponse.setStorys(pageStory.getContent());

        getStorysByCategoryResponse.setJpaPage(pageStory);

        return getStorysByCategoryResponse;
    }

    @ApiOperation(value = "???????????????id????????????????????????2.11.0", notes = "")
    @RequestMapping(value = "/getSerialAndStorysBySerialId", method = RequestMethod.GET)
    @ResponseBody
    GetSerialStoryAndStoryListReponse getSerialAndStorysBySerialId(
            @RequestHeader(value = "ssToken", required = false) String ssToken,
//            @ApiParam(value = "??????token???????????????", defaultValue = "", required = false)
//            @RequestParam(required = false, defaultValue = "") String token,
            @ApiParam(value = "????????????id") @RequestParam Long serialStoryId
    ) throws ApiNotTokenException {


        GetSerialStoryAndStoryListReponse response = new GetSerialStoryAndStoryListReponse();
        Long userId;
        if (ssToken == null || ssToken.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(ssToken);
        }
        SerialStory serialStory = serialStoryService.getSerialStoryDetail(userId, serialStoryId);
        Page<Story> pageStory = storyService.getStorysBySerialId(serialStoryId, userId, 0, 100);
        response.setSerialStory(serialStory);
        response.setStoryList(pageStory.getContent());
        return response;
    }

    @ApiOperation(value = "???????????????id????????????", notes = "")
    @RequestMapping(value = "/getStorysBySerialId", method = RequestMethod.GET)
    @ResponseBody
    GetStorysBySerialStoryIdResponse getStorysBySerialId(@ApiParam(value = "??????token???????????????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String token,
                                                         @ApiParam(value = "????????????id") @RequestParam Long serialStoryId,
                                                         @RequestParam int pageNo, @RequestParam int pageSize) throws ApiNotTokenException {
        Long userId;
        if (token == null || token.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }
//        pageNo = pageNo - 1;//?????????0????????????????????????1

        GetStorysBySerialStoryIdResponse getStorysBySerialStoryIdResponse = new GetStorysBySerialStoryIdResponse();
        Page<Story> pageStory = storyService.getStorysBySerialId(serialStoryId, userId, pageNo, pageSize);
        getStorysBySerialStoryIdResponse.setStorys(pageStory.getContent());
        getStorysBySerialStoryIdResponse.setJpaPage(pageStory);

        return getStorysBySerialStoryIdResponse;
    }


    @ApiOperation(value = "????????????id????????????????????????", notes = "???????????????????????????")
    @RequestMapping(value = "/getStoryDetailById", method = RequestMethod.GET)
    @ResponseBody
    @ApiResponses({@ApiResponse(code = 1, message = "??????", response = GetStoryDetailByIdResponse.class)
            , @ApiResponse(code = 201, message = "????????????????????????", response = ExceptionResponse.class)})
    GetStoryDetailByIdResponse getStoryDetailById(@ApiParam(value = "??????id") @RequestParam long id,
                                                                               @ApiParam(value = "??????token?????????????????????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String token
    ) throws ApiNotTokenException, ApiNotFoundException {


        Long userId;
        if (token == null || token.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }
        //????????????????????????????????????????????????

        GetStoryDetailByIdResponse getStoryDetailByIdResponse = new GetStoryDetailByIdResponse();
        Story story = this.storyService.getStoryDetailById(id, userId);
        if (story == null) {
            throw new ApiNotFoundException("????????????????????????");
        }
        storyService.setStoryAppFile(story);
        getStoryDetailByIdResponse.setStory(story);

//        if(VersionUtil.getPlatform(request)== StoryConfig.Platfrom.IOS
//                &&"2.12.0".equals(VersionUtil.getVerionInfo(request))){
//            GetStoryFatherByIdResponse fatherByIdResponse=new GetStoryFatherByIdResponse();
//            fatherByIdResponse.setStory(story);
//            if(fatherByIdResponse.getStory().getIsBuy()>0){
//                fatherByIdResponse.getStory().setIsFree(1);
//            }
//            return fatherByIdResponse;
//        }


        return getStoryDetailByIdResponse;
    }

    @ApiOperation(value = "????????????", notes = "")
    @RequestMapping(value = "/getStorysBySearch", method = RequestMethod.GET)
    @ResponseBody
    GetStorysBySearchResponse getStorysBySearch(
            @ApiParam(value = "??????token?????????????????????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String token,
            @ApiParam(value = "????????????") @RequestParam String content,
            @ApiParam(value = "????????????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String ver,
            @RequestParam int pageNo, @RequestParam int pageSize) throws ApiNotTokenException, Exception {
        Long userId;
        if (token == null || token.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }

//        pageNo = pageNo - 1;//?????????0????????????????????????1h

        GetStorysBySearchResponse getStorysBySearchResponse = new GetStorysBySearchResponse();

        //????????????
        List<Story> pageStory;
        Page<Map> page = null;
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.indexOf("ios") != -1) {
            //???????????????????????????????????????config???ver????????????????????????
            String key = "version";
            if (userAgent.contains("appname:zhijianStory")) {
                key = "zhijianStory" + "_version";
            }
            Config config = this.configService.getConfigByKey(key);
            //????????????????????????
            Integer iosIsCheck = VersionUtil.getIosIsCheck(request, configService,geoipService);
            if(iosIsCheck ==1){
                //???????????????????????????????????????????????????
                page = elasticService.searchStoryNotNow(content, pageNo, pageSize);
                List<Map> maps = page.getContent();
                pageStory = this.storyService.getStoryByMaps(userId, maps, request);
            } else {
                page = elasticService.searchStory(content, pageNo, pageSize);
                List<Map> maps = page.getContent();
                pageStory = this.storyService.getStoryByMaps(userId, maps, request);
            }
        } else {
            page = elasticService.searchStory(content, pageNo, pageSize);
            List<Map> maps = page.getContent();
            pageStory = this.storyService.getStoryByMaps(userId, maps, request);
        }

        getStorysBySearchResponse.setJpaPage(page);
        getStorysBySearchResponse.setStorys(pageStory);
        return getStorysBySearchResponse;


        /**
         * ?????????????????????,????????????,??????????????????
         */
        //????????????
//        Page<Story> pageStory;
//        String userAgent = request.getHeader("User-Agent");
//        if (userAgent.indexOf("ios") != -1) {
//            //???????????????????????????????????????config???ver????????????????????????
//            String key = "version";
//            if(userAgent.contains("appname:zhijianStory")){
//                key = "zhijianStory"+"_version";
//            }
//            Config config=this.configService.getConfigByKey(key);
//                //???????????????????????????????????????????????????
//                pageStory=this.storyService.getNotNowStorysBySearch(userId,content,pageNo,pageSize,request);
//            }else{
//               pageStory=this.storyService.getStorysBySearch(userId,content,pageNo,pageSize,request);
//            }
//        }else{
//             pageStory=this.storyService.getStorysBySearch(userId,content,pageNo,pageSize,request);
//        }
//
//        getStorysBySearchResponse.setStorys(pageStory.getContent());
//        getStorysBySearchResponse.setJpaPage(pageStory);
//
//        return getStorysBySearchResponse;
    }

    @RequestMapping(value = "/getIndex", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "??????", notes = "1.2???????????????")
    @Deprecated
    GetIndexResponse getIndex(@ApiParam(value = "??????token", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String token,
                              @ApiParam(value = "?????????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String ver
//                                                        @ApiParam(value = "?????????")@RequestParam String ver,
    ) throws ApiException {

        GetIndexResponse getIndexResponse = new GetIndexResponse();

        getIndexResponse.getStatus().setCode(0);
        getIndexResponse.getStatus().setMsg("??????????????????????????????????????????????????????");
        return getIndexResponse;
    }

    @RequestMapping(value = "/getIndex130", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "??????1.3.0?????????", notes = "")
    GetIndex130Response getIndex130(@ApiParam(value = "??????token", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String token,
                                    @ApiParam(value = "?????????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String ver
//                                                        @ApiParam(value = "?????????")@RequestParam String ver,
    ) throws ApiException {

        GetIndex130Response getIndexResponse = new GetIndex130Response();
        getIndexResponse.getStatus().setCode(0);
        getIndexResponse.getStatus().setMsg("??????????????????????????????????????????????????????");
        return getIndexResponse;
    }

    @RequestMapping(value = "/getIndex150", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "??????1.5.0?????????", notes = "")
    GetIndex150Response getIndex150(@ApiParam(value = "??????token", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String token,
                                    @ApiParam(value = "?????????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String ver
//                                                        @ApiParam(value = "?????????")@RequestParam String ver,
    ) throws ApiException {
        GetIndex150Response response = new GetIndex150Response();
        List<Ads> adsList = new ArrayList<>();
        Ads ad = new Ads();
        ad.setImgPath("limit_ver.jpg");
        ad.setUrl("#");
        ad.setAdPositionStyle(AdPositionStyle.INDEX);
        ad.setContent("?????????");
        ad.setCreateTime(new Date());
        ad.setOrderBy(0);
        ad.setTitle("?????????");
        ad.setTargetType(0);
        ad.setTargetValue(0);
        ad.setStatus(1);
        ad.setId(0L);
        adsList.add(ad);
//        getIndexResponse.setAds(adsList);
        response.setAds(adsList);


        response.setGroups(new ArrayList<>());
        //??????????????????
        //??????????????????
//        Page<StoryHotSale> storyHotSalePage = storyService.getMoreHotSaleStorys(userId,pageNo,pageSize);
//        for (StoryHotSale item :storyHotSalePage.getContent()) {
//            response.getHotSaleStorys().add(item.getStory());
//        }
        //??????????????????
//        List<DisplayGroup> displayGroups = storyService.getStoryGroups();
        response.setGroupList(new ArrayList<>());

        return response;
    }

    @RequestMapping(value = "getIndex200", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "?????? 2.0.0 ????????????")
    GetIndex200Response getIndex200(
            @ApiParam(value = "??????token", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String token,
            @ApiParam(value = "?????????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String ver
    ) {
//????????????????????????
//        ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(15,
//                new BasicThreadFactory.Builder().namingPattern("getIndex-schedule-pool-%d").daemon(true).build());
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("getIndex-pool-%d").build();
        ExecutorService executor = new ThreadPoolExecutor(5, 15,
                5000L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

//        final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(15);
        ThreadResult threadResult = new ThreadResult();
        Long userId;
        if (token == null || token.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }
        User user = userService.getUser(userId);
        GetIndex200Response resp = this.getIndex200And260(user, 2, threadResult);

        resp = this.responseSetThreadValue(resp, executor, threadResult);
        resp.getStatus().setCode(0);
        resp.getStatus().setMsg("?????????????????????????????????????????????");
        return resp;
    }

    @RequestMapping(value = "getIndex260", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "?????? 2.6.0 ????????????")
    GetIndex200Response getIndex260(
            @RequestHeader(value = "ssToken", required = false) String ssToken,
            @ApiParam(value = "?????????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String ver
    ) {
        //????????????????????????
        final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(15);

        /**
         * ????????????
         */
        ThreadResult threadResult = new ThreadResult();
//        Long userId;
        Long loginId = (Long) request.getAttribute("loginId");
        User user = userService.getUser(loginId.intValue());

//        logger.info("-------------begin");
        GetIndex200Response response = this.getIndex200And260(user, 3, threadResult);
//        logger.info("-------------1");
        List<CustomGroup> groups = response.getGroups();
//        GetSerialStoryPageResponse subjectSerial = serialStoryController.getSmartGameSerialStoryPage(ssToken, SerialStoryStyle.SUBJECT_SERIAL.getId(), 1, 1);
        CustomGroup serialStoryGroup = new CustomGroup();
        serialStoryGroup.setId(1003L);
        serialStoryGroup.setName("????????????");
        serialStoryGroup.setContent("????????????????????????");
        serialStoryGroup.setIcon("");
        serialStoryGroup.setPadIcon("");
        serialStoryGroup.setTargetType(1);
        serialStoryGroup.setTargetValue(5);
        serialStoryGroup.setIsSubscribe(0);
//        serialStoryGroup.setSerialStories(subjectSerial.getSerialStoryList());
        groups.add(1, serialStoryGroup);

        GroupSerialThread groupSerialThread = new GroupSerialThread(serialStoryService, SerialStoryStyle.SUBJECT_SERIAL, user, 0, 1);
        threadResult.serialThreads.add(groupSerialThread);

//        logger.info("-------------2");
//        GetSerialStoryPageResponse ipSerial = serialStoryController.getSmartGameSerialStoryPage(ssToken, SerialStoryStyle.IP_STORY_SERIAL.getId(), 1, 3);
        CustomGroup serialStoryGroupIp = new CustomGroup();
        serialStoryGroupIp.setId(1002L);
        serialStoryGroupIp.setName("????????????");
        serialStoryGroupIp.setContent("????????????ip??????");
        serialStoryGroupIp.setIcon("");
        serialStoryGroupIp.setPadIcon("");
        serialStoryGroupIp.setIsSubscribe(0);
//        serialStoryGroupIp.setSerialStories(ipSerial.getSerialStoryList());
        groups.add(2, serialStoryGroupIp);
        groupSerialThread = new GroupSerialThread(serialStoryService, SerialStoryStyle.IP_STORY_SERIAL, user, 0, 2);
        threadResult.serialThreads.add(groupSerialThread);
//        logger.info("-------------3");
        //??????????????????
        threadResult.groupPromptThread = new GroupPromptThread(promptService, user);
//        if(userId!=0L){
//            try {
//                Prompt prompt = promptService.getPrompt(userId.intValue());
//                response.setPrompt(prompt);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        //?????????
//        if(userId==0L){
//            response.setPrompt(new Prompt(PromptStyle.STUDY_PLAN.getId(),PromptStyle.STUDY_PLAN.getName(),PromptStyle.STUDY_PLAN.getUrl()));
//        }
        logger.info("-------------end");
        response.setGroups(groups);

        response = this.responseSetThreadValue(response, executor, threadResult);

        logger.info("-------------end resp");
        response.getStatus().setCode(0);
        response.getStatus().setMsg("??????????????????????????????????????????????????????");
        return response;
    }

    @RequestMapping(value = "getIndex280", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "?????? 2.8.0 ????????????")
    GetIndex280Response getIndex280(
            @RequestHeader(value = "ssToken", required = false) String ssToken,
            @ApiParam(value = "?????????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String ver
    ) {

        //????????????????????????
        final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(15);

        /**
         * ????????????
         */
        ThreadResult threadResult = new ThreadResult();
        Long loginId = (Long) request.getAttribute("loginId");
        User user = userService.getUser(loginId.intValue());
        logger.info("-------------begin");
        GetIndex280Response response = this.getIndex280And290Response(280, user, 3, threadResult);
        logger.info("-------------3");
        threadResult.groupPromptThread = new GroupPromptThread(promptService, user);
        response = this.responseSetThreadValue280(response, executor, threadResult);
        logger.info("-------------end resp");
        response.getStatus().setCode(0);
        response.getStatus().setMsg("??????????????????????????????????????????????????????");
        return response;
    }


    @RequestMapping(value = "getIndex290", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "?????? 2.9.0 ????????????")
    GetIndex280Response getIndex290(
            @RequestHeader(value = "ssToken", required = false) String ssToken,
            @ApiParam(value = "?????????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String ver
    ) {

        //????????????????????????
        final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(15);

        /**
         * ????????????
         */
        ThreadResult threadResult = new ThreadResult();
        Long loginId = (Long) request.getAttribute("loginId");
        User user = userService.getUser(loginId.intValue());
        logger.info("-------------begin");
        GetIndex280Response response = this.getIndex280And290Response(290, user, 3, threadResult);
        logger.info("-------------3");

        String userAgent = request.getHeader("User-Agent");
        if (userAgent.contains("ios")) {
            //???????????????????????????????????????config???ver????????????????????????
            String ver210 = "2.10.0";
            /**2.10.0 ???????????????*/
            if (VersionUtil.isAllow(request, ver210)) {
                threadResult.groupPromptThread = null;
            } else {
                threadResult.groupPromptThread = new GroupPromptThread(promptService, user);
            }
        } else {
            threadResult.groupPromptThread = new GroupPromptThread(promptService, user);
        }
        response = this.responseSetThreadValue280(response, executor, threadResult);
        logger.info("-------------end resp");
        response.getStatus().setCode(0);
        response.getStatus().setMsg("??????????????????????????????????????????????????????");
        return response;
    }

    /**
     * ??????????????????????????????????????????????????? 280
     * ?????????responseSetThreadValue ?????????
     *
     * @param user           ??????
     * @param serialPageSize ????????????
     * @param threadResult   ??????????????????
     * @return
     */
    private GetIndex280Response getIndex280And290Response(Integer index, User user, Integer serialPageSize, ThreadResult threadResult) {

        GetIndex280Response response = new GetIndex280Response();
        //??????svip
        if (user != null && (user.getSvip().equals(SvipStyle.LEVEL_FOUR.getId()) || user.getSvip().equals(SvipStyle.LEVEL_THREE.getId()))) {
            response.setIsSvip(1);
        } else {
            response.setIsSvip(0);
        }
        logger.info("-------------001");
        //????????????
        Integer isCheckVer = 0;
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.contains("ios")) {
            //???????????????????????????????????????config???ver????????????????????????
            String key = "version";
            if (userAgent.contains("appname:zhijianStory")) {
                key = "zhijianStory" + "_version";
            }
            Config config = this.configService.getConfigByKey(key);
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
        String platform = VersionUtil.getPlatformStr(request);
        String ver = VersionUtil.getVerionInfo(request);
        threadResult.groupAdThread = new GroupAdThread(adsService, channel, platform, isCheckVer, ver, user);

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
        if (user != null && user.getIsAbilityPlan() == 1) {
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
        if (user != null && user.getId() != 0) {
            Page<ViewRecord> viewRecordPage = viewRecordService.getViewrecordByUserIdAndType(user.getId(), 0, 4);
            viewRecordGroup.setViewRecordList(viewRecordPage.getContent());
            List<Story> storys = new ArrayList<>();
            for (ViewRecord s : viewRecordPage.getContent()) {
                storys.add(s.getStory());
            }
            viewRecordGroup.setStorys(storys);
            response.setUserReadRecordGroup(viewRecordGroup);
        } else {
            viewRecordGroup.setStorys(new ArrayList<>());
            response.setUserReadRecordGroup(viewRecordGroup);
        }
        //???????????? 2.9.0 ??????????????????
        if (index == 280) {
            Long userId = 0L;
            if (user != null) {
                userId = user.getId();
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


        logger.info("-------?????????????????? 4 ???------003");
        List<CustomGroup> cgroups = new ArrayList<>();
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
        GroupSerialThread groupSerialThread3 = new GroupSerialThread(serialStoryService, SerialStoryStyle.SMART_GAME_SERIAL, user, 0, 4);
        threadResult.serialThreads.add(groupSerialThread3);

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

        response.setSerialStoryCount(3);
        //??????
        response.setSerialGroup(huiBenGroup);
        response.setGameSerialGroup(gameSerialGroup);
        response.setIpStoryGroup(ipSerialGroup);

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
        GroupStoryThread groupStoryThread9 = new GroupStoryThread(groupRelevanceService, user, 9, 0, 6);
        threadResult.storyThreads.add(groupStoryThread9);
        GroupStoryThread groupStoryThread12 = new GroupStoryThread(groupRelevanceService, user, 12, 0, 6);
        threadResult.storyThreads.add(groupStoryThread12);
        // 10  11
        GroupStoryThread groupStoryThread10 = new GroupStoryThread(groupRelevanceService, user, 10, 0, 6);
        threadResult.storyThreads.add(groupStoryThread10);
        GroupStoryThread groupStoryThread11 = new GroupStoryThread(groupRelevanceService, user, 11, 0, 6);
        threadResult.storyThreads.add(groupStoryThread11);
        // 17
        GroupStoryThread groupStoryThread17 = new GroupStoryThread(groupRelevanceService, user, 17, 0, 6);
        threadResult.storyThreads.add(groupStoryThread17);
        // 14
        GroupStoryThread groupStoryThread14 = new GroupStoryThread(groupRelevanceService, user, 14, 0, 6);
        threadResult.storyThreads.add(groupStoryThread14);

        logger.info("-------------004");

        return response;
    }

    /**
     * ????????????????????????response280
     *
     * @param response
     * @param executor
     * @param threadResult
     * @return
     */
    private GetIndex280Response responseSetThreadValue280(GetIndex280Response response, ExecutorService executor, ThreadResult threadResult) {
        logger.info("=====1");
        for (GroupStoryThread groupStoryThreadItem : threadResult.storyThreads) {
            executor.execute(groupStoryThreadItem);
        }

        executor.execute(threadResult.groupAdThread);
        for (GroupSerialThread groupSerialThread : threadResult.serialThreads) {
            executor.execute(groupSerialThread);
        }
        executor.execute(threadResult.groupAdThread);
        if (threadResult.groupPromptThread != null) {
            executor.execute(threadResult.groupPromptThread);
        }

        logger.info("=====2");
        try {
            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {
            ignored.printStackTrace();
        }
        logger.info("=====3");
        //???????????? groupId 9 ???????????? 10 ????????????  11 ????????????  12 ???????????? 13 ???????????????  14 ???????????? 16 ????????????  17 ????????????
        //??????
        for (GroupStoryThread groupStoryThread1 : threadResult.storyThreads) {
            // 9????????????  12????????????
            if (groupStoryThread1.getGroupId() == 9) {
                response.getClassicAndCreateGroup().get(0).setStorys(groupStoryThread1.getStoryPage().getContent());
            }
            if (groupStoryThread1.getGroupId() == 12) {
                response.getClassicAndCreateGroup().get(1).setStorys(groupStoryThread1.getStoryPage().getContent());
            }
            //10 ????????????  11 ????????????
            if (groupStoryThread1.getGroupId() == 10) {
                response.getEmotionAndHumourGroup().get(0).setStorys(groupStoryThread1.getStoryPage().getContent());
            }
            if (groupStoryThread1.getGroupId() == 11) {
                response.getEmotionAndHumourGroup().get(1).setStorys(groupStoryThread1.getStoryPage().getContent());
            }
            // 17????????????
            if (groupStoryThread1.getGroupId() == 17) {
                response.getNewStoryGroup().get(0).setStorys(groupStoryThread1.getStoryPage().getContent());
            }
            // 14????????????
            if (groupStoryThread1.getGroupId() == 14) {
                response.getTraditionCultureGroup().get(0).setStorys(groupStoryThread1.getStoryPage().getContent());
            }
        }
        for (GroupSerialThread groupSerialThread : threadResult.serialThreads) {
            //??????????????????
            if (response.getSerialGroup().getId() == 1000L && groupSerialThread.getSerialStoryStyle() == SerialStoryStyle.INDEX_SERIAL) {
                response.getSerialGroup().setSerialStories(groupSerialThread.getSerialStoryList());
            }
            //IP??????
            if (response.getIpStoryGroup().getId() == 1002L && groupSerialThread.getSerialStoryStyle() == SerialStoryStyle.IP_STORY_SERIAL) {
                response.getIpStoryGroup().setSerialStories(groupSerialThread.getSerialStoryList());
            }
            //????????????
            if (response.getGameSerialGroup().getId() == 1003L && groupSerialThread.getSerialStoryStyle() == SerialStoryStyle.SMART_GAME_SERIAL) {
                response.getGameSerialGroup().setSerialStories(groupSerialThread.getSerialStoryList());
            }
        }
        response.setAds(threadResult.groupAdThread.getAdsList());
        if (threadResult.groupPromptThread != null) {
            response.setPrompt(threadResult.groupPromptThread.getPrompt());
        }
        logger.info("=====4");
        return response;
    }


    /**
     * ???????????????????????????????????????????????????
     * ?????????responseSetThreadValue ?????????
     *
     * @param user           ??????
     * @param serialPageSize ????????????
     * @param threadResult   ??????????????????
     * @return
     */
    private GetIndex200Response getIndex200And260(User user, Integer serialPageSize, ThreadResult threadResult) {
        Long userId;


        GetIndex200Response response = new GetIndex200Response();

        if (user != null &&
                (user.getSvip().equals(SvipStyle.LEVEL_FOUR.getId()) || user.getSvip().equals(SvipStyle.LEVEL_THREE.getId()))
                ) {
            response.setIsSvip(1);
        } else {
            response.setIsSvip(0);
        }
        logger.info("-------------001");
        //????????????
        Integer isCheckVer = 0;
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.contains("ios")) {
            //???????????????????????????????????????config???ver????????????????????????
            String key = "version";
            if (userAgent.contains("appname:zhijianStory")) {
                key = "zhijianStory" + "_version";
            }
            Config config = this.configService.getConfigByKey(key);
            String ver = VersionUtil.getVerionInfo(request);
            //????????????????????????
            Integer iosIsCheck = VersionUtil.getIosIsCheck(request, configService,geoipService);
            if(iosIsCheck ==1){
                isCheckVer = 1;
            }
        }
        logger.info("-------------0010");
        LessonIndex lessonIndex = new LessonIndex();
        lessonIndex.setName("????????????");
        lessonIndex.setContent("????????????");
        lessonIndex.setIntro("?????????????????????");
        lessonIndex.setLessonList(lessonService.getLessonList());
        response.setLessonIndex(lessonIndex);

        logger.info("-------------0011");
        //????????????
        String channel = request.getHeader("channel");
        String platform = VersionUtil.getPlatformStr(request);
        String ver = VersionUtil.getVerionInfo(request);
        threadResult.groupAdThread = new GroupAdThread(adsService, channel, platform, isCheckVer, ver, user);

//        List<Ads> adsList=this.adsService.getAds(0,8,isCheckVer,channel,platform);

//        if(user != null && user.getIsTest().equals(1)){
//            List<Ads> adsTestList = this.adsService.getTestAds();
//            List<Ads> newAdsList = new ArrayList<>(adsList);
//            newAdsList.addAll(adsTestList);
//            response.setAds(newAdsList);
//        } else {
//            response.setAds(adsList);
//        }
        logger.info("-------------002");
        List<CustomGroup> cgroups = new ArrayList<>();

        DisplayGroup group = displayGroupService.getGroupById(3L);
        CustomGroup freeStoryGroup = new CustomGroup(group);
        freeStoryGroup.setPadIcon("2017/08/30/free.png");
        // Page<Story> freeStoryPage = storyService.getStorysByCategoryId(group.getId().intValue(),userId,0,6,request);
//        Page<Story> freeGroupResponse = groupRelevanceService.getNewGroupStoryList(user, 3, 0, 6);
//        freeStoryGroup.setStorys(freeGroupResponse.getContent());
        freeStoryGroup.setTargetValue(3);
        freeStoryGroup.setTargetType(5);
        cgroups.add(0, freeStoryGroup);

//        Page<SerialStory> serialStoryPage = serialStoryService.getCommonSerialStoryPage(userId,SerialStoryStyle.INDEX_SERIAL,0,serialPageSize);
        CustomGroup serialStoryGroup = new CustomGroup();
        serialStoryGroup.setId(1000L);
        serialStoryGroup.setName("????????????");
        serialStoryGroup.setContent("????????????????????????");
        serialStoryGroup.setIcon("");
        serialStoryGroup.setPadIcon("");
        serialStoryGroup.setIsSubscribe(0);
//        serialStoryGroup.setSerialStories(serialStoryPage.getContent());
        cgroups.add(serialStoryGroup);
        GroupSerialThread groupSerialThread = new GroupSerialThread(serialStoryService, SerialStoryStyle.INDEX_SERIAL, user, 0, serialPageSize);
        threadResult.serialThreads.add(groupSerialThread);
        //??????????????????
        List<DisplayGroup> displayGroups = storyService.getStoryGroupListByNewCategory();
        for (DisplayGroup item : displayGroups) {
//            Page<Story> groupResponse = groupRelevanceService.getNewGroupStoryList(user, item.getId().intValue(), 0, 6);
//            item.setStorys(groupResponse.getContent());
            item.setTargetValue(item.getId().intValue());
        }
        response.setGroups(cgroups);
        response.setGroupList(displayGroups);

        logger.info("-------------003");


        GroupStoryThread groupStoryThread = new GroupStoryThread(groupRelevanceService, user, 3, 0, 6);
        threadResult.storyThreads.add(groupStoryThread);
        for (DisplayGroup group1 : displayGroups) {
            groupStoryThread = new GroupStoryThread(groupRelevanceService, user, group1.getId().intValue(), 0, 6);
            threadResult.storyThreads.add(groupStoryThread);
        }

        logger.info("-------------004");


        return response;
    }

    /**
     * ????????????????????????response
     *
     * @param response
     * @param executor
     * @param threadResult
     * @return
     */
    private GetIndex200Response responseSetThreadValue(GetIndex200Response response, ExecutorService executor, ThreadResult threadResult) {
        logger.info("=====1");
        for (GroupStoryThread groupStoryThreadItem : threadResult.storyThreads) {
            executor.execute(groupStoryThreadItem);
        }


        executor.execute(threadResult.groupAdThread);

//        if(VersionUtil.isAllow(request,"2.6.0")){
        for (GroupSerialThread groupSerialThread : threadResult.serialThreads) {
            executor.execute(groupSerialThread);
        }
        executor.execute(threadResult.groupAdThread);
        if (threadResult.groupPromptThread != null) {
            executor.execute(threadResult.groupPromptThread);
        }

//        }
        logger.info("=====2");
        try {
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {
            ignored.printStackTrace();
        }
        logger.info("=====3");
        //??????
        for (GroupStoryThread groupStoryThread1 : threadResult.storyThreads) {
            if (groupStoryThread1.getGroupId() == 3) {
                response.getGroups().get(0).setStorys(groupStoryThread1.getStoryPage().getContent());
            }

            for (DisplayGroup displayGroup : response.getGroupList()) {
                if (groupStoryThread1.getGroupId() == displayGroup.getId().intValue()) {
                    if (displayGroup == null) {
                        continue;
                    }
                    displayGroup.setStorys(groupStoryThread1.getStoryPage().getContent());
                }
            }

        }

        for (GroupSerialThread groupSerialThread : threadResult.serialThreads) {

            for (CustomGroup customGroup : response.getGroups()) {
                if (customGroup.getId() == 1000L && groupSerialThread.getSerialStoryStyle() == SerialStoryStyle.INDEX_SERIAL) {
                    customGroup.setSerialStories(groupSerialThread.getSerialStoryList());
                }
                if (customGroup.getId() == 1002L && groupSerialThread.getSerialStoryStyle() == SerialStoryStyle.IP_STORY_SERIAL) {
                    customGroup.setSerialStories(groupSerialThread.getSerialStoryList());
                }
                if (customGroup.getId() == 1003L && groupSerialThread.getSerialStoryStyle() == SerialStoryStyle.SUBJECT_SERIAL) {
                    customGroup.setSerialStories(groupSerialThread.getSerialStoryList());
                }
            }

        }
        response.setAds(threadResult.groupAdThread.getAdsList());
        if (threadResult.groupPromptThread != null) {
            response.setPrompt(threadResult.groupPromptThread.getPrompt());
        }


        logger.info("=====4");
        return response;
    }


    @RequestMapping(value = "/getIndex161", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "??????1.6.1?????????", notes = "")
    GetIndex161Response getIndex161(@ApiParam(value = "??????token", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String token,
                                    @ApiParam(value = "?????????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String ver
//                                                        @ApiParam(value = "?????????")@RequestParam String ver,
    ) throws ApiException {
        Long userId;

        if (token == null || token.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }
        //??????????????????
        User user = userService.getUser(userId);

        GetIndex161Response response = new GetIndex161Response();
        //??????????????????
        Integer pageNo = 0;
        Integer pageSize = 8;
        //?????????????????????
        Integer isNow = 1;
        List<Magazine> magazines = magazineService.getMagazineByIsNow(isNow);
        Integer isSubscribe = 0;
//        Integer isSubscribeSvip = 0;
        isSubscribe = storyService.isSubscribe(userId);
//        if(user!=null && user.getSvip() == 1){
//            //?????????vip???????????????????????????????????????????????????????????????
//            isSubscribe = 1;
//        }
        //??????????????????
//        List<DisplayGroup> groupList;
//        Integer targetType = 2;
        Long[] groupIds = new Long[]{3L};
        //????????????
        Integer isCheckVer = 0;
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.indexOf("ios") != -1) {
            //???????????????????????????????????????config???ver????????????????????????
            String key = "version";
            if (userAgent.contains("appname:zhijianStory")) {
                key = "zhijianStory" + "_version";
            }
            Config config = this.configService.getConfigByKey(key);
            //????????????????????????
            Integer iosIsCheck = VersionUtil.getIosIsCheck(request, configService,geoipService);
            if(iosIsCheck ==1){
                isCheckVer = 1;
            } else {
                groupIds = new Long[]{3L, 4L, 7L};
            }
        } else {
//            groupList=this.displayGroupService.getGroups();
            groupIds = new Long[]{3L, 4L, 7L};
        }
        //????????????
        String channel = request.getHeader("channel");
        String platform = VersionUtil.getPlatformStr(request);
        List<Ads> adsList = this.adsService.getAds(pageNo, pageSize, isCheckVer, channel, platform, VersionUtil.getVerionInfo(request), user);
        response.setAds(adsList);


        Page<GroupRelevance> groupRelevancelist;
        Page<Story> storys;
        List<Story> storyList;
        DisplayGroup group;
        List<DisplayGroup> groups = new ArrayList<>();
        Story story;
        for (Long groupId : groupIds) {
            group = displayGroupService.getGroupById(groupId);
            group.setIsSubscribe(isSubscribe);
            group.setStorys(new ArrayList());


            //?????????4????????????????????????????????????
            if (group.getTargetType() == 4) {
                //????????????//??????????????????group_rel
                groupRelevancelist = this.groupRelevanceService.getGroupRevelanceByGroupId(group.getId(), pageNo, 4);
                if (groupRelevancelist == null) {
                    continue;
                }
                for (GroupRelevance relevance : groupRelevancelist) {
                    story = storyService.getStory(relevance.getStoryId());
                    storyService.setStoryIsBuy(user, story);
                    storyService.setStoryAppFile(story);
                    group.getStorys().add(story);
                }

            } else if (group.getTargetType() == 2) {
                //????????????,?????????????????????????????????
                if (magazines.size() > 0) {
                    pageSize = 8;
                    //???????????????????????????????????????????????????8???????????????????????????6
//                    if(user!=null && user.getSvip() == 1){
//                        isSubscribe = 0;//??????????????????
//                    }
                    if (group.getId() == 4L) {
                        group.setPadIcon("2017/08/30/subscription.png");
                        if (isSubscribe == 0) {
                            pageSize = 6;
                            storys = storyService.getStorysByIsNow(isNow, userId, pageNo, pageSize, request);
                            group.setStorys(storys.getContent());
                        } else {
                            storyList = storyService.getUserBuyStorys(userId, 2, 0, 6, request);
                            group.setStorys(storyList);
                        }
                    }
                }

            } else if (group.getTargetType() == 1 || group.getTargetType() == 3) {
                //????????????,???story???categoryId
                if (group.getId() == 3L) {
                    group.setPadIcon("2017/08/30/free.png");
                    //???????????????3???
                    pageSize = 3;
                }
                storys = storyService.getStorysByCategoryId(group.getId().intValue(), userId, pageNo, pageSize, request);
                group.setStorys(storys.getContent());
            }
            groups.add(group);
        }

        List<CustomGroup> cgroups = new ArrayList<>();
        CustomGroup serialStoryGroup;
        for (DisplayGroup item : groups) {
            serialStoryGroup = new CustomGroup(item);
            serialStoryGroup.setStorys(item.getStorys());
            serialStoryGroup.setIsSubscribe(item.getIsSubscribe());
            cgroups.add(serialStoryGroup);

        }

//        List<DisplayGroup> groups = new ArrayList<>();

        //???????????????
        Page<SerialStory> serialStoryPage = serialStoryService.getCommonSerialStoryPage(userId, SerialStoryStyle.INDEX_SERIAL, 0, 2);
//        response.setSerialStories(serialStoryPage.getContent());
        serialStoryGroup = new CustomGroup();
        serialStoryGroup.setId(1000L);
        serialStoryGroup.setName("????????????");
        serialStoryGroup.setContent("????????????????????????");
        serialStoryGroup.setIcon("");
        serialStoryGroup.setPadIcon("");
        serialStoryGroup.setIsSubscribe(isSubscribe);
        serialStoryGroup.setSerialStories(serialStoryPage.getContent());
        if (isCheckVer.equals(1)) {
            cgroups.add(1, serialStoryGroup);//ios??????????????????????????????
        } else {
            cgroups.add(2, serialStoryGroup);
        }

        response.setGroups(cgroups);

        //??????????????????
        List<DisplayGroup> displayGroups = storyService.getStoryGroups();
        response.setGroupList(displayGroups);

        return response;
    }

    @RequestMapping(value = "/getUserSerialStorys", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "?????????????????????", notes = "")
    GetUserSerialStorysResponse getUserSerialStorys(@ApiParam(value = "??????token") @RequestParam(required = false) String token,
                                                    @ApiParam(value = "??????") @RequestParam Integer pageNo,
                                                    @ApiParam(value = "??????") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {

        Long userId;
        try {
            userId = userService.checkAndGetCurrentUserId(token);
        } catch (ApiNotTokenException e) {
            e.printStackTrace();
            userId = -1L;
        }
//        pageNo = pageNo - 1;

        GetUserSerialStorysResponse response = serialStoryService.getUserSerialStorys(userId, pageNo, pageSize);

        return response;
    }

    @RequestMapping(value = "/getSerialStoryDetail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "?????????????????????", notes = "")
    GetSerialStoryDetailResponse getSerialStoryDetail(@ApiParam(value = "??????token") @RequestParam(required = false) String token,
                                                      @ApiParam(value = "?????????id") @RequestParam Long serialStoryId
    ) throws ApiNotTokenException {

        Long userId;
        try {
            userId = userService.checkAndGetCurrentUserId(token);
        } catch (ApiNotTokenException e) {
            e.printStackTrace();
            userId = -1L;
        }

        GetSerialStoryDetailResponse response = new GetSerialStoryDetailResponse();
        SerialStory serialStory = serialStoryService.getSerialStoryDetail(userId, serialStoryId);
        List<SerialBanner> serialBanners=serialBannerService.findBySerialId(serialStoryId.intValue());
        if(serialBanners!=null){
            serialStory.setBanners(serialBanners);
        }

        response.setSerialStory(serialStory);
        return response;
    }


    @RequestMapping(value = "/shop_serial_story_intro", method = RequestMethod.GET)
    @ApiOperation(value = "?????????????????????", notes = "")
    void getSerialStoryIntroById(
            @ApiParam(value = "?????????ID") @RequestParam Long serialStoryId,
            HttpServletResponse httpServletResponse
    ) throws ApiException {

        String s = "";
        s.toCharArray();
        SerialStory serialStory = serialStoryService.getSerialStoryDetail(0L, serialStoryId);
        try {
            httpServletResponse.setContentType("text/html");
            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.getWriter().print("<!DOCTYPE html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
            httpServletResponse.getWriter().print("<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1.0, maximum-scale=1,user-scalable=yes\" />");
            httpServletResponse.getWriter().print("<meta name=\"format-detection\" content=\"telephone=no\" />");
            httpServletResponse.getWriter().print("<style>body{margin:24px 0px;}p{width:100%;display:block;margin:auto;font-size: 14px;color: #666666;margin: 0px auto;line-height: 28px;}img{display:block;margin-bottom:0px;max-width:100%;}strong{color:#88a4ff;}</style></head><body>");
            httpServletResponse.getWriter().print(serialStory.getIntro());
            httpServletResponse.getWriter().print("</body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/getIndexGroupMore", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "??????????????????", notes = "")
    GetIndexGroupMoreResponse getIndexGroupMore(@ApiParam(value = "??????(1,2,3,4)") @RequestParam Integer targetType,
                                                @ApiParam(value = "?????????1,3???4???groupId???2?????????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") Long targetValue,
                                                @ApiParam(value = "??????token", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String token,
                                                @ApiParam(value = "??????") @RequestParam Integer pageNo,
                                                @ApiParam(value = "??????") @RequestParam Integer pageSize
//                                                        @ApiParam(value = "?????????")@RequestParam String ver,
    ) throws ApiNotTokenException {
        Long userId;
        try {
            userId = userService.checkAndGetCurrentUserId(token);
        } catch (ApiNotTokenException e) {
            e.printStackTrace();
            userId = -1L;
        }

        User user = userService.getUser(userId);

        GetIndexGroupMoreResponse getIndexGroupMoreResponse = new GetIndexGroupMoreResponse();
//        pageNo = pageNo - 1;

        if (targetType == 1 || targetType == 3) {

            Page<Story> storys = storyService.getStorysByCategoryId(targetValue.intValue(), userId, pageNo, pageSize, request);
            getIndexGroupMoreResponse.setStorys(storys.getContent());
            getIndexGroupMoreResponse.setJpaPage(storys);

        } else if (targetType == 1 || targetType == 3 || targetType == 4) {
            //targetValue?????????groupId?????????????????????????????????
            Page<GroupRelevance> groupRelevancelist = this.groupRelevanceService.getGroupRevelanceByGroupId(targetValue, pageNo, pageSize);
            Story story;
            for (GroupRelevance relevance : groupRelevancelist) {
                story = storyService.getStory(relevance.getStoryId());
                storyService.setStoryIsBuy(user, story);
                storyService.setStoryAppFile(story);
                getIndexGroupMoreResponse.getStorys().add(story);
            }
            getIndexGroupMoreResponse.setJpaPage(groupRelevancelist);


        } else if (targetType == 2) {
            //targetValue ???????????????id????????????????????????????????????
//            Page<Story> pageStory=this.storyService.getStorysBySerialStoryId(targetValue,userId,pageNo,pageSize);
//            getIndexGroupMoreResponse.setStorys(pageStory.getContent());
//            getIndexGroupMoreResponse.setJpaPage(pageStory);
            Integer isNow = 1;
//            Magazine magazine = magazineService.getMagazineByIsNow(isNow);
            //??????magazineid????????????
            Page<Story> storys = storyService.getStorysByIsNow(isNow, userId, pageNo, pageSize, request);
            getIndexGroupMoreResponse.setStorys(storys.getContent());
            getIndexGroupMoreResponse.setJpaPage(storys);
        }

        if(targetValue!=null&&getIndexGroupMoreResponse.getStorys()!=null){
            DisplayGroup displayGroup= displayGroupService.findOne(targetValue.intValue());
            for(Story story:getIndexGroupMoreResponse.getStorys()){
                story.setRefererGroupId(targetValue.intValue());
            }
        }

        return getIndexGroupMoreResponse;

    }

    @RequestMapping(value = "/getStoryGroups", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "??????????????????", notes = "")
    GetStoryGroupsResponse getStoryGroups(
    ) throws ApiNotTokenException {

        GetStoryGroupsResponse response = new GetStoryGroupsResponse();

        //??????????????????
        List<DisplayGroup> groups = storyService.getStoryGroups();

        response.setGroups(groups);

        return response;

    }

    @RequestMapping(value = "/new_story_group", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "????????????????????????2.0.0 ???????????????", notes = "")
    GetStoryGroupsResponse getNewStoryGroup(
    ) throws ApiNotTokenException {
        GetStoryGroupsResponse response = new GetStoryGroupsResponse();
        List<DisplayGroup> groups = storyService.getNewStoryGroups();
        DisplayGroup displayGroup = new DisplayGroup();
        displayGroup.setName("??????");
        displayGroup.setContent("??????");
        displayGroup.setId(0L);
        List<DisplayGroup> groupList = new ArrayList<>(groups);
        groupList.add(0, displayGroup);
        response.setGroups(groupList);
        return response;
    }

    @RequestMapping(value = "/getStoryBooks", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "???????????????app????????????", notes = "")
    GetStoryBooksResponse getStoryBooks(
            @ApiParam(value = "??????") @RequestParam Integer pageNo,
            @ApiParam(value = "??????") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {
        Integer targetType = 11;//?????????app????????????
//        Long targetValue;//??????id

        GetStoryBooksResponse getStoryBooksResponse = new GetStoryBooksResponse();
//        pageNo = pageNo - 1;
        //??????????????????????????????
        //targetValue?????????groupId?????????????????????????????????
        //??????????????????
        List<DisplayGroup> groupList = this.displayGroupService.getGroupsByTargetType(targetType);
        Page<GroupRelevance> groupRelevancelist;
        for (DisplayGroup group : groupList) {
            groupRelevancelist = this.groupRelevanceService.getRevelanceByGroupId(group.getId(), pageNo, pageSize);
            getStoryBooksResponse.setStorys(groupRelevancelist.getContent());
            getStoryBooksResponse.setJpaPage(groupRelevancelist);
        }

        return getStoryBooksResponse;

    }

    @ApiOperation(value = "????????????????????????????????????", notes = "")
    @RequestMapping(value = "/getUserBuyStorys", method = RequestMethod.GET)
    @ResponseBody
    GetUserBuyStorysResponse getUserBuyStorys(@ApiParam(value = "??????token") @RequestParam String token,
                                              @RequestParam int pageNo, @RequestParam int pageSize) throws ApiNotTokenException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
//        pageNo = pageNo - 1;//?????????0????????????????????????1
        Integer pageSize2 = 3;
        Integer pageSize3 = 10;//??????????????????????????????
        GetUserBuyStorysResponse getUserBuyStorysResponse = new GetUserBuyStorysResponse();
        //????????????????????????
        Integer type = 1;
        Page<BuyStoryRecord> pageBuyStoryRecords = this.storyService.getUserBuyStoryRecords(userId, type, pageNo, pageSize2);//??????????????????????????????
        getUserBuyStorysResponse.setBuyStoryRecords(pageBuyStoryRecords.getContent());

        //????????????????????????????????????????????????????????????
        Page<BuyMagazineRecord> buyMagazineRecords = magazineService.getBuyMagazineRecordByUserId(userId, pageNo, pageSize);

        Magazine magazine;
        Page<Story> storys;
        for (BuyMagazineRecord buyMagazineRecord : buyMagazineRecords) {
            magazine = this.magazineService.getMagazineById(buyMagazineRecord.getMagazineId());
            if (magazine == null) {
                continue;
            }
            storys = this.storyService.getStorysByMagazineId(magazine.getId(), userId, pageNo, pageSize3);
            magazine.setStorys(storys.getContent());
//            getStoryBooksResponse.setStorys(groupRelevancelist.getContent());
//            getStoryBooksResponse.setJpaPage(groupRelevancelist);
            getUserBuyStorysResponse.getMagazines().add(magazine);
        }


        getUserBuyStorysResponse.setJpaPage(buyMagazineRecords);

        return getUserBuyStorysResponse;
    }

    @ApiOperation(value = "???????????????????????????", notes = "")
    @RequestMapping(value = "/getUserSubscribeStorys", method = RequestMethod.GET)
    @ResponseBody
    GetUserSubscribeStorysResponse getUserSubscribeStorys(@ApiParam(value = "??????token") @RequestParam String token,
                                                          @RequestParam int pageNo, @RequestParam int pageSize) throws ApiNotTokenException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
//        pageNo = pageNo - 1;//?????????0????????????????????????1
        Integer pageSize2 = 10;//??????????????????????????????
        GetUserSubscribeStorysResponse getUserSubscribeStorysResponse = new GetUserSubscribeStorysResponse();

        //????????????????????????????????????????????????????????????
        Page<BuyMagazineRecord> buyMagazineRecords = magazineService.getBuyMagazineRecordByUserId(userId, pageNo, pageSize);

        Magazine magazine;
        Page<Story> storys;
        for (BuyMagazineRecord buyMagazineRecord : buyMagazineRecords) {
            magazine = this.magazineService.getMagazineById(buyMagazineRecord.getMagazineId());

            if (magazine != null) {

                if (magazine.getPublishTime() != null) {
                    //???????????????
                    DateFormat format = new SimpleDateFormat("M???d???");
                    String dateString = format.format(magazine.getPublishTime());
                    magazine.setName(dateString);
                }

                storys = this.storyService.getStorysByMagazineId(magazine.getId(), userId, 0, pageSize2);
                magazine.setStorys(storys.getContent());
                getUserSubscribeStorysResponse.getMagazines().add(magazine);
            }

        }


        getUserSubscribeStorysResponse.setJpaPage(buyMagazineRecords);

        return getUserSubscribeStorysResponse;
    }

    @ApiOperation(value = "????????????????????????", notes = "")
    @RequestMapping(value = "/getUserSubscribeInfo", method = RequestMethod.GET)
    @ResponseBody
    GetUserSubscribeInfoResponse getUserSubscribeInfo(@ApiParam(value = "??????token") @RequestParam String token
    ) throws ApiNotTokenException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);

        GetUserSubscribeInfoResponse getUserSubscribeInfoResponse = new GetUserSubscribeInfoResponse();
        //????????????????????????????????????????????????????????????
        //????????????????????????????????????????????????????????????
        SubscriptionRecord subscriptionRecord = magazineService.getUserSubscribeInfo(userId);
        getUserSubscribeInfoResponse.setStartTime(subscriptionRecord.getStartTime());
        getUserSubscribeInfoResponse.setEndTime(subscriptionRecord.getEndTime());
        return getUserSubscribeInfoResponse;
    }

    @ApiOperation(value = "????????????????????????????????????", notes = "")
    @RequestMapping(value = "/getUserLastSubscribeInfo", method = RequestMethod.GET)
    @ResponseBody
    GetUserLastSubscribeInfoResponse getUserLastSubscribeInfo(@ApiParam(value = "??????token") @RequestParam String token
    ) throws ApiNotTokenException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
        GetUserLastSubscribeInfoResponse response = new GetUserLastSubscribeInfoResponse();
        SubscriptionRecord subscriptionRecord = magazineService.getUserLastSubscribeInfo(userId);
        response.setStartTime(subscriptionRecord.getStartTime());
        response.setEndTime(subscriptionRecord.getEndTime());
        response.setIntro(subscriptionRecord.getIntro());
        return response;
    }

    @ApiOperation(value = "????????????????????????", notes = "")
    @RequestMapping(value = "/getUserSubscribeRecords", method = RequestMethod.GET)
    @ResponseBody
    GetUserSubscribeRecordsResponse getUserSubscribeRecords(@ApiParam(value = "??????token") @RequestParam String token
    ) throws ApiNotTokenException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);

        GetUserSubscribeRecordsResponse response = magazineService.getUserSubscribeRecords(userId);

        return response;
    }

    @ApiOperation(value = "???????????????????????????????????????????????????1?????????2?????????????????????0???", notes = "????????????")
    @RequestMapping(value = "/getMoreUserBuyStorys", method = RequestMethod.GET)
    @ResponseBody
    @Deprecated
    GetMoreUserBuyStorysResponse getMoreUserBuyStorys(@ApiParam(value = "??????token") @RequestParam String token,
                                                      @ApiParam(value = "1??????2??????0?????????") @RequestParam Integer type,
                                                      @RequestParam int pageNo,
                                                      @RequestParam int pageSize) throws ApiNotTokenException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
//        pageNo = pageNo - 1;//?????????0????????????????????????1
        GetMoreUserBuyStorysResponse getMoreUserBuyStorysResponse = new GetMoreUserBuyStorysResponse();
        Page<BuyStoryRecord> pageBuyStoryRecords = this.storyService.getUserBuyStoryRecords(userId, type, pageNo, pageSize);
        getMoreUserBuyStorysResponse.setBuyStoryRecords(pageBuyStoryRecords.getContent());
        getMoreUserBuyStorysResponse.setJpaPage(pageBuyStoryRecords);

        return getMoreUserBuyStorysResponse;
    }

    @ApiOperation(value = "???????????????????????????", notes = "")
    @RequestMapping(value = "/getUserStorys", method = RequestMethod.GET)
    @ResponseBody
    GetUserStoryListResponse getUserStorys(@ApiParam(value = "??????token") @RequestParam String token,
                                           @ApiParam(value = "1??????2??????") @RequestParam Integer type,
                                           @ApiParam(value = "??????id???0???????????????") @RequestParam Integer groupId,
                                           @RequestParam int pageNo,
                                           @RequestParam int pageSize) throws ApiNotTokenException {
        Long userId;
        try {
            userId = userService.checkAndGetCurrentUserId(token);
        } catch (ApiNotTokenException e) {
            e.printStackTrace();
            userId = -1L;
        }
//        pageNo = pageNo - 1;//?????????0????????????????????????1

        String isOpenNewList = MyEnv.env.getProperty("get.story.subscription.list");

        if (type.equals(2) && (groupId.equals(0) || groupId.equals(8)) && isOpenNewList.equals("true")) {
            return buyStorySubscriptionRecordService.getUserStoryList(userId, groupId, pageNo, pageSize);
        } else {
            GetUserStorysResponse response = new GetUserStorysResponse();
            Page<BuyStoryRecord> pageBuyStoryRecords = this.storyService.getUserStorys(userId, type, groupId, pageNo, pageSize);
            List<BuyStoryRecord> buyStoryRecordList = pageBuyStoryRecords.getContent();
            if (groupId.equals(8)) {
                for (BuyStoryRecord item : buyStoryRecordList) {
                    item.setStory(item.getStory().getStory());
                }
            }
            response.setBuyStoryRecords(buyStoryRecordList);
            response.setJpaPage(pageBuyStoryRecords);
            return response;
        }
    }

    @ApiOperation(value = "???????????????????????????", notes = "")
    @RequestMapping(value = "/user_has_buy_storys", method = RequestMethod.GET)
    @ResponseBody
    GetUserStorysResponse getUserHasBuyStorys(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "??????id???0???????????? 1???????????? 2???????????? 3???????????? 4???????????????") @RequestParam Integer groupId,
            @ApiParam(value = "pageNo") @RequestParam Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {
        Long userId = userService.checkAndGetCurrentUserId(token);
//        pageNo = pageNo - 1; //?????????0????????????????????????1
        GetUserStorysResponse response = new GetUserStorysResponse();
        if (!groupId.equals(0)) {
            StoryGroupRelateStyle storyGroupRelateStyle = StoryGroupRelateStyle.getByGroupId(groupId);
            groupId = storyGroupRelateStyle.getCategoryNewId();
        }
        Page<BuyStoryRecord> pageBuyStoryRecords = storyService.getUserHasBuyStorysByNewType(userId, groupId, pageNo, pageSize);
        List<BuyStoryRecord> buyStoryRecordList = pageBuyStoryRecords.getContent();
        response.setBuyStoryRecords(buyStoryRecordList);
        response.setJpaPage(pageBuyStoryRecords);
        return response;
    }

    @ApiOperation(value = "??????????????????", notes = "1.1.1????????????")
    @RequestMapping(value = "/getMoreStorys", method = RequestMethod.GET)
    @ResponseBody
    @Deprecated
    GetMoreStorysResponse getMoreStorys(
            @ApiParam(value = "??????token", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String token,
            @ApiParam(value = "??????id") @RequestParam Long storyId
    ) throws ApiNotTokenException, ParseException {

        Long userId;
        if (token == null || token.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }
        GetMoreStorysResponse getMoreStorysResponse = new GetMoreStorysResponse();

        int pageNo = 0;
        int pageSize = 3;

        //????????????
        Page<Story> storys = this.storyService.getMoreStorys(userId, storyId, pageNo, pageSize);
        getMoreStorysResponse.setStorys(storys.getContent());
        getMoreStorysResponse.setJpaPage(storys);

        return getMoreStorysResponse;

    }


    @ApiOperation(value = "??????????????????", notes = "????????????")
    @RequestMapping(value = "/getRecommendStorys", method = RequestMethod.GET)
    @ResponseBody
    GetRecommendStorysResponse getRecommendStorys(
            @ApiParam(value = "??????token", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String token,
            @ApiParam(value = "??????id") @RequestParam Long storyId
    ) throws ApiNotTokenException {

        Long userId;
        if (token == null || token.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }
        GetRecommendStorysResponse getRecommendStorysResponse = new GetRecommendStorysResponse();

        int pageNo = 0;
        int pageSize = 3;

        //????????????
        Page<StoryRecommend> recommendStorysPage = this.storyService.getRecommendStorys(userId, storyId, pageNo, pageSize, request);
        if (recommendStorysPage.getContent().size() > 0) {
            //??????????????????
            for (StoryRecommend item : recommendStorysPage.getContent()) {
                getRecommendStorysResponse.getStorys().add(item.getStory());
            }
            getRecommendStorysResponse.setJpaPage(recommendStorysPage);
        } else {
            //??????????????????????????????????????????
//            pageSize=3;
//            Page<Story> storyPage = storyService.getNowStorys(userId,storyId,pageNo,pageSize);
//            getRecommendStorysResponse.setStorys(storyPage.getContent());
//            getRecommendStorysResponse.setJpaPage(storyPage);

            //????????????????????????????????????????????????
            pageSize = 3;
            int storyCount = storyService.getStorysCount(storyId);//??????????????????????????????????????????
            int pageCount = storyCount / 3;
            int p = (int) (Math.random() * pageCount) - 1;
            if (p < 0) {
                p = 0;
            }
            Page<Story> storyPage = storyService.getOtherStorys(userId, storyId, p, pageSize, request);
            getRecommendStorysResponse.setStorys(storyPage.getContent());
            getRecommendStorysResponse.setJpaPage(storyPage);
        }
        return getRecommendStorysResponse;

    }

    @ApiOperation(value = "??????????????????????????????", notes = "")
    @RequestMapping(value = "/getStorysByPublishDate", method = RequestMethod.GET)
    @ResponseBody
    @Deprecated
    GetMoreStorysResponse getStorysByPublishDate(
            String beginDate,
            String endDate

    ) throws ApiException {

        GetMoreStorysResponse getMoreStorysResponse = new GetMoreStorysResponse();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDat;
        Date endDat;
        try {
            beginDat = sd.parse(beginDate);
            endDat = sd.parse(endDate);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiInputFormatException("??????????????????");
        }

        int pageSize = 32;
        //????????????
        Page<Story> storys = this.storyService.getStorysByDateRange(beginDat, endDat, pageSize);


        getMoreStorysResponse.setStorys(storys.getContent());
        getMoreStorysResponse.setJpaPage(storys);

        return getMoreStorysResponse;

    }

    @ApiOperation(value = "????????????????????????", notes = "")
    @RequestMapping(value = "/getSubscriptionSchedules", method = RequestMethod.GET)
    @ResponseBody
    GetSubscriptionSchedulesResponse getSubscriptionSchedules(
            @ApiParam(value = "?????????Y-m???") @RequestParam String month
    ) throws ApiException {

        GetSubscriptionSchedulesResponse response = new GetSubscriptionSchedulesResponse();
        List<SubscriptionSchedule> subscriptionSchedules = magazineService.getSubscriptionSchedules(month);
        response.setSubscriptionSchedules(subscriptionSchedules);
        return response;

    }

    @ApiOperation(value = "??????????????????????????????")
    @RequestMapping(value = "/new_group_story_list", method = RequestMethod.GET)
    @ResponseBody
    GetNewGroupStoryListResponse getNewGroupStoryList(
            @ApiParam(value = "??????token") @RequestParam(required = false, defaultValue = "") String token,
            @ApiParam(value = "groupId") @RequestParam Integer groupId,
            @ApiParam(value = "pageNo") @RequestParam Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam Integer pageSize
    ) {
        Long userId;
        if (token == null || token.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }
        User user = userService.getUser(userId);
        Page<Story> stories = groupRelevanceService.getNewGroupStoryList(user, groupId, pageNo, pageSize);
        ;

        if(groupId!=null){
            DisplayGroup group=displayGroupService.findOne(groupId);
            for(Story story:stories.getContent()){
                story.setRefererGroupId(groupId);
            }
        }
        GetNewGroupStoryListResponse getNewGroupStoryListResponse = new GetNewGroupStoryListResponse();
        getNewGroupStoryListResponse.setStorys(stories.getContent());
        getNewGroupStoryListResponse.setJpaPage(stories);
        return getNewGroupStoryListResponse;
    }

    @ApiOperation(value = "????????????????????????")
    @RequestMapping(value = "/getRadioList", method = RequestMethod.GET)
    @ResponseBody
    @Cacheable(cacheNames = "getRadioList_v3", key = "'getRadioList_v3'+#p0")
    public GetRadioListResponse getRadioList(
            @RequestHeader(value = "ssToken", required = false) String ssToken
    ) {

        GetRadioListResponse response = new GetRadioListResponse();
        Long userId;
        if (ssToken == null || ssToken.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(ssToken);
        }
        List<Story> radioList = storyService.getRadioList(userId);
        response.setName("????????????");
        response.setContent("?????????????????????????????????");
        response.setStorys(radioList);
        return response;
    }


    @ApiOperation(value = "????????????????????????")
    @RequestMapping(value = "/getRadioListCategory", method = RequestMethod.GET)
    @ResponseBody
    public GetRadioListCategoryResponse getRadioListCategory(
            @RequestHeader(value = "ssToken", required = false) String ssToken
    ) {

        GetRadioListCategoryResponse response = new GetRadioListCategoryResponse();
        List<SerialCategory> list = new ArrayList<>();
        List<SerialStory> serialStories = serialStoryService.getSerialStoryBySerialType(SerialStoryStyle.AUDIO_SERIAL);
        for (SerialStory s : serialStories) {
            SerialCategory serialCategory = new SerialCategory();
            serialCategory.setSerialId(s.getId().intValue());
            serialCategory.setName(s.getName());
            list.add(serialCategory);
        }
        response.setSerialCategories(list);
        return response;

    }

    @ApiOperation(value = "????????????????????????2.11.0")
    @RequestMapping(value = "/getRadioList211", method = RequestMethod.GET)
    @ResponseBody
    public GetRadioListResponse getRadioList211(
            @RequestHeader(value = "ssToken", required = false) String ssToken,
            @ApiParam(value = "serialId") @RequestParam(required = false) Integer serialId
    ) {

        GetRadioListResponse response = new GetRadioListResponse();
        Long userId;
        if (ssToken == null || ssToken.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(ssToken);
        }
        if (serialId == null || serialId < 71 || serialId > 73) {
            serialId = 71;
        }
        Page<Story> pageStory = storyService.getStorysBySerialId(serialId.longValue(), userId, 0, 100);
        response.setSerialId(serialId);
        response.setName("????????????");
        response.setContent("???????????????????????????");
        response.setStorys(pageStory.getContent());
        return response;
    }

    @ApiOperation(value = "????????????????????????")
    @RequestMapping(value = "/getRadioDetail", method = RequestMethod.GET)
    @ResponseBody
    public GetRadioDetailResponse getRadioDetail(
            @ApiParam(value = "storyId") @RequestParam Integer storyId
    ) {

        GetRadioDetailResponse response = new GetRadioDetailResponse();

        StoryAudioContent storyAudioContent = storyService.getStoryAudioContenById(storyId);
        if (storyAudioContent != null) {
            response.setAudioContent(storyAudioContent.getContent());
        }
        return response;
    }


    @ApiOperation(value = "?????????????????????????????????", notes = "??????????????????")
    @RequestMapping(value = "/user_has_buy_story_lesson", method = RequestMethod.GET)
    @ResponseBody
    @Deprecated
    GetUserHasBuyStoryAndLessonResponse user_has_buy_story_lesson(
            @ApiParam(value = "??????token") @RequestParam(required = false, defaultValue = "") String token,
            @ApiParam(value = "groupId") @RequestParam Integer groupId
    ) {
        Long userId;
        if (token == null || token.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }
        GetUserHasBuyStoryAndLessonResponse response = new GetUserHasBuyStoryAndLessonResponse();
        response.setStoryList(groupRelevanceService.getUserStoryByGroupId(userId, groupId));
        response.setLessonList(lessonService.getLessonListAndBuyNum(userId));
        return response;
    }

}
