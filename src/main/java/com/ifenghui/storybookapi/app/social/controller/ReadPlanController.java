package com.ifenghui.storybookapi.app.social.controller;

import com.ifenghui.storybookapi.app.social.entity.ReadPlan;
import com.ifenghui.storybookapi.app.social.entity.UserJoinReadPlan;
import com.ifenghui.storybookapi.app.social.response.*;
import com.ifenghui.storybookapi.app.social.service.ReadPlanService;
import com.ifenghui.storybookapi.app.transaction.entity.goods.Goods;
import com.ifenghui.storybookapi.app.transaction.response.GetGoodsResponse;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.entity.UserExtend;
import com.ifenghui.storybookapi.app.user.service.UserExtendService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/readPlan")
@Api(value = "阅读计划和推广大使", description = "阅读计划和推广大使")
public class ReadPlanController {

    @Autowired
    UserService userService;

    @Autowired
    UserExtendService userExtendService;

    @Autowired
    ReadPlanService readPlanService;

    @RequestMapping(value="/join_read_plan",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "增加阅读计划", notes = "增加阅读计划")
    JoinReadPlanResponse joinReadPlan(
            @ApiParam(value = "用户token")@RequestParam String  token,
            @ApiParam(value = "计划名称")@RequestParam Integer planName
    ) throws ApiNotTokenException {
        JoinReadPlanResponse response = new JoinReadPlanResponse();
        Long userId = userService.checkAndGetCurrentUserId(token);
        readPlanService.joinReadPlan(userId.intValue(), planName);
        return response;
    }

    @RequestMapping(value="/read_plan_detail",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "阅读计划详情", notes = "阅读计划详情")
    GetReadPlanResponse getReadPlan(
            @ApiParam(value = "用户token")@RequestParam(required = false) String  token
    ) throws ParseException {
        GetReadPlanResponse response = new GetReadPlanResponse();
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        String nowStr = format.format(now);
        Integer nowInt = Integer.parseInt(nowStr);
        ReadPlan readPlan = readPlanService.getOneMonthReadPlan(nowInt);
        response.setIsJoinReadPlan(0);
        if(token != null) {
            Long userId = userService.checkAndGetCurrentUserId(token);
            UserJoinReadPlan userJoinReadPlan = readPlanService.getUserJoinReadPlanByUserIdAndPlanName(userId.intValue(), nowInt);
            if(userJoinReadPlan != null){
                response.setIsJoinReadPlan(1);
            }
        }
        response.setReadPlan(readPlan);
        return response;
    }

    @RequestMapping(value="/user_read_plan_list",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取阅读计划日历数据", notes = "增加阅读计划")
    GetUserReadPlanRecordListRespone getUserReadPlanRecordList(
            @ApiParam(value = "用户token")@RequestParam(required = false) String  token,
            @ApiParam(value = "计划名称")@RequestParam Integer planName
    ) {
        GetUserReadPlanRecordListRespone respone = new GetUserReadPlanRecordListRespone();
        if(!TextUtils.isEmpty(token)){
            Long userId = userService.checkAndGetCurrentUserId(token);
            respone.setUserReadPlanRecordList(readPlanService.getUserReadPlanRecordListByPlanNameAndUserId(planName, userId.intValue()));
            return respone;
        } else {
            return respone;
        }
    }

    @RequestMapping(value="/user_share_detail",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "推广大使数据", notes = "推广大使数据")
    GetUserExtendResponse getUserExtend(
            @ApiParam(value = "用户token")@RequestParam(required = false) String  token
    ) {
        GetUserExtendResponse response = new GetUserExtendResponse();
        if(token != null) {
            Long userId = userService.checkAndGetCurrentUserId(token);
            response.setUserExtend(userExtendService.findUserExtendByUserId(userId));
        }
        return response;
    }

    @RequestMapping(value="/user_share_friend_list",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "分享用户列表", notes = "分享用户列表")
    GetShareUserPageResponse getShareUserPage(
            @ApiParam(value = "用户token")@RequestParam(required = false) String  token,
            @ApiParam(value = "pageNo") @RequestParam Integer pageNo,
            @ApiParam(value = "pageSize")@RequestParam Integer pageSize
    ){
        GetShareUserPageResponse response = new GetShareUserPageResponse();
        Long userId = userService.checkAndGetCurrentUserId(token);
        Page<UserExtend> userPage = userExtendService.getUsersByUserExtendParentId(userId.intValue(), pageNo, pageSize);
        List<User> userList = userExtendService.getUserListFromUserExtendList(userPage.getContent());
        response.setUserList(userList);
        response.setJpaPage(userPage);
        return response;
    }


    @RequestMapping(value="/read_plan_reward",method = RequestMethod.GET)
    @ApiOperation(value = "获奖名单介绍详情", notes = "")
    void getReadPlanUserReward(
            @ApiParam(value = "planName")@RequestParam Integer planName,
            HttpServletResponse httpServletResponse
    ) throws ApiException {
        ReadPlan readPlan = readPlanService.getReadPlan(planName);
        try {
            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.getWriter().print("<!DOCTYPE html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
            httpServletResponse.getWriter().print("<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1.0, maximum-scale=1,user-scalable=yes\" />");
            httpServletResponse.getWriter().print("<meta name=\"format-detection\" content=\"telephone=no\" />");
            httpServletResponse.getWriter().print("<style>p{width:100%;display:block;margin:auto;font-size: 14px;color: #666666;margin: 0px auto;line-height: 21px;}img{display:block;margin-bottom:0px;width:100%;}body{margin:0px;}</style></head><body>");
            httpServletResponse.getWriter().print(readPlan.getRewardUserList());
            httpServletResponse.getWriter().print("</body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
