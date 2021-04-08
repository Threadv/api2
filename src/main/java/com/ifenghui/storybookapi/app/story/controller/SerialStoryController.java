package com.ifenghui.storybookapi.app.story.controller;


import com.ifenghui.storybookapi.adminapi.controlleradmin.resp.IpBrand;
import com.ifenghui.storybookapi.aop.ApiConfigAop;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.story.entity.*;
import com.ifenghui.storybookapi.app.story.response.*;
import com.ifenghui.storybookapi.app.story.service.*;
import com.ifenghui.storybookapi.app.transaction.dao.BuyStoryRecordDao;
import com.ifenghui.storybookapi.app.story.dao.SerialStoryDao;

import com.ifenghui.storybookapi.app.transaction.entity.BuyStoryRecord;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.style.IpBrandStyle;
import com.ifenghui.storybookapi.style.SerialStoryStyle;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@EnableAutoConfiguration
//@EnableAutoConfiguration
//@SpringBootApplication
//@ConfigurationProperties(prefix = "spring")
@Api(value = "StorySerialController", description = "故事集管理api")
@RequestMapping("/api/storySerial")
public class SerialStoryController {

    @Autowired
    StoryService storyService;

    @Autowired
    SerialStoryService serialStoryService;

    @Autowired
    UserService userService;

    @Autowired
    BuyStoryRecordDao buyStoryRecordDao;

    @Autowired
    SerialStoryDao serialStoryDao;

    @Autowired
    IpLabelStoryService ipLabelStoryService;

//    @Autowired
//    UserDao userDao;

    @Autowired
    VipSerialGetRecordService vipSerialGetRecordService;

    @Autowired
    IpLabelService ipLabelService;
    @Autowired
    HttpServletRequest request;

    @Autowired
    StoryController storyController;

    @ApiOperation(value = "获得系列故事集列表和用户已购买故事列表")
    @RequestMapping(value = "/serial_story_list_and_user_has_buy_storys",method = RequestMethod.GET)
    @ResponseBody
    GetUserSerialStorysAndHasBuyStoryResponse getCommentSerialStoryAndUserHasBuyPages(
            @ApiParam(value = "用户token",defaultValue = "",required = false) @RequestParam(required = false,defaultValue = "") String  token,
            @ApiParam(value = "pageNo") @RequestParam Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {

        GetUserSerialStorysAndHasBuyStoryResponse response = new GetUserSerialStorysAndHasBuyStoryResponse();
        Long userId = userService.checkAndGetCurrentUserId(token);

        //第一页加载系列古事
        if(pageNo ==0){
            GetUserSerialStorysResponse response2 = serialStoryService.getUserSerialStorys(userId,pageNo,pageSize);
            List<SerialStory> serialStoryList = response2.getSerialStoryList();
            response.setSerialStoryList(serialStoryList);
        }


        Page<BuyStoryRecord> pageBuyStoryRecords = storyService.getUserHasBuyStorysByNewType(userId,0,pageNo,pageSize);
        List<BuyStoryRecord> buyStoryRecordList = pageBuyStoryRecords.getContent();

        response.setJpaPage(pageBuyStoryRecords);
        response.setBuyStoryRecords(buyStoryRecordList);
        return response;
    }

    @ApiOperation(value = "获得系列故事集列表")
    @RequestMapping(value = "/serial_story_list",method = RequestMethod.GET)
    @ResponseBody
    GetUserSerialStorysResponse getCommentSerialStoryPage(
        @ApiParam(value = "用户token",defaultValue = "",required = false) @RequestParam(required = false,defaultValue = "") String  token,
        @ApiParam(value = "pageNo") @RequestParam Integer pageNo,
        @ApiParam(value = "pageSize") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {

        Long userId;
        if(token == null || token.length() <= 0){
            userId = 0L;
        }else{
            userId = userService.checkAndGetCurrentUserId(token);
        }
        //默认从0开始，所以需要减1

        GetUserSerialStorysResponse response = serialStoryService.getUserSerialStorys(userId,pageNo,pageSize);
//        BuyStoryRecord buyStoryRecord = new BuyStoryRecord();
//        buyStoryRecord.setUserId(userId);
//        buyStoryRecord.setType(2);
        List<BuyStoryRecord> buyStoryRecordList = buyStoryRecordDao.getBuyStoryRecordsByUserIdAndType(userId,2);
        List<SerialStory> serialStoryList = response.getSerialStoryList();
        if(buyStoryRecordList.size() > 0){
            SerialStory serialStory = serialStoryDao.findOne(3L);
            serialStory.setStoryCount(buyStoryRecordList.size());
            serialStoryList.add(0,serialStory);
        }
        response.setSerialStoryList(serialStoryList);
        return response;
    }

    @ApiOperation(value = "获得益智故事集列表")
    @RequestMapping(value = "/smart_serial_story_list",method = RequestMethod.GET)
    @ResponseBody
    public GetSerialStoryPageResponse getSmartGameSerialStoryPage(
            @ApiParam(value = "用户token",defaultValue = "",required = false) @RequestParam(required = false,defaultValue = "") String  token,
            @ApiParam(value = "系列故事类型") @RequestParam(required = false, defaultValue = "3") Integer serialType,
            @ApiParam(value = "pageNo") @RequestParam Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {

        GetSerialStoryPageResponse response = new GetSerialStoryPageResponse();
        Long userId;
        if(token == null || token.length() <= 0){
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }
        //默认从0开始，所以需要减1
        if(serialType == null || serialType.toString().equals("")) {
            serialType = 3;
        }
        SerialStoryStyle serialStoryStyle = SerialStoryStyle.getById(serialType);
        Page<SerialStory> serialStoryPage = serialStoryService.getCommonSerialStoryPage(userId, serialStoryStyle, pageNo,pageSize);
        List<SerialStory> serialStoryList = serialStoryPage.getContent();
        User user = userService.getUser(userId);
        for(SerialStory item : serialStoryList){
            serialStoryService.setSerialStoryIsBuy(user,item);
        }
        response.setSerialStoryList(serialStoryList);
        return response;
    }

    @ApiOperation(value = "获得系列故事集和故事集详情")
    @RequestMapping(value = "/serial_story_and_story_detail", method = RequestMethod.GET)
    @ResponseBody
    GetSerialStoryAndStoryListReponse getSerialStoryAndStoryList(
            @ApiParam(value = "用户token（非必传）",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") String  token,
            @ApiParam(value = "故事合集id")@RequestParam Long serialStoryId
    ) {
        GetSerialStoryAndStoryListReponse response = new GetSerialStoryAndStoryListReponse();
        Long userId;
        if(token == null || token.length() <= 0){
            userId = 0L;
        }else{
            userId = userService.checkAndGetCurrentUserId(token);
        }
        SerialStory serialStory = serialStoryService.getSerialStoryDetail(userId,serialStoryId);
        List<Story> storyList = storyService.getStorySerialDetail(userId,serialStoryId);
        response.setStoryList(storyList);
        response.setSerialStory(serialStory);
        return response;
    }

    @ApiOperation(value = "获得系列故事集和故事详情根据分组分类")
    @RequestMapping(value = "/serial_story_group_story_detail", method = RequestMethod.GET)
    @ResponseBody
    GetSerialStoryAndStoryListByGroupReponse getSerialStoryAndStoryListByGroup(
            @ApiParam(value = "用户token（非必传）",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") String  token,
            @ApiParam(value = "故事合集id")@RequestParam Long serialStoryId
    ) {
        GetSerialStoryAndStoryListByGroupReponse response = new GetSerialStoryAndStoryListByGroupReponse();
        Long userId;
        if(token == null || token.length() <= 0){
            userId = 0L;
        }else{
            userId = userService.checkAndGetCurrentUserId(token);
        }
        SerialStory serialStory = serialStoryService.getSerialStoryDetail(userId,serialStoryId);
        List<SerialStoryGroup> serialStoryGroupList = serialStoryService.getSerialStoryGroupList(userId,serialStoryId.intValue());
        response.setSerialStory(serialStory);
        response.setSerialStoryGroupList(serialStoryGroupList);
        return response;
    }


    @RequestMapping(value="/serial_story_video_intro",method = RequestMethod.GET)
    @ApiOperation(value = "家教课视频版介绍详情", notes = "")
    void getSerialStoryVideoIntroById(
            @ApiParam(value = "故事ID")@RequestParam Integer storyId,
            HttpServletResponse httpServletResponse
    ) throws ApiException {

        StoryVideoIntro storyVideoIntro = storyService.getStoryVideoIntroByStoryId(storyId);
        String intro = "";
        if(storyVideoIntro != null){
            intro = storyVideoIntro.getIntro();
        }
        try {
            httpServletResponse.setContentType("text/html");
            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.getWriter().print("<!DOCTYPE html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
            httpServletResponse.getWriter().print("<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1.0, maximum-scale=1,user-scalable=yes\" />");
            httpServletResponse.getWriter().print("<meta name=\"format-detection\" content=\"telephone=no\" />");
            httpServletResponse.getWriter().print("<style>body{margin:24px 0px;}p{width:100%;display:block;margin:auto;font-size: 14px;color: #666666;margin: 0px auto;line-height: 28px;}img{display:block;margin-bottom:0px;max-width:100%;}strong{color:#88a4ff;}</style></head><body>");
            httpServletResponse.getWriter().print(intro);
            httpServletResponse.getWriter().print("</body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value="/vip_gain_serial_story",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "会员获取系列故事", notes = "")
    BaseResponse vipGetSerialRecord(
            @RequestHeader("ssToken") String ssToken,
            @ApiParam(value = "serialStoryId")@RequestParam Integer serialStoryId
    ) {
        BaseResponse response = new BaseResponse();
        Long userId = userService.checkAndGetCurrentUserId(ssToken);
        vipSerialGetRecordService.createVipSerialGetRecord(userId.intValue(), serialStoryId);
        return response;
    }

    @RequestMapping(value = "/ip_story_list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取ip故事列表", notes = "")
    GetIpLabelStoryPageResponse getIpLabelStoryPage(
            @RequestHeader(value = "ssToken", required = false) String ssToken,
            @ApiParam(value = "pageNo") @RequestParam Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam Integer pageSize,
            @ApiParam(value = "ipId") @RequestParam Integer ipId,
            @ApiParam(value = "ipLabelParentId") @RequestParam Integer ipLabelParentId,
            @ApiParam(value = "ipLabelId") @RequestParam Integer ipLabelId
    ) {
        GetIpLabelStoryPageResponse response = new GetIpLabelStoryPageResponse();

        Long userId=(Long)request.getAttribute("loginId");
        Page<IpLabelStory> ipLabelStoryPage = ipLabelStoryService.getIpLabelStoryPage(pageNo, pageSize, ipId, ipLabelId, ipLabelParentId);
        List<Story> storyList = ipLabelStoryService.getStoryListByIpLabelStoryPage(ipLabelStoryPage, userId);
        response.setJpaPage(ipLabelStoryPage);
        response.setStoryList(storyList);
        return response;
    }


    @RequestMapping(value = "/ip_story_list_2_12", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取ip故事列表,2.12版本增加，对应brandid", notes = "")
    GetIpLabelStoryPageResponse getIpLabelStoryPage(
            @RequestHeader(value = "ssToken", required = false) String ssToken,
            @ApiParam(value = "pageNo") @RequestParam Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam Integer pageSize,
            @ApiParam(value = "ipBrandId") @RequestParam(required = false) Integer ipBrandId,
            @ApiParam(value = "ipLabelId") @RequestParam Integer ipLabelId
    ) {
        GetIpLabelStoryPageResponse response = new GetIpLabelStoryPageResponse();

        Long userId=(Long)request.getAttribute("loginId");
        Page<IpLabelStory> ipLabelStoryPage = ipLabelStoryService.getIpLabelStorysByIpLabelId(ipLabelId,new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"orderBy")));
        List<Story> storyList = ipLabelStoryService.getStoryListByIpLabelStoryPage(ipLabelStoryPage, userId);
        response.setJpaPage(ipLabelStoryPage);
        response.setStoryList(storyList);
        return response;
    }


    @RequestMapping(value = "/ip_label_list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取ip标签列表", notes = "")
    GetIpLabelListResponse getIpLabelList(
            @RequestHeader(required = false) String ssToken,
            @ApiParam(value = "ipId") @RequestParam Integer ipId
    ) {
        Long userId;
        try{
            userId= userService.checkAndGetCurrentUserId(ssToken);
        }catch (ApiNotTokenException e){
            e.printStackTrace();
            userId=-1L;
        }

        GetIpLabelListResponse response = new GetIpLabelListResponse();

        SerialStory serialStory = serialStoryService.getSerialStoryDetail(userId,ipId.longValue());

        response.setName(serialStory.getName());
        response.setBanner(serialStory.getBannerUrl());

        List<IpLabel> ipLabelList = ipLabelService.getIpLabelListByIpId(ipId);
        response.setIpLabelList(ipLabelList);
        return response;
    }

//
    @RequestMapping(value = "/ip_label_and_serial_list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取ip标签列表,2.12。增加故事集返回，标签查询方式变更", notes = "")
    GetIpLabelAndSeriesResponse getIpLabelAndSerialList(
            @ApiParam(value = "ipBrandId") @RequestParam Integer ipBrandId
    ) {
        Long userId= ApiConfigAop.getCurrentUserId(request);

        User user=userService.getUser(userId);

        GetIpLabelAndSeriesResponse response = new GetIpLabelAndSeriesResponse();

        IpBrand ipBrand=new IpBrand(IpBrandStyle.getById(ipBrandId));

        //1获取所有label
        List<IpLabel> ipLabels= ipLabelService.getIpLabelListByIpBrandId(ipBrandId);

        //2获得所有故事集
        List<SerialStory> serialStories=serialStoryService.getSerialStoryByIpBrandId(ipBrandId);
        for(SerialStory serialStory:serialStories){
            serialStoryService.setSerialStoryIsBuy(user,serialStory);
        }



        //3获得第一个label下的故事,2,21李天宇确认显示某标签下所有的
        if(ipLabels.size()>0){
            List<Story> stories=new ArrayList<>();
            List<IpLabelStory> ipLabelStories= ipLabelStoryService.getIpLabelStorysAndSetStoryByIpLabelId(ipLabels.get(0).getId());
            for(IpLabelStory ipLabelStory:ipLabelStories){
                storyService.setStoryIsBuy(user,ipLabelStory.getStory());
                stories.add(ipLabelStory.getStory());
            }
            response.setFirstLabelStories(stories);
        }


        response.setName(ipBrand.getTitle());
//        response.setBanner(ipBrand.getBannerUrl());


        response.setIpLabelList(ipLabels);
        response.setSerialStories(serialStories);
        return response;
    }
}
