package com.ifenghui.storybookapi.active1902.admincontroller;



import com.ifenghui.storybookapi.active1902.entity.Question;
import com.ifenghui.storybookapi.active1902.entity.Schedule;
import com.ifenghui.storybookapi.active1902.response.ScheduleListResponse;
import com.ifenghui.storybookapi.active1902.service.*;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * wsl 2019-3-26
 * 排期管理
 */
@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@RestController
@RequestMapping(value = "/activity1902/admin/schedule", name = "首页相关")
public class ScheduleAdminController {


    @Autowired
    UserAnswerService userAnswerService;
    @Autowired
    UserAwardsService userAwardsService;
    @Autowired
    AnswerService answerService;
    @Autowired
    QuestionService questionService;
    @Autowired
    AwardsService awardsService;

    @Autowired
    ScheduleService scheduleService;




    @ApiOperation(value = "排期列表")
    @RequestMapping(value = "get_schedules", name = "问题列表", method = RequestMethod.GET)
    @ResponseBody
    public ScheduleListResponse getSchedules(
//            @ApiParam(value = "scheduleId" ) @RequestParam(required = false) Integer scheduleId,
            @ApiParam(value = "pageNo" ) @RequestParam(required = false) Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam(required = false) Integer pageSize
    ) {

      Page<Schedule> schedulePage= scheduleService.findAll(new Schedule(),new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));

      for(Schedule schedule:schedulePage.getContent()){
          //问题
          schedule.setQuestions(questionService.findQuestionsByScheduleId(schedule.getId()));
          //答案
          for(Question question:schedule.getQuestions()){
            question.setAnswerList(answerService.getAnswersByQuestionId(question.getId()));
          }
      }
      ScheduleListResponse response=new ScheduleListResponse();
        response.setSchedules(schedulePage.getContent());
        response.setJpaPage(schedulePage);
        return response;
    }
    @ApiOperation(value = "增加排期")
    @RequestMapping(value = "add_schedule", name = "增加排期", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse addSchedule(
            @ApiParam(value = "icon") @RequestParam(required = false) String icon,
            @ApiParam(value = "img") @RequestParam(required = false) String img,
            @ApiParam(value = "questionImg") @RequestParam(required = false) String questionImg,
            @ApiParam(value = "titImg") @RequestParam(required = false) String titImg

    ) {

        Schedule schedule=new Schedule();
        schedule.setFinish_num(0);
        schedule.setAdd_num(0);
        schedule.setCreateTime(new Date());
        schedule.setIcon(icon);
        schedule.setImg(img);
        schedule.setQuestionImg(questionImg);
        schedule.setTitImg(titImg);
        schedule.setStatus(0);

        scheduleService.save(schedule);

        BaseResponse response=new BaseResponse();
        return response;
    }

    @ApiOperation(value = "修改")
    @RequestMapping(value = "update_schedule", name = "修改", method = RequestMethod.PUT)
    @ResponseBody
    public BaseResponse update_schedule(
            @ApiParam(value = "id") @RequestParam(required = false) Integer id,
            @ApiParam(value = "icon") @RequestParam(required = false) String icon,
            @ApiParam(value = "img") @RequestParam(required = false) String img,
            @ApiParam(value = "questionImg") @RequestParam(required = false) String questionImg,
            @ApiParam(value = "titImg") @RequestParam(required = false) String titImg,
            @ApiParam(value = "status") @RequestParam(required = false) Integer status

    ) {

        Schedule schedule=scheduleService.findOne(id);
        if(img!=null){
            schedule.setImg(img);
        }

        if(icon!=null){
            schedule.setIcon(icon);
        }
        if(questionImg!=null){
            schedule.setQuestionImg(questionImg);
        }
        if(questionImg!=null){
            schedule.setTitImg(titImg);
        }

        schedule.setStatus(status);

        scheduleService.save(schedule);

        BaseResponse response=new BaseResponse();
        return response;
    }

}
