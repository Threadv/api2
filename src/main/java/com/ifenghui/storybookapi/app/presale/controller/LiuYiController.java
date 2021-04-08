package com.ifenghui.storybookapi.app.presale.controller;

import io.swagger.annotations.Api;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "六一活动", description = "六一活动")
@RequestMapping("/sale/liuyi")
public class LiuYiController {

//    @Value("${certPath}")
//    public String certpath;
//
//    @Value("${saleconfig.oss.url}")
//    String ossUrl;
//
//    @Value("${fwwxkey}")
//    String wxkey;
//
//    @Value("${wxcallurl}")
//    String wxcallurl;
//
//    @Value("${fwmch_id}")
//    String wx_mch_id;
//
//    @Value("${pre_sale_out_trade}")
//    String outTradeNoPre;
//
//
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
//    PreSaleUserService preSaleUserService;
//
//    @Autowired
//    Environment env;
//
//    @Autowired
//    HttpServletRequest request;
//
//    @Autowired
//    HttpServletResponse response;
//
//
//    /**
//     * 2、通过商品id创建订单，返回订单信息
//     *
//     * @param goodsId
//     * @return
//     */
//    @RequestMapping(value = "/add_pay", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "生成订单", notes = "生成订单")
//    PreSalePayResponse addPay(
//            @ApiParam(value = "商品id") @RequestParam Integer goodsId,
//            @ApiParam(value = "unionid") @RequestParam String  unionid,
//            @ApiParam(value = "渠道") @RequestParam(defaultValue = "无",required = false) String  channel,
//            @ApiParam(value = "活动id") @RequestParam(defaultValue = "6") Integer activityId
//
//    ) {
//
//        WXConfig.wxcallurl=this.wxcallurl;
//        WXConfig.pre_sale_out_trade=this.outTradeNoPre;
//        PreSalePreSalePayServiceImpl.certpath = certpath;
//        PreSalePayResponse response = new PreSalePayResponse();
//        // 微信key
//        String wxkey = env.getProperty("fwwxkey");
//        PreSaleGoods preSaleGoods = goodsService.findGoodsById(goodsId);
//        //通过uniond查询user
//        PreSaleUser user = preSaleUserService.getUserByUnionid(unionid);
//        if(user==null){
//            return response;
//        }
//        //商品价格
//        int price = preSaleGoods.getPrice();
//        //未完成状态订单
//        PreSalePay preSalePay = payService.addPay(user.getId(),2, goodsId, price, activityId,0, OrderPayStyle.DEFAULT_NULL,channel);
//        if(preSalePay==null){
//            response.getStatus().setCode(3);
//            response.getStatus().setMsg("无法创建订单，活动已过期");
//            return response;
//        }
//        //用户openid
//        String openId = user.getOpenId();
//        //生成jsonObject
//        JSONObject jsonObject = payService.doUnifiedOrder(outTradeNoPre + preSalePay.getId(),
//                preSaleGoods.getContent(), preSaleGoods.getPrice(), preSaleGoods.getId(), request, openId, wxkey);
//        if (jsonObject == null) {
//            response.getStatus().setCode(0);
//            response.getStatus().setMsg("失败");
//        }
//        response.setJsonObject(jsonObject);
////        preSalePayService.doUnifiedOrder();
//        response.setPreSalePay(preSalePay);
//        return response;
//    }
//
//
//    /**
//     * 获得详情
//     *
//     * @param unionid
//     * @param activityId
//     * @return
//     */
//    @RequestMapping(value = "/get_detail", method = RequestMethod.GET)
//    @ResponseBody
//    @ApiOperation(value = "获得礼品和兑换码详情", notes = "获得礼品和兑换码详情")
//    GiftCodeResponse getDetail(
//            @ApiParam(value = "unionid") @RequestParam String unionid,
//            @ApiParam(value = "活动id") @RequestParam(defaultValue = "6") Integer activityId
//    ) {
//
//        GiftCodeResponse response = new GiftCodeResponse();
//
//        //通过uniond查询user
//        PreSaleUser user = preSaleUserService.getUserByUnionid(unionid);
//        if(user==null){
//            return response;
//        }
//        Integer userId = user.getId();
//        List<PreSaleGift> giftList = giftService.getGiftListByUserId(userId, activityId);
//        if (giftList != null && giftList.size()>0) {
//            //查询code
//            List<PreSaleCode> codeList = codeService.getCodeListByUserIdAndActivityId(userId, activityId);
//            if(codeList!=null && codeList.size()>0){
//                response.setCodeList(codeList);
//            }
//            response.setGiftList(giftList);
//        }
//
//        return response;
//    }
//    /**
//     * payId查详情
//     *
//     * @param activityId
//     * @return
//     */
//    @RequestMapping(value = "/get_gift_by_payId", method = RequestMethod.GET)
//    @ResponseBody
//    @ApiOperation(value = "PayId获得礼物信息", notes = "PayId获得礼物信息")
//    PreSaleGiftResponse getGiftByPayId(
//            @ApiParam(value = "订单Id") @RequestParam Integer payId,
//            @ApiParam(value = "活动id") @RequestParam(defaultValue = "6") Integer activityId
//    ) {
//        PreSaleGiftResponse response = new PreSaleGiftResponse();
//
//        PreSaleGift giftByPayId = giftService.getGiftByPayId(payId);
//        if(giftByPayId!=null && giftByPayId.getStatus()==1){
//            response.getStatus().setCode(2);
//            response.getStatus().setMsg("已领取礼物");
//        }
//        response.setPreSaleGift(giftByPayId);
//        return response;
//    }
}
