package com.ifenghui.storybookapi.active1902.admincontroller;


import com.ifenghui.storybookapi.active1902.entity.Answer;
import com.ifenghui.storybookapi.active1902.response.AnswerResponse;
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
@RequestMapping(value = "/activity1902/admin/answer", name = "答案编辑")
public class AnswerAdminController {


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




    @ApiOperation(value = "答案列表")
    @RequestMapping(value = "get_answer", name = "答案列表", method = RequestMethod.GET)
    @ResponseBody
    public AnswerResponse getAnswer(
            @ApiParam(value = "id" ) @RequestParam(required = false) Integer id
    ) {

        Answer answer= answerService.findOne(id);
        AnswerResponse response=new AnswerResponse();
      response.setAnswer(answer);
        return response;
    }
    @ApiOperation(value = "答案编辑")
    @RequestMapping(value = "add_answer", name = "答案编辑", method = RequestMethod.PUT)
    @ResponseBody
    public AnswerResponse addAnswer(
            @ApiParam(value = "questioinId" ) @RequestParam Integer questioinId,
            @ApiParam(value = "content" ) @RequestParam(required = false) String content,
            @ApiParam(value = "name" ) @RequestParam(required = false) String name,
            @ApiParam(value = "isRight" ) @RequestParam(required = false) Integer isRight
    ) {

        Answer answer= new Answer();
        answer.setQuestionId(questioinId);
        answer.setContent(content);
        answer.setName(name);
        answer.setIsRight(isRight);
        answerService.save(answer);
        AnswerResponse response=new AnswerResponse();
        response.setAnswer(answer);
        return response;
    }

    @ApiOperation(value = "答案编辑")
    @RequestMapping(value = "update_answer", name = "答案编辑", method = RequestMethod.PUT)
    @ResponseBody
    public AnswerResponse updateAnswer(
            @ApiParam(value = "id" ) @RequestParam Integer id,
            @ApiParam(value = "content" ) @RequestParam(required = false) String content,
            @ApiParam(value = "name" ) @RequestParam(required = false) String name,
            @ApiParam(value = "isRight" ) @RequestParam(required = false) Integer isRight
    ) {

        Answer answer= answerService.findOne(id);

        if(content!=null&&!"".equals(content)){
            answer.setContent(content);
        }
        if(name!=null&&!"".equals(name)) {
            answer.setName(name);
        }
        if(isRight!=null){
            answer.setIsRight(isRight);
        }
        answerService.update(answer);
        AnswerResponse response=new AnswerResponse();
        response.setAnswer(answer);
        return response;
    }


}
