package com.ifenghui.storybookapi.adminapi.controlleradmin.ability;


import com.ifenghui.storybookapi.adminapi.controlleradmin.resp.AbilityPlanOrdersResponse;
import com.ifenghui.storybookapi.adminapi.controlleradmin.user.resp.UserAbilityPlanRelatesResponse;
import com.ifenghui.storybookapi.adminapi.controlleradmin.user.resp.WeekPlanJoinsResponse;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanIntro;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanJoin;
import com.ifenghui.storybookapi.app.studyplan.response.GetWeekPlanIntroPageResponse;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanIntroService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanJoinService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanUserRecordService;
import com.ifenghui.storybookapi.app.studyplan.service.impl.WeekPlanUserRecordServiceImpl;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.AbilityPlanOrder;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.UserAbilityPlanRelate;
import com.ifenghui.storybookapi.app.transaction.service.AbilityPlanOrderService;
import com.ifenghui.storybookapi.app.transaction.service.UserAbilityPlanRelateService;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderMixService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.entity.UserExtend;
import com.ifenghui.storybookapi.app.user.response.GetUserResponse;
import com.ifenghui.storybookapi.app.user.response.GetUsersResponse;
import com.ifenghui.storybookapi.app.user.service.UserExtendService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.style.AdminResponseType;
import com.ifenghui.storybookapi.style.RechargeStyle;
import com.ifenghui.storybookapi.style.WeekPlanStyle;
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
@RequestMapping("/adminapi/ability")
public class AbilityAdminController {

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

    @Autowired
    WeekPlanIntroService weekPlanIntroService;

    @Autowired
    WeekPlanUserRecordService weekPlanUserRecordService;
    @Autowired
    AbilityPlanOrderService abilityPlanOrderService;

    @Autowired
    OrderMixService orderMixService;

    @ApiOperation(value = "获得优能计划权订单列表")
    @RequestMapping(value = "/get_user_ability_order_list",method = RequestMethod.GET)
    @ResponseBody
    AbilityPlanOrdersResponse getUserAblityPlanOrderListAdmin(
//            @ApiParam(value = "userId")  Integer userId,
//            @ApiParam(value = "isDel")  Integer isDel,
//            @ApiParam(value = "status")  Integer status,
            @ApiParam(value = "pageNo")  Integer pageNo,
            @ApiParam(value = "pageSize")  Integer pageSize,
//            @ApiParam(value = "isTest")  Integer isTest,
//            @ApiParam(value = "isCode")  Integer isCode
            AbilityQuery abilityQuery
    ) throws ApiNotTokenException {
        if(pageSize==null){
            pageSize=10;
        }
        if(pageNo==null){
            pageNo=0;
        }
//        AbilityPlanOrder order=new AbilityPlanOrder();
//        if(abilityQuery.getIsDel()!=null){
//            order.setIsDel(abilityQuery.getIsDel());
//        }
//        if(abilityQuery.getStatus()!=null){
//            order.setStatus(abilityQuery.getStatus());
//        }
//        if(abilityQuery.getUserId()!=null){
//            order.setUserId(abilityQuery.getUserId());
//        }
//        if(abilityQuery.getIsTest()!=null){
//            order.setIsTest(abilityQuery.getIsTest());
//        }


        Page<AbilityPlanOrder> page= abilityPlanOrderService.findAll(abilityQuery,new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));



        AbilityPlanOrdersResponse response=new AbilityPlanOrdersResponse();

        response.setAbilityPlanOrders(page.getContent());
        response.setJpaPage(page);

        return response;
    }
    @ApiOperation(value = "撤销订单")
    @RequestMapping(value = "/remove_user_ability_order",method = RequestMethod.DELETE)
    @ResponseBody
    BaseResponse removeUserAblityPlanOrderListAdmin(
            @ApiParam(value = "id") @RequestParam() Integer id
    ) throws ApiNotTokenException {
        AbilityPlanOrder order= abilityPlanOrderService.getAbilityPlanOrder(id);
        orderMixService.cancelOrder(order.getUserId(),id, RechargeStyle.BUY_ABILITY_PLAN);


        return new BaseResponse();
    }

    @ApiOperation(value = "获得优能计划权益周期列表")
    @RequestMapping(value = "/get_user_ability_plan_relate_list",method = RequestMethod.GET)
    @ResponseBody
    UserAbilityPlanRelatesResponse getUserAblityPlanListAdmin(
                    @ApiParam(value = "userId") @RequestParam() Integer userId
            ) throws ApiNotTokenException {

        UserAbilityPlanRelatesResponse response=new UserAbilityPlanRelatesResponse();
        UserAbilityPlanRelate userAbilityPlanRelate=new UserAbilityPlanRelate();
        userAbilityPlanRelate.setUserId(userId);
        Page<UserAbilityPlanRelate> planRelatePage= userAbilityPlanRelateService.findAll(userAbilityPlanRelate,new PageRequest(0,20,new Sort(Sort.Direction.DESC,"id")));

        response.setUserAbilityPlanRelates(planRelatePage.getContent());
        response.setJpaPage(planRelatePage);
//        Page<User> userPage= userService.findAll(findUser,new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));
//        response.setUsers(userPage.getContent());
//        response.setJpaPage(userPage);
        return response;
    }

    @ApiOperation(value = "获得优能计划权益周期列表")
    @RequestMapping(value = "/remove_user_ability_plan_relate",method = RequestMethod.DELETE)
    @ResponseBody
    BaseResponse removeUserAblityPlanListAdmin(
            @ApiParam(value = "id") @RequestParam() Integer id
    ) throws ApiNotTokenException {
        userAbilityPlanRelateService.delete(id);

        return new BaseResponse();
    }

    @ApiOperation(value = "周计划已购周数")
    @RequestMapping(value = "/get_user_week_plan_join_list",method = RequestMethod.GET)
    @ResponseBody
    WeekPlanJoinsResponse getUserWeekPlanListAdmin(
            @ApiParam(value = "userId") @RequestParam() Integer userId
    ) throws ApiNotTokenException {

        WeekPlanJoinsResponse response=new WeekPlanJoinsResponse();
        WeekPlanJoin weekPlanJoin=new WeekPlanJoin();
        weekPlanJoin.setUserId(userId);
        Page<WeekPlanJoin> weekPlanJoinPage= weekPlanJoinService.findAll(weekPlanJoin,new PageRequest(0,5));


        response.setWeekPlanJoins(weekPlanJoinPage.getContent());
        response.setJpaPage(weekPlanJoinPage);
//        Page<User> userPage= userService.findAll(findUser,new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));
//        response.setUsers(userPage.getContent());
//        response.setJpaPage(userPage);
        return response;
    }

    @ApiOperation(value = "撤销已购周数")
    @RequestMapping(value = "/clear_user_week_plan_join",method = RequestMethod.DELETE)
    @ResponseBody
    BaseResponse getUserWeekPlanListAdmin(
            @ApiParam(value = "userId") @RequestParam() Integer userId,
            @ApiParam(value = "weekPlanId") @RequestParam() Integer weekPlanId
    ) throws ApiNotTokenException {

        WeekPlanJoin weekPlanJoin=weekPlanJoinService.getWeekPlanJoinByUserIdAndType(userId,WeekPlanStyle.getById(weekPlanId));
        if(weekPlanJoin!=null){
            weekPlanJoin.setBuyNum(1);
            weekPlanJoinService.update(weekPlanJoin);
        }

        return new BaseResponse();
    }

    @ApiOperation(value = "修改购买周数")
    @RequestMapping(value = "/put_user_week_plan_join",method = RequestMethod.PUT)
    @ResponseBody
    BaseResponse getUserWeekPlanListAdmin(
            @ApiParam(value = "userId") @RequestParam() Integer userId,
            @ApiParam(value = "weekPlanId") @RequestParam() Integer weekPlanId,
            @ApiParam(value = "buyNum") @RequestParam() Integer buyNum
    ) throws ApiNotTokenException {

        WeekPlanJoin weekPlanJoin=weekPlanJoinService.getWeekPlanJoinByUserIdAndType(userId,WeekPlanStyle.getById(weekPlanId));
        if(weekPlanJoin!=null){
            weekPlanJoin.setBuyNum(buyNum);
            weekPlanJoinService.update(weekPlanJoin);
        }

        return new BaseResponse();
    }

    //WeekPlanUserRecord 推送记录

    @ApiOperation(value = "周计划记录")
    @RequestMapping(value = "/get_week_plan_intro_page", method = RequestMethod.GET)
    @ResponseBody
    GetWeekPlanIntroPageResponse getWeekPlanIntroPageAdmin(
            @ApiParam(value = "userId") Integer userId,
            @ApiParam(value = "planStyle") @RequestParam() WeekPlanStyle planStyle
    ) {
/*
TWO_FOUR(1,"2到4岁"),
    FOUR_SIX(2,"4到6岁"),
 */
        if (planStyle.getId() == 0) {
            throw new ApiNotFoundException("年龄段未选择！");
        }


        Page<WeekPlanIntro> weekPlanIntroPage = weekPlanIntroService.getWeekPlanIntroPage(0, 53, planStyle, userId.intValue());
        GetWeekPlanIntroPageResponse response = new GetWeekPlanIntroPageResponse();
        response.setWeekPlanIntroList(weekPlanIntroPage.getContent());
        response.setJpaPage(weekPlanIntroPage);

        return response;
    }
    @ApiOperation(value = "推送周计划")
    @RequestMapping(value = "/add_week_plan_user_record", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse getWeekPlanIntroPageAdmin(
            @ApiParam(value = "userId") Integer userId,
            @ApiParam(value = "weekPlanId") @RequestParam() Integer weekPlanId
    ) {
/*
TWO_FOUR(1,"2到4岁"),
    FOUR_SIX(2,"4到6岁"),
 */
        BaseResponse response=new BaseResponse();
        WeekPlanIntro weekPlanIntro= weekPlanIntroService.findOne(weekPlanId);

        //验证购买数
        WeekPlanJoin weekPlanJoin= weekPlanJoinService.getWeekPlanJoinByUserIdAndType(userId,weekPlanIntro.getWeekPlanStyle());

        if(weekPlanIntro.getWeekNum()>weekPlanJoin.getBuyNum()){
            response=new BaseResponse(AdminResponseType.PARAM_CHECK_ERR);
            response.getStatus().setMsg("还未购买无法推送浏览权");
            return response;
        }


        //增加推送
        weekPlanUserRecordService.addWeekPlanUserRecord(weekPlanId,userId,0);

        return response;
    }
    //story_week_plan_user_record


//    @ApiOperation(value = "修改年龄段")
//    @RequestMapping(value = "/update_week_plan_user_join", method = RequestMethod.POST)
//    @ResponseBody
//    BaseResponse getWeekPlanIntroPageAdmin(
//            @ApiParam(value = "userId") Integer userId,
//            @ApiParam(value = "weekPlanId") @RequestParam() Integer weekPlanId
//    ) {
///*
//TWO_FOUR(1,"2到4岁"),
//    FOUR_SIX(2,"4到6岁"),
// */
//        BaseResponse response=new BaseResponse();
//        WeekPlanIntro weekPlanIntro= weekPlanIntroService.findOne(weekPlanId);
//
//        //验证购买数
//        WeekPlanJoin weekPlanJoin= weekPlanJoinService.getWeekPlanJoinByUserIdAndType(userId,weekPlanIntro.getWeekPlanStyle());
//
//        if(weekPlanIntro.getWeekNum()>weekPlanIntro.getWeekNum()){
//            response=new BaseResponse(AdminResponseType.PARAM_CHECK_ERR);
//            return response;
//        }
//
//
//        //增加推送
//        weekPlanUserRecordService.addWeekPlanUserRecord(weekPlanId,userId,0);
//
//        return response;
//    }
}
