package com.ifenghui.storybookapi.adminapi.controlleradmin.settings;

import com.ifenghui.storybookapi.app.app.entity.Help;
import com.ifenghui.storybookapi.app.app.response.*;
import com.ifenghui.storybookapi.app.app.service.HelpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)//支持跨域
@Controller
@Api(value = "问题帮助", description = "问题帮助")
@RequestMapping("/adminapi/help")
public class HelpAdminController {

    @Autowired
    HelpService questionService;

    @RequestMapping(value = "/getQuestions", method = RequestMethod.GET)
    @ApiOperation("获得列表")
    @ResponseBody
    HelpsResponse getQuestions(
            @ApiParam(value = "pageNo") @RequestParam(required = false,defaultValue = "0") Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam(required = false,defaultValue = "99") Integer pageSize
    ) {

        Page<Help> page= questionService.findAllByPage(pageNo,pageSize);
        HelpsResponse response=new HelpsResponse();
        response.setHelps(page.getContent());
        response.setJpaPage(page);
        return response ;
    }

    @RequestMapping(value = "/getQuestion", method = RequestMethod.GET)
    @ApiOperation("问题提示")
    @ResponseBody
    HelpResponse getQuestion(
            @ApiParam(value = "id") @RequestParam() Integer id
    ) {
        Help question = questionService.findOne(id.longValue());
        HelpResponse response=new HelpResponse();
        response.setHelp(question);
        return response ;
    }

    @RequestMapping(value = "/addQuestion", method = RequestMethod.POST)
    @ApiOperation("新增问题提示")
    @ResponseBody
    BaseResponse addQuestion(
            @ApiParam("question") @RequestParam() String question,
            @ApiParam(value = "answer") @RequestParam() String answer
    ) {
        BaseResponse response = new BaseResponse();
        Help q = new Help();
       q.setQuestion(question);
       q.setAnswer(answer);
       q.setCreateTime(new Date());
       q.setPosition(0);
       questionService.save(q);
        return response;
    }

    @RequestMapping(value = "/editQuestion", method = RequestMethod.POST)
    @ApiOperation("编辑问题提示")
    @ResponseBody
    BaseResponse editQuestion(
            @ApiParam("id") @RequestParam() Integer id,
            @ApiParam("question") @RequestParam() String question,
            @ApiParam(value = "answer") @RequestParam() String answer
    ) {
        BaseResponse response = new BaseResponse();
        Help q = questionService.findOne(id.longValue());
        if (q != null){
            q.setQuestion(question);
            q.setAnswer(answer);
            q.setCreateTime(new Date());
            questionService.save(q);
        }
        return response;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiOperation("删除")
    @ResponseBody
    BaseResponse removeFeedBack(
            @ApiParam(value = "id") @RequestParam() Integer id
    ) {
        questionService.del(id.longValue());
        BaseResponse response=new BaseResponse();
        return response ;
    }

    @ApiOperation(value = "修改排序")
    @RequestMapping(value = "update_serial_position",method = RequestMethod.PUT)
    @ResponseBody
    BaseResponse putPosition(Integer[] ids) throws Exception {
        Help help;
        for(int i=0;i<ids.length;i++){
            help = questionService.findOne(ids[i]);
            if(help.getPosition()==ids.length-i){
                continue;
            }
            help.setPosition(ids.length-i);

            questionService.save(help);
        }
        return new BaseResponse();
    }

}
