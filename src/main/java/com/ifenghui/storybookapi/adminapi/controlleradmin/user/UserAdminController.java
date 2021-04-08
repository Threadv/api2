package com.ifenghui.storybookapi.adminapi.controlleradmin.user;


import com.ifenghui.storybookapi.adminapi.controlleradmin.user.resp.UserAbilityPlanRelatesResponse;
import com.ifenghui.storybookapi.adminapi.controlleradmin.user.resp.WeekPlanJoinsResponse;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.response.GetUserSerialStorysResponse;
import com.ifenghui.storybookapi.app.story.service.IpLabelService;
import com.ifenghui.storybookapi.app.story.service.SerialStoryService;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanJoin;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanJoinService;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.UserAbilityPlanRelate;
import com.ifenghui.storybookapi.app.transaction.service.UserAbilityPlanRelateService;
import com.ifenghui.storybookapi.app.transaction.service.impl.UserAbilityPlanRelateServiceImpl;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.entity.UserExtend;
import com.ifenghui.storybookapi.app.user.response.GetUserResponse;
import com.ifenghui.storybookapi.app.user.response.GetUsersResponse;
import com.ifenghui.storybookapi.app.user.service.UserExtendService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "故事集管理", description = "故事集管理api")
@RequestMapping("/adminapi/user")
public class UserAdminController {

    @Autowired
    UserService userService;

    @Autowired
    UserExtendService userExtendService;
    @Autowired
    HttpServletRequest request;

    @Autowired
    UserAbilityPlanRelateService userAbilityPlanRelateService;

    @Autowired
    WeekPlanJoinService weekPlanJoinService;

    @ApiOperation(value = "获得系列故事集列表")
    @RequestMapping(value = "/get_user_list",method = RequestMethod.GET)
    @ResponseBody
    GetUsersResponse getUserListAdmin(
            @ApiParam(value = "nick") @RequestParam(required = false,defaultValue = "") String nick,
            @ApiParam(value = "sid") @RequestParam(required = false,defaultValue = "") String sid,
            @ApiParam(value = "phone") @RequestParam(required = false,defaultValue = "") String phone,
        @ApiParam(value = "pageNo") @RequestParam(required = false) Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam Integer pageSize,
            @ApiParam(value = "id")  Integer id
    ) throws ApiNotTokenException {
        if(pageNo==null){
            pageNo=0;
        }

        GetUsersResponse response=new GetUsersResponse();
        User findUser=new User();
        if(nick!=null&&!"".equals(nick)){
            findUser.setNick(nick);
        }
        if(phone!=null&&!"".equals(phone)){
            findUser.setPhone(phone);
        }
        if(sid!=null&&sid.length()>4){
            id= UserUtil.getUidBySid(sid);
        }
        if(id!=null){
            findUser.setId(id.longValue());
        }
        Page<User> userPage= userService.findAll(findUser,new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));
        response.setUsers(userPage.getContent());
        response.setJpaPage(userPage);
        return response;
    }

    @ApiOperation(value = "获得系列故事集列表")
    @RequestMapping(value = "/get_user",method = RequestMethod.GET)
    @ResponseBody
    GetUserResponse getUserAdmin(
            @ApiParam(value = "id")  Integer id
    ) throws ApiNotTokenException {


        GetUserResponse response=new GetUserResponse();
        User user=userService.getUser(id.longValue());
        UserExtend userExtend=userExtendService.findUserExtendByUserId(id.longValue());

        response.setUser(user);
        response.setUserExtend(userExtend);
//        Page<User> userPage= userService.findAll(findUser,new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));
//        response.setUsers(userPage.getContent());
//        response.setJpaPage(userPage);
        return response;
    }

    @ApiOperation(value = "修改用户状态")
    @RequestMapping(value = "/update_user",method = RequestMethod.PUT)
    @ResponseBody
    BaseResponse updateUserAdmin(
            @ApiParam(value = "isTest") @RequestParam(required = false) Integer isTest,//修改测试状态
            @ApiParam(value = "unsubsribe") @RequestParam(required = false) Integer unsubsribe,//注销状态
            @ApiParam(value = "deviceLimitNum") @RequestParam(required = false) Integer deviceLimitNum,//修改设备限制
            @ApiParam(value = "weekPlanType") @RequestParam(required = false) Integer weekPlanType,//切换年龄段
            @ApiParam(value = "password") @RequestParam(required = false) String password,
            @ApiParam(value = "isAbilityPlan") @RequestParam(required = false) Integer isAbilityPlan,
            @ApiParam(value = "id")  Integer id
    ) throws ApiNotTokenException {

        User user=userService.getUser(id.longValue());
        if(isTest!=null){
            user.setIsTest(isTest);
        }
        if(unsubsribe!=null&&user.getUnsubscribe().intValue() != unsubsribe){
            user.setUnsubscribe(unsubsribe);
            if(unsubsribe==1){
                user.setPhone(user.getPhone()+"-"+user.getId());
            }
        }
        if(isAbilityPlan!=null){
            user.setIsAbilityPlan(isAbilityPlan);
        }
        userService.updateUser(user);
        UserExtend userExtend=userExtendService.findUserExtendByUserId(id.longValue());
        if(deviceLimitNum!=null){
            userExtend.setDeviceLimitNum(deviceLimitNum);
        }
        if(weekPlanType!=null){
            userExtend.setWeekPlanType(weekPlanType);
        }

        if(password!=null&&password.length()>5){
            user.setPassword(password);
        }
        userExtendService.update(userExtend);

        return new BaseResponse();
    }

    @ApiOperation(value = "注销账号")
    @RequestMapping(value = "/remove_user",method = RequestMethod.DELETE)
    @ResponseBody
    BaseResponse removeUser(
            @ApiParam(value = "id")  Integer id
    ) throws ApiNotTokenException {
        userService.removeUser(id);


        return new BaseResponse();
    }


}
