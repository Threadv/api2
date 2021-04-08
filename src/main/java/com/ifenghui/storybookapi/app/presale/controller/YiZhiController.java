package com.ifenghui.storybookapi.app.presale.controller;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "益智活动", description = "益智活动")
@RequestMapping("/sale/yizhi")
public class YiZhiController {

//    @Value("${pre_sale_out_trade}")
//    String outTradeNoPre;
//
//    @Autowired
//    ActivityService yiZhiActivityService;
//
//    @Autowired
//    YiZhiActivityUserService userService;
//
//    @Autowired
//    YiZhiActivityUserReadRecordService userReadRecordService;
//
//    @Autowired
//    PreSalePayService payService;
//
//    @Autowired
//    PreSaleGoodsService goodsService;
//
//    @Autowired
//    PreSaleGiftService giftService;
//
//
//    @RequestMapping(value = "/set_pay_address", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "领取礼物填写地址信息", notes = "领取礼物填写地址信息")
//    @Transactional
//    synchronized
//    PreSaleGiftResponse setPayAddress(
//            @ApiParam(value = "用户id") @RequestParam Integer userId,
//            @ApiParam(value = "商品id") @RequestParam Integer goodsId,
//            @ApiParam(value = "收件人") @RequestParam String receiver,
//            @ApiParam(value = "联系电话") @RequestParam String phone,
//            @ApiParam(value = "收件地址") @RequestParam String address,
//            @ApiParam(value = "活动id") @RequestParam(defaultValue = "2") Integer activityId
//    ) {
//
//        WXConfig.pre_sale_out_trade = this.outTradeNoPre;
//        PreSaleGiftResponse response = new PreSaleGiftResponse();
//        PreSaleGoods goodsById = goodsService.findGoodsById(goodsId);
//        //查看库存订单数超过库存不能领取
//        List<PreSalePay> payList = payService.getPayList(goodsId, activityId);
//        if (payList != null && payList.size() > goodsById.getStorage()) {
//            response.getStatus().setCode(4);
//            response.getStatus().setMsg("超出库存限制！");
//            return response;
//        }
//        //通过userId查询礼物信息
//        PreSaleGift giftByUserId = giftService.getGiftByUserId(userId, activityId);
//        if (giftByUserId != null && giftByUserId.getStatus() == 1) {
//            response.getStatus().setCode(2);
//            response.getStatus().setMsg("已经领取过礼物");
//            return response;
//        }
//        //获得商品
//        PreSaleGoods goods = goodsService.findGoodsById(goodsId);
//        //直接购买成功添加订单完成
//        PreSalePay adPay = payService.add(userId, goodsId, goods.getPrice(), activityId, 1, OrderPayStyle.DEFAULT_NULL);
//        if(adPay==null){
//            response.getStatus().setCode(3);
//            response.getStatus().setMsg("无法创建订单，活动已过期");
//            return response;
//        }
//        /**
//         * 填写地址信息
//         */
//        PreSaleGift preSaleGift = giftService.setGiftInfo(adPay.getId(), activityId, receiver, phone, address,1);
//        response.setPreSaleGift(preSaleGift);
//        return response;
//    }
//
//
//    @RequestMapping(value = "/get_gift_detail_by_userId", method = RequestMethod.GET)
//    @ResponseBody
//    @ApiOperation(value = "通过userId看礼物信息", notes = "通过userId看礼物信息")
//    PreSaleGiftResponse getGiftStatus(
//            @ApiParam(value = "用户id") @RequestParam Integer userId,
//            @ApiParam(value = "活动id") @RequestParam(defaultValue = "2") Integer activityId
//    ) {
//
//        PreSaleGiftResponse response = new PreSaleGiftResponse();
//        //通过userId查询礼物信息
//        PreSaleGift giftByUserId = giftService.getGiftByUserId(userId, activityId);
//        response.setPreSaleGift(giftByUserId);
//        return response;
//    }
//
//
//    @RequestMapping(value = "/get_user_read_record", method = RequestMethod.GET)
//    @ResponseBody
//    @ApiOperation(value = "返回益智查看记录", notes = "返回益智查看记录")
//    YiZhiActivityUserReadRecordsResponse getReadRecord(
//            @ApiParam(value = "用户id") @RequestParam Integer userId
//    ) {
//
//        YiZhiActivityUserReadRecordsResponse response = new YiZhiActivityUserReadRecordsResponse();
//        Map<String, String> map = new HashMap();
//        List<YiZhiActivityUserReadRecord> userReadRecordList = userReadRecordService.getUserReadRecordList(userId,2);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        for (YiZhiActivityUserReadRecord p : userReadRecordList) {
//            String cratetDay = sdf.format(p.getCreateTime());
//            map.put(cratetDay, cratetDay);
//        }
//        response.setReadRecordSize(map.size());
//        response.setReadRecordList(userReadRecordList);
//        System.out.println(userReadRecordList.size());
//        return response;
//    }
//
//    @RequestMapping(value = "/get_activity", method = RequestMethod.GET)
//    @ResponseBody
//    @ApiOperation(value = "活动信息", notes = "活动信息")
//    ActivityResponse getActivity(
//            @ApiParam(value = "活动id") @RequestParam Integer id
//    ) {
//
//        ActivityResponse response = new ActivityResponse();
//        Activity preSaleActivity = yiZhiActivityService.findById(id);
//
//        response.setPreSaleActivity(preSaleActivity);
//        return response;
//    }
//
//    @RequestMapping(value = "/get_userid", method = RequestMethod.GET)
//    @ResponseBody
//    @ApiOperation(value = "通过token获得userId", notes = "通过token获得userId")
//    GetUserIdResponse getUserId(
//            @ApiParam(value = "用户token") @RequestParam String token
//    ) {
//
//        GetUserIdResponse response = new GetUserIdResponse();
//        //通过token获取userId
//
//        Integer userId;
//        if (token == null || token.length() <= 0) {
//            response.getStatus().setCode(2);
//            response.getStatus().setMsg("token不存在！");
//            return response;
//        }
//        try {
//            userId = userService.checkAndGetCurrentUserId(token);
//            response.setUserId(userId);
//            return response;
//        } catch (Exception e) {
//            response.getStatus().setCode(4);
//            response.getStatus().setMsg("token相关用户不存在！");
//            return response;
//        }
//    }

}
