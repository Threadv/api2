package com.ifenghui.storybookapi.app.presale.controller;


import io.swagger.annotations.Api;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "体验活动", description = "体验活动")
@RequestMapping("/sale/experience")
public class ExperienceController {

//    @Value("${pre_sale_out_trade}")
//    String outTradeNoPre;
//
//    @Value("${ifenghuiurl}")
//    String ifenghuiurl;
//
//    @Autowired
//    PreSaleGoodsService preSaleGoodsService;
//
//    @Autowired
//    YiZhiActivityUserService userService;
//
//    @Autowired
//    PreSaleUserService preSaleUserService;
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
//    @Autowired
//    PreSaleShareRecordService shareRecordService;
//
//    @Autowired
//    YiZhiActivityUserReadRecordService userReadRecordService;
//
//    @Autowired
//    PreSaleGiftCountService giftCountService;
//
//    @Autowired
//    GiftCheckService giftCheckService;
//
//    @Autowired
//    PreSaleCodeService codeService;
//
//    @Autowired
//    BuyLessonItemService buyLessonItemService;
//
//    @RequestMapping(value = "/add_lesson_one", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "解锁一节课程", notes = "解锁一节课程")
//    synchronized YiZhiBaseResponse addLessonOne(
//            @ApiParam(value = "用户token") @RequestParam String token,
//            @ApiParam(value = "商品id 启蒙版一节 18 成长版一节 19") @RequestParam Integer goodsId,
//            @ApiParam(value = "userType") @RequestParam Integer userType,
//            @ApiParam(value = "渠道") @RequestParam(defaultValue = "无") String channel,
//            @ApiParam(value = "活动id") @RequestParam(required = false, defaultValue = "3") Integer activityId
//    ) {
//
//        YiZhiBaseResponse response = new YiZhiBaseResponse();
//        WXConfig.ifenghuiurl = this.ifenghuiurl;
//        WXConfig.pre_sale_out_trade = this.outTradeNoPre;
//        //校验userId
//        Integer userId = 0;
//        try {
//            if (userType == 1) {
//                userId = userService.checkAndGetCurrentUserId(token);
//            } else if (userType == 2) {
//                userId = preSaleUserService.getUserByUnionid(token).getId();
//            }
//        } catch (Exception e) {
//            response.getStatus().setCode(4);
//            response.getStatus().setMsg("token相关用户不存在！");
//            return response;
//        }
//        //判断是否解锁
//        PreSalePay pay1 = payService.getPayByUserIdAndGoodsId(userId, 18, activityId);
//        PreSalePay pay2 = payService.getPayByUserIdAndGoodsId(userId, 19, activityId);
//        if (pay1 != null || pay2 != null) {
//            response.getStatus().setCode(3);
//            response.getStatus().setMsg("已购买");
//            return response;
//        }
//        //直接购买成功添加订单完成 一节课
//        PreSalePay adPay = payService.addPay(userId, userType, goodsId, 0, activityId, 1, OrderPayStyle.DEFAULT_NULL, channel);
//        //生成兑换码一节课
//        PreSaleCode code = codeService.addCode(adPay.getId(), adPay.getUserType());
//        String URL = WXConfig.ifenghuiurl + "/api/vipcode/subscribeByCode";
//        try {
//            String loadJson = HttpUtil.sendPost(URL, "token=" + token + "&code=" + code.getCode());
//            JSONObject json = JSONObject.fromObject(loadJson);
//            //取得得到的状态码和信息
//            Object status = json.get("status");
//            JSONObject json2 = JSONObject.fromObject(status);
//            int statusCode = (int) json2.get("code");
//            if (statusCode == 1) {
//                // 填写地址信息
//                PreSaleGift gift = giftService.setGiftInfo(adPay.getId(), activityId, "虚拟物品无收件人", "虚拟物品无电话", "虚拟物品无地址", userType);
//                //添加统计信息
//                giftCountService.addItem(userId, 1, gift.getId(), activityId, "分享成功解锁一节飞船阅读课");
//                response.getStatus().setMsg("成功");
//            }
//        } catch (Exception e) {
//            response.getStatus().setMsg("失败");
//        }
//        return response;
//    }
//
//    /**
//     * 查看领取条件--
//     * 分享体验活动
//     *
//     * @param token
//     * @return
//     */
//    @RequestMapping(value = "/get_experience_status", method = RequestMethod.GET)
//    @ResponseBody
//    @ApiOperation(value = "查看杂志领取和分享状态", notes = "查看杂志领取和分享状态")
//    GetExperienceResponse getExperienceStatus(
//            @ApiParam(value = "用户token") @RequestParam String token,
//            @ApiParam(value = "活动id  ——————————    " +
//                    "返回状态 0 可领取 1 已完成 2 未完成") @RequestParam(required = false, defaultValue = "3") Integer activityId
//    ) {
//
//        GetExperienceResponse response = new GetExperienceResponse();
//        WXConfig.ifenghuiurl = this.ifenghuiurl;
//        //校验userId
//        Integer userId;
//        try {
//            userId = userService.checkAndGetCurrentUserId(token);
//        } catch (Exception e) {
//            response.getStatus().setCode(4);
//            response.getStatus().setMsg("token相关用户不存在！");
//            return response;
//        }
//        //通过userId、杂志礼物id、查询礼物信息 判断是否领取
//        List<PreSaleGift> giftList = giftService.getGiftListByUserId(userId, activityId);
//        if (giftList != null && giftList.size() > 0) {
//            for (PreSaleGift p : giftList) {
//                if (p.getGoodsId() == 5) {//杂志  tastStatus
//                    response.setIsRead(1);
//                    response.setTastStatus(1);
//                    response.setTastMsg("已领取杂志！");
//                    response.setGift(p);
//                }
//            }
//        }
//        //storyid relate 是53 或1，体验课阅读记录  获得杂志
//        if (response.getTastStatus() == null || response.getTastStatus() != 1) { // 未领取过
//            BuyLessonItemRecord readStatus1 = buyLessonItemService.findReadStatus(userId, 1, 1);
//            BuyLessonItemRecord readStatus53 = buyLessonItemService.findReadStatus(userId, 53, 1);
//            if (readStatus1 != null || readStatus53 != null) {
//                response.setIsRead(1);
//                //添加礼物领取校验
//                PreSaleGiftCheck giftCheck = giftCheckService.add(userId, 5, activityId);
//                response.setTastMsg("可领取杂志！");
//                response.setTastStatus(0);
//            } else {
//                response.setIsRead(0);
//                response.setTastStatus(2);
//                response.setTastMsg("未阅读体完验课不可领取杂志！");
//            }
//        }
//
//        //查看分享状态
//        //判断是否解锁
//        PreSalePay pay = payService.getPayByUserIdAndGoodsId(userId, 18, activityId);
//        PreSalePay pay1 = payService.getPayByUserIdAndGoodsId(userId, 19, activityId);
//        if (pay != null || pay1 != null) {
//            response.setShareStatus(1);
//            response.setShareMsg("已解锁！");
//        } else {
//            List<PreSaleShareRecord> recordList = shareRecordService.findRecordList(userId, activityId);
//            if (recordList != null && recordList.size() >= 1) {
//                response.setShareMsg("已分享,可解锁！");
//                response.setShareStatus(0);
//            } else {
//                response.setShareStatus(2);
//                response.setShareMsg("未分享！");
//            }
//        }
//        String URL = WXConfig.ifenghuiurl + "/api/lessonBuy/share_magazine_status";
//        int buyStatus;
//        int growthLessonCanBuy;
//        int enlightenLessonCanBuy;
//        try {
//            String loadJson = HttpUtil.sendGet(URL, "token=" + token);
//            JSONObject json = JSONObject.fromObject(loadJson);
//            buyStatus = (int) json.get("buyStatus");
//            growthLessonCanBuy = (int) json.get("growthLessonCanBuy");
//            enlightenLessonCanBuy = (int) json.get("enlightenLessonCanBuy");
//            response.setBuyStatus(buyStatus);
//            response.setGrowthLessonCanBuy(growthLessonCanBuy);
//            response.setEnlightenLessonCanBuy(enlightenLessonCanBuy);
//        } catch (Exception e) {
//            buyStatus = 0;
//            growthLessonCanBuy = 0;
//            enlightenLessonCanBuy = 0;
//            response.setBuyStatus(buyStatus);
//            response.setGrowthLessonCanBuy(growthLessonCanBuy);
//            response.setEnlightenLessonCanBuy(enlightenLessonCanBuy);
//        }
//        return response;
//    }
//
//    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//
//    /**
//     * 领取互动课故事券
//     *
//     * @param token
//     * @param goodsId
//     * @param activityId
//     * @param num
//     * @return
//     */
//
//    @RequestMapping(value = "/add_hudong_coupon", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "互动故事添加故事券", notes = "互动故事添加故事券")
//    YiZhiBaseResponse addHudongCoupon(
//            @ApiParam(value = "用户token") @RequestParam String token,
//            @ApiParam(value = "商品Id 7") @RequestParam Integer goodsId,
//            @ApiParam(value = "活动Id") @RequestParam(defaultValue = "3") Integer activityId,
//            @ApiParam(value = "故事券数量") @RequestParam Integer num
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
//            if (p.getGoodsId() == goodsId) {//互动课故事券  tastStatus  goodsId = 7
//                response.getStatus().setCode(3);
//                response.getStatus().setMsg("已领互动故事获得的取故事券！");
//                return response;
//            }
//        }
//        //判断是否达成条件
//        Integer status1 = this.getStatus(token, activityId).getHuDongStatus();
//        if (status1 != null && status1 == 2) {
//            response.getStatus().setCode(2);
//            response.getStatus().setMsg("未阅读够3个互动故事！");
//            return response;
//        }
//
//        //获得商品
//        PreSaleGoods goods = goodsService.findGoodsById(goodsId);
//        //直接购买成功添加订单完成
//        PreSalePay adPay = payService.add(userId, goodsId, goods.getPrice(), activityId, 1, OrderPayStyle.WEIXINP_PAY);
//        /**
//         * 填写地址信息
//         */
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
//            giftCountService.addItem(userId, 1, gift.getId(), activityId, "完成互动故事获得故事兑换券" + num + "张");
//        }
//        response.getStatus().setCode(code);
//        response.getStatus().setMsg(msg);
//        return response;
//    }
//
//    /**
//     * 领取分享故事券
//     *
//     * @param token
//     * @param goodsId
//     * @param activityId
//     * @param num
//     * @return
//     */
//
//    @RequestMapping(value = "/add_share_coupon", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "分享添加故事券", notes = "分享添加故事券")
//    YiZhiBaseResponse addShareCoupon(
//            @ApiParam(value = "用户token") @RequestParam String token,
//            @ApiParam(value = "商品Id 6") @RequestParam Integer goodsId, //6
//            @ApiParam(value = "活动Id") @RequestParam(defaultValue = "3") Integer activityId,
//            @ApiParam(value = "故事券数量") @RequestParam Integer num
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
//            if (p.getGoodsId() == goodsId) {//分享故事券  tastStatus  goodsId = 6
//                response.getStatus().setCode(3);
//                response.getStatus().setMsg("已领分享获得的取故事券！");
//                return response;
//            }
//        }
//
//        //判断是否达成条件
//        Integer status1 = this.getStatus(token, activityId).getShareStatus();
//        if (status1 != null && status1 == 2) {
//            response.getStatus().setCode(2);
//            response.getStatus().setMsg("未分享不可领取分享礼物！");
//            return response;
//        }
//
//        //获得商品
//        PreSaleGoods goods = goodsService.findGoodsById(goodsId);
//        //直接购买成功添加订单完成
//        PreSalePay adPay = payService.add(userId, goodsId, goods.getPrice(), activityId, 1, OrderPayStyle.WEIXINP_PAY);
//        /**
//         * 填写地址信息
//         */
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
//    /**
//     * 添加分享记录
//     *
//     * @return
//     */
//    @RequestMapping(value = "/set_share_record", method = RequestMethod.GET)
//    @ResponseBody
//    @ApiOperation(value = "添加分享记录", notes = "添加分享记录")
//    YiZhiBaseResponse setShareRecord(
//            @ApiParam(value = "用户token") @RequestParam String sstoken
//    ) {
//
//        YiZhiBaseResponse response = new YiZhiBaseResponse();
//        Integer userId;
//        try {
//            userId = userService.checkAndGetCurrentUserId(sstoken);
//        } catch (Exception e) {
//            response.getStatus().setCode(4);
//            response.getStatus().setMsg("token相关用户不存在！");
//            return response;
//        }
//        //添加分享记录
//        shareRecordService.addShareRecord(userId, 3);
//        return response;
//    }
//
//    /**
//     * 领取商品杂志
//     *
//     * @param token
//     * @return
//     */
//    @RequestMapping(value = "/set_gift_info", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "领取杂志填写地址信息", notes = "领取杂志填写地址信息")
//    @Transactional
//    synchronized PreSaleGiftResponse setGiftInfo(
//            @ApiParam(value = "用户token") @RequestParam String token,
//            @ApiParam(value = "商品id 5") @RequestParam Integer goodsId,
//            @ApiParam(value = "用户类型") @RequestParam(defaultValue = "1") Integer userType,
//            @ApiParam(value = "收件人") @RequestParam String receiver,
//            @ApiParam(value = "联系电话") @RequestParam String phone,
//            @ApiParam(value = "收件地址") @RequestParam String address,
//            @ApiParam(value = "活动id") @RequestParam(defaultValue = "3") Integer activityId
//    ) {
//
//        WXConfig.pre_sale_out_trade = this.outTradeNoPre;
//        PreSaleGiftResponse response = new PreSaleGiftResponse();
//        Integer userId;
//        try {
//            userId = userService.checkAndGetCurrentUserId(token);
//        } catch (Exception e) {
//            response.getStatus().setCode(4);
//            response.getStatus().setMsg("token相关用户不存在！");
//            return response;
//        }
//        //通过userId、杂志礼物id、查询礼物信息 判断是否领取
//        List<PreSaleGift> giftList = giftService.getGiftListByUserId(userId, activityId);
//        for (PreSaleGift p : giftList) {
//            if (p.getGoodsId() == goodsId) {//杂志  tastStatus  goodsId = 5
//                response.getStatus().setCode(3);
//                response.getStatus().setMsg("已领取杂志！");
//                return response;
//            }
//        }
//
//        //判断是否达成条件
//        Integer status1 = this.getStatus(token, activityId).getTastStatus();
//        if (status1 != null && status1 == 2) {
//            response.getStatus().setCode(2);
//            response.getStatus().setMsg("未阅读完不可领取杂志！");
//            return response;
//        }
//        //查询check表是否存在记录可领取礼物
//        PreSaleGiftCheck giftCheck = giftCheckService.findOne(userId, goodsId, activityId);
//        //直接购买成功添加订单完成- 成功订单
//        PreSalePay adPay = payService.add(userId, goodsId, 0, activityId, 1, OrderPayStyle.DEFAULT_NULL);
//        //填写地址信息 - 领取成功
//        PreSaleGift preSaleGift = giftService.setGiftInfo(adPay.getId(), activityId, receiver, phone, address, userType);
//        //添加统计信息
//        giftCountService.addItem(userId, userType, preSaleGift.getId(), activityId, "体验飞船阅读课领取杂志");
//        response.setPreSaleGift(preSaleGift);
//        try {
//            //设置为已领取状态giftCheck
//            giftCheckService.setSuccess(giftCheck.getId());
//        } catch (Exception e) {
//            return response;
//        }
//        return response;
//    }
//
//    /**
//     * 查看领取条件
//     *
//     * @param token
//     * @return
//     */
//    @RequestMapping(value = "/get_status", method = RequestMethod.GET)
//    @ResponseBody
//    @ApiOperation(value = "查看领取条件(旧)", notes = "查看领取条件（旧）")
//    ExperienceGetStatusResponse getStatus(
//            @ApiParam(value = "用户token") @RequestParam String token,
//            @ApiParam(value = "活动id") @RequestParam(required = false, defaultValue = "3") Integer activityId
//    ) {
//
//        ExperienceGetStatusResponse response = new ExperienceGetStatusResponse();
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
//        //查询阅读互动故事记录 1 和 4  类型
//        List<Integer> typeList = new ArrayList<>();
//        typeList.add(1);
//        typeList.add(4);
//        Map<Integer, YiZhiActivityUserReadRecord> map = userReadRecordService.getReadRecordListByTypes(userId, typeList, activityId);
//
//        //通过userId、杂志礼物id、查询礼物信息 判断是否领取
//        List<PreSaleGift> giftList = giftService.getGiftListByUserId(userId, activityId);
//        if (giftList != null && giftList.size() > 0) {
//            for (PreSaleGift p : giftList) {
//                if (p.getGoodsId() == 5) {//杂志  tastStatus
//                    response.setTastStatus(1);
//                    response.setTastMsg("已领取杂志！");
//                    response.setGift(p);
//                }
//                if (p.getGoodsId() == 6) {//分享礼物  shareStatus
//                    response.setShareStatus(1);
//                    response.setShareMsg("已领取分享礼物！");
//                }
//                if (p.getGoodsId() == 7) {//互动课礼物  huDongStatus
//                    response.setHuDongStatus(1);
//                    response.setHuDongSize(map.size());
//                    response.setHuDongMsg("已领取互动课礼物！");
//                }
//            }
//        }
//
//        //storyid relate 是53 或1，体验课阅读记录  获得杂志
//        if (response.getTastStatus() == null || response.getTastStatus() != 1) { // 未领取过
//            BuyLessonItemRecord readStatus1 = buyLessonItemService.findReadStatus(userId, 1, 1);
//            BuyLessonItemRecord readStatus53 = buyLessonItemService.findReadStatus(userId, 53, 1);
//            if (readStatus1 != null || readStatus53 != null) {
//                //添加礼物领取校验
//                giftCheckService.add(userId, 5, activityId);
//                response.setTastStatus(0);
//                response.setTastMsg("可领取杂志！");
//            } else {
//                response.setTastStatus(2);
//                response.setTastMsg("未阅读体完验课不可领取杂志！");
//            }
//        }
//
//        //类型 1  4 ，互动课阅读记录大于3 故事券
//        if (response.getHuDongStatus() == null || response.getHuDongStatus() != 1) { // 未领取过
//            if (map != null && map.size() >= 3) {
//                response.setHuDongStatus(0);
//                response.setHuDongSize(map.size());
//                response.setHuDongMsg("可领取互动故事奖励！");
//            } else {
//                response.setHuDongStatus(2);
//                response.setHuDongSize(map.size());
//                response.setHuDongMsg("未阅读够3个互动故事！");
//            }
//        }
//        //分享记录  查询是否领取 故事券
//        if (response.getShareStatus() == null || response.getShareStatus() != 1) { // 未领取过
//            List<PreSaleShareRecord> recordList = shareRecordService.findRecordList(userId, activityId);
//            if (recordList != null && recordList.size() >= 1) {
//                response.setShareStatus(0);
//                response.setShareMsg("可领取分享礼物！");
//            } else {
//                response.setShareStatus(2);
//                response.setShareMsg("未分享不可领取分享礼物！");
//            }
//        }
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
