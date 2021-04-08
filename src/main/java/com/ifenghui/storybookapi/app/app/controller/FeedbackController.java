package com.ifenghui.storybookapi.app.app.controller;

/**
 * Created by jia on 2016/12/22.
 * 意见反馈
 */

import com.ifenghui.storybookapi.api.response.base.ApiStatus;
import com.ifenghui.storybookapi.app.app.response.AddFeedbackResponse;
import com.ifenghui.storybookapi.app.app.response.GetFeedbackResponse;
import com.ifenghui.storybookapi.app.app.response.GetFeedbacksByUserIdResponse;
import com.ifenghui.storybookapi.app.app.response.GetFeedbacksResponse;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.app.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;

import com.ifenghui.storybookapi.app.app.entity.Feedback;
import com.ifenghui.storybookapi.app.app.service.FeedbackService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/feedback")
@Api(value = "用户反馈信息",description = "用户反馈信息")
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;

    @Autowired
    UserService userService;

    @RequestMapping(value="/getFeedback",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取反馈详情",notes = "根据id获取反馈详情")
    GetFeedbackResponse getFeedback(@ApiParam(value = "ID")@RequestParam Long id) {
//        return "Hello World!";
        Feedback feedback=feedbackService.getFeedback(id);
        GetFeedbackResponse getFeedbackResponse=new GetFeedbackResponse();
        System.out.println(feedback);
        getFeedbackResponse.setFeedback(feedback);

        return getFeedbackResponse;
    }

    @RequestMapping(value="/getFeedbacksByUserId",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取某个用户的多条反馈",notes = "获取某个用户的多条反馈")
    GetFeedbacksByUserIdResponse getFeedbacksByUserId(@ApiParam(value = "用户token")@RequestParam String  token,
                                                      @ApiParam(value = "页码")@RequestParam Integer pageNo,
                                                      @ApiParam(value = "条数")@RequestParam Integer pageSize
    ) {
//        return "Hello World!";
        Long userId= null;
        try {
            userId = userService.checkAndGetCurrentUserId(token);
        } catch (ApiNotTokenException e) {
            e.setApimsg("用户token不存在");
        }
        Page<Feedback> page=feedbackService.getFeedbacksByUserIdAndPage(userId,pageNo,pageSize);

        for (Feedback f:page.getContent()){
            f.setReadType(1);
            feedbackService.addFeedback(f);
        }

        GetFeedbacksByUserIdResponse getFeedbacksByUserIdResponse=new GetFeedbacksByUserIdResponse();
        getFeedbacksByUserIdResponse.setFeedbacks(page.getContent());
//        getFeedbacksByUserIdResponse.setPage(page);
        return getFeedbacksByUserIdResponse;
    }

    @RequestMapping(value="/getFeedbacks",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分页获取多条反馈",notes = "分页获取多条反馈")
    GetFeedbacksResponse getFeedbacks(@ApiParam(value = "页码")@RequestParam int pageNo,
                                      @ApiParam(value = "每页数目") @RequestParam int pageSize
    ) {

        Page<Feedback> feedbacks=feedbackService.getFeedbacksByPage(pageNo,pageSize);
        List<Feedback> feedbacks1=feedbacks.getContent();
        GetFeedbacksResponse t=new GetFeedbacksResponse();
        System.out.println(feedbacks1);
        t.setFeedbacks(feedbacks1);
        return t;
    }


    @RequestMapping(value="/addFeedback",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加反馈",notes = "")
    AddFeedbackResponse addFeedback(@RequestHeader("ssToken") String ssToken,
                                    @ApiParam(value = "反馈内容")@RequestParam String content,
                                    @ApiParam(value = "联系方式")@RequestParam String contactInfo,
                                    @ApiParam(value = "版本")@RequestParam String ver
        ) throws ApiNotTokenException {
            Long userId;
            if(ssToken == null || ssToken.length() <= 0){
                userId = 0L;
            }else{
                userId = userService.checkAndGetCurrentUserId(ssToken);
            }
        AddFeedbackResponse addFeedbackResponse=new AddFeedbackResponse();
            if (userId == null || userId == 0){
                ApiStatus status = new ApiStatus();
                status.setCode(0);
                status.setMsg("未登录");
                addFeedbackResponse.setStatus(status);
                return addFeedbackResponse;
            }

            Feedback feedback=new Feedback();
            Date createTime=new Date();

            feedback.setCreateTime(createTime);
            feedback.setContent(content);
            feedback.setContactInfo(contactInfo);
            feedback.setVer(ver);
            feedback.setUserId(userId);
            feedback.setReadType(1);
            feedback.setStatus(0);

            Feedback f=feedbackService.addFeedback(feedback);
            System.out.println(f);
            addFeedbackResponse.setFeedback(f);
            addFeedbackResponse.getStatus().setMsg("意见反馈成功");
            return addFeedbackResponse;

    }

}
