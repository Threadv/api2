package com.ifenghui.storybookapi.adminapi.controlleradmin.order;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.wallet.entity.PayCallbackRecord;
import com.ifenghui.storybookapi.app.wallet.entity.PayRechargeOrder;
import com.ifenghui.storybookapi.app.wallet.response.PayCallbackRecordResponse;
import com.ifenghui.storybookapi.app.wallet.response.PayRechargeOrdersResponse;
import com.ifenghui.storybookapi.app.wallet.service.CallBackService;
import com.ifenghui.storybookapi.app.wallet.service.PayRechargeOrderService;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.style.RechargePayStyle;
import com.ifenghui.storybookapi.style.RechargeStyle;
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

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "充值订单管理", description = "充值订单管理api")
@RequestMapping("/adminapi/order")
public class RechaargeOrderAdminController {

    @Autowired
    PayRechargeOrderService payRechargeOrderService;

    @Autowired
    CallBackService CallBackService;

    @ApiOperation(value = "充值订单列表")
    @RequestMapping(value = "/get_recharge_order_list",method = RequestMethod.GET)
    @ResponseBody
    PayRechargeOrdersResponse getOrderList(
            DateQuery dateQuery,
            @ApiParam(value = "userId") @RequestParam(required = false) Integer userId,
            @ApiParam(value = "支付类型") @RequestParam(required = false) RechargePayStyle rechargePayStyle,
            @ApiParam(value = "类型") @RequestParam(required = false) RechargeStyle rechargeStyle,
            @ApiParam(value = "状态") @RequestParam(required = false) Integer status,
            @ApiParam(value = "是否测试") @RequestParam(required = false) Integer isTest,
            @ApiParam(value = "渠道") @RequestParam(required = false)  String channel,
            @ApiParam(value = "pageNo") @RequestParam Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {
        PayRechargeOrdersResponse response=new PayRechargeOrdersResponse();

        PayRechargeOrder payRechargeOrder = new PayRechargeOrder();
        if (userId != null){
            payRechargeOrder.setUserId(userId);
        }
        if (rechargePayStyle != null){
            payRechargeOrder.setPayStyle(rechargePayStyle);
        }
        if (rechargeStyle != null){
            payRechargeOrder.setRechargeStyle(rechargeStyle);
        }
        if (status != null){
            payRechargeOrder.setStatus(status);
        }
        if (isTest != null){
            payRechargeOrder.setIsTest(isTest);
        }
        if (channel != null && !"".equals(channel)){
            payRechargeOrder.setChannel(channel);
        }

        Page<PayRechargeOrder> page = payRechargeOrderService.findAllBy(payRechargeOrder,dateQuery,new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));
        response.setPayRechargeOrders(page.getContent());
        response.setJpaPage(page);
        return response;
    }

    @ApiOperation(value = "撤销订单/退款")
    @RequestMapping(value = "/cacal_recharge_order",method = RequestMethod.GET)
    @ResponseBody
    BaseResponse cacalOrder(
            @ApiParam(value = "id") @RequestParam(required = false) Integer id
                            ){
        BaseResponse response = new BaseResponse();
        PayRechargeOrder payRechargeOrder = payRechargeOrderService.getPayRechargeOrder(id.longValue());
        if (payRechargeOrder != null){
            //撤销订单
            if (payRechargeOrder.getStatus() == 0){
                payRechargeOrder.setStatus(3);
            }else if(payRechargeOrder.getStatus() == 1){
             //退款 （测试用，只改状态）
                payRechargeOrder.setStatus(2);
                //TODO

            }
            payRechargeOrderService.updateRechargePayOrder(payRechargeOrder);
        }
        return response;
    }

    @ApiOperation(value = "查看回调")
    @RequestMapping(value = "/get_payCallBack",method = RequestMethod.GET)
    @ResponseBody
    PayCallbackRecordResponse getPayCallBack(
            @ApiParam(value = "id") @RequestParam(required = false) Integer id
    ){
        PayCallbackRecordResponse response = new PayCallbackRecordResponse();

        PayCallbackRecord payCallbackRecord = CallBackService.getPayCallbackRecordByOrderId(id.longValue());

        response.setPayCallbackRecord(payCallbackRecord);
        return response;
    }
}
