package com.ifenghui.storybookapi.app.story.controller;

import com.ifenghui.storybookapi.app.app.service.AdService;
import com.ifenghui.storybookapi.app.story.entity.LessonItem;
import com.ifenghui.storybookapi.app.story.entity.LessonItemRelate;
import com.ifenghui.storybookapi.app.story.entity.LessonStudyVideo;
import com.ifenghui.storybookapi.app.story.response.lesson.GetLessonItemDetailResponse;
import com.ifenghui.storybookapi.app.story.response.lesson.GetLessonItemListResponse;
import com.ifenghui.storybookapi.app.story.response.lesson.GetLessonStudyVideoResponse;
import com.ifenghui.storybookapi.app.story.service.LessonItemRelateService;
import com.ifenghui.storybookapi.app.story.service.LessonItemService;
import com.ifenghui.storybookapi.app.story.service.LessonStudyVideoService;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.BuyLessonItemRecord;
import com.ifenghui.storybookapi.app.transaction.service.lesson.BuyLessonItemRecordService;
import com.ifenghui.storybookapi.app.transaction.service.lesson.PayLessonPriceService;
import com.ifenghui.storybookapi.app.user.service.UserService;
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

import java.util.List;

@Controller
@EnableAutoConfiguration
@Api(value = "lessonController", description = "课程管理api")
@RequestMapping("/api/lesson")
public class LessonController {

    @Autowired
    UserService userService;

    @Autowired
    LessonItemService lessonItemService;

    @Autowired
    LessonItemRelateService lessonItemRelateService;

    @Autowired
    PayLessonPriceService payLessonPriceService;

    @Autowired
    BuyLessonItemRecordService buyLessonItemRecordService;

    @Autowired
    LessonStudyVideoService lessonStudyVideoService;

    @ApiOperation(value = "获得课程章节列表")
    @RequestMapping(value = "/lesson_item_list",method = RequestMethod.GET)
    @ResponseBody
    GetLessonItemListResponse getLessonItemPage(
            @ApiParam(value = "用户token") @RequestParam(required = false,defaultValue = "") String  token,
            @ApiParam(value = "lessonId") @RequestParam Integer lessonId
    ) {
        Long userId;
        if(token == null || token.length() <= 0){
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }

        return lessonItemService.getLessonItemListByLessonId(lessonId,userId.intValue());
    }

    @ApiOperation(value = "获得课程章节故事详情")
    @RequestMapping(value = "/lesson_item_detail_list",method = RequestMethod.GET)
    @ResponseBody
    GetLessonItemDetailResponse getLessonItemDetail(
            @ApiParam(value = "用户token")@RequestParam(required = false) String  token,
            @ApiParam(value = "lessonItemId") @RequestParam Integer lessonItemId
    ) {
        GetLessonItemDetailResponse response = new GetLessonItemDetailResponse();
        Long userId;
        if(token == null || token.length() <= 0){
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }
        List<LessonItemRelate> lessonItemRelateList = lessonItemRelateService.getLessonItemRelateList(lessonItemId, userId);
        response.setLessonItemRelateList(lessonItemRelateList);
        return response;
    }

}
