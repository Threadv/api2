package com.ifenghui.storybookapi.app.presale.controller;



import com.ifenghui.storybookapi.app.presale.entity.*;
import com.ifenghui.storybookapi.app.presale.exception.PreSaleException;
import com.ifenghui.storybookapi.app.presale.exception.PreSaleNotFoundException;
import com.ifenghui.storybookapi.app.presale.response.AppCallBackeResponse;
import com.ifenghui.storybookapi.app.presale.response.PayResponse;
import com.ifenghui.storybookapi.app.presale.response.PreSalePayResponse;
import com.ifenghui.storybookapi.app.presale.service.*;
import com.ifenghui.storybookapi.app.transaction.entity.OrderPayActivity;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import com.ifenghui.storybookapi.style.WalletStyle;
import com.ifenghui.storybookapi.util.JS_SDK.WXConfig;
import com.ifenghui.storybookapi.util.PreSaleMD5Util;
import io.swagger.annotations.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)//内部开发暂时支持跨域
@Controller
@EnableAutoConfiguration
@Api(value = "订单", description = "订单相关接口")
@RequestMapping("/sale/pay")

public class PreSalePayController {


    @Value("${certPath}")
    public String certpath;

//    @Value("${saleconfig.oss.url}")
//    String ossUrl;

    @Value("${fwwxkey}")
    String wxkey;

    @Value("${sale.activity.notify}")
    String wxcallurl;

    @Value("${fwmch_id}")
    String wx_mch_id;

    @Value("${pre_sale_out_trade}")
    String outTradeNoPre;

    @Value("${sale.wxnotifyurl}")
    String wxnotifyurl;

    @Value("${sale.appnotifyurl}")
    String appnotifyurl;

    @Value("${sale.huiweinotifyurl}")
    String huiweinotifyurl;

    @Value("${sale.alinotifyurl}")
    String alinotifyurl;

    @Value("${apppay.successurl}")
    String appPaySuccessUrl;

//    @Value("${appgiftsuccessurl}")
//    String appgiftsuccessurl;

//    @Value("${ifenghuiurl}")
//    String ifenghuiurl;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    @Autowired
    Environment env;

    @Autowired
    PreSalePayService payService;

    @Autowired
    PreSalePayCallBackService callBackService;

//    @Autowired
//    PreSaleCodeService codeService;

    @Autowired
    PreSaleGoodsService goodsService;

    @Autowired
    YiZhiActivityUserService userService;

    @Autowired
    PreSaleUserService saleUserService;

    @Autowired
    ActivityService activityService;

    @Autowired
    PreSaleGiftService giftService;

    @Autowired
    PreSaleGiftCountService giftCountService;

    @Autowired
    PreSaleCodeService preSaleCodeService;

//    @Autowired
//    RemoteAppApiService remoteAppApiService;

    private static Logger logger = Logger.getLogger(PreSalePayController.class);


    String devPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6G+EO/1bGC1sxE7rNULfE8Hl+pie7dHfqv6sGP+ITUnzRyJUtKULgoz76WCu8AFyNqMKnpSxnMucws1WlGbJs+rqwrXfB8yr5eyxaUiwewQQArQAlt3zD/YQidsglsA+UmqFJBkfP1zcpUo6MYZH98cxO/B34qwQbsVNP5pjfV0OHed0H+W0a/hdp4HQiqZ/Ir3AELjpjWuvxP0XT3t1sYsOVRa/XTYS+Gd1dG9BwHwaaIwJh++Wc8MUHCkMx9tkJQdIPxPQWJ2/oR0SoEMlmPGq/mWC6knUm+eFru7AoKEcbNbIeEorDJcSurklFQdWwxkjMCmZ8jzXmco3UeBXFQIDAQAB";
    String childDevPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6G+EO/1bGC1sxE7rNULfE8Hl+pie7dHfqv6sGP+ITUnzRyJUtKULgoz76WCu8AFyNqMKnpSxnMucws1WlGbJs+rqwrXfB8yr5eyxaUiwewQQArQAlt3zD/YQidsglsA+UmqFJBkfP1zcpUo6MYZH98cxO/B34qwQbsVNP5pjfV0OHed0H+W0a/hdp4HQiqZ/Ir3AELjpjWuvxP0XT3t1sYsOVRa/XTYS+Gd1dG9BwHwaaIwJh++Wc8MUHCkMx9tkJQdIPxPQWJ2/oR0SoEMlmPGq/mWC6knUm+eFru7AoKEcbNbIeEorDJcSurklFQdWwxkjMCmZ8jzXmco3UeBXFQIDAQAB";

    /**
     * app添加订单待支付状态
     * 微信支付使用add_pay
     * @param token
     * @param goodsId
     * @param channel
     * @return
     */
    @RequestMapping(value = "/add_order", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加app订单-未支付状态", notes = "添加app订单-未支付状态")
    @Transactional
    PreSalePayResponse addOrder(
            @ApiParam(value = "unionId/token") @RequestParam String token,
//            @ApiParam(value = "用户类型") @RequestParam(defaultValue = "0") Integer userType,
            @ApiParam(value = "activityId") @RequestParam(defaultValue = "0") Integer activityId,
            @ApiParam(value = "商品id") @RequestParam Integer goodsId,
            @ApiParam(value = "渠道") @RequestParam(defaultValue = "无") String channel

    ) {

        WXConfig.wxcallurl = this.wxcallurl;
        WXConfig.pre_sale_out_trade = this.outTradeNoPre;
//        PreSalePayServiceImpl.certpath = certpath;
        PreSalePayResponse response = new PreSalePayResponse();
        Integer userId = 0;

        Activity activity=  activityService.findById(activityId);
        try {
            if (activity.getUserType() == 1) {//app
                userId = userService.checkAndGetCurrentUserId(token);
            } else if (activity.getUserType() == 2) {//wx
                return addPay(token,activityId,goodsId,channel);
//                PreSaleUser user = saleUserService.getUserByUnionid(token);
//                userId = user.getId();
            }
        } catch (Exception e) {
            response.getStatus().setCode(4);
            response.getStatus().setMsg("用户不存在！");
            return response;
        }
        PreSaleGoods goods = goodsService.findGoodsById(goodsId);
        PreSalePay pay = payService.addOrder(userId, activity.getUserType(), goodsId, goods.getPrice(), goods.getActivityId(), 0, OrderPayStyle.DEFAULT_NULL, channel);
        if (pay == null) {
            response.getStatus().setCode(3);
            response.getStatus().setMsg("无法创建订单，活动已过期");
            return response;
        }
        response.setPreSalePay(pay);
        return response;
    }

    /**
     * 查找单个订单
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/get_pay", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取单个订单", notes = "获取单个订单")
    PayResponse getPay(
            @ApiParam(value = "订单id") @RequestParam Integer id
    ) {

        PayResponse response = new PayResponse();
        PreSalePay pay = payService.findPayById(id);
        response.setPay(pay);

        return response;
    }

//    @RequestMapping(value = "/get_payNotify", method = RequestMethod.GET)
//    @ResponseBody
//    @ApiOperation(value = "获取回调地址", notes = "获取回调地址")
//    PayNotifyResponse getPayNotify(
//            @ApiParam(value = "订单id") @RequestParam Integer id
//    ) {
//
//        String app_id = "2017091108667845";
//        String private_key = env.getProperty("private_key");
//
//        PayNotifyResponse response = new PayNotifyResponse();
//        PreSalePay pay = payService.findPayById(id);
//        if (pay == null) {
//            response.getStatus().setCode(0);
//            response.getStatus().setMsg("失败");
//            return response;
//        }
//        String orderString = payService.getAlipayStr(pay.getPrice(), pay.getId(), app_id, private_key, alinotifyurl);
//
//        PayRechargeOrder order = new PayRechargeOrder();
//        order.setId(pay.getId().longValue());
//        order.setUserId(pay.getUserId());
//        order.setAmount(pay.getPrice());
//        order.setPayStyle(RechargePayStyle.getById(pay.getPayStyle()));
//        order.setSuccessTime(pay.getSuccessTime());
//        order.setStatus(pay.getStatus());
//        order.setWxPayNotifyUrl(wxnotifyurl);
//        order.setAliPayNotifyUrl(alinotifyurl);
//        order.setIosPayNotifyUrl(appnotifyurl);
//        order.setHuaweiPayNotifyUrl(huiweinotifyurl);
//        if (pay.getGoodsId() == 24 || pay.getGoodsId() == 25) {
//            //跳转领取礼物成功页
//            order.setAppPaySuccessUrl(appgiftsuccessurl + "?id=" + id + "&goods_id=" + pay.getGoodsId());
//        } else {
//            order.setAppPaySuccessUrl(appPaySuccessUrl + "?id=" + id + "&goods_id=" + pay.getGoodsId());
//        }
//
//        order.setOrderCode(pay.getOutTradeNo());
//        order.setOrderString(orderString);
//        order.setPayAccount("00");
//        order.setRechargeStyle(RechargeStyle.getById(0));
//        order.setChannel(pay.getChannel());
//        order.setTradeNo(pay.getOutTradeNo());
//        order.setIsTest(0);
//        order.setAppName("appname");
//        order.setCreateTime(pay.getCreateTime());
//        order.setWalletStyle(WalletStyle.getById(0));
//        response.setPayRechargeOrder(order);
//        return response;
//    }

    /**
     * 添加支付成功订单
     *
     * @param activityId
     * @param token
     * @param goodsId
     * @param channel
     * @return
     */
    @RequestMapping(value = "/add_pay_success", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加订单-支付成功状态", notes = "添加订单-支付成功状态")
    @Transactional
    PreSalePayResponse addPaySuccess(
            @ApiParam(value = "token/unionId") @RequestParam String token,
            @ApiParam(value = "用户类型") @RequestParam Integer userType,
            @ApiParam(value = "活动id") @RequestParam Integer activityId,
            @ApiParam(value = "商品id") @RequestParam Integer goodsId,
            @ApiParam(value = "渠道") @RequestParam(defaultValue = "无") String channel
    ) {

        WXConfig.wxcallurl = this.wxcallurl;
        WXConfig.pre_sale_out_trade = this.outTradeNoPre;
//        PreSalePayServiceImpl.certpath = certpath;
        PreSalePayResponse response = new PreSalePayResponse();

        Integer userId = 0;
        try {
            if (userType == 1) {//app-token
                userId = userService.checkAndGetCurrentUserId(token);
            } else if (userType == 2) {//wx- unionid
                PreSaleUser user = saleUserService.getUserByUnionid(token);
                userId = user.getId();
            }
        } catch (Exception e) {
            response.getStatus().setCode(4);
            response.getStatus().setMsg("用户不存在！");
            return response;
        }
        PreSaleGoods goods = goodsService.findGoodsById(goodsId);
        if (goods.getPrice() != 0) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg("付费商品");
            return response;
        }
        PreSalePay pay = payService.addPay(userId, userType, goodsId, goods.getPrice(), activityId, 1, OrderPayStyle.DEFAULT_NULL, channel);
        if (pay == null) {
            response.getStatus().setCode(3);
            response.getStatus().setMsg("无法创建订单，活动已过期");
            return response;
        }
        response.setPreSalePay(pay);
        return response;
    }

    /**
     * 添加订单待支付状态
     * 微信支付使用
     *
     * @param activityId
     * @param unionId
     * @param goodsId
     * @param channel
     * @return
     */
    @RequestMapping(value = "/add_pay", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加订单-未支付状态", notes = "添加订单-未支付状态")
    @Transactional
    PreSalePayResponse addPay(
            @ApiParam(value = "unionId") @RequestParam String unionId,
            @ApiParam(value = "活动id") @RequestParam Integer activityId,
            @ApiParam(value = "商品id") @RequestParam Integer goodsId,
            @ApiParam(value = "渠道") @RequestParam(defaultValue = "无") String channel

    ) {
/*
    @ApiParam(value = "unionId/token") @RequestParam String token,
//            @ApiParam(value = "用户类型") @RequestParam(defaultValue = "0") Integer userType,
            @ApiParam(value = "activityId") @RequestParam(defaultValue = "0") Integer activityId,
            @ApiParam(value = "商品id") @RequestParam Integer goodsId,
            @ApiParam(value = "渠道") @RequestParam(defaultValue = "无") String channel
 */
        Integer userType=2;//微信支付
        WXConfig.wxcallurl = this.wxcallurl;
        WXConfig.pre_sale_out_trade = this.outTradeNoPre;
//        PreSalePayServiceImpl.certpath = certpath;
        PreSalePayResponse response = new PreSalePayResponse();
        Integer userId;
        PreSaleUser user = saleUserService.getUserByUnionid(unionId);
        if (user == null) {
            response.getStatus().setMsg("用户不存在！");
            response.getStatus().setCode(4);
            return response;
        }
        userId = user.getId();
        WXConfig.wxcallurl = this.wxcallurl;
        WXConfig.pre_sale_out_trade = this.outTradeNoPre;
//        PreSalePayServiceImpl.certpath = certpath;
        // 微信key
        String wxkey = env.getProperty("fwwxkey");
        PreSaleGoods preSaleGoods = goodsService.findGoodsById(goodsId);
        //商品价格
        int price = preSaleGoods.getPrice();
        //未完成状态订单
        PreSalePay preSalePay = payService.addPay(userId, userType, goodsId, price, activityId, 0, OrderPayStyle.DEFAULT_NULL, channel);
        if (preSalePay == null) {
            response.getStatus().setCode(3);
            response.getStatus().setMsg("无法创建订单，活动已过期");
            return response;
        }
        //用户openid
        String openId = user.getOpenId();
        //生成jsonObject
        JSONObject jsonObject = payService.doUnifiedOrder(outTradeNoPre + preSalePay.getId(),
                preSaleGoods.getContent(), preSaleGoods.getPrice(), preSaleGoods.getId(), request, openId, wxkey);
        if (jsonObject == null) {
            response.getStatus().setCode(0);
            response.getStatus().setMsg("失败");
        }

        response.setJsonObject(jsonObject);
        response.setPreSalePay(preSalePay);
        return response;
    }

//    /**
//     * 微信回调接口
//     *
//     * @throws IOException
//     * @throws PreSaleException
//     */
//    @RequestMapping(value = "/wxcallBack", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "微信支付回调", notes = "微信支付回调")
//    @ApiResponses({@ApiResponse(code = 1, message = "成功", response = PreSaleGetUserResponse.class)
//            , @ApiResponse(code = 201, message = "没有找到这个用户", response = PreSaleExceptionResponse.class)})
//    void wxpaycallBack(
//    ) throws IOException, PreSaleException {
//
//        logger.info("接收参数");
//        logger.info("entry ");
//        //获取url判断回掉来源
//        PreSaleWeixinNotify wxNotify = new PreSaleWeixinNotify(request);
//        //获得微信服务器传递的信息
//
//        logger.info("第一次日志-----=========orderid=========----" + wxNotify.getOrderId().intValue());
//        logger.info("准备验签");
//        //验签
//        String appid;
//        String wxkey;
//        String mch_id;
//
//        appid = env.getProperty("fwappid");
//        wxkey = env.getProperty("fwwxkey");
//        mch_id = env.getProperty("fwmch_id");
//
//        logger.info("-----fwappid----" + appid);
//        logger.info("-----fwwxkey----" + wxkey);
//        logger.info("-----fwmch_id----" + mch_id);
//
//        boolean flagSign = wxNotify.checkWeixinSign(wxNotify, appid, wxkey, mch_id);
//
//        logger.info("=========orderid=========----" + wxNotify.getOrderId().intValue());
//
//        if (!flagSign) {//验签失败
//            logger.info("fail");
//            response.getWriter().write(setXML("FAILURE", "444444"));
//            return;
//        }
//
//        try {
//            PreSalePay preSalePay = payService.findPayById(wxNotify.getOrderId().intValue());//获取此订单数据
//            if (preSalePay == null) {
//                throw new PreSaleNotFoundException("订单已删除 ");
//            }
//            PreSalePayCallBack checkRecord = callBackService.getPayCallbackRecordByPayId(preSalePay.getId());
//            //存回调数据
//            if (checkRecord == null) {
//                PreSalePayCallBack payCallback = new PreSalePayCallBack();
//                payCallback.setContent(wxNotify.getXmlDoc());
//                payCallback.setCreateTime(new Date());
//                payCallback.setPayId(wxNotify.getOrderId().intValue());
//                callBackService.addPayCallback(payCallback);
//                this.setPayStatusSuccess(wxNotify.getOrderId().intValue(), OrderPayStyle.WEIXINP_PAY);
//                this.addCode(wxNotify.getOrderId().intValue());
//                this.addGift(wxNotify.getOrderId().intValue());
//                //添加订单冗余表
//                this.addOrderPayActivity(wxNotify.getOrderId().intValue(), OrderPayStyle.WEIXINP_PAY);
//            }
//        } catch (PreSaleNotFoundException e) {
//            e.printStackTrace();
//            logger.info("-------------------wxpayNotify----payRechargeOrder--null---");
//            logger.info("订单已删除");
//            response.getWriter().write(setXML("FAILURE", "recharge order has delete!"));
//            return;
//        }
//
//        logger.info("-------------------wxpayNotify-------success-");
//        System.out.println("success");
//        response.getWriter().write(setXML("SUCCESS", ""));
//        return;
//    }


    /**
     * 设置订单完成
     */
    private void setPayStatusSuccess(int payId, OrderPayStyle payStyle) throws PreSaleNotFoundException {
        //设置订单支付成功状态
        payService.setPayStatusSuccess(payId, payStyle);
    }

//    /**
//     * 生成兑换码
//     */
//    private void addCode(int payId) throws PreSaleNotFoundException {
//
//        WXConfig.ifenghuiurl = this.ifenghuiurl;
//        PreSalePay pay = payService.findPayById(payId);
//        PreSaleGoods goods = goodsService.findGoodsById(pay.getGoodsId());
//        String token = "";
//        if (pay.getUserType() == 1) {
//            List<UserToken> list = userService.getValidUserTokenListByUserId(pay.getUserId());
//            token = list.get(0).getToken();
//        }
//        if (goods.getCodeType() != 0) {
//            Activity activity=activityService.findById(pay.getActivityId());
////            goods.getCodeType();
//
//            VipCodesResponse codesResponse= remoteAppApiService.getCodeTypes();
//            CodeTypeItem codeType=null;
//            for(CodeTypeItem codeTypeItem: codesResponse.getCodeTypeItems()){
//                if(codeTypeItem.getId()==goods.getCodeType().intValue()){
//                    codeType=codeTypeItem;
//                    break;
//                }
//            }
//
//            if(codeType != null){
//                PreSaleCode preSaleCode = remoteAppApiService.addCode(4,"微信活动:"+activity.getContent(),codeType.getTitle(), pay.getActivityId()).getPreSaleCode();
//                pay.setCode(preSaleCode.getCode());
//                payService.update(pay);
//
//
////                if (!token.equals("")) {
////
////
//////                String URL = WXConfig.ifenghuiurl + "/api/vipcode/subscribeByCode";
////                    try {
//////                    String loadJson = HttpUtil.sendPost(URL, "token=" + token + "&code=" + preSaleCode.getCode());
//////                    JSONObject json = JSONObject.fromObject(loadJson);
////
////                        JSONObject json= remoteAppApiService.subscribeByCode(token,preSaleCode.getCode(),1);
////
////                        //取得得到的状态码和信息
////                        Object status = json.get("status");
////                        JSONObject json2 = JSONObject.fromObject(status);
////                        logger.info("code = " + json2.get("code"));
////                        logger.info("---------兌換成功-------------");
////                    } catch (Exception e) {
////                        logger.info("---------兑换失败------");
////                        logger.info(e);
////                    }
////                }
//            }
//        }
//    }

    /**
     * 生成礼品带领取信息
     */
    private void addGift(int payId) throws PreSaleNotFoundException {
        PreSaleGift gift = giftService.addGiftByPayId(payId);
        Activity activity = activityService.findById(gift.getActivityId());
        PreSaleGoods goods = goodsService.findGoodsById(gift.getGoodsId());
        //添加礼品统计信息
        giftCountService.addItem(gift.getUserId(), gift.getUserType(), gift.getId(), gift.getActivityId(), activity.getContent() + goods.getContent());
    }

    private void addOrderPayActivity(int payId, OrderPayStyle orderPayStyle) throws PreSaleNotFoundException {

//        OrderPayActivity.ossUrl = ossUrl;
//        PreSaleGoods.ossUrl = ossUrl;
        PreSalePay pay = payService.findPayById(payId);
        PreSaleGoods goods = goodsService.findGoodsById(pay.getGoodsId());
        OrderPayActivity orderPayActivity = payService.addOrderPayActivity(pay.getUserId(), pay.getPrice(), pay.getGoodsId(), pay.getStatus(), goods.getContent(), goods.getIconUrl(), orderPayStyle);
        payService.addOrderMix(orderPayActivity.getId(), 5, pay.getUserId(), 1, 0);
    }


//    /**
//     * 新支付宝支付回调地址
//     *
//     * @throws Exception
//     */
//    @RequestMapping(value = "/aliNotify", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "支付宝支付回调地址", notes = "支付宝支付回调地址")
//    @ApiResponses({@ApiResponse(code = 1, message = "成功", response = PreSaleGetUserResponse.class)
//            , @ApiResponse(code = 201, message = "没有找到这个用户", response = PreSaleExceptionResponse.class)})
//    void mtxAlipayNotify(
//    ) throws Exception {
//        logger.info("---------aliNotify--top-appname-----");
//
//        AlipayConfig.partner = env.getProperty("partner");
//        AlipayConfig.private_key = env.getProperty("private_key");
//        AlipayConfig.alipay_public_key = env.getProperty("alipay_public_key");
//        logger.info("------------aliNotify--------");
//        //获取支付宝POST过来反馈信息
//        Map<String, String> params = VersionUtil.getParams(request);
//        logger.info("-------------mtx-alipayNotify-----params---" + params);
//        String alipaypublicKey = env.getProperty("alipay_public_key");
//        String charset = "utf-8";
//        boolean signVerfied = AlipaySignature.rsaCheckV1(params, alipaypublicKey, charset, "RSA");
//        logger.info("-------------signVerfied--------" + signVerfied);
//        System.out.println("验证签名的结果是：" + signVerfied);
//        if (signVerfied == false) {
//            response.getWriter().print("fail");
//            return;
//        }
//        //按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure
//        String callbackMsg = transMapToString(params);
//        String orderIdstr = params.get("out_trade_no");
//        logger.info("-------------out_trade_no--------" + orderIdstr);
//        long orderId;
//        orderId = Long.parseLong(orderIdstr.split("__")[1]);//订单id
//        String ostatus = params.get("trade_status");
//        if (ostatus.equals("TRADE_FINISHED") || ostatus.equals("TRADE_SUCCESS")) {
//            logger.info("-------------mtx-------alipayNotify-----TRADE_FINISHED--TRADE_SUCCESS-----");
//            try {
//                PreSalePay preSalePay = payService.findPayById((int) orderId);//获取此订单数据
//                if (preSalePay == null) {
//                    throw new PreSaleNotFoundException("订单已删除 ");
//                }
//                PreSalePayCallBack checkRecord = callBackService.getPayCallbackRecordByPayId(preSalePay.getId());
//                //存回调数据
//                if (checkRecord == null) {
//                    PreSalePayCallBack payCallback = new PreSalePayCallBack();
//                    payCallback.setContent(callbackMsg);
//                    payCallback.setCreateTime(new Date());
//                    payCallback.setPayId((int) orderId);
//                    callBackService.addPayCallback(payCallback);
//                    //设置订单成功状态
//                    this.setPayStatusSuccess((int) orderId, OrderPayStyle.ALI_PAY);
//                    //生成兑换码
//                    this.addCode((int) orderId);
//                    //生成礼信息待领取
//                    this.addGift((int) orderId);
//                    //添加订单冗余表
//                    this.addOrderPayActivity((int) orderId, OrderPayStyle.ALI_PAY);
//                }
//            } catch (PreSaleNotFoundException e) {
//                e.printStackTrace();
//                response.getWriter().print("fail");
//                return;
//            }
//            response.getWriter().print("success");
//            return;
//        }
//        return;
//    }

//    /**
//     * wx支付回调地址
//     *
//     * @throws Exception
//     */
//    @RequestMapping(value = "/wxNotify/{appname}", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "微信支付回调地址", notes = "微信支付回调地址")
//    @ApiResponses({@ApiResponse(code = 1, message = "成功", response = PreSaleGetUserResponse.class)
//            , @ApiResponse(code = 201, message = "没有找到这个用户", response = PreSaleExceptionResponse.class)})
//    void wxNotify(
//            @ApiParam(value = "appname") @PathVariable("appname") String appname
//    ) throws IOException, PreSaleException {
//
//        logger.info("--------------------payNotify------appname---" + appname);
//        logger.info("接收参数");
//        logger.info("entry");
//        PreSaleWeixinNotify wxNotify = new PreSaleWeixinNotify(request);
//        logger.info("准备验签");
//        //验签
//        String appid;
//        String wxkey;
//        String mch_id;
//        if (appname.equals("app")) {
//            logger.info("-------wxpayNotify-------");
//            appid = env.getProperty("appappid");
//            wxkey = env.getProperty("appwxkey");
//            mch_id = env.getProperty("appmch_id");
//        } else if (appname.equals("xiaochengxu")) {
//            appid = env.getProperty("fwappid");
//            wxkey = env.getProperty("fwwxkey");
//            mch_id = env.getProperty("fwmch_id");
//        } else {
//            logger.info("fail");
//            return;
//        }
//        logger.info("-----appid----" + appid);
//        logger.info("-----wxkey----" + wxkey);
//        logger.info("-----machid----" + mch_id);
//        PreSalePay pay = payService.findPayById(wxNotify.getOrderId().intValue());//获取此订单数据
//        if (pay == null) {
//            throw new PreSaleNotFoundException("订单已删除 ");
//        }
//        boolean flagSign = wxNotify.checkWeixinSign(wxNotify, appid, wxkey, mch_id);
//        if (!flagSign) {//验签失败
//            logger.info("fail");
//            response.getWriter().write(setXML("FAILURE", "444444"));
//            return;
//        }
//        try {
//            PreSalePay preSalePay = payService.findPayById(wxNotify.getOrderId().intValue());//获取此订单数据
//            if (preSalePay == null) {
//                throw new PreSaleNotFoundException("订单已删除 ");
//            }
//            PreSalePayCallBack checkRecord = callBackService.getPayCallbackRecordByPayId(preSalePay.getId());
//            //存回调数据
//            if (checkRecord == null) {
//                PreSalePayCallBack payCallback = new PreSalePayCallBack();
//                payCallback.setCreateTime(new Date());
//                payCallback.setContent(wxNotify.getXmlDoc());
//                payCallback.setPayId(wxNotify.getOrderId().intValue());
//                callBackService.addPayCallback(payCallback);
//                //设置订单成功状态
//                this.setPayStatusSuccess(wxNotify.getOrderId().intValue(), OrderPayStyle.WEIXINP_PAY);
//                //生成兑换码
//                this.addCode(wxNotify.getOrderId().intValue());
//                //生成礼信息待领取
//                this.addGift(wxNotify.getOrderId().intValue());
//                //添加订单冗余表
//                this.addOrderPayActivity(wxNotify.getOrderId().intValue(), OrderPayStyle.WEIXINP_PAY);
//            }
//        } catch (PreSaleNotFoundException e) {
//            e.printStackTrace();
//            logger.info("------------------payNotify-----null---");
//            logger.info("订单已删除");
//            response.getWriter().write(setXML("FAILURE", " order has delete!"));
//            return;
//        }
//        logger.info("------------------payNotify-------success-");
//        System.out.println("success");
//        response.getWriter().write(setXML("SUCCESS", ""));
//        return;
//    }
//
    /**
     * app支付回调地址
     *
     * @throws Exception
     */
    @RequestMapping(value = "/appNotify", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "app支付回调地址", notes = "app支付回调地址")
//    @ApiResponses({@ApiResponse(code = 1, message = "成功", response = PreSaleGetUserResponse.class)
//            , @ApiResponse(code = 201, message = "没有找到这个用户", response = PreSaleExceptionResponse.class)})
    public AppCallBackeResponse appNotify(
            @ApiParam(value = "price") @RequestParam("price") Integer price,
            @ApiParam(value = "orderId") @RequestParam("orderId") Integer orderId,
            @ApiParam(value = "walletStyle") @RequestParam("walletStyle") WalletStyle walletStyle,
            @ApiParam(value = "sign") @RequestParam("sign") String sign
    ) throws IOException, PreSaleException {

//        OrderPayActivity.ossUrl = ossUrl;

        OrderPayStyle payStyle = OrderPayStyle.DEFAULT_NULL;
        if (walletStyle == WalletStyle.IOS_WALLET) {
            payStyle = OrderPayStyle.IOS_BLANCE;
        }
        if (walletStyle == WalletStyle.ANDROID_WALLET) {
            payStyle = OrderPayStyle.ANDRIOD_BLANCE;
        }

        AppCallBackeResponse response = new AppCallBackeResponse();
        boolean flagSign = this.checkAppSign(sign, price, orderId, "vista688");
        if (!flagSign) {//验签失败
            logger.info("fail");
            response.setCallBackStatus(0);
            response.getStatus().setMsg("验签失败");
            return response;
        }
        try {
            PreSalePay preSalePay = payService.findPayById(orderId);//获取此订单数据
            if (preSalePay == null) {
                throw new PreSaleNotFoundException("订单已删除 ");
            }
            PreSalePayCallBack checkRecord = callBackService.getPayCallbackRecordByPayId(preSalePay.getId());
            //存回调数据
            if (checkRecord == null) {
                PreSalePayCallBack payCallback = new PreSalePayCallBack();
                payCallback.setCreateTime(new Date());
                payCallback.setContent("orderId:" + orderId + "金额:" + price + "_app回调");
                payCallback.setPayId(orderId);
                callBackService.addPayCallback(payCallback);
                //设置订单成功
                this.setPayStatusSuccess(orderId, payStyle);
                //生成兑换码
//                this.addCode(orderId);


                PreSaleCode preSaleCode= preSaleCodeService.addCode(orderId);
                preSalePay.setCode(preSaleCode.getCode());
                payService.update(preSalePay);

                //生成礼信息待领取
                this.addGift(orderId);
                //添加订单冗余表
                this.addOrderPayActivity(orderId, payStyle);
            }
        } catch (PreSaleNotFoundException e) {
            e.printStackTrace();
            logger.info("---------appNotify-----null---");
            logger.info("订单已删除");
            response.setCallBackStatus(0);
            response.getStatus().setMsg("订单已删除");
            return response;
        }
        logger.info("------------------appNotify-------success-");
        System.out.println("success");
        response.setCallBackStatus(1);
        response.getStatus().setMsg("success");
        return response;
    }

    /**
     * app验签
     *
     * @return true成功 false失败
     */
    public static boolean checkAppSign(String appSign, Integer price, Integer payId, String str) {

        String s = price + "" + payId + str;
        System.out.println("字符串拼接后是" + s.toString());
        String sign = PreSaleMD5Util.MD5Encode(s.toString(), "UTF-8");
        System.out.println("MD5加密：" + sign);
        logger.info("签名是：" + sign);
        logger.info("返回签名是：" + appSign);
        if (!sign.equals(appSign)) {//验签失败
            return false;
        }
        return true;
    }

//    /**
//     * 华为支付回调地址
//     *
//     * @throws Exception
//     */
//    @RequestMapping(value = "/huaweiPayNotify", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "华为回调地址", notes = "")
//    @ApiResponses({@ApiResponse(code = 1, message = "成功", response = PreSaleGetUserResponse.class)
//            , @ApiResponse(code = 201, message = "没有找到这个用户", response = PreSaleExceptionResponse.class)})
//    void huaweiPayNotify(
//            HttpServletRequest request
//    ) throws Exception {
//        logger.info("--------------------huaweiPayNotify----start--appname--");
//        request.setCharacterEncoding("UTF-8");
//
//        Map<String, Object> map;
//        CallbackDemo callbackDemo = new CallbackDemo();
//        map = callbackDemo.getValue(request);
//        logger.info("--------------------huaweiPayNotify----map----" + map);
//        if (null == map) {
//            return;
//        }
//        String sign = (String) map.get("sign");
//        logger.info("--------------------huaweiPayNotify----sign----" + sign);
//        ResultDomain result = new ResultDomain();
//        result.setResult(1);
//
//        logger.info("--------------------huaweiPayNotify----signType----" + (String) map.get("signType"));
//        if (RSA.rsaDoCheck(map, sign, devPubKey, (String) map.get("signType"))) {
//            result.setResult(0);
//            logger.info("--------------------huaweiPayNotify----yan qian success----");
//            String callbackMsg = transMapToString(map);
//            String orderIdstr = (String) map.get("requestId");
//            Long orderId;
//            //可以从订单号获取
//            orderId = Long.parseLong(orderIdstr.split("_")[1]);
//            logger.info("--------------------huaweiPayNotify----getOrderId----" + orderId);
//            String ostatus = (String) map.get("result");
//            if (ostatus.equals("0")) {
//                logger.info("--------------------huaweiPayNotify------------");
//                try {
//                    PreSalePay preSalePay = payService.findPayById(orderId.intValue());//获取此订单数据
//                    if (preSalePay == null) {
//                        throw new PreSaleNotFoundException("订单已删除 ");
//                    }
//                    PreSalePayCallBack checkRecord = callBackService.getPayCallbackRecordByPayId(preSalePay.getId());
//                    //存回调数据
//                    if (checkRecord == null) {
//                        PreSalePayCallBack payCallback = new PreSalePayCallBack();
//                        payCallback.setCreateTime(new Date());
//                        payCallback.setContent(callbackMsg);
//                        payCallback.setPayId(orderId.intValue());
//                        callBackService.addPayCallback(payCallback);
//                        //设置订单成功
//                        this.setPayStatusSuccess(orderId.intValue(), OrderPayStyle.HUAWEI_PAY);
//                        //生成兑换码
//                        this.addCode(orderId.intValue());
//                        //生成礼信息待领取
//                        this.addGift(orderId.intValue());
//                        //添加订单冗余表
//                        this.addOrderPayActivity(orderId.intValue(), OrderPayStyle.HUAWEI_PAY);
//                        result.setResult(0);
//                        this.huaweiPayResult(result);
//                    }
//                } catch (PreSaleNotFoundException e) {
//                    e.printStackTrace();
//                    result.setResult(3);
//                }
//            } else {
//                //支付失败
//                result.setResult(3);
//            }
//        } else {
//            //验签失败
//            logger.info("--------------------huaweiPayNotify-------yanqian shibai-----");
//            result.setResult(1);
//        }
//        this.huaweiPayResult(result);
//        return;
//    }

//    private void huaweiPayResult(ResultDomain result) throws Exception {
//
//        CallbackDemo callbackDemo = new CallbackDemo();
//        String resultinfo = callbackDemo.convertJsonStyle(result);
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        PrintWriter out = response.getWriter();
//        out.print(resultinfo);
//        out.close();
//        return;
//    }

}
