package com.ifenghui.storybookapi.app.transaction.controller;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanJoin;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanJoinService;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.AbilityPlanOrder;
import com.ifenghui.storybookapi.app.transaction.service.AbilityPlanOrderService;
import com.ifenghui.storybookapi.app.transaction.service.UserAbilityPlanRelateService;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Date: 2018/11/9 10:37
 * @Description:
 */
@Controller
@EnableAutoConfiguration
@Api(value = "宝宝会读（优能计划）相关", description = "宝宝会读（优能计划）相关")
@RequestMapping("/api/abilityPlan")
public class AbilityPlanController {

    @Autowired
    UserAbilityPlanRelateService userAbilityPlanRelateService;

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Autowired
    WeekPlanJoinService weekPlanJoinService;

    @Autowired
    AbilityPlanOrderService abilityPlanOrderService;




    @ApiOperation(value = "免费一周未推送过的补充数据week_plan_join——buy_num")
    @RequestMapping(value = "/add_one_week_to_user", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse addBuyNum22(
            @ApiParam(value = "钥匙") @RequestParam() String pass
    ) {
        if (!"vista688".equals(pass)) {

            throw new ApiNotFoundException("密码错误");
        }
        Pageable pageable = new PageRequest(0, 30, new Sort(Sort.Direction.ASC, "id"));
        Page<User> userPage = userDao.findAll(pageable);
        //补第一周数据修改planChangeCount
//        weekPlanJoinService.updateUserExtendPlanChangeCount(userPage.getContent());
        for (int i = 1; i < userPage.getTotalPages(); i++) {
            pageable = new PageRequest(i, 30, new Sort(Sort.Direction.ASC, "id"));
//            userPage = userDao.findAll(pageable);
//            weekPlanJoinService.updateUserExtendPlanChangeCount(userPage.getContent());
        }
        return new BaseResponse();
    }

    @ApiOperation(value = "补充数据week_plan_join——buy_num")
    @RequestMapping(value = "/add_buyNum", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse addBuyNum(@ApiParam(value = "钥匙") @RequestParam() String pass
    ) {
        if (!"vista688".equals(pass)) {

            throw new ApiNotFoundException("密码错误");
        }
        Page<WeekPlanJoin> weekPlanJoinPage = weekPlanJoinService.getWeekPlanJoinByBuyNum(0, 30);
//        weekPlanJoinService.updateOrderPlanStyle(weekPlanJoinPage.getContent());
//        weekPlanJoinService.addBuyNum(weekPlanJoinPage.getContent());
        for (int i = 1; i < weekPlanJoinPage.getTotalPages(); i++) {
            weekPlanJoinPage = weekPlanJoinService.getWeekPlanJoinByBuyNum(i, 30);
//            weekPlanJoinService.updateOrderPlanStyle(weekPlanJoinPage.getContent());
//            weekPlanJoinService.addBuyNum(weekPlanJoinPage.getContent());
        }
        return new BaseResponse();
    }


    @ApiOperation(value = "VIP用户补充数据week_plan_join——buy_num")
    @RequestMapping(value = "/add_buyNum_vip", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse addBuyNumVip(@ApiParam(value = "钥匙") @RequestParam() String pass
    ) {

        if (!"vista688".equals(pass)) {

            throw new ApiNotFoundException("密码错误");
        }
        Pageable pageable = new PageRequest(0, 30, new Sort(Sort.Direction.ASC, "id"));
        Page<User> userPage = userDao.findAllBySvipLevelThreeAndFour(pageable);
//        weekPlanJoinService.addBuyNumVip(userPage.getContent());
        for (int i = 1; i < userPage.getTotalPages(); i++) {
            pageable = new PageRequest(i, 30, new Sort(Sort.Direction.ASC, "id"));
//            userPage = userDao.findAllBySvipLevelThreeAndFour(pageable);
//            weekPlanJoinService.addBuyNumVip(userPage.getContent());
        }
        return new BaseResponse();
    }


    @ApiOperation(value = "补充用户relate")
    @RequestMapping(value = "/add_relate", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse addrelateBy(@ApiParam(value = "钥匙") @RequestParam() String pass
    ) {

        if (!"vista688".equals(pass)) {

            throw new ApiNotFoundException("密码错误");
        }
        Pageable pageable = new PageRequest(0, 30, new Sort(Sort.Direction.ASC, "id"));
        Page<User> userPage = userDao.findAll(pageable);
//        abilityPlanOrderService.addRelateByVip(userPage.getContent());
        for (int i = 1; i < userPage.getTotalPages(); i++) {
            pageable = new PageRequest(i, 30, new Sort(Sort.Direction.ASC, "id"));
            userPage = userDao.findAll(pageable);
//            abilityPlanOrderService.addRelateByVip(userPage.getContent());
        }

        return new BaseResponse();
    }


    @ApiOperation(value = "修改vip的buynum")
    @RequestMapping(value = "/upd_buy_num", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse updnum(@ApiParam(value = "钥匙") @RequestParam() String pass
    ) {

        if (!"vista688".equals(pass)) {
            throw new ApiNotFoundException("密码错误");
        }
        Pageable pageable = new PageRequest(0, 30, new Sort(Sort.Direction.ASC, "id"));
        Page<AbilityPlanOrder> orderPage = abilityPlanOrderService.getOrdersByTypeAndStatus(0, 1, pageable);
//        abilityPlanOrderService.updateBuyNum(orderPage.getContent());
        for (int i = 1; i < orderPage.getTotalPages(); i++) {
            pageable = new PageRequest(i, 30, new Sort(Sort.Direction.ASC, "id"));
//            orderPage = abilityPlanOrderService.getOrdersByTypeAndStatus(0, 1, pageable);
//            abilityPlanOrderService.updateBuyNum(orderPage.getContent());
        }
        return new BaseResponse();
    }

    @ApiOperation(value = "通过VIP补充用户订单")
    @RequestMapping(value = "/add_order_by_vip", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse addOrderByVip(@ApiParam(value = "钥匙") @RequestParam() String pass
    ) {

        if (!"vista688".equals(pass)) {

            throw new ApiNotFoundException("密码错误");
        }
        Pageable pageable = new PageRequest(0, 30, new Sort(Sort.Direction.ASC, "id"));
        Page<User> userPage = userDao.findAllBySvipLevelThreeAndFour(pageable);
//        abilityPlanOrderService.addOrderByVip(userPage.getContent());
        for (int i = 1; i < userPage.getTotalPages(); i++) {
            pageable = new PageRequest(i, 30, new Sort(Sort.Direction.ASC, "id"));
//            userPage = userDao.findAllBySvipLevelThreeAndFour(pageable);
//            abilityPlanOrderService.addOrderByVip(userPage.getContent());
        }

        return new BaseResponse();
    }

    @ApiOperation(value = "通过VIP补充推送记录")
    @RequestMapping(value = "/add_record_by_vip", method = RequestMethod.POST)
    @ResponseBody
    BaseResponse addRecordByVip(@ApiParam(value = "钥匙") @RequestParam() String pass
    ) {

        if (!"vista688".equals(pass)) {

            throw new ApiNotFoundException("密码错误");
        }
        Pageable pageable = new PageRequest(0, 30, new Sort(Sort.Direction.ASC, "id"));
        Page<User> userPage = userDao.findAllBySvipLevelThreeAndFour(pageable);
//        abilityPlanOrderService.addRecordByVip(userPage.getContent());
        for (int i = 1; i < userPage.getTotalPages(); i++) {
            pageable = new PageRequest(i, 30, new Sort(Sort.Direction.ASC, "id"));
//            userPage = userDao.findAllBySvipLevelThreeAndFour(pageable);
//            abilityPlanOrderService.addRecordByVip(userPage.getContent());
        }
        return new BaseResponse();
    }


}

