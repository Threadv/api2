package com.ifenghui.storybookapi.app.presale.controller;

import io.swagger.annotations.Api;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "陪娃成长计划", description = "陪娃成长计划")
@RequestMapping("/sale/growth")
public class GrowthController {

//    @Value("${pre_sale_out_trade}")
//    String outTradeNoPre;
//
//    @Value("${ifenghuiurl}")
//    String ifenghuiurl;
//
//    @Autowired
//    YiZhiActivityUserReadRecordService userReadRecordService;
//
//    @Autowired
//    PreSalePayService payService;
//
//    @Autowired
//    YiZhiActivityUserService userService;
//
//    @Autowired
//    PreSaleGoodsService goodsService;
//
//    @Autowired
//    PreSaleGiftService giftService;
//
//    @Autowired
//    GiftCheckService giftCheckService;
//
//    @Autowired
//    PreSaleGiftCountService giftCountService;
//
//    @Autowired
//    PreSaleCodeService codeService;
//
//    @Autowired
//    GetStoryPictureService getStoryPictureService;
//
//    @RequestMapping(value = "/add_coupon", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "添加故事券", notes = "添加故事券")
//    YiZhiBaseResponse addCoupon(
//            @ApiParam(value = "用户token") @RequestParam String token,
//            @ApiParam(value = "商品Id 26") @RequestParam Integer goodsId, //26
//            @ApiParam(value = "活动Id") @RequestParam(defaultValue = "7") Integer activityId,
//            @ApiParam(value = "故事券数量 1") @RequestParam Integer num
//    ) {
//
//        WXConfig.ifenghuiurl = this.ifenghuiurl;
//        WXConfig.pre_sale_out_trade = this.outTradeNoPre;
//        YiZhiBaseResponse response = new YiZhiBaseResponse();
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
//        //通过userId、杂志礼物id、查询礼物信息 判断是否领取
//        List<PreSaleGift> giftList = giftService.getGiftListByUserId(userId, activityId);
//        for (PreSaleGift p : giftList) {
//            if (p.getGoodsId() == goodsId) {//故事券goodsId = 26
//                response.getStatus().setCode(3);
//                response.getStatus().setMsg("已领故事券！");
//                return response;
//            }
//        }
//
//        //直接购买成功添加订单完成
//        PreSalePay adPay = payService.add(userId, goodsId, 0, activityId, 1, OrderPayStyle.DEFAULT_NULL);
//        //填写地址信息
//        PreSaleGift gift = giftService.setGiftInfo(adPay.getId(), activityId, "虚拟物品无收件人", "虚拟物品无电话", "虚拟物品无地址", 1);
//
//        //添加故事券
//        String loadJson = this.sendStoryCoupon(token, num);
//        JSONObject json = JSONObject.fromObject(loadJson);
//        //取得得到的状态码和信息
//        Object status = json.get("status");
//        JSONObject json2 = JSONObject.fromObject(status);
//        int code = (int) json2.get("code");
//        String msg = (String) json2.get("msg");
//
//        //添加统计信息
//        if (code == 1) {
//            giftCountService.addItem(userId, 1, gift.getId(), activityId, "分享活动获得故事兑换券" + num + "张");
//        }
//        response.getStatus().setCode(code);
//        response.getStatus().setMsg(msg);
//        return response;
//    }
//
//
//    @RequestMapping(value = "/get_story_picture", method = RequestMethod.GET)
//    @ResponseBody
//    @ApiOperation(value = "今日故事", notes = "今日故事")
//    GetStoryPicture getStoryPicture(
//    ) {
//
//        GetStoryPicture response = new GetStoryPicture();
//        //获得今日故事封面图
//        StoryPicture storyPicture = getStoryPictureService.findOne();
//        response.setStoryPicture(storyPicture);
//
//        return response;
//    }
//
//
//    @RequestMapping(value = "/get_read_record_status", method = RequestMethod.GET)
//    @ResponseBody
//    @ApiOperation(value = "返回益智查看记录和领取状态", notes = "返回益智查看记录和领取状态")
//    YiZhiActivityUserReadRecordsResponse getReadRecord(
//            @ApiParam(value = "用户token") @RequestParam(required = false) String token
//    ) {
//
//        YiZhiActivityUserReadRecordsResponse response = new YiZhiActivityUserReadRecordsResponse();
//
//        if (token == null || token.equals("null") || token.equals("")) {
//            //获得今日故事封面图
//            StoryPicture storyPicture = getStoryPictureService.findOne();
//            response.setStoryPicture(storyPicture);
//            return response;
//        }
//        //未达成条件时状态
//        response.setCouponStatus(2);
//        response.setBackStatus(2);
//        response.setCaseStatus(2);
//        //校验userId
//        Integer userId;
//        try {
//            userId = userService.checkAndGetCurrentUserId(token);
//        } catch (Exception e) {
//            response.getStatus().setCode(4);
//            response.getStatus().setMsg("token相关用户不存在！");
//            return response;
//        }
//
//        //查询领取状态、阅读记录
//        List<YiZhiActivityUserReadRecord> userReadRecordList = userReadRecordService.getUserReadRecordListByUserId(userId, 7);
//        if (userReadRecordList.size() >= 3) {
//            response.setCouponStatus(0);
//        }
//        if (userReadRecordList.size() >= 10) {
//            //小背包
//            giftCheckService.add(userId, 27, 7);
//            response.setBackStatus(0);
//        }
//
//        //判断是否领取
//        List<PreSaleGift> giftListByUserId = giftService.getGiftListByUserId(userId, 7);
//        if (giftListByUserId != null && giftListByUserId.size() > 0) {
//            for (PreSaleGift p : giftListByUserId) {
//                if (p.getGoodsId() == 26) {//故事券1张
//                    response.setCouponStatus(1);
//                }
//                if (p.getGoodsId() == 27) {//小背包
//                    response.setBackStatus(1);
//                    response.setBackGift(p);
//                }
//                if ((p.getGoodsId() == 24 && p.getStatus() == 0) || (p.getGoodsId() == 25 && p.getStatus() == 0)) {//拉杆箱
//                    response.setCaseStatus(0);
//                    response.setPayId(p.getPayId());
//                    response.setGoodsId(p.getGoodsId());
//                }
//                if ((p.getGoodsId() == 24 && p.getStatus() == 1) || (p.getGoodsId() == 25 && p.getStatus() == 1)) {//拉杆箱
//                    response.setCaseStatus(1);
//                    response.setCaseGift(p);
//                }
//            }
//        }
//        response.setReadRecordSize(userReadRecordList.size());
//        response.setReadRecordList(userReadRecordList);
//
//        //获得今日故事封面图
//        StoryPicture storyPicture = getStoryPictureService.findOne();
//        response.setStoryPicture(storyPicture);
//
//        return response;
//    }
//
//    /**
//     * 添加故事券
//     *
//     * @param token
//     * @param num
//     * @return
//     */
//
//    public String sendStoryCoupon(String token, Integer num) {
//
//        WXConfig.ifenghuiurl = this.ifenghuiurl;
//        YiZhiBaseResponse response = new YiZhiBaseResponse();
//        //添加故事兑换券  /api/coupon/activity_send_story_coupon
//        String URL = WXConfig.ifenghuiurl + "/api/coupon/activity_send_story_coupon";
//        String loadJson = HttpUtil.sendPost(URL, "token=" + token + "&sendCouponNum=" + num);
//
//        return loadJson;
//    }
}
