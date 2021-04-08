package com.ifenghui.storybookapi.active1902.admincontroller;

import com.ifenghui.storybookapi.active1902.entity.Question;
import com.ifenghui.storybookapi.active1902.response.QuestionResponse;
import com.ifenghui.storybookapi.active1902.service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Date: 2019/2/19 10:50
 * @Description:
 */
@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@RestController
@RequestMapping(value = "/activity1902/admin/question", name = "首页相关")
public class QuestionAdminController {


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




    @ApiOperation(value = "问题列表")
    @RequestMapping(value = "get_question", name = "问题列表", method = RequestMethod.GET)
    @ResponseBody
    public QuestionResponse getQuestions(
            @ApiParam(value = "id" ) @RequestParam(required = false) Integer id
    ) {

        Question question= questionService.findOne(id);
      QuestionResponse response=new QuestionResponse();
      response.setQuestion(question);
        return response;
    }

    @ApiOperation(value = "问题修改")
    @RequestMapping(value = "update_question", name = "问题列表", method = RequestMethod.PUT)
    @ResponseBody
    public QuestionResponse getQuestions(
            @ApiParam(value = "id" ) @RequestParam Integer id,
            @ApiParam(value = "content" ) @RequestParam(required = false) String content,
            @ApiParam(value = "name" ) @RequestParam(required = false) String name,
            @ApiParam(value = "audio" ) @RequestParam(required = false) String audio
    ) {

        Question question= questionService.findOne(id);

        if(content!=null&&!"".equals(content)){
            question.setContent(content);
        }
        if(name!=null&&!"".equals(name)) {
            question.setName(name);
        }
        if(audio!=null&&!"".equals(audio)) {
            question.setAudio(audio);
        }
        questionService.update(question);
        QuestionResponse response=new QuestionResponse();
        response.setQuestion(question);
        return response;
    }

    @ApiOperation(value = "add")
    @RequestMapping(value = "add_question", name = "add", method = RequestMethod.POST)
    @ResponseBody
    public QuestionResponse addQuestions(
            @ApiParam(value = "scheduleId" ) @RequestParam Integer scheduleId,
            @ApiParam(value = "content" ) @RequestParam(required = false) String content,
            @ApiParam(value = "name" ) @RequestParam(required = false) String name,
            @ApiParam(value = "audio" ) @RequestParam(required = false) String audio
    ) {

        Question question=new Question();
        question.setScheduleId(scheduleId);

        if(content!=null&&!"".equals(content)){
            question.setContent(content);
        }
        if(name!=null&&!"".equals(name)) {
            question.setName(name);
        }
        if(audio!=null&&!"".equals(audio)) {
            question.setAudio(audio);
        }
        questionService.save(question);
        QuestionResponse response=new QuestionResponse();
        response.setQuestion(question);
        return response;
    }

}
