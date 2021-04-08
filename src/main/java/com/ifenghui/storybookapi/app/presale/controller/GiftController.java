package com.ifenghui.storybookapi.app.presale.controller;



import com.ifenghui.storybookapi.app.presale.entity.*;
import com.ifenghui.storybookapi.app.presale.response.PreSaleGiftListResponse;
import com.ifenghui.storybookapi.app.presale.response.PreSaleGiftResponse;
import com.ifenghui.storybookapi.app.presale.service.*;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import com.ifenghui.storybookapi.util.JS_SDK.WXConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "礼品", description = "礼品相关接口")
@RequestMapping("/sale/gift")
public class GiftController {

    @Value("${pre_sale_out_trade}")
    String outTradeNoPre;

    @Autowired
    PreSaleGiftService giftService;

    @Autowired
    PreSaleGiftCountService giftCountService;

    @Autowired
    YiZhiActivityUserService userService;

    @Autowired
    PreSaleUserService preSaleUserService;

    @Autowired
    ActivityService activityService;

    @Autowired
    PreSaleGoodsService goodsService;

    @Autowired
    GiftCheckService giftCheckService;

    @Autowired
    PreSalePayService payService;

//    @Autowired
//    RemoteAdminRoleApiService remoteAdminRoleApiService;

    @RequestMapping(value = "/set_gift_info", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "领取礼物成功填写地址信息", notes = "领取礼物成功填写地址信息,适合先写地址后付款的流程")
    @Transactional
    synchronized PreSaleGiftResponse setGiftInfo(
            @ApiParam(value = "token") @RequestParam String token,
            @ApiParam(value = "商品id") @RequestParam Integer goodsId,
            @ApiParam(value = "用户类型") @RequestParam Integer userType,
            @ApiParam(value = "收件人") @RequestParam String receiver,
            @ApiParam(value = "联系电话") @RequestParam String phone,
            @ApiParam(value = "收件地址") @RequestParam String address,
            @ApiParam(value = "活动id") @RequestParam Integer activityId
    ) {

        PreSaleGiftResponse response = new PreSaleGiftResponse();
        WXConfig.pre_sale_out_trade = this.outTradeNoPre;
        //校验userId
        Integer userId = 0;
        try {
            if (userType == 1) {
                userId = userService.checkAndGetCurrentUserId(token);
            } else if (userType == 2) {
                userId = preSaleUserService.getUserByUnionid(token).getId();
            }
        } catch (Exception e) {
            response.getStatus().setCode(4);
            response.getStatus().setMsg("token相关用户不存在！");
            return response;
        }
//        PreSalePay pay = payService.findPayById(payId);
        Activity activity = activityService.findById(activityId);
        PreSaleGoods goods = goodsService.findGoodsById(goodsId);
        //查看库存订单数超过库存不能领取
        List<PreSalePay> payList = payService.getPayList(goods.getId(), activityId);
        if (payList != null && payList.size() > goods.getStorage()) {
            response.getStatus().setCode(2);
            response.getStatus().setMsg("超出库存限制！");
            return response;
        }
        //查询check表是否存在记录可领取礼物
        PreSaleGiftCheck giftCheck = giftCheckService.findOne(userId, goodsId, activityId);
        if (giftCheck != null && giftCheck.getStatus() == 0) {
            //直接购买成功添加订单完成- 成功订单
            PreSalePay adPay = payService.add(userId, goodsId, 0, activityId, 1, OrderPayStyle.DEFAULT_NULL);
            //填写地址信息
            PreSaleGift preSaleGift = giftService.setGiftInfo(adPay.getId(), activityId, receiver, phone, address,userType);
            //设置为已领取状态giftCheck
            giftCheckService.setSuccess(giftCheck.getId());
            //添加统计信息
            giftCountService.addItem(userId, userType,preSaleGift.getId(), activityId, activity.getContent() + goods.getContent());
            response.setPreSaleGift(preSaleGift);
            return response;
        }
        response.getStatus().setCode(3);
        response.getStatus().setMsg("未达成条件或已经领取过礼物");
        return response;
    }

//    /**
//     * 填写已存在礼品地址信息
//     *
//     * @param receiver
//     * @param phone
//     * @param address
//     * @param activityId
//     * @return
//     */
//    @RequestMapping(value = "/set_address", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "填写已存在礼品地址信息", notes = "填写已存在礼品地址信息，适合先付款后写地址的流程")
//    @Transactional
//   synchronized PreSaleGiftResponse setAddress(
//            @ApiParam(value = "wx用户unionId /token") @RequestParam String token,
//            @ApiParam(value = "payId") @RequestParam Integer payId,
//            @ApiParam(value = "用户类型") @RequestParam Integer userType,
//            @ApiParam(value = "收件人") @RequestParam String receiver,
//            @ApiParam(value = "联系电话") @RequestParam String phone,
//            @ApiParam(value = "收件地址") @RequestParam String address,
//            @ApiParam(value = "活动id") @RequestParam Integer activityId
//    ) {
//
//        PreSaleGiftResponse response = new PreSaleGiftResponse();
//        Integer userId = 0;
//        try {
//            if (userType == 1) {
//                userId = userService.checkAndGetCurrentUserId(token);
//            } else if (userType == 2) {
//                userId = preSaleUserService.getUserByUnionid(token).getId();
//            }
//        } catch (Exception e) {
//            response.getStatus().setCode(4);
//            response.getStatus().setMsg("用户不存在！");
//            return response;
//        }
////        PreSalePay pay = payService.getPayByUserIdAndGoodsId(userId, goodsId, activityId);
////        if(pay == null){
////            response.getStatus().setCode(2);
////            response.getStatus().setMsg("未完成订单");
////            return response;
////        }
////        Integer payId = pay.getId();
//        PreSaleGift gift = giftService.getGiftByPayId(payId);
//        if (gift != null && gift.getStatus() == 1) {
//            response.getStatus().setCode(3);
//            response.getStatus().setMsg("物流已经填写");
//            return response;
//        }
//        /**
//         * 填写地址信息
//         */
//        PreSaleGift preSaleGift = giftService.setAddress(payId, receiver, phone, address);
//        //添加统计信息
//        PreSaleGiftCount giftCount = giftCountService.findByGiftId(preSaleGift.getId());
//        Activity activity = activityService.findById(activityId);
//        if (giftCount != null) {
//
//            PreSaleGoods goods = goodsService.findGoodsById(gift.getGoodsId());
//            giftCountService.addItem(userId,userType, preSaleGift.getId(), activityId, activity.getContent() + goods.getContent());
//        }
//        response.setPreSaleGift(preSaleGift);
//
//        //x修改订单的物流状态
//        PreSalePay preSalePay= payService.findPayById(payId);
//        if(preSalePay.getStatus()==0){
//            return response;
//        }
//        preSalePay.setIsExpress(1);
//        payService.update(preSalePay);
//
//        //导出到物流中心
//        remoteAdminRoleApiService.addCenterOrder(gift.getName()
//                ,"SALE_ACTIVITY"
//                ,preSalePay.getSuccessTime()
//                ,"活动导入"+activity.getContent()
//                ,1
//                ,gift.getPayId()+""
//                ,gift.getPhone()
//                ,gift.getReceiver()
//                ,""
//                ,""
//                ,""
//                ,gift.getAddress()
//                ,23
//                ,0
//        );
//        preSalePay.setIsExpressCenter(1);
//        payService.update(preSalePay);
//        return response;
//    }

    /**
     * wx用户通过unionId获取礼品列表
     *
     * @param unionId
     * @param activityId
     * @return
     */
    @RequestMapping(value = "/get_gifts_by_unionId_and_activityId", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "uinion获取礼品列表", notes = "uinion获取礼品列表")
    PreSaleGiftListResponse getGiftsByUnionIdAndActividty(
            @ApiParam(value = "用户token") @RequestParam String unionId,
            @ApiParam(value = "活动id") @RequestParam Integer activityId
    ) {

        PreSaleGiftListResponse response = new PreSaleGiftListResponse();
        PreSaleUser user = preSaleUserService.getUserByUnionid(unionId);
        if (user == null) {
            response.getStatus().setCode(4);
            response.getStatus().setMsg("用户不存在！");
            return response;
        }
        List<PreSaleGift> giftList = giftService.getGiftListByUserId(user.getId(), activityId);
        response.setPreSaleGiftList(giftList);
        return response;
    }

    /**
     * app用户通过userId获取礼品列表
     *
     * @param token
     * @param activityId
     * @return
     */
    @RequestMapping(value = "/get_gifts_by_token_and_activityId", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "token获取礼品列表", notes = "token获取礼品列表")
    PreSaleGiftListResponse getGiftsByTokenAndActividty(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "活动id") @RequestParam Integer activityId
    ) {

        PreSaleGiftListResponse response = new PreSaleGiftListResponse();
        Integer userId;
        try {
            userId = userService.checkAndGetCurrentUserId(token);
        } catch (Exception e) {
            response.getStatus().setCode(4);
            response.getStatus().setMsg("token相关用户不存在！");
            return response;
        }

        List<PreSaleGift> giftList = giftService.getGiftListByUserId(userId, activityId);
        response.setPreSaleGiftList(giftList);
        return response;
    }

    /**
     * 通过id获取礼品详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/get_gift", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取礼品详情", notes = "获取礼品详情")
    PreSaleGiftResponse getGiftById(
            @ApiParam(value = "礼品id") @RequestParam Integer id
    ) {

        PreSaleGiftResponse response = new PreSaleGiftResponse();
        PreSaleGift gift = giftService.getGiftById(id);
        response.setPreSaleGift(gift);
        return response;
    }

    @RequestMapping(value = "/get_gift_by_payId", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取礼品详情", notes = "获取礼品详情")
    PreSaleGiftResponse getGiftByPayId(
            @ApiParam(value = "订单id") @RequestParam Integer payId
    ) {

        PreSaleGiftResponse response = new PreSaleGiftResponse();
        PreSaleGift gift = giftService.getGiftByPayId(payId);
        response.setPreSaleGift(gift);
        return response;
    }


//    @RequestMapping(value = "/output_to_express_center", method = RequestMethod.GET)
//    @ResponseBody
//    @ApiOperation(value = "导入地址到物流中心", notes = "导入地址到物流中心,只支持类型是-43的全年订阅兑换")
//    PreSaleGiftResponse outPutToExpressCenter(
//            @ApiParam(value = "订单id") @RequestParam Integer payId
//    ) {
//
//        PreSaleGiftResponse response = new PreSaleGiftResponse();
//        PreSaleGift gift = giftService.getGiftByPayId(payId);
//        response.setPreSaleGift(gift);
//        Activity activity=activityService.findById(gift.getActivityId());
//
//        PreSalePay order=payService.findPayById(gift.getPayId());
//        if(order.getStatus()!=1){
//            response.getStatus().setCode(0);
//            return response;
//        }
//        PreSaleGoods goods=goodsService.findGoodsById(gift.getGoodsId());
//        if(goods.getCodeType()!=-43){
//            response.getStatus().setCode(0);
//            return response;
//        }
//        remoteAdminRoleApiService.addCenterOrder(gift.getName()
//                ,"SALE_ACTIVITY"
//                ,order.getSuccessTime()
//                ,"活动导入"+activity.getContent()
//                ,1
//                ,gift.getPayId()+""
//                ,gift.getPhone()
//                ,gift.getReceiver()
//                ,""
//                ,""
//                ,""
//                ,gift.getAddress()
//                ,23
//                ,0
//        );
//        return response;
//    }
}
