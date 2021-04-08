package com.ifenghui.storybookapi.app.app.controller;

import com.ifenghui.storybookapi.api.response.base.ApiStatus;
import com.ifenghui.storybookapi.app.app.dao.NoticeDao;
import com.ifenghui.storybookapi.app.app.dao.NoticeUserDao;
import com.ifenghui.storybookapi.app.app.entity.Notice;
import com.ifenghui.storybookapi.app.app.entity.NoticeUser;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.app.response.GetNoticeResponse;
import com.ifenghui.storybookapi.app.app.response.GetNoticeUserStatusResponse;
import com.ifenghui.storybookapi.app.app.response.GetNoticesResponse;
import com.ifenghui.storybookapi.app.app.service.FeedbackService;
import com.ifenghui.storybookapi.app.app.service.NoticeService;
import com.ifenghui.storybookapi.app.app.service.NoticeUserService;
import com.ifenghui.storybookapi.app.app.service.TemplateNoticeService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.style.NoticeStyle;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/notice")
@Api(value = "系统消息接口", description = "系统消息接口")
public class NoticeController {

    @Autowired
    NoticeUserService noticeUserService;

    @Autowired
    NoticeUserDao noticeUserDao;

    @Autowired
    NoticeDao noticeDao;

    @Autowired
    NoticeService noticeService;

    @Autowired
    UserService userService;

    @Autowired
    TemplateNoticeService templateNoticeService;

    @Autowired
    FeedbackService feedbackService;

    @RequestMapping(value = "/get_status", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获得未读状态，2.14 新增 查询未读官方回复意见反馈数", notes = "获得未读状态")
    GetNoticeUserStatusResponse getStatus(
            @ApiParam(value = "用户token") @RequestParam String token
    ) {
        GetNoticeUserStatusResponse response = new GetNoticeUserStatusResponse();
        //通过token获取userId  sdsf
        Long userId;
        if (token == null || token.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }
        //查询通知
        List<NoticeUser> noticeUserList = noticeUserService.getNoticeUserList(userId.intValue());
        int count = 0;
        for (NoticeUser noticeUser : noticeUserList) {
            //判断是否有未读消息
            if (noticeUser.getIsRead() == 0) {
                count++;
            }
        }
        if (count > 0) {
            response.setReadStatus(1);
        } else {
            response.setReadStatus(0);
        }
        response.setCount(count);

        //未读官方回复意见反馈
        Integer feedbackCount = feedbackService.findCountBy(userId,0);
        response.setFeedbackCount(feedbackCount);

        return response;
    }

    @RequestMapping(value = "/get_notice_list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询用户消息列表", notes = "查询用户消息列表")
    GetNoticesResponse getNoticeList(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "pageNo") @RequestParam Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam Integer pageSize
    ) {

        GetNoticesResponse response = new GetNoticesResponse();
        //通过token获取userId  sdsf
        Long userId;
        if (token == null || token.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }

        if(userId==6917){
            response.setNoticeList(new ArrayList<>());
            return response;
        }
        //查询通知
        Page<NoticeUser> noticeUserPage = noticeUserService.findAll(userId, pageNo, pageSize);
        List<NoticeUser> noticeUserList = noticeUserPage.getContent();
        List<Notice> noticeList = new ArrayList<>();
        //通过通知查询消息
        for (NoticeUser noticeUser : noticeUserList) {
            //查找对应消息
            Notice notice = noticeService.findNotice(noticeUser.getNoticeId());
            //非空判断
            if (notice != null) {
                Notice newNotice = new Notice();
                newNotice.setId(notice.getId());
                newNotice.setCreateTime(noticeUser.getCreateTime());
                newNotice.setContent(notice.getContent());
                newNotice.setRedirectStyle(notice.getRedirectStyle());
                newNotice.setTargetValue(notice.getTargetValue());
                newNotice.setUrl(notice.getUrl());
                newNotice.setIcon(notice.getIcon());
                noticeList.add(newNotice);
            }
        }
        response.setNoticeList(noticeList);
        response.setJpaPage(noticeUserPage);
        return response;
    }

    @RequestMapping(value = "/get_unread_list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询用户未读消息列表-弃用", notes = "查询用户未读消息列表-弃用")
    GetNoticesResponse getUnreadList(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "pageNo") @RequestParam Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam Integer pageSize
    ) {
        GetNoticesResponse response = new GetNoticesResponse();
        //通过token获取userId
        Long userId;
        if (token == null || token.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }
        //查询通知
        Page<NoticeUser> noticeUserPage = noticeUserService.getUnreadList(userId, pageNo, pageSize);
        List<NoticeUser> noticeUserList = noticeUserPage.getContent();
        List<Notice> noticeList = new ArrayList<>();
        //通过通知查询消息
        for (NoticeUser noticeUser : noticeUserList) {
            //查找对应消息
            Notice notice = noticeService.findNotice(noticeUser.getNoticeId());
            Notice newNotice = new Notice();
            newNotice.setId(notice.getId());
            newNotice.setCreateTime(noticeUser.getCreateTime());
            newNotice.setContent(notice.getContent());
            newNotice.setRedirectStyle(notice.getRedirectStyle());
            newNotice.setTargetValue(notice.getTargetValue());
            newNotice.setIcon(notice.getIcon());
            newNotice.setUrl(newNotice.getUrl());
            noticeList.add(newNotice);
        }
        response.setJpaPage(noticeUserPage);
        response.setNoticeList(noticeList);
        return response;
    }

    @RequestMapping(value = "/get_notice", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查看单个消息", notes = "查看单个消息")
    GetNoticeResponse getNotice(
            @ApiParam(value = "消息Id") @RequestParam Integer noticeUserId
    ) {

        GetNoticeResponse response = new GetNoticeResponse();
        //查询通知
        NoticeUser noticeUser = noticeUserService.getNotice(noticeUserId);
        //通过通知查询消息
        Notice notice = noticeService.findNotice(noticeUser.getNoticeId());
        response.setNotice(notice);
        return response;
    }

    @RequestMapping(value = "/set_to_read", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "设置为已读状态", notes = "设置为已读状态")
    BaseResponse set2Read(
            @ApiParam(value = "用户token") @RequestParam String token
    ) {
        BaseResponse response = new BaseResponse();

        //通过token获取userId
        Long userId;
        if (token == null || token.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }
        //查询通知
        noticeUserService.setToRead(userId.intValue());
        return response;
    }

    @RequestMapping(value = "/add_notice_test", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "添加系统消息测试", notes = "添加系统消息测试")
    BaseResponse addNoticeTest(
            @ApiParam(value = "用户token") @RequestParam String token
    ) {
        BaseResponse response = new BaseResponse();
        Long userId = userService.checkAndGetCurrentUserId(token);
        Map<String, String> contentMap = new HashMap<String, String>();
        contentMap.put("lessonNum", "50");
        contentMap.put("starCount", "100");
        String msg = templateNoticeService.getTemplateByNoticeStyleAndMap(NoticeStyle.BUY_LESSON, contentMap);
        ApiStatus status = new ApiStatus();
        status.setMsg(msg);
        response.setStatus(status);
        return response;
    }


}
