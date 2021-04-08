package com.ifenghui.storybookapi.adminapi.controlleradmin.feedback;

import com.ifenghui.storybookapi.app.app.entity.Feedback;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.app.response.FeedbacksResponse;
import com.ifenghui.storybookapi.app.app.response.GetFeedbackResponse;
import com.ifenghui.storybookapi.app.app.response.GetFeedbacksResponse;
import com.ifenghui.storybookapi.app.app.service.FeedbackService;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterOrder;
import com.ifenghui.storybookapi.app.express.response.ExpressCenterOrderResponse;
import com.ifenghui.storybookapi.app.express.response.ExpressCenterOrdersResponse;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterOrderService;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterPhoneBindService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.style.ExpressSrcStyle;
import com.ifenghui.storybookapi.style.PublishType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)//支持跨域
@Controller
@Api(value = "用户反馈", description = "用户反馈")
@RequestMapping("/adminapi/feedback")
public class FeedbackAdminController {

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/getFeedbacks", method = RequestMethod.GET)
    @ApiOperation("获得反馈列表")
    @ResponseBody
    GetFeedbacksResponse getFeedbacks(
            @ApiParam(value = "status") @RequestParam(required = false) Integer status,
            @ApiParam(value = "userId") @RequestParam(required = false) Integer userId,
            @ApiParam(value = "pageNo") @RequestParam(required = false,defaultValue = "0") Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam(required = false,defaultValue = "20") Integer pageSize
    ) {
        Feedback feedback = new Feedback();
        if (status != null){
            feedback.setStatus(status);
        }
        if (userId != null){
            feedback.setUserId(userId.longValue());
        }
        Page<Feedback> feedbackPage= feedbackService.findAll(feedback,pageNo,pageSize);

        GetFeedbacksResponse response=new GetFeedbacksResponse();
        response.setFeedbacks(feedbackPage.getContent());
        response.setJpaPage(feedbackPage);
        return response ;
    }

    @RequestMapping(value = "/getFeedback", method = RequestMethod.GET)
    @ApiOperation("反馈信息")
    @ResponseBody
    GetFeedbackResponse getFeedBack(
            @ApiParam(value = "id") @RequestParam() Integer id
    ) {
        Feedback feedback = feedbackService.getFeedback(id.longValue());
        GetFeedbackResponse response=new GetFeedbackResponse();
        response.setFeedback(feedback);
        return response ;
    }

    @RequestMapping(value = "/addFeedback", method = RequestMethod.POST)
    @ApiOperation("提交意见反馈回复")
    @ResponseBody
    BaseResponse addFeedback(
            @ApiParam("contact") @RequestParam() String contact,
            @ApiParam(value = "id") @RequestParam() Integer id
    ) {
        BaseResponse response = new BaseResponse();
        Feedback feedback=feedbackService.getFeedback(id.longValue());
        if (feedback != null) {
            feedback.setContact(contact);
            feedback.setStatus(1);
            feedback.setReadType(0);
            feedbackService.addFeedback(feedback);
        }

        return response;
    }

    @RequestMapping(value = "/removeFeedBack", method = RequestMethod.GET)
    @ApiOperation("删除反馈信息")
    @ResponseBody
    BaseResponse removeFeedBack(
            @ApiParam(value = "id") @RequestParam() Integer id
    ) {
        feedbackService.del(id.longValue());
        BaseResponse response=new BaseResponse();
        return response ;
    }

}
