package com.ifenghui.storybookapi.app.social.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifenghui.storybookapi.api.response.base.ApiPage;
import com.ifenghui.storybookapi.app.social.entity.UserGrowthDiaryImg;
import com.ifenghui.storybookapi.app.social.entity.UserGrowthDiaryMusic;
import com.ifenghui.storybookapi.app.social.response.ScheduleSmallImgJsonDataResponse;
import com.ifenghui.storybookapi.app.social.entity.UserGrowthDiary;
import com.ifenghui.storybookapi.app.social.response.*;
import com.ifenghui.storybookapi.app.social.service.*;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.config.MyEnv;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/userGrowthDiary")
@Api(value="用户日记",description = "用户日记相关接口")
public class UserGrowthDiaryController {

    @Autowired
    UserGrowthDiaryImgService userGrowthDiaryImgService;

    @Autowired
    UserGrowthDiaryService userGrowthDiaryService;

    @Autowired
    UserService userService;

    @Autowired
    UserGrowthDiaryMusicService userGrowthDiaryMusicService;

    @Autowired
    HttpServletRequest request;

    @RequestMapping(value="/upload_img_growth_diary",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "上传图片并且发朋友圈", notes = "上传图片并打卡")
    AddUserGrowthDiaryResponse addUserGrowthDiary (
            @ApiParam(value = "用户token")@RequestParam String  token,
            @ApiParam(value = "内容")@RequestParam String content,
            @ApiParam(value = "宽度")@RequestParam Integer width,
            @ApiParam(value = "高度")@RequestParam Integer height,
            @ApiParam(value = "大图地址")@RequestParam(required = false, defaultValue = "") String bigImgPath,
            @ApiParam(value = "发布日期")@RequestParam(required = false, defaultValue = "0") Long recordDate,
            @ApiParam(value = "图片地址list形式")@RequestParam String jsonData
    ) throws ParseException {
        AddUserGrowthDiaryResponse response = new AddUserGrowthDiaryResponse();
        Long userId = userService.checkAndGetCurrentUserId(token);

        ObjectMapper objectMapper = new ObjectMapper();

        List<ScheduleSmallImg> diaryImgList = null;
        try {
            ScheduleSmallImgJsonDataResponse jsonDataResponse = objectMapper.readValue(jsonData,ScheduleSmallImgJsonDataResponse.class);
            diaryImgList = jsonDataResponse.getScheduleImgSmallList();
        } catch(Exception e){
            e.printStackTrace();
        }

        Date recordTime = new Date();
        if(!recordDate.equals(0L)){
            recordTime = new Date(recordDate);
        }

        /**
         * 先检查是否需要增加星星值
         */
        List<TaskFinish> taskFinishList = userGrowthDiaryService.isNeedAddUserStarRecord(userId.intValue());
        response.setTaskFinishInfo(taskFinishList);
        UserGrowthDiary userGrowthDiary = userGrowthDiaryService.addUserGrowthDiary(
                userId.intValue(),content,
                recordTime,diaryImgList,
                width,
                height,
                bigImgPath
                );
        return response;
    }

    @RequestMapping(value="/growth_diary_list",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获得成长日记列表", notes = "获得成长日记列表")
    GetUserGrowthDiaryPageResponse getUserGrowthDiaryPage (
            @ApiParam(value = "用户token")@RequestParam String  token,
            @ApiParam(value = "月份情况")@RequestParam(required = false, defaultValue = "0") Integer  monthDate,
            @ApiParam(value = "pageNo")@RequestParam Integer pageNo,
            @ApiParam(value = "pageSize")@RequestParam Integer pageSize
    ) {
        GetUserGrowthDiaryPageResponse response = new GetUserGrowthDiaryPageResponse();
        Long userId = userService.checkAndGetCurrentUserId(token);
        if(monthDate == null || monthDate.equals(0) ){
            monthDate = null;
        }
        Page<UserGrowthDiary> userGrowthDiaryPage = userGrowthDiaryService.getUserGrowthDiaryPage(userId.intValue(),monthDate,pageNo,pageSize);
        List<UserGrowthDiary> userGrowthDiaryList = userGrowthDiaryPage.getContent();

        /**
         * 增加年份标识假数据制造
         */
        if(pageNo.equals(1) && monthDate != null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
            String nowString = simpleDateFormat.format(new Date());
            String monthDateString = String.valueOf(monthDate).substring(0,4);
            if(!nowString.equals(monthDateString)){
                userGrowthDiaryList.get(0).setCrossYear(1);
            }
        }
        response.setUserGrowthDiaryList(userGrowthDiaryList);
        response.setJpaPage(userGrowthDiaryPage);
        return response;
    }

    @RequestMapping(value="/delete_growth_diary",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "删除用户日记记录", notes = "删除用户日记记录")
    DeleteUserGrowthDiaryResponse deleteUserGrowthDiary(
            @ApiParam(value = "用户token")@RequestParam String  token,
            @ApiParam(value = "日记id")@RequestParam Integer userGrowthDiaryId
    ) {
        DeleteUserGrowthDiaryResponse response = new DeleteUserGrowthDiaryResponse();
        Long userId = userService.checkAndGetCurrentUserId(token);
        userGrowthDiaryService.deleteUserGrowthDiary(userGrowthDiaryId, userId.intValue());
        return response;
    }

    @RequestMapping(value="/recover_growth_diary_data",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "补全成长记录数据")
    DeleteUserGrowthDiaryResponse recoverUserGrowthDiaryData(){
        DeleteUserGrowthDiaryResponse response = new DeleteUserGrowthDiaryResponse();
        userGrowthDiaryService.recoverUserGrowthDiaryData();
        return response;
    }

    @RequestMapping(value="/recover_growth_diary_img_data", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "补全成长记录小图数据")
    DeleteUserGrowthDiaryResponse recoverUserGrowthDiaryImgData(){
        DeleteUserGrowthDiaryResponse response = new DeleteUserGrowthDiaryResponse();
        userGrowthDiaryImgService.recoverUserGrowthDiaryImgData();
        return response;
    }

    @RequestMapping(value="/month_data_select", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="获得时光机")
    GetMonthDataSelectListResponse getMonthDataSelectList(
            @ApiParam(value = "用户token")@RequestParam String  token
    ) throws ParseException {
        GetMonthDataSelectListResponse response = new GetMonthDataSelectListResponse();
        Long userId = 0L;
        if(!token.equals("")){
            userId = userService.checkAndGetCurrentUserId(token);
        }
        List<MonthDateSelect> monthDateSelectList = userGrowthDiaryService.getMonthDateSelect(userId);
        response.setMonthDateSelectList(monthDateSelectList);
        return response;
    }

    @RequestMapping(value = "week_growth_diary_page", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分页获得周成长日记")
    GetUserGrowthImgPageByWeekNumResponse getUserGrowthImgPageByWeekNum(
            @ApiParam(value = "用户token")@RequestParam String  token,
            @ApiParam(value = "当前周数")@RequestParam Integer weekNum,
            @ApiParam(value = "图片id")@RequestParam(required = false, defaultValue = "0") Integer userGrowthImgId,
//            @ApiParam(value = "pageNo")@RequestParam Integer  pageNo,
            @ApiParam(value = "pageSize")@RequestParam Integer  pageSize
    ){
        Long userId = 0L;
        if(!token.equals("")){
            userId = userService.checkAndGetCurrentUserId(token);
        }

        GetUserGrowthImgPageByWeekNumResponse response = userGrowthDiaryImgService.getUserGrowthImgPageByWeekNum(userId.intValue(),weekNum,1,pageSize, userGrowthImgId);
        ApiPage apiPage = response.getPage();
        apiPage.setRsCount(userGrowthDiaryImgService.getRsCountByUserIdAndWeekNum(userId.intValue(),weekNum));
        response.setPage(apiPage);
        return response;
    }

    @RequestMapping(value = "all_growth_diary_list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "全部成长日记（周为单位）")
    GetAllUserGrowthDiaryImgByWeekResponse getAllUserGrowthDiaryImgByWeek(
            @ApiParam(value = "用户token")@RequestParam String  token
    ) {
        GetAllUserGrowthDiaryImgByWeekResponse response = new GetAllUserGrowthDiaryImgByWeekResponse();
        Long userId=(Long)request.getAttribute("loginId");
        List<GrowthDiaryWeek> growthDiaryWeekList = userGrowthDiaryImgService.getGrowthDiaryWeekList(userId.intValue());
        response.setGrowthDiaryWeekList(growthDiaryWeekList);
        return response;
    }

    @RequestMapping(value = "day_growth_diary_list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "每周成长日记（以日为单位）")
    GetDayGrowthDiaryListResponse getDayGrowthDiaryList(
            @ApiParam(value = "用户token")@RequestParam String token,
            @ApiParam(value = "周id") @RequestParam Integer weekNum
    ) throws ParseException {
        GetDayGrowthDiaryListResponse response = new GetDayGrowthDiaryListResponse();
        Long userId = 0L;
        if(!token.equals("")){
            userId = userService.checkAndGetCurrentUserId(token);
        }
        List<GrowthDiaryDay> growthDiaryDayList = userGrowthDiaryImgService.getGrowthDiaryDayList(userId.intValue(),weekNum);
        response.setGrowthDiaryDayList(growthDiaryDayList);
        response.setBeginTime(userGrowthDiaryImgService.getGrowthDiaryDate(userId.intValue(),weekNum, Sort.Direction.ASC).getRecordDate());
        response.setEndTime(userGrowthDiaryImgService.getGrowthDiaryDate(userId.intValue(),weekNum, Sort.Direction.DESC).getRecordDate());
        response.setRsCount(userGrowthDiaryImgService.getRsCountByUserIdAndWeekNum(userId.intValue(),weekNum));
        return response;
    }

    @RequestMapping(value = "user_growth_diary_music_page", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "背景音乐列表")
    GetUserGrowthDiaryMusicPageResponse getUserGrowthDiaryMusicPage(
            @ApiParam(value = "pageNo")@RequestParam Integer  pageNo,
            @ApiParam(value = "pageSize")@RequestParam Integer  pageSize
    ) {
        GetUserGrowthDiaryMusicPageResponse response = new GetUserGrowthDiaryMusicPageResponse();
        Page<UserGrowthDiaryMusic> userGrowthDiaryMusicPage = userGrowthDiaryMusicService.getUserGrowthDiaryMusicPage(pageNo, pageSize);
        response.setUserGrowthDiaryMusicList(userGrowthDiaryMusicPage.getContent());
        response.setJpaPage(userGrowthDiaryMusicPage);
        return response;
    }


    @RequestMapping(value = "/videoShare.action", method = RequestMethod.GET)
    public String share(
            HttpServletRequest request,
            HttpServletResponse response,
            ModelMap modelMap,
            Integer videoId
    ) throws Exception {

        UserGrowthDiaryImg userGrowthDiaryImg = userGrowthDiaryImgService.getUserGrowthDiaryImgById(videoId);
        String videoPath = MyEnv.env.getProperty("oss.url") + userGrowthDiaryImg.getVideoPath();
        modelMap.put("videoPath", videoPath);
        return "videoShare/index";

    }

}
