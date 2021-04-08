package com.ifenghui.storybookapi.app.social.controller;

/**
 * Created by jia on 2016/12/23.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.social.entity.LessonViewRecord;
import com.ifenghui.storybookapi.app.social.response.*;
import com.ifenghui.storybookapi.app.social.service.*;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanTaskFinish;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanTaskFinishService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.style.LabelTargetStyle;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.ifenghui.storybookapi.app.social.entity.ViewRecord;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/viewrecord")
@Api(value = "观看记录", description = "观看记录")
public class ViewRecordController {
    Logger logger=Logger.getLogger(ViewRecordController.class);
    @Autowired
    ViewRecordService viewRecordService;

    @Autowired
    UserService userService;

    @Autowired
    StoryService storyService;
    @Autowired
    UserReadRecordService userReadRecordService;
    @Autowired
    HttpServletRequest request;

    @Autowired
    UserReadStudyVideoService userReadStudyVideoService;

    @Autowired
    LessonVideoViewRecordService lessonVideoViewRecordService;

    @Autowired
    LessonViewRecordService lessonViewRecordService;

    @Autowired
    WeekPlanTaskFinishService weekPlanTaskFinishService;


    @RequestMapping(value = "/sync_view_record", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "同步阅读记录（新）")
    GetViewRecordsByUserIdResponse syncViewRecord(
            @ApiParam(value = "用户token") @RequestParam String token,
                @ApiParam(value = "json          {\n" +
                        "            \"viewRecords\": [\n" +
                        "            {\n" +
                        "                \"storyId\": 1,\n" +
                        "                    \"time\": \"2017-02-11 11:53:43\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"storyId\": 2,\n" +
                        "                    \"time\": \"2017-02-11 11:53:43\"\n" +
                        "            }]\n" +
                        "        }") @RequestParam String jsonData
    ) {

        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
        User user = userService.getUser(userId);
        GetViewRecordsByUserIdResponse response = new GetViewRecordsByUserIdResponse();

        //解析json参数
        //jackson json转换工具
        ObjectMapper objectMapper = new ObjectMapper();
        SynchroViewRecordJsonDataResponse synchroViewRecordResponse = new SynchroViewRecordJsonDataResponse();

        try {
            SynchroViewRecordJsonDataResponse resp = objectMapper.readValue(jsonData, SynchroViewRecordJsonDataResponse.class);
            synchroViewRecordResponse.setViewRecords(resp.getViewRecords());
        } catch (IOException e) {
            logger.info(e);
        }
        ViewRecord viewrecord;
        Page<ViewRecord> viewRecordPage;
        Story story;
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (FormatViewRecordResponse item : synchroViewRecordResponse.getViewRecords()) {
            //查询此用户是否有这个storyid的观看记录
            viewRecordPage = viewRecordService.getViewrecordsByUserAndStoryId(userId, item.getStoryId(), 0, 20);
            try {
                date = sdf.parse(item.getTime());
            } catch (ParseException e) {
                logger.info(e);
            }
            if (viewRecordPage.getContent().size() > 0) {
                //有观看记录则判断json时间是否晚于这个时间，晚于则改时间，早于则不改
                for (ViewRecord vitem : viewRecordPage.getContent()) {
                    if ((vitem.getCreateTime().getTime() - date.getTime()) < 0) {
                        vitem.setCreateTime(date);
                        viewRecordService.eidtViewRecord(vitem);
                    }
                }
            } else {
                try {
                    viewrecord = new ViewRecord();
                    story = storyService.getStory(item.getStoryId());
                    //json里的time
                    viewrecord.setCreateTime(date);
                    viewrecord.setType(story.getType());
                    viewrecord.setStoryId(item.getStoryId());
                    viewrecord.setUser(user);
                    viewRecordService.addViewrecord(viewrecord);
                } catch (ApiNotFoundException e) {
                    logger.info(e);
                }
            }
            try {
                viewRecordService.addLessonViewRecord(userId.intValue(), item.getStoryId().intValue());
            } catch (ApiException e) {
               logger.info(e);
            }
        }

        GetViewRecordsByUserIdResponse viewrecordsResponse = this.getViewrecordsByUser(token, 1, 20);

        response.setViewrecords(viewrecordsResponse.getViewrecords());
        return response;
    }

    @RequestMapping(value = "/getViewrecord", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取观看记录详情", notes = "获取观看记录详情")
    GetViewRecordResponse getViewrecord(@ApiParam(value = "ID") @RequestParam Long id) {
        ViewRecord viewrecord = viewRecordService.getViewrecord(id);
        GetViewRecordResponse t = new GetViewRecordResponse();
        System.out.println(viewrecord);
        t.setViewrecord(viewrecord);

        return t;
    }

    @RequestMapping(value = "/getViewrecordsByUser", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取特定用户多条观看记录", notes = "获取特定用户多条观看记录")
    GetViewRecordsByUserIdResponse getViewrecordsByUser(@ApiParam(value = "用户token") @RequestParam String token,
                                                        @ApiParam(value = "页码") @RequestParam Integer pageNo,
                                                        @ApiParam(value = "条数") @RequestParam Integer pageSize
    ) {
        Long userId = null;
        try {
            userId = userService.checkAndGetCurrentUserId(token);
        } catch (ApiNotTokenException e) {
            e.setApimsg("用户token不存在");
        }
//        pageNo = pageNo - 1;
//        List<ViewRecord> vs=viewRecordService.getViewrecordsByUser(userId);
//        List<ViewRecord> vs=viewRecordService.getViewrecordsByUserAndPage(userId,pageNo,pageSize);
        Page<ViewRecord> vs = viewRecordService.getViewrecordsByUserAndPage(userId, pageNo, pageSize, request);

        GetViewRecordsByUserIdResponse t = new GetViewRecordsByUserIdResponse();

//        if(vs.getContent()!=null && vs.getContent().size()>0){
//            for(int i=0;i<vs.getContent().size();i++){
////                vs.getContent().get(i).setStory(storyService.getStory(vs.getContent().get(i).getStoryId()));
//            }
        t.setViewrecords(vs.getContent());
        t.setJpaPage(vs);
//        }
        return t;
    }

    @RequestMapping(value = "/getViewrecords", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分页获取多条观看记录", notes = "分页获取多条观看记录")
    GetViewRecordsResponse getViewrecords(@ApiParam(value = "页码") @RequestParam Integer pageNo,
                                          @ApiParam(value = "条数") @RequestParam Integer pageSize
    ) {
//        pageNo = pageNo - 1;
        GetViewRecordsResponse t = new GetViewRecordsResponse();
        Page<ViewRecord> vs = viewRecordService.getViewrecordsByPage(pageNo, pageSize);
        if (vs.getContent() != null && vs.getContent().size() > 0) {
            for (int i = 0; i < vs.getContent().size(); i++) {
                vs.getContent().get(i).setStory(storyService.getStory(vs.getContent().get(i).getStoryId()));
            }
            t.setViewrecords(vs.getContent());
            t.setJpaPage(vs);
        }

        t.setViewrecords(vs.getContent());
        t.setJpaPage(vs);

        return t;
    }


    @RequestMapping(value = "/addViewrecord", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加观看记录", notes = "添加观看记录")
    AddViewRecordResponse addViewrecord(
//            @ApiParam(value = "用户token") @RequestParam String token,
                                        @ApiParam(value = "故事ID") @RequestParam Long storyId
//                                        @ApiParam(value = "类型")@RequestParam Integer type
//                                        @ApiParam(value = "创建时间")@RequestParam String createTime
    ) throws ParseException {
//        Long userId = null;
        Long userId=(Long)request.getAttribute("loginId");
//        try {
//            userId = userService.checkAndGetCurrentUserId(token);
//        } catch (ApiNotTokenException e) {
//            e.setApimsg("用户token不存在");
//        }
//        Date date=new Date();
        ViewRecord viewrecord = new ViewRecord();
        AddViewRecordResponse addViewRecordResponse = new AddViewRecordResponse();
//        if(createTime!=null){
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            try {
//                date = sdf.parse(createTime);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
        Story story = storyService.getStory(storyId);
        if (story.getScheduleType().intValue() > 0) {
            addViewRecordResponse.getStatus().setCode(2);
            addViewRecordResponse.getStatus().setMsg("此类型故事不添加浏览记录");
            return addViewRecordResponse;
        }
        //处理连续阅读天数
        viewRecordService.addUserReadRecordCount(userId, storyId);
        User user = this.userService.getUser(userId);
        //查询此用户有没有观看过这个故事，有则删除，添加个新的
        Integer pageNo = 0;
        Integer pageSize = 100;
        Page<ViewRecord> viewRecordPage = viewRecordService.getViewrecordsByUserAndStoryId(userId, storyId, pageNo, pageSize);
        if (viewRecordPage.getContent().size() > 0) {
            //有记录则删除

            for (ViewRecord item : viewRecordPage.getContent()) {
                viewRecordService.delViewrecord(item.getId());
            }
        }
        viewrecord.setCreateTime(new Date());
        viewrecord.setStoryId(storyId);
        viewrecord.setType(story.getType());
        viewrecord.setUser(user);
        ViewRecord v = viewRecordService.addViewrecord(viewrecord);
        System.out.println(v);
        addViewRecordResponse.setViewrecord(v);

        try {
            viewRecordService.addLessonViewRecord(user.getId().intValue(), storyId.intValue());
        } catch (ApiException e){
            logger.info("userId:"+user.getId()+" storyId:"+storyId.intValue());
            e.printStackTrace();
        }

        return addViewRecordResponse;
    }

    @RequestMapping(value = "/synchroViewRecord", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "同步观看记录", notes = "同步观看记录")
    SynchroViewRecordResponse addViewrecord(@ApiParam(value = "用户token") @RequestParam String token,
                                            @ApiParam(value = "观看记录json数据") @RequestParam String jsonData
//                                        @ApiParam(value = "创建时间")@RequestParam String createTime
    ) throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
        User user = userService.getUser(userId);
//        {
//            "viewRecords": [
//            {
//                "storyId": 1,
//                    "time": "2017-02-11 11:53:43"
//            },
//            {
//                "storyId": 2,
//                    "time": "2017-02-11 11:53:43"
//            }]
//        }
        //解析json参数
        //jackson json转换工具
        ObjectMapper objectMapper = new ObjectMapper();
        SynchroViewRecordJsonDataResponse synchroViewRecordResponse = new SynchroViewRecordJsonDataResponse();

        try {
            SynchroViewRecordJsonDataResponse resp = objectMapper.readValue(jsonData, SynchroViewRecordJsonDataResponse.class);
            synchroViewRecordResponse.setViewRecords(resp.getViewRecords());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ViewRecord viewrecord = new ViewRecord();
        Page<ViewRecord> viewRecordPage;
        Story story;
        SynchroViewRecordResponse synchroViewRecordResponse1 = new SynchroViewRecordResponse();
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (FormatViewRecordResponse item : synchroViewRecordResponse.getViewRecords()) {
            //查询此用户是否有这个storyid的观看记录
            viewRecordPage = viewRecordService.getViewrecordsByUserAndStoryId(userId, item.getStoryId(), 0, 20);
            try {
                date = sdf.parse(item.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (viewRecordPage.getContent().size() > 0) {
                //有观看记录则判断json时间是否晚于这个时间，晚于则改时间，早于则不改
                for (ViewRecord vitem : viewRecordPage.getContent()) {


                    if ((vitem.getCreateTime().getTime() - date.getTime()) < 0) {
                        //晚于，则修改
                        vitem.setCreateTime(date);
                        viewRecordService.eidtViewRecord(vitem);
                    }
                }

            } else {
                //没有记录则新加一条记录
                try {
                    viewrecord = new ViewRecord();
                    story = storyService.getStory(item.getStoryId());
                    viewrecord.setCreateTime(date);//json里的time
                    viewrecord.setStoryId(item.getStoryId());
                    viewrecord.setType(story.getType());//视频1音频2
                    viewrecord.setUser(user);
                    viewRecordService.addViewrecord(viewrecord);
                } catch (ApiNotFoundException e) {
                    e.printStackTrace();
                }
            }
            try {
                viewRecordService.addLessonViewRecord(userId.intValue(), item.getStoryId().intValue());
            } catch (ApiException e) {
                e.printStackTrace();
            }

        }

        return synchroViewRecordResponse1;
    }

    //删除阅读记录接口
    @RequestMapping(value = "/delViewrecord", method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除观看记录", notes = "删除观看记录")
    Void delViewrecord(@ApiParam(value = "用户token") @RequestParam String token,
                       @ApiParam(value = "ID") @RequestParam Long id
    ) throws ApiNotTokenException {
        Long userId = null;
        userId = userService.checkAndGetCurrentUserId(token);

        ViewRecord viewrecord = viewRecordService.getViewrecord(id);
        if (viewrecord == null || userId != viewrecord.getUser().getId().longValue()) {
            return null;
        }
        viewRecordService.delViewrecord(id);
        return null;


    }

    @CrossOrigin
    @RequestMapping(value = "/addActivityViewRecord", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "添加活动浏览记录", notes = "添加活动浏览记录-跟董秋确认不再需要")
    @Deprecated
    Void addActivityViewRecord(
            @ApiParam(value = "记录id") @RequestParam Integer id
    ) {

        if (id == null) {
            return null;
        }
        viewRecordService.addActivityViewRecord(id);
        return null;
    }

    @RequestMapping(value = "/add_study_video_record", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加视频阅读完成记录")
    BaseResponse addUserStudyVideoRecord(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "课程章节id") @RequestParam Integer itemId,
            @ApiParam(value = "视频id") @RequestParam Integer videoId
    ) {
        BaseResponse response = new BaseResponse();
        Long userId = userService.checkAndGetCurrentUserId(token);
        if(itemId.equals(0)) {
            weekPlanTaskFinishService.setWeekPlanTaskFinishEnter(videoId, LabelTargetStyle.Video, userId.intValue());
        } else {
            userReadStudyVideoService.addUserReadStudyVideoRecord(itemId, videoId, userId.intValue());
        }
        return response;
    }

    @RequestMapping(value = "/add_lesson_video_view_record", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加视频阅读浏览记录")
    BaseResponse addLessonVideoViewRecord(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "课程章节id") @RequestParam Integer itemId,
            @ApiParam(value = "视频id") @RequestParam Integer videoId
    ) {
        BaseResponse response = new BaseResponse();
        Long userId = userService.checkAndGetCurrentUserId(token);
        lessonVideoViewRecordService.addLessonVideoViewRecordAndLessonViewRecord(itemId, videoId, userId.intValue());
        return response;
    }

    @RequestMapping(value = "/lesson_view_record_list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获得课程浏览记录")
    GetLessonViewRecordListResponse getLessonViewRecordList(
            @ApiParam(value = "用户token") @RequestParam(required = false) String token
    ) {

        GetLessonViewRecordListResponse response = new GetLessonViewRecordListResponse();
        if (token == null || token.equals("")) {
            return response;
        }
        Long userId = userService.checkAndGetCurrentUserId(token);
        List<LessonViewRecord> lessonViewRecordList = lessonViewRecordService.getLessonViewRecordByUserId(userId.intValue());
        response.setLessonViewRecordList(lessonViewRecordList);
        return response;
    }
}
