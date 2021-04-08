package com.ifenghui.storybookapi.app.presale.admin;


import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSalePay;
import com.ifenghui.storybookapi.app.presale.entity.PreSalePayCallBack;
import com.ifenghui.storybookapi.app.presale.response.PreSalePayCallbacksResponse;
import com.ifenghui.storybookapi.app.presale.response.PreSalePaysResponse;
import com.ifenghui.storybookapi.app.presale.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "订单管理")
@RequestMapping("/sale/presaleadmin/order")
public class SaleOrderAdminController {


    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;


    @Autowired
    Environment env;

    @Autowired
    PreSalePayService preSalePayService;

    @Autowired
    PreSaleGoodsService preSaleGoodsService;

    @Autowired
    ActivityService activityService;

    @Autowired
    PreSalePayCallBackService payCallBackService;

    @Autowired
    PreSaleUserService userService;

    private static Logger logger = Logger.getLogger(SaleOrderAdminController.class);


    @RequestMapping(value = "/get_orders", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "活动订单", notes = "活动列表")
    PreSalePaysResponse getOrdersAdmin(
            @ApiParam(value = "activityId") @RequestParam(required = false) Integer activityId,
            @ApiParam(value = "goodsId") @RequestParam(required = false) Integer goodsId,
            @ApiParam(value = "status") @RequestParam(required = false) Integer status,
            @ApiParam(value = "pageNo") @RequestParam Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam Integer pageSize
    ) {
        PreSalePay preSalePay=new PreSalePay();
        preSalePay.setActivityId(activityId);
        preSalePay.setGoodsId(goodsId);
        preSalePay.setStatus(status);
        PreSalePaysResponse response = new PreSalePaysResponse();
        Page<PreSalePay> preSalePays = preSalePayService.findAll(preSalePay,new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));

        for(PreSalePay preSalePayItem:preSalePays.getContent()){
            if(preSalePayItem.getGoodsId()!=null){
                preSalePayItem.setPreSaleGoods(preSaleGoodsService.findGoodsById(preSalePayItem.getGoodsId()));
            }
            if(preSalePayItem.getActivityId()!=null){
                preSalePayItem.setActivity(activityService.findById(preSalePayItem.getActivityId()));
            }
            if(preSalePayItem.getUserId()!=null){
                preSalePayItem.setUser(userService.findUserById(preSalePayItem.getUserId()));
            }

        }
        response.setPreSalePays(preSalePays.getContent());
        response.setJpaPage(preSalePays);
        return response;
    }


    @RequestMapping(value = "/get_order_callbacks", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "活动订单", notes = "活动列表")
    PreSalePayCallbacksResponse getOrderCallBacks(
            @ApiParam(value = "orderId") @RequestParam(required = false) Integer orderId,
            @ApiParam(value = "pageNo") @RequestParam Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam Integer pageSize
    ) {
        PreSalePayCallBack preSalePayCallBack=new PreSalePayCallBack();
        preSalePayCallBack.setPayId(orderId);
        PreSalePayCallbacksResponse response = new PreSalePayCallbacksResponse();
        Page<PreSalePayCallBack> preSalePayCallBacks = payCallBackService.findAll(preSalePayCallBack,new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));


        response.setPreSalePayCallBacks(preSalePayCallBacks.getContent());
        response.setJpaPage(preSalePayCallBacks);
        return response;
    }

    @RequestMapping(value = "/update_order", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value = "活动订单", notes = "活动列表")
    BaseResponse getOrderCallBacks(
            @ApiParam(value = "id") @RequestParam(required = false) Integer id,
            @ApiParam(value = "status") @RequestParam Integer status
    ) {
        PreSalePay preSalePay=preSalePayService.findPayById(id);
//        preSalePay.setActivityId(activityId);
//        preSalePay.setGoodsId(goodsId);
        preSalePay.setStatus(status);
        preSalePayService.update(preSalePay);
        return new BaseResponse();
    }
}
