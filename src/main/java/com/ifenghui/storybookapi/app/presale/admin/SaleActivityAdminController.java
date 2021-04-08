package com.ifenghui.storybookapi.app.presale.admin;




import com.ifenghui.storybookapi.app.presale.entity.Activity;
import com.ifenghui.storybookapi.app.presale.response.ActivitiesResponse;
import com.ifenghui.storybookapi.app.presale.response.ActivityResponse;
import com.ifenghui.storybookapi.app.presale.service.ActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "预售活动管理", description = "预售活动管理")
@RequestMapping("/sale/presaleadmin/activity")
public class SaleActivityAdminController {


    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;


    @Autowired
    Environment env;

    @Autowired
    ActivityService activityService;

    private static Logger logger = Logger.getLogger(SaleActivityAdminController.class);


    @RequestMapping(value = "/get_activities", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "活动列表", notes = "活动列表")
    ActivitiesResponse getActivities(
            @ApiParam(value = "pageNo") @RequestParam Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam Integer pageSize
    ) {

        ActivitiesResponse response = new ActivitiesResponse();
        Page<Activity> activities = activityService.findAll(new Activity(),new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));
        response.setActivities(activities.getContent());
        response.setJpaPage(activities);
        return response;
    }

    @RequestMapping(value = "/get_activity", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "活动")
    ActivityResponse getActivity(
            @ApiParam(value = "id") @RequestParam Integer id
    ) {

        ActivityResponse response = new ActivityResponse();
        Activity activities = activityService.findById(id);
        response.setPreSaleActivity(activities);

        return response;
    }

    @RequestMapping(value = "/add_activity", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "增加活动")
    ActivityResponse getActivity(
            @ApiParam(value = "content") @RequestParam String content,
            @ApiParam(value = "userType 1:app,2:wx") @RequestParam Integer userType,
            @ApiParam(value = "startTime") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @ApiParam(value = "endTime") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime
    ) {

        ActivityResponse response = new ActivityResponse();
        Activity activity=new Activity();
        activity.setContent(content);
        activity.setUserType(userType);
        activity.setStartTime(startTime);
        activity.setEndTime(endTime);
        activityService.save(activity);
//        response.setPreSaleActivity(activities);

        return response;
    }

    @RequestMapping(value = "/update_activity", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "修改活动")
    ActivityResponse updateActivity(
            @ApiParam(value = "id") @RequestParam Integer id,
            @ApiParam(value = "content") @RequestParam String content,
            @ApiParam(value = "userType 1:app,2:wx") @RequestParam Integer userType,
            @ApiParam(value = "startTime") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
            @ApiParam(value = "endTime") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime
    ) {

        ActivityResponse response = new ActivityResponse();
        Activity activity=activityService.findById(id);
        activity.setContent(content);
        activity.setUserType(userType);
        activity.setStartTime(startTime);
        activity.setEndTime(endTime);
        activityService.save(activity);
//        response.setPreSaleActivity(activities);

        return response;
    }

    @RequestMapping(value = "/delete_activity", method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "删除")
    ActivityResponse updateActivity(
            @ApiParam(value = "id") @RequestParam Integer id
    ) {

//        ActivityResponse response = new ActivityResponse();
//        activityService.delete(id);

//        response.setPreSaleActivity(activities);

        return new ActivityResponse();
    }
}
