package com.ifenghui.storybookapi.app.presale.controller;



import com.ifenghui.storybookapi.app.presale.service.PreSaleGiftService;
import com.ifenghui.storybookapi.app.presale.service.YiZhiActivityUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "订阅用户送礼", description = "订阅用户送礼")
@RequestMapping("/sale/subscribe")
public class SubscribeController {

//    @Autowired
//    YiZhiActivityUserService userService;
//
//    @Autowired
//    PreSaleGiftService giftService;
//
//    @Autowired
//    SubscribeService subscribeService;
//
//    @Autowired
//    PreSalePayService payService;
//
//    @Autowired
//    PreSaleGoodsService goodsService;
//
//    @Autowired
//    PreSaleCodeService codeService;
//
//    @Autowired
//    PreSaleGiftCountService giftCountService;
//
//    @Autowired
//    RemoteAppApiService remoteAppApiService;
//
//    /**
//     * 领取互动课故事券
//     *
//     * @param token
//     * @return
//     */
//
//    @RequestMapping(value = "/get_subscribe_status", method = RequestMethod.GET)
//    @ResponseBody
//    @ApiOperation(value = "查看状态信息", notes = "查看状态信息")
//    SubscribeResponse getSubscribeStatus(
//            @ApiParam(value = "用户token") @RequestParam String token,
//            @ApiParam(value = "活动id") @RequestParam(required = false, defaultValue = "5") Integer activityId
//    ) {
//
//        SubscribeResponse response = new SubscribeResponse();
//
//        Integer userId;
//        try {
//            userId = userService.checkAndGetCurrentUserId(token);
//        } catch (Exception e) {
//            response.getStatus().setCode(4);
//            response.getStatus().setMsg("token相关用户不存在！");
//            return response;
//        }
//
//        PreSaleGift gift = giftService.getGiftByUserId(userId, activityId);
//        if (gift != null) {
//
//            PreSaleCode code = codeService.getCodeByUserIdAndActivityId(userId, activityId);
//            response.setCode(code);
////            response.setGift(gift);
//            response.getStatus().setCode(2);
//            response.getStatus().setMsg("已领取过礼物");
//            return response;
//        }
//        //判断是否订阅用户且没有过期
//        SubscriptionRecord subscriptionRecord = subscribeService.getStatus(userId);
//        if (subscriptionRecord!=null){
//            response.getStatus().setCode(1);
//            response.getStatus().setMsg("可领取");
////            response.setSubscriptionRecord(subscriptionRecord);
//            return response;
//        }
//
//        response.getStatus().setCode(3);
//        response.getStatus().setMsg("用户不在订阅期");
//        return response;
//    }


//    /**
//     * 获得兑换码
//     *
//     * @param token
//     * @param activityId
//     * @return
//     */
//    @RequestMapping(value = "/get_code", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "获得兑换码", notes = "获得兑换码")
//    @Transactional
//    synchronized
//    SubscribeResponse getCode(
//            @ApiParam(value = "用户token") @RequestParam String token,
//            @ApiParam(value = "商品名称  启蒙版5节 8  成长版5节 11") @RequestParam Integer goodsId,
//            @ApiParam(value = "用户类型") @RequestParam Integer userType,
//            @ApiParam(value = "活动id") @RequestParam(required = false, defaultValue = "5") Integer activityId
//    ) {
//
//
//        SubscribeResponse response = new SubscribeResponse();
//        Integer userId;
//        try {
//            userId = userService.checkAndGetCurrentUserId(token);
//        } catch (Exception e) {
//            response.getStatus().setCode(4);
//            response.getStatus().setMsg("token相关用户不存在！");
//            return response;
//        }
//
//        PreSaleGift gift = giftService.getGiftByUserId(userId, activityId);
//        if (gift != null) {
////            response.setGift(gift);
//            response.getStatus().setCode(2);
//            response.getStatus().setMsg("已领取过礼物");
//            return response;
//        }
//
//        //判断是否订阅用户且没有过期
//        SubscriptionRecord subscriptionRecord = subscribeService.getStatus(userId);
//        if (subscriptionRecord == null){
//            response.getStatus().setCode(3);
//            response.getStatus().setMsg("用户不在订阅期");
//            return response;
//        }
//
//        //获得商品
//        PreSaleGoods goods = goodsService.findGoodsById(goodsId);
//        PreSalePay adPay = payService.add(userId, goodsId, goods.getPrice(), activityId, 1, OrderPayStyle.WEIXINP_PAY);
//        /**
//         * 填写地址信息
//         */
//        PreSaleGift preSaleGift = giftService.setGiftInfo(adPay.getId(), activityId, "虚拟物品无收件人", "虚拟物品无电话", "虚拟物品无地址",1);
////        response.setGift(preSaleGift);
//
//        //添加统计信息
//        if(goodsId == 8){
//            giftCountService.addItem(userId,1, preSaleGift.getId(),activityId, "订阅用户获得课程兑换码启蒙版-5节课");
//        }else if(goodsId == 11){
//            giftCountService.addItem(userId, 1,preSaleGift.getId(),activityId, "订阅用户获得课程兑换码成长版-5节课");
//        }
//
//        //生成兑换码 自己ID
//        PreSaleCode code = codeService.addCode(userId, userType,activityId, goodsId, SaleStyle.WX_SERVICE);
//        PreSaleCode code = remoteAppApiService.addSaleCode(4, "微信活动:"+ac,activityId, goodsId, SaleStyle.WX_SERVICE);
//        if(code!=null){
//            response.setCode(code);
//        }
//        return response;
//    }

}
