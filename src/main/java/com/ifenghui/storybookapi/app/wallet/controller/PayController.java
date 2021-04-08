package com.ifenghui.storybookapi.app.wallet.controller;

/**
 * Created by wml on 2016/12/28.
 */
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.config.AlipayConfig;
import com.huawei.pay.callback.demo.domain.ResultDomain;
import com.huawei.pay.callback.demo.servlet.CallbackDemo;
import com.huawei.pay.callback.demo.util.RSA;
import com.ifenghui.storybookapi.api.response.exception.ExceptionResponse;
import com.ifenghui.storybookapi.app.app.service.ConfigService;
import com.ifenghui.storybookapi.app.app.service.TemplateNoticeService;
import com.ifenghui.storybookapi.app.social.controller.SharerController;
import com.ifenghui.storybookapi.app.system.service.GeoipService;
import com.ifenghui.storybookapi.app.transaction.controller.*;
import com.ifenghui.storybookapi.api.notify.WeixinNotify;


import com.ifenghui.storybookapi.app.story.service.SerialStoryService;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.transaction.entity.vip.PayVipOrder;
import com.ifenghui.storybookapi.app.transaction.response.*;
import com.ifenghui.storybookapi.app.transaction.entity.*;
import com.ifenghui.storybookapi.app.transaction.service.*;
import com.ifenghui.storybookapi.app.transaction.service.lesson.PayLessonOrderService;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderService;
import com.ifenghui.storybookapi.app.user.entity.UserInfo;
import com.ifenghui.storybookapi.app.user.response.GetUserResponse;
import com.ifenghui.storybookapi.app.wallet.dao.PayCallbackRecordDao;
import com.ifenghui.storybookapi.app.wallet.entity.*;
import com.ifenghui.storybookapi.app.wallet.response.*;
import com.ifenghui.storybookapi.app.wallet.service.*;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.entity.UserAccount;
import com.ifenghui.storybookapi.exception.*;
import com.ifenghui.storybookapi.app.user.service.UserAccountService;
import com.ifenghui.storybookapi.app.user.service.UserInfoService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.style.*;
import com.ifenghui.storybookapi.util.HttpRequest;
import com.ifenghui.storybookapi.util.IosNotifyHttpUtil;
import com.ifenghui.storybookapi.util.VersionUtil;
import com.ifenghui.storybookapi.util.ios.InApp;
import com.ifenghui.storybookapi.util.ios.IosNotify;
import com.ifenghui.storybookapi.web.ShareController;
import io.swagger.annotations.*;
import io.swagger.annotations.ApiResponse;
import io.swagger.models.auth.In;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


import static com.ifenghui.storybookapi.util.MD5Util.getMD5;
import static com.ifenghui.storybookapi.util.SetXMLUtil.setXML;
import static com.ifenghui.storybookapi.util.Map2StringUtil.transMapToString;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/pay")
@Api(value="支付",description = "支付相关接口")
public class PayController {
    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    PayService payService;
    @Autowired
    StoryService storyService;
    @Autowired
    UserInfoService userInfoService;
    //    CouponService
    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    PaySubscriptionPriceService paySubscriptionPriceService;

    @Autowired
    SerialStoryService serialStoryService;

    @Autowired
    CouponDeferredSubscriptionService couponDeferredSubscriptionService;
    @Autowired
    CouponService couponSerivce;
    @Autowired
    private Environment env;


    @Autowired
    WalletService walletService;
    @Autowired
    PayRechargeOrderService payRechargeOrderService;
    @Autowired
    PayAfterOrderService payAfterOrderService;
    @Autowired
    UserAccountRecordService userAccountRecordService;
    @Autowired
    UserAccountService userAccountService;
    @Autowired
    CouponService couponService;
    @Autowired
    CouponDeferredService couponDeferredService;
    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    @Autowired
    CouponSubscriptionService couponSubscriptionService;

    @Autowired
    ShoppingCartController shoppingCartController;

    @Autowired
    CouponController couponController;

    @Autowired
    OrderController orderController;

    @Autowired
    CallBackService callBackService;
    @Autowired
    PayCallbackRecordDao payCallbackRecordDao;

    @Autowired
    BuyStoryService buyStoryService;

    @Autowired
    BuySerialService buySerialService;

    @Autowired
    SubscriptionController subscriptionController;

    @Autowired
    WalletController walletController;

    @Autowired
    VipCodeController vipCodeController;

    @Autowired
    SerialBuyController serialBuyController;

    @Autowired
    PayLessonOrderService payLessonOrderService;

    @Autowired
    StoryBuyController storyBuyController;

    @Autowired
    TemplateNoticeService templateNoticeService;

    @Autowired
    SharerController sharerController;

    @Autowired
    UserCashAccountRecordService userCashAccountRecordService;

    @Autowired
    PayVipOrderService payVipOrderService;

    @Autowired
    AbilityPlanOrderService abilityPlanOrderService;

    @Autowired
    ConfigService configService;
    @Autowired
    GeoipService geoipService;

    private static Logger logger = Logger.getLogger(PayController.class);

    String devPubKey =      "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6G+EO/1bGC1sxE7rNULfE8Hl+pie7dHfqv6sGP+ITUnzRyJUtKULgoz76WCu8AFyNqMKnpSxnMucws1WlGbJs+rqwrXfB8yr5eyxaUiwewQQArQAlt3zD/YQidsglsA+UmqFJBkfP1zcpUo6MYZH98cxO/B34qwQbsVNP5pjfV0OHed0H+W0a/hdp4HQiqZ/Ir3AELjpjWuvxP0XT3t1sYsOVRa/XTYS+Gd1dG9BwHwaaIwJh++Wc8MUHCkMx9tkJQdIPxPQWJ2/oR0SoEMlmPGq/mWC6knUm+eFru7AoKEcbNbIeEorDJcSurklFQdWwxkjMCmZ8jzXmco3UeBXFQIDAQAB";
    String childDevPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6G+EO/1bGC1sxE7rNULfE8Hl+pie7dHfqv6sGP+ITUnzRyJUtKULgoz76WCu8AFyNqMKnpSxnMucws1WlGbJs+rqwrXfB8yr5eyxaUiwewQQArQAlt3zD/YQidsglsA+UmqFJBkfP1zcpUo6MYZH98cxO/B34qwQbsVNP5pjfV0OHed0H+W0a/hdp4HQiqZ/Ir3AELjpjWuvxP0XT3t1sYsOVRa/XTYS+Gd1dG9BwHwaaIwJh++Wc8MUHCkMx9tkJQdIPxPQWJ2/oR0SoEMlmPGq/mWC6knUm+eFru7AoKEcbNbIeEorDJcSurklFQdWwxkjMCmZ8jzXmco3UeBXFQIDAQAB";


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
//    @ApiOperation(value = "购物车列表,请改用shopping中的接口", notes = "")
    GetShoppingCartResponse test(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "页码") @RequestParam Integer pageNo,
            @ApiParam(value = "条数") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {
        PayCallbackRecord payCallbackRecord=new PayCallbackRecord();
        payCallbackRecord.setPayType(RechargePayStyle.HUAWEI_PAY);
        payCallbackRecord.setCreateTime(new Date());
        payCallbackRecord.setOrderCode("xxx1");
        payCallbackRecord.setOrderId(1L);
        payCallbackRecord.setCallbackMsg("asdf");
        PayCallbackRecord payCallbackRecord2= payCallbackRecordDao.save(payCallbackRecord);

        payCallbackRecord2=payCallbackRecord2;
        return null;
    }



    @RequestMapping(value = "/getShoppingCart", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "购物车列表,请改用shopping中的接口", notes = "")
    GetShoppingCartResponse getShoppingCart(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "页码") @RequestParam Integer pageNo,
            @ApiParam(value = "条数") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {
        return shoppingCartController.getShoppingCart(token, pageNo, pageSize);
    }


    @RequestMapping(value = "/delShoppingCart", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除购物车,请改用shopping中的接口", notes = "")
    @ApiResponses({@ApiResponse(code = 206, message = "无权限删除", response = ExceptionResponse.class)})
    DelShoppingCartResponse delShoppingCart(
            @ApiParam(value = "用户token") @RequestParam String token,
//              @ApiParam(value = "购物车id（多个id数组）")@RequestParam Long cartIds[]
            @ApiParam(value = "购物车id（多个id逗号分割）") @RequestParam String cartIdsStr
    ) throws ApiException {
        return shoppingCartController.delShoppingCart(token, cartIdsStr);

    }


    @RequestMapping(value = "/addShoppingCart", method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({@ApiResponse(code = 205, message = "已添加过购物车", response = ExceptionResponse.class)
            , @ApiResponse(code = 207, message = "超出数量限制", response = ExceptionResponse.class)})
    @ApiOperation(value = "添加购物车,请改用shopping中的接口", notes = "")
    AddShoppingCartResponse addShoppingCart(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "故事id") @RequestParam Long storyId
    ) throws ApiException {
        return shoppingCartController.addShoppingCart(token, storyId);
    }


    @RequestMapping(value = "/createPayStoryOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "购物车结算/单本直接购买获取订单接口", notes = "")
    GetBuyStorysOrderResponse getBuyStorysOrder(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "故事id（多个id逗号分割）") @RequestParam String storyIdsStr
    ) throws ApiException {
        return orderController.getBuyStorysOrder(token,storyIdsStr,new ArrayList<>());
    }


    //这是为了兼容1.7版本之前购买留的入口，先不去掉
    @RequestMapping(value = "/getBuyStorysOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "兼容老版本getBuyStorysOrderOld-1.7", notes = "")
    GetBuyStorysOrderResponse getBuyStorysOrderOld(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "故事id（多个id逗号分割）") @RequestParam String storyIdsStr
    ) throws ApiException {
//        GetBuyStorysOrderResponse response=new GetBuyStorysOrderResponse();
        //        response.getStatus().setCode(0);
//        response.getStatus().setMsg("请升级APP到最新版本！");
//        return response;
//        return response;
        return orderController.getBuyStorysOrder(token,storyIdsStr,new ArrayList<>());

    }


    @RequestMapping(value = "/buyStorysByBalance", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "购物车结算/单本直接购买余额支付接口", notes = "")
    BuyOrderByBalanceResponse buyStorysByBalance(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "订单号") @RequestParam String orderCode,
             @ApiParam(value = "钱包类型") @RequestParam(required = false) WalletStyle walletStyle
    ) throws ApiException {
        if(walletStyle==null){
            walletStyle=WalletStyle.IOS_WALLET;
        }
        Long orderId=  Long.parseLong(orderCode.split("_")[1]);
        logger.info("--------------------buyStorysByBalance-------orderiD-----"+orderId);
        return storyBuyController.buyStorysByBalance(token, orderId, walletStyle);
    }

    @RequestMapping(value = "/buyStorys", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "购物车结算/单本直接购买余额支付接口", notes = "")
    BuyOrderByBalanceResponse buyStorys(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "订单号") @RequestParam String orderCode
    ) throws ApiException {
        return this.buyStorysByBalance(token, orderCode, null);
    }


    @RequestMapping(value = "/buySerialStory", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "余额购买故事集", notes = "")
    BuyOrderByBalanceResponse buySerialStory(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "订单号") @RequestParam String orderCode,
            @ApiParam(value = "钱包类型") @RequestParam(required = false) WalletStyle walletStyle
    ) throws ApiException {
        if(null == walletStyle){
            walletStyle = WalletStyle.IOS_WALLET;
        }
        Long orderId =  Long.parseLong(orderCode.split("_")[1]);
        BuyOrderByBalanceResponse response = serialBuyController.buySerialStoryByBalance(token, orderId, walletStyle);
        return response;
    }


    @RequestMapping(value = "/editShoppingCartStatus", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "选中购物车修改状态", notes = "")
    EditShoppingCartResponse editShoppingCartStatus(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "购物车id") @RequestParam Long cartId,
            @ApiParam(value = "类型1选中2取消选中") @RequestParam Integer type
    ) throws ApiNotTokenException {
        return shoppingCartController.editShoppingCartStatus(token,cartId,type);

    }

    @RequestMapping(value = "/getSubscriptionPrice", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取订阅价格->请改用 subscription/getSubscriptionPrice", notes = "")
    GetSubscriptionPriceResponse getSubscriptionPrice(
            @ApiParam(value = "用户token") @RequestParam String token
    ) throws ApiNotTokenException {

        return subscriptionController.getSubscriptionPrice(token);
    }

    @RequestMapping(value = "/getSubscriptionOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({@ApiResponse(code = 209, message = "优惠券已使用", response = ExceptionResponse.class),
            @ApiResponse(code = 210, message = "优惠券已过期", response = ExceptionResponse.class)
    })
    @ApiOperation(value = "订阅期刊获取订单号,请使用subscription.createSubscriptionOrder", notes = "")
    GetSubscriptionOrderResponse getSubscriptionOrder(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "价格id") @RequestParam Long priceId,
            @ApiParam(value = "优惠券id（多个id逗号分割）", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String couponIdsStr,
            @ApiParam(value = "赠阅券id", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") Long couponDeferredId
    ) throws ApiException {
        return subscriptionController.createSubscriptionOrder(token,priceId,couponIdsStr,couponDeferredId);
    }


    /**
     * 返回混合订单，用户取消订单
     *
     * @param token
     * @param status
     * @param pageNo
     * @param pageSize
     * @return
     * @throws ApiException
     */
    @RequestMapping(value = "/getUserPayOrder", method = RequestMethod.GET)
    @ResponseBody
//    @ApiResponses({
//            @ApiResponse(code=212,message="用户与订单信息不符",response = ExceptionResponse.class)
//    })
    @ApiOperation(value = "获取用户总订单", notes = "订阅+购买")
    GetUserPayOrderResponse getUserPayOrder(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "状态", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") Integer status,
            @ApiParam(value = "页码") @RequestParam Integer pageNo,
            @ApiParam(value = "条数") @RequestParam Integer pageSize
//            @ApiParam(value = "（订阅）订阅订单id")@RequestParam Long orderId
    ) throws ApiException {
        return orderController.getUserPayOrder(token,status,pageNo,pageSize);

    }


    @RequestMapping(value = "/getUserPayOrderDetail", method = RequestMethod.GET)
    @ResponseBody
//    @ApiResponses({
//            @ApiResponse(code=212,message="用户与订单信息不符",response = ExceptionResponse.class)
//    })
    @ApiOperation(value = "获取用户总订单", notes = "订阅+购买+购买故事集,详细见Order ->getUserPayOrderDetail")
    GetUserPayOrderDetailResponse getUserPayOrderDetail(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "不同类型对应的订单id") @RequestParam Long orderId,
            @ApiParam(value = "订单类型（1购买,2订阅3故事集）") @RequestParam Integer type
    ) throws ApiException {

        return orderController.getUserPayOrderDetail(token,orderId,type);

    }


    @RequestMapping(value = "/delUserPayOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除订单", notes = "")
    @ApiResponses({@ApiResponse(code = 206, message = "无权限删除", response = ExceptionResponse.class)})
    DelUserPayOrderResponse delUserPayOrder(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "订单id") @RequestParam Long orderId,
            @ApiParam(value = "订单类型（1单本购买2订阅3故事集）") @RequestParam Integer type
    ) throws ApiException {
        return orderController.delUserPayOrder(token,orderId,type);
    }

    @RequestMapping(value = "/cancelUserPayOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code = 212, message = "用户与订单信息不符", response = ExceptionResponse.class)
    })
    @ApiOperation(value = "取消订单", notes = "")
    CancelUserPayOrderResponse cancelUserPayOrder(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "订单id") @RequestParam Long orderId,
            @ApiParam(value = "类型（1购买2订阅3故事集4课程）") @RequestParam Integer type
    ) throws ApiException {
        return orderController.cancelUserPayOrder(token,orderId,type);
    }


    @RequestMapping(value = "/subscribeByBalance", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "余额订阅期刊", notes = "")
    @ApiResponses({@ApiResponse(code = 204, message = "余额不足", response = ExceptionResponse.class)})
    SubscribeByBalanceResponse subscribeByBalance(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "订单号") @RequestParam String orderCode,
            @ApiParam(value = "钱包类型") @RequestParam(required = false) WalletStyle walletStyle
    ) throws ApiException {

        return subscriptionController.subscribeByBalance(token,orderCode,walletStyle);
    }

    @RequestMapping(value = "/getWalletBalance", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取钱包余额", notes = "")
    GetWalletBalanceResponse getWalletBalance(
            @ApiParam(value = "用户token") @RequestParam String token
    ) throws ApiNotTokenException {
        return walletController.getWalletBalance(token);
    }

    @RequestMapping(value = "/getCodes", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取兑换码列表->请使用vipcode/getCodes", notes = "")
    GetCodesResponse getCodes(
            @ApiParam(value = "用户token") @RequestParam String token
    ) throws ApiException {

        return vipCodeController.getCodes(token);

    }

    @RequestMapping(value = "/subscribeByCode", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "兑换码订阅期刊/购买故事集->请使用vipcode/subscribeByCode", notes = "")
    @ApiResponses({@ApiResponse(code = 208, message = "code激活失败", response = ExceptionResponse.class)})
    SubscribeByCodeResponse subscribeByCode(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "兑换码") @RequestParam String code
    ) throws ApiException {
        return  vipCodeController.subscribeByCode(token,code, null, null, null, null,null);

    }

    @RequestMapping(value = "/vip_code_detail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "兑换码详情-》请使用vipcode/vip_code_detail", notes = "兑换码详情")
    GetVipCodeDetailResponse getVipcodeDetail(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "兑换码") @RequestParam String code
    ) throws ApiException {
        return vipCodeController.getVipcodeDetail(token,code);
    }




    @RequestMapping(value = "/getPayJournalAccount", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取消费流水记录", notes = "获取消费流水记录")
    GetPayJournalAccountResponse getPayJournalAccount(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "钱包类型") @RequestParam(required = false) WalletStyle walletStyle,
            @ApiParam(value = "类型 1充值 2消费") @RequestParam(required = false, defaultValue = "0") Integer type,
            @ApiParam(value = "页码") @RequestParam Integer pageNo,
            @ApiParam(value = "条数") @RequestParam Integer pageSize
    ) throws ApiException {
        if(walletStyle == null){
            walletStyle = WalletStyle.IOS_WALLET;
        }
        Long userId = userService.checkAndGetCurrentUserId(token);
//        pageNo = pageNo - 1;//默认从0开始，所以需要减1h

        if(userId!=null &&userId==6917){
            GetPayJournalAccountResponse getPayJournalAccountResponse=new GetPayJournalAccountResponse();
            getPayJournalAccountResponse.setUserAccountRecords(new ArrayList<>());
            return getPayJournalAccountResponse;
        }
        if(walletStyle.equals(WalletStyle.SHARE_CASH_WALLET)){
            return userCashAccountRecordService.getPayJournalAccountFromUserCashAccountRecord(userId, type, pageNo, pageSize);
        } else {
            return userAccountRecordService.getPayJournalAccountFromUserAccountRecord(userId, walletStyle, type, pageNo, pageSize);
        }

    }

    @RequestMapping(value = "/all_cash_water", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取所有现金流水", notes = "获取所有现金流水")
    GetPayJournalAccountResponse getAllCashWater(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "页码") @RequestParam Integer pageNo,
            @ApiParam(value = "条数") @RequestParam Integer pageSize
    ) throws ApiException {
        Long userId = userService.checkAndGetCurrentUserId(token);
//        pageNo = pageNo - 1;//默认从0开始，所以需要减1h

        return userCashAccountRecordService.getAllPayJournalAccountFromUserCashAccountRecord(userId, pageNo, pageSize);
    }

    @RequestMapping(value = "/getRechargePrice", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取充值价格列表)", notes = "获取充值价格列表")
    GetPayRechargePriceResponse getRechargePrice(
            @ApiParam(value = "类型 1ios 2 安卓") @RequestParam(required = false) WalletStyle walletStyle
    ) throws ApiException {
        if(walletStyle == null){
            walletStyle = WalletStyle.IOS_WALLET;
        }
        Page<PayRechargePrice> payRechargePricPage = payService.getRechargePrice(walletStyle);
        GetPayRechargePriceResponse getRechargePriceResponse = new GetPayRechargePriceResponse();
        getRechargePriceResponse.setPayRechargePrices(payRechargePricPage.getContent());
        getRechargePriceResponse.setIsCheck(VersionUtil.getIosIsCheck(request, configService,geoipService));
        return getRechargePriceResponse;
    }


    @RequestMapping(value = "/getPayOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "即时到账交易接口，被getRechargeOrder取代【已弃用】)", notes = "支付获取订单和回调地址")
    BuyStorysPayApiResponse getPayOrder(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "订单号") @RequestParam String orderCode,
            @ApiParam(value = "类型（购买1订阅2充值0）") @RequestParam Integer type,
            @ApiParam(value = "类型为充值0时传充值价格id（其他类型传0）", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") Long priceId,
            @ApiParam(value = "渠道", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String channel
    ) throws ApiException {
        throw new ApiVersionException("当前版本不支持,请升级到最新版本");
    }

    @RequestMapping(value = "/getRechargeOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "即时到账交易接口-获得充值订单【已弃用】)", notes = "支付获取订单和回调地址")
    BuyStorysPayApiResponse getRechargeOrder(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "订阅或购买订单号") @RequestParam Long payOrderId,
            @ApiParam(value = "类型（购买1订阅2充值0）") @RequestParam Integer type,
            @ApiParam(value = "类型为充值0时传充值价格id（其他类型传0）", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") Long priceId,
            @ApiParam(value = "渠道", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String channel
    ) throws ApiException {
        throw new ApiVersionException("当前版本不支持,请升级到最新版本");
    }

    @RequestMapping(value = "/getNewRechargeOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "即时到账交易接口-获得充值订单)（1.7版本确认在用）", notes = "支付获取订单和回调地址")
    public BuyStorysPayApiResponse getNewRechargeOrder(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "订阅或购买订单号") @RequestParam Long payOrderId,
            @ApiParam(value = "类型（0充值，1购买，2订阅，3购买故事集 4购买课程, 7购买vip, 9 购买宝宝会读）") @RequestParam Integer type,
            @ApiParam(value = "类型为充值0时传充值价格id（其他类型传0）", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") Long priceId,
            @ApiParam(value = "渠道", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String channel
    ) throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
        //获取支付包类型
        String appName = "";
        String app_id;
        String private_key;
        String notify_url;
        String userAgent=request.getHeader("User-Agent");
        logger.info("------------new--------getNewRechargeOrder------userAgent--" + userAgent);
        if(userAgent.indexOf("appname:childstory") == -1){//不存在
             app_id = "2017091108667845";
             private_key = env.getProperty("private_key");
             notify_url = env.getProperty("mtxalipay.notify");
        }else{
            logger.info("------------new--------getNewRechargeOrder------majiabao--" );
            //存在则为儿童包
            appName = "childstory";
            app_id = "2018010401570066";
            private_key = env.getProperty("child_private_key");
//            notify_url = env.getProperty("mtxalipay.notify");
            notify_url = env.getProperty("childalipay.notify")+"/"+appName;
        }
        if(userAgent.contains("appname:zhijianStory")){
            appName = "zhijianStory";
        }
        RechargeStyle rechargeStyle=RechargeStyle.getById(type);
        PayRechargeOrder payRechargeOrder = payService.addPayRechargeOrder(userId, payOrderId, rechargeStyle, priceId, channel, app_id, private_key, notify_url,appName);
        BuyStorysPayApiResponse buyStorysPayApiResponse = new BuyStorysPayApiResponse();
        buyStorysPayApiResponse.setPayRechargeOrder(payRechargeOrder);
        logger.info("------------new--------getNewRechargeOrder------result--" + buyStorysPayApiResponse);
        return buyStorysPayApiResponse;
    }

    @RequestMapping(value = "/getUserValidityCoupons", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取用户可用代金券列表->请使用coupon/getUserValidityCoupons", notes = "")
    GetUserValidityCouponsResponse getUserValidityCouponsOut(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "类型【1订阅，2购买故事集】") @RequestParam(required = false) Integer type,
            @ApiParam(value = "订阅价格id/故事集id") @RequestParam(required = false) Long priceId,
            @ApiParam(value = "页码") @RequestParam Integer pageNo,
            @ApiParam(value = "条数") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {
        return couponController.getUserValidityCoupons(token,type,priceId,pageNo,pageSize);
    }

    @RequestMapping(value = "/getUserCoupons", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取用户优惠券列表>请使用coupon/getUserCoupons", notes = "")
    GetUserCouponsResponse getUserCoupons(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "类型(1未过期,2已过期)") @RequestParam Integer type,
            @ApiParam(value = "页码") @RequestParam Integer pageNo,
            @ApiParam(value = "条数") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {
        return couponController.getUserCoupons(token,type,pageNo,pageSize);

    }

    @RequestMapping(value = "/getExpiredMixCoupons", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取用户已过期券列表>请使用coupon/getExpiredMixCoupons", notes = "")
    GetExpiredMixCouponsResponse getExpiredMixCoupons(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "页码") @RequestParam Integer pageNo,
            @ApiParam(value = "条数") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {
        return couponController.getExpiredMixCoupons(token,pageNo,pageSize);

    }

    @RequestMapping(value = "/getUserUnReadCoupons", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取用户未读优惠券列表>请使用coupon/getUserUnReadCoupons", notes = "")
    GetUserUnReadCouponsResponse getUserUnReadCoupons(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "页码") @RequestParam Integer pageNo,
            @ApiParam(value = "条数") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {
        return couponController.getUserUnReadCoupons(token,pageNo,pageSize);

    }

    @RequestMapping(value = "/getCouponByShare", method = RequestMethod.GET)
    @ResponseBody
    @ApiResponse(code = 210, message = "第一次分享成功后返回的状态", response = ExceptionResponse.class)
    @ApiOperation(value = "分享获取优惠券", notes = "")
    GetCouponByShareResponse getCouponByShare(
            @ApiParam(value = "用户token") @RequestParam String token
    ) throws ApiException {
        return couponController.getCouponByShare(token);

    }

    @RequestMapping(value = "/getShareInfo", method = RequestMethod.GET)
    @ResponseBody
//    @ApiResponse(code=210,message="第一次分享成功后返回的状态",response = ExceptionResponse.class)
    @ApiOperation(value = "获取分享优惠券信息", notes = "")
    GetShareInfoResponse getShareInfo(
            @ApiParam(value = "用户token") @RequestParam String token
    ) throws ApiException {
        return couponController.getShareInfo(token);

    }

    @RequestMapping(value = "/acceptCoupon", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "领取优惠券", notes = "")
    public AcceptCouponResponse acceptCoupon(
            @ApiParam(value = "用户id") @RequestParam Long userId,
            @ApiParam(value = "电话号") @RequestParam String phone
    ) throws ApiException {
        return couponController.acceptCoupon(userId,phone);

    }

    @RequestMapping(value = "/collectDeferredCoupon", method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({@ApiResponse(code = 203, message = "用户信息验证无效", response = ExceptionResponse.class),
            @ApiResponse(code = 1, message = "成功", response = ExceptionResponse.class),
            @ApiResponse(code = 2, message = "已领取", response = ExceptionResponse.class)
    })
    @ApiOperation(value = "领取赠阅券", notes = "")
    CollectDeferredCouponResponse collectDeferredCoupon(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "赠阅券id") @RequestParam Long couponId,
            @ApiParam(value = "推广渠道", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String channel
    ) throws ApiException {
        return couponController.collectDeferredCoupon(token,couponId,channel);

    }

    @RequestMapping(value = "/getUserValidityDeferredCoupons", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取用户可用赠阅券列表", notes = "")
    GetUserValidityDeferredCouponsResponse getUserValidityDeferredCoupons(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "订阅价格id") @RequestParam Long priceId,
            @ApiParam(value = "页码") @RequestParam Integer pageNo,
            @ApiParam(value = "条数") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {
        return couponController.getUserValidityDeferredCoupons(token,priceId,pageNo,pageSize);

    }

    @RequestMapping(value = "/getUserDeferredCoupons", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取用户赠阅券列表", notes = "")
    GetUserDeferredCouponsResponse getUserDeferredCoupons(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "类型(1未过期,2已过期)") @RequestParam Integer type,
            @ApiParam(value = "页码") @RequestParam Integer pageNo,
            @ApiParam(value = "条数") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {
        return couponController.getUserDeferredCoupons(token,type,pageNo,pageSize);

    }

    @RequestMapping(value = "/isUserSubscribed", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "判断用户是否已订阅过一年", notes = "")
    IsUserSubscribedResponse isUserSubscribed(
            @ApiParam(value = "用户id") @RequestParam String token
    ) throws ApiException {
        return subscriptionController.isUserSubscribed(token);
    }

    @RequestMapping(value = "/addUserInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加活动用户信息", notes = "")
    AddUserInfoResponse addUserInfo(
            @ApiParam(value = "用户id") @RequestParam String token,
            @ApiParam(value = "名字") @RequestParam String name,
            @ApiParam(value = "电话号") @RequestParam String phone,
            @ApiParam(value = "地址") @RequestParam String addr
    ) throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);

        AddUserInfoResponse addUserInfoResponse = new AddUserInfoResponse();

        //判断是否已经添加过
        UserInfo userInfoTest = userInfoService.getUserInfo(userId);
        if (userInfoTest != null) {
            throw new ApiIsAddException("已添加过");
        }

        Void res = userInfoService.addUserInfo(userId, name, phone, addr);
        addUserInfoResponse.getStatus().setMsg("领取成功");
        return addUserInfoResponse;
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取活动用户信息", notes = "")
    GetUserInfoResponse getUserInfo(
            @ApiParam(value = "用户id") @RequestParam String token
    ) throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);

        GetUserInfoResponse getUserInfoResponse = new GetUserInfoResponse();
        UserInfo userInfo = userInfoService.getUserInfo(userId);
        getUserInfoResponse.setUserInfo(userInfo);
        return getUserInfoResponse;
    }

    /**
     * 处理订单后置操作
     *
     * @param orderId 订单号
     * @param payStyle 购买类型1微信 2支付宝
     * @throws ApiException
     */
    private void processOrderAfter(Long orderId, RechargePayStyle payStyle) throws ApiException {
        /**
         * 根据充值订单查询出
         */

        PayAfterOrder payAfterOrder = payAfterOrderService.getPayAfterOrderByOrderId(orderId);
        PayRechargeOrder rechargeOrder = payRechargeOrderService.getPayRechargeOrder(payAfterOrder.getOrderId());

        OrderPayStyle orderPayStyle = null;
        if(payStyle==RechargePayStyle.WEIXINP_PAY){
            orderPayStyle=OrderPayStyle.WEIXINP_PAY;
        }else if(payStyle==RechargePayStyle.ALI_PAY){
            orderPayStyle=OrderPayStyle.ALI_PAY;
        }else if(payStyle==RechargePayStyle.HUAWEI_PAY){
            orderPayStyle=OrderPayStyle.HUAWEI_PAY;
        }else if(payStyle==RechargePayStyle.ZHIJIAN_PAY){
            orderPayStyle=OrderPayStyle.ZHIJIAN_PAY;
        }

        if(payAfterOrder == null){

            logger.info("--------------------payNotify------have-no-after-succsse-");
            return;
        }

        if ( payAfterOrder.getType().equals(0) ) {
            /**
             * 没有后置操作则结束
             */
            logger.info("--------------------payNotify------have-payafterorder----");
            if(rechargeOrder.getWalletStyle().equals(WalletStyle.ANDROID_WALLET)){
                String discount = "9";
                if(rechargeOrder.getAmount().equals(100000)){
                    walletService.changeUserDiscount(rechargeOrder.getUserId(), 90);
                    discount = "9";
                } else if(rechargeOrder.getAmount().equals(200000)){
                    discount = "8";
                    walletService.changeUserDiscount(rechargeOrder.getUserId(), 80);
                } else if(rechargeOrder.getAmount().equals(300000)){
                    discount = "7";
                    walletService.changeUserDiscount(rechargeOrder.getUserId(), 70);
                }
                /**
                 *  发送充值用户推送消息
                 */
                Map<String, String> contentMap = new HashMap<>();
                Integer money = rechargeOrder.getAmount() / 100;
                contentMap.put("money", money.toString());
                contentMap.put("discount", discount);
                templateNoticeService.addNoticeToUserByUserIdAndMessage(NoticeStyle.ANDROID,contentMap,rechargeOrder.getUserId());
            }

            System.out.println("success");
            return;
        }
        /**
         * 根据后置订单判断是订阅还是购买
         */
        if (payAfterOrder.getType().equals(RechargeStyle.BUY_STORY.getId())) {
            logger.info("--------------------payNotify-------pay-after-Buy Story-");
            /**
             * 购买
             */
            buyStoryService.buyStorysByBalance(payAfterOrder.getUserId(), payAfterOrder.getPayOrderId(), orderPayStyle,rechargeOrder.getWalletStyle());
        } else if (payAfterOrder.getType().equals(RechargeStyle.SUBSCRIPTION.getId())) {
            logger.info("--------------------payNotify-------pay-after- Subscription -");
            /**
             * 订阅
             */
            payService.subscribeByBalance(payAfterOrder.getPayOrderId(), payAfterOrder.getUserId(), orderPayStyle,rechargeOrder.getWalletStyle());
        } else if (payAfterOrder.getType().equals(RechargeStyle.SERIAL.getId())) {
            logger.info("--------------------payNotify-------pay-after---Buy Serial Story-");
            /**
             * 购买故事集
             */
            buySerialService.buySerialStoryByBalance(payAfterOrder.getUserId(), payAfterOrder.getPayOrderId(),orderPayStyle,rechargeOrder.getWalletStyle());
        } else if (payAfterOrder.getType().equals(RechargeStyle.LESSON.getId())) {
            logger.info("--------------------payNotify-------pay-after---Buy Lesson-");
            /**
             * 购买课程
             */
            payLessonOrderService.buyLessonByBalance(payAfterOrder.getUserId(), payAfterOrder.getPayOrderId().intValue(), orderPayStyle, rechargeOrder.getWalletStyle());
        } else if (payAfterOrder.getType().equals(RechargeStyle.BUY_SVIP.getId())){
            logger.info("--------------------payNotify-------pay-after---Buy vip-");
            /**
             * 购买svip
             */
            payVipOrderService.buyVipByBalance(payAfterOrder.getUserId(), payAfterOrder.getPayOrderId().intValue(), orderPayStyle, rechargeOrder.getWalletStyle());
        }else if (payAfterOrder.getType().equals(RechargeStyle.BUY_ABILITY_PLAN.getId())){
            logger.info("--------------------payNotify-------pay-after---Buy abilityPlan-");
            /**
             * 购买宝宝会读（优能计划）
             */
            abilityPlanOrderService.buyAbilityPlanByBalance(payAfterOrder.getUserId(), payAfterOrder.getPayOrderId().intValue(), orderPayStyle, rechargeOrder.getWalletStyle());
        }
    }

    /**
     * 设置订单完成，用于验签，和订单完成后的操作
     */
    private void setOrderSuccess(int orderId, String payAccount, RechargePayStyle rechargePayStyle, String notifyMsg, String tradeNo, String intro) throws ApiNotFoundException{
        PayRechargeOrder payRechargeOrder = payRechargeOrderService.getPayRechargeOrder((long)orderId);//获取此充值订单数据
        if (payRechargeOrder == null) {
            throw new ApiNotFoundException("订单已删除");
//            logger.info("--------------------wxpayNotify----payRechargeOrder--null---");
//            logger.info("订单已删除");
//            response.getWriter().write(setXML("FAILURE", "recharge order has delete!"));
//            return;
        } else if(payRechargeOrder.getStatus().equals(1)){
            return ;
        }

        PayCallbackRecord checkRecord = callBackService.getPayCallbackRecordByOrderCode(payRechargeOrder.getOrderCode());

        //存回调数据 orderId message time payType
        if (checkRecord == null) {
            PayCallbackRecord payCallbackRecord = new PayCallbackRecord();
            payRechargeOrder.setPayAccount(payAccount);
            payCallbackRecord.setOrderCode(payRechargeOrder.getOrderCode());
            payCallbackRecord.setPayType(rechargePayStyle);//微信支付
            payCallbackRecord.setPayRechargeOrder(payRechargeOrder);
            payCallbackRecord.setCreateTime(new Date());
            payCallbackRecord.setCallbackMsg(notifyMsg);
            logger.info("--------------------wxpayNotify------add-paycallrecord--");
            callBackService.addPayCallbackRecord(payCallbackRecord);
        }

        //验签成功  修改订单表状态
        logger.info("修改订单状态");
        logger.info("添加购买记录");
        payRechargeOrder.setStatus(1);
        payRechargeOrder.setPayStyle(rechargePayStyle);

        if(payRechargeOrder.getWalletStyle().equals(WalletStyle.SMALL_PROGRAM_WALLET)) {

        } else if(rechargePayStyle.equals(RechargePayStyle.IOS_PAY) || rechargePayStyle.equals(RechargePayStyle.ZHIJIAN_PAY)){
            payRechargeOrder.setWalletStyle(WalletStyle.IOS_WALLET);
        } else {
            payRechargeOrder.setWalletStyle(WalletStyle.ANDROID_WALLET);
        }

        payRechargeOrder.setTradeNo(tradeNo);
        payRechargeOrder.setSuccessTime(new Date());

        payRechargeOrderService.updateRechargePayOrder(payRechargeOrder);

        /**
         * 增加钱包余额
         */
        walletService.addAmountToWallet(payRechargeOrder.getUserId(),payRechargeOrder.getWalletStyle(),RechargeStyle.RECHARGE,payRechargeOrder.getAmount(),"recharge_after_"+orderId,intro);
        /**
         * 添加分成记录（调用微信接口）
         * 分销已经不再使用
         */
//        this.fenxiaoCheckOrder(payRechargeOrder, rechargePayStyle);
        /**
         * 查询后置订单
         */
        logger.info("--------------------payNotify------have-processOrder--");
        this.processOrderAfter((long)orderId, rechargePayStyle);
        logger.info("--------------------payNotify------have-processOrder--end--");
    }


    @RequestMapping(value = "/wxpayNotify", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "微信支付回调地址", notes = "微信支付回调地址")
    @ApiResponses({@ApiResponse(code = 1, message = "成功", response = GetUserResponse.class)
            , @ApiResponse(code = 201, message = "没有找到这个用户", response = ExceptionResponse.class)})
    void wxpayNotify(
    ) throws IOException, ApiException {

        logger.info("接收参数");
        logger.info("entry");
        //获取url判断回掉来源
//        appname = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/")+1);//获取url最后一个/的内容

        WeixinNotify wxNotify = new WeixinNotify(request);
//        //获得微信服务器传递的信息

        logger.info("准备验签");
        //验签
        String appid;
        String wxkey;
        String mch_id;

            appid = MyEnv.env.getProperty("appid");
            wxkey=MyEnv.env.getProperty("wxkey");
            mch_id = MyEnv.env.getProperty("mch_id");

        logger.info("-----appid----"+appid);
        logger.info("-----wxkey----"+wxkey);
        logger.info("-----machid----"+mch_id);
        boolean flagSign = wxNotify.checkWeixinSign(wxNotify, appid, wxkey, mch_id);

        if (!flagSign) {//验签失败
            logger.info("fail");
            response.getWriter().write(setXML("FAILURE", "444444"));
            return;
        }

        try{
            this.setOrderSuccess(wxNotify.getOrderId().intValue(),wxNotify.getPayAccount(),RechargePayStyle.WEIXINP_PAY,wxNotify.getXmlDoc(),wxNotify.getTradeNo(),"微信支付充值");
        }catch (ApiNotFoundException e){
            e.printStackTrace();
            logger.info("--------------------wxpayNotify----payRechargeOrder--null---");
            logger.info("订单已删除");
            response.getWriter().write(setXML("FAILURE", "recharge order has delete!"));
            return;
        }

        logger.info("--------------------wxpayNotify-------success-");
        System.out.println("success");
        response.getWriter().write(setXML("SUCCESS", ""));
        return;
    }

    @RequestMapping(value = "/childWxpayNotify/{appname}", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "微信支付回调地址", notes = "微信支付回调地址")
    @ApiResponses({@ApiResponse(code = 1, message = "成功", response = GetUserResponse.class)
            , @ApiResponse(code = 201, message = "没有找到这个用户", response = ExceptionResponse.class)})
    void childWxpayNotify(
            @ApiParam(value = "appname")@PathVariable("appname")  String appname
    ) throws IOException, ApiException {

        logger.info("--------------------wxpayNotify------appname---"+appname);
        logger.info("接收参数");
        logger.info("entry");
        //获取url判断回掉来源
//        appname = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/")+1);//获取url最后一个/的内容

        WeixinNotify wxNotify = new WeixinNotify(request);
//        //获得微信服务器传递的信息

        logger.info("准备验签");
        //验签
        String appid;
        String wxkey;
        String mch_id;
        if(appname.equals("childstory")) {
            logger.info("-------------mtx-------wxpayNotify---majiabao-----");
            appid = MyEnv.env.getProperty("child_wx_appid");
            wxkey=MyEnv.env.getProperty("child_wxkey");
            mch_id = MyEnv.env.getProperty("child_wx_mch_id");
        } else if(appname.equals("xiaochengxu")){
            logger.info("-------------mtx-------wxpayNotify--------");
            appid = MyEnv.env.getProperty("xiaochengxu_appid");
            wxkey = MyEnv.env.getProperty("wxkey");
            mch_id = MyEnv.env.getProperty("mch_id");
        }else{
            appid = MyEnv.env.getProperty("appid");
            wxkey=MyEnv.env.getProperty("wxkey");
            mch_id = MyEnv.env.getProperty("mch_id");
        }
        logger.info("-----appid----"+appid);
        logger.info("-----wxkey----"+wxkey);
        logger.info("-----machid----"+mch_id);
        boolean flagSign = wxNotify.checkWeixinSign(wxNotify, appid, wxkey, mch_id);

        if (!flagSign) {//验签失败
            logger.info("fail");
            response.getWriter().write(setXML("FAILURE", "444444"));
            return;
        }

        try{
            this.setOrderSuccess(wxNotify.getOrderId().intValue(),wxNotify.getPayAccount(),RechargePayStyle.WEIXINP_PAY,wxNotify.getXmlDoc(),wxNotify.getTradeNo(),"微信支付充值");
        }catch (ApiNotFoundException e){
            e.printStackTrace();
            logger.info("--------------------wxpayNotify----payRechargeOrder--null---");
            logger.info("订单已删除");
            response.getWriter().write(setXML("FAILURE", "recharge order has delete!"));
            return;
        }

        logger.info("--------------------wxpayNotify-------success-");
        System.out.println("success");
        response.getWriter().write(setXML("SUCCESS", ""));
        return;
    }
    @RequestMapping(value = "/zhijianWxpayNotify/{appname}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "ios马甲包微信支付回调地址", notes = "ios马甲包微信/ios内购支付回调地址")
    @ApiResponses({@ApiResponse(code = 1, message = "成功", response = GetUserResponse.class)
            , @ApiResponse(code = 201, message = "没有找到这个用户", response = ExceptionResponse.class)})
    ZhijianWxpayResponse zhijianWxpayNotify(

            @ApiParam(value = "appname")@PathVariable("appname")  String appname,

            @ApiParam(value = "bundleid")@RequestParam String bundleid,
            @ApiParam(value = "fee")@RequestParam String fee,
            @ApiParam(value = "goodsid")@RequestParam String goodsid,
            @ApiParam(value = "goodsname")@RequestParam String goodsname,
            @ApiParam(value = "orderno")@RequestParam String orderno,
            @ApiParam(value = "paytime")@RequestParam String paytime,
            @ApiParam(value = "paytype")@RequestParam String paytype,
            @ApiParam(value = "result")@RequestParam String result,
            @ApiParam(value = "timestamp")@RequestParam String timestamp,
            @ApiParam(value = "userid")@RequestParam String userid,
            @ApiParam(value = "sign")@RequestParam String sign
    ) throws IOException, ApiException {
        ZhijianWxpayResponse response = new ZhijianWxpayResponse();
        logger.info("--------------------zhijianWxpayNotify------appname---"+appname);
//        //获得微信服务器传递的信息

        logger.info("准备验签");
        //验签
        String strA = "bundleid="+bundleid+"&fee="+fee+"&goodsid="+goodsid+"&goodsname="+goodsname+"&orderno="+orderno+"&paytime="+paytime+"&paytype="+paytype+"&result="+result+"&timestamp="+timestamp+"&userid="+userid;
        String strB = strA + "&key=SsHSivBkt0QOWQ4g9CkCdMtoEAmy5ZWi";
        String fhsign = getMD5(strB).toUpperCase();

        logger.info("--------------------zhijianWxpayNotify------sign---"+sign);
        logger.info("--------------------zhijianWxpayNotify------fhsign---"+fhsign);

        if(!fhsign.equals(sign)){
            //验签失败
            logger.info("yan qian shi bai");
            response.setReturncode("FAIL");
            response.setReturnmsg("签名不对");
            return response;
        }


        //获得支付订单
        Long orderId = Long.parseLong(goodsid);

        try{
            String intro="指尖";
            if(paytype.equals("0")){
                intro = "指尖微信支付充值";
            }
            if(paytype.equals("6")){
                intro = "指尖ios支付充值";
            }
            this.setOrderSuccess(orderId.intValue(),bundleid,RechargePayStyle.ZHIJIAN_PAY,result,orderno,intro);
        }catch (ApiNotFoundException e){
            e.printStackTrace();
            logger.info("--------------zhijian-----wxpayNotify----payRechargeOrder--null---");
            logger.info("订单已删除");
            response.setReturncode("FAIL");
            response.setReturnmsg("订单已删除");
            return response;
        }


        response.setReturncode("SUCCESS");
        response.setReturnmsg("");
        return response;
    }
    /**
     * 分销用，提交订单
     *
     * @param payRechargeOrder
     * @param rechargePayStyle      1ios2微信3支付宝4华为
     */
    private void fenxiaoCheckOrder(PayRechargeOrder payRechargeOrder, RechargePayStyle rechargePayStyle) {
        int source_type=0;
        if(rechargePayStyle == RechargePayStyle.IOS_PAY){
            source_type=1;
        }else if(rechargePayStyle == RechargePayStyle.WEIXINP_PAY){
            source_type=2;
        }else if(rechargePayStyle == RechargePayStyle.ALI_PAY){
            source_type=3;
        }else if(rechargePayStyle == RechargePayStyle.HUAWEI_PAY){
            source_type=4;
        }
//        HttpRequest httpRequest = new HttpRequest();
//        String url = "http://wx.storybook.ifenghui.com/public/index.php/api/order/orderCheck";
        String url = env.getProperty("fenxiao.url") + "public/index.php/api/order/orderCheck";
        String vipActiveResult;
        long userId = payRechargeOrder.getUser().getId();
        String nick = payRechargeOrder.getUser().getNick();
        String avatar = payRechargeOrder.getUser().getAvatar();
        String unionid;
        UserAccount userAccount = userAccountService.getUserAccountByUserIdAndSrcType(userId, 1);
        if (userAccount != null) {
            unionid = userAccount.getSrcId();
            Integer amount = payRechargeOrder.getAmount();//订单金额
//            Integer source_type = source_type;//1ios2微信3支付宝
            String ip = (String) request.getRemoteAddr();//获取用户客户端ip
            String param = "app_uid=" + userId
                    + "&nick=" + nick
                    + "&avatar=" + avatar
                    + "&unionid=" + unionid
                    + "&amount=" + amount
                    + "&app_order_num=" + payRechargeOrder.getId()
                    + "&source_type=" + source_type
                    + "&ip=" + ip;
            try {
                HttpRequest.sendPost(url, param);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



    /**
     * 新支付宝支付回调地址
     *
     * @throws Exception
     */
    @RequestMapping(value = "/mtxAlipayNotify", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "新支付宝支付回调地址", notes = "漫天下账号")
    @ApiResponses({@ApiResponse(code = 1, message = "成功", response = GetUserResponse.class)
            , @ApiResponse(code = 201, message = "没有找到这个用户", response = ExceptionResponse.class)})
    void mtxAlipayNotify(
    ) throws Exception {
        logger.info("-------------mtx-------alipayNotify--top-appname-----");

        //普通app包
        AlipayConfig.partner = env.getProperty("partner");
        AlipayConfig.private_key = env.getProperty("private_key");
        AlipayConfig.alipay_public_key = env.getProperty("alipay_public_key");



        logger.info("-------------mtx-------alipayNotify--------");
        //获取支付宝POST过来反馈信息
        Map<String, String> params = VersionUtil.getParams(request);

        logger.info("-------------mtx-alipayNotify-----params---" + params);

        String alipaypublicKey = env.getProperty("alipay_public_key");
        String charset = "utf-8";
        boolean signVerfied = AlipaySignature.rsaCheckV1(params, alipaypublicKey, charset, "RSA");

        System.out.println("验证签名的结果是：" + signVerfied);
        if (signVerfied == false) {

            response.getWriter().print("fail");
            return;
        }

        //按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure

        String callbackMsg = transMapToString(params);

        String prefix = env.getProperty("order.prefix");

        String orderIdstr = params.get("out_trade_no");

        long orderId;
        //可以从订单号中区分 是单行本 还是  app
        orderId = Long.parseLong(orderIdstr.split("_")[1]);
        if (!orderIdstr.split("_")[0].equals(prefix)) {
            logger.info("--------mtx------------alipayNotify----dingdanbijiaoshibai----");
            response.getWriter().print("fail");
            return;
        }
        logger.info("------------mtx--------alipayNotify----getOrderId----" + orderId);

        PayRechargeOrder payRechargeOrder = payRechargeOrderService.getPayRechargeOrder(orderId);//获取此充值订单数据
        // 获取 payAccount  buyer_id
        String buyer_id = params.get("buyer_id");
        if (buyer_id == null) {
            buyer_id = "";
        }
        payRechargeOrder.setPayAccount(buyer_id);//买家支付宝账号


        String ostatus = params.get("trade_status");
        if (ostatus.equals("TRADE_FINISHED") || ostatus.equals("TRADE_SUCCESS")) {
            logger.info("-------------mtx-------alipayNotify-----TRADE_FINISHED--TRADE_SUCCESS-----");

            try{
                String tradeNo = params.get("trade_no");
                this.setOrderSuccess(payRechargeOrder.getId().intValue()
                        ,buyer_id
                        ,RechargePayStyle.ALI_PAY
                        ,callbackMsg
                        ,tradeNo
                        ,"支付宝支付充值");
            }catch (ApiNotFoundException e){
                e.printStackTrace();
                response.getWriter().print("fail");
                return;
            }

            response.getWriter().print("success");
            return;
        }
        return;
    }
    /**
     * 新支付宝支付回调地址
     *
     * @throws Exception
     */
    @RequestMapping(value = "/childAlipayNotify/{appname}", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "新支付宝支付回调地址", notes = "漫天下账号")
    @ApiResponses({@ApiResponse(code = 1, message = "成功", response = GetUserResponse.class)
            , @ApiResponse(code = 201, message = "没有找到这个用户", response = ExceptionResponse.class)})
    void childAlipayNotify(
            @ApiParam(value = "appname")@PathVariable("appname") String appname
    ) throws Exception {
        logger.info("-------------mtx-------childAlipayNotify--top-appname-----"+appname);
        //获取url判断回掉来源
//         appname = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/")+1);//获取url最后一个/的内容
        if(appname.equals("childstory")){
            logger.info("-------------mtx-------childAlipayNotify---majiabao-----");
            //马甲包
            AlipayConfig.partner = env.getProperty("partner");
            AlipayConfig.private_key = env.getProperty("child_private_key");
            AlipayConfig.alipay_public_key = env.getProperty("child_alipay_public_key");
        }else{
            //普通app包
            AlipayConfig.partner = env.getProperty("partner");
            AlipayConfig.private_key = env.getProperty("private_key");
            AlipayConfig.alipay_public_key = env.getProperty("alipay_public_key");
        }


        logger.info("-------------mtx-------childAlipayNotify--------");
        //获取支付宝POST过来反馈信息
        Map<String, String> params =VersionUtil.getParams(request);

        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        String alipaypublicKey = env.getProperty("alipay_public_key");
        String charset = "utf-8";
        boolean signVerfied = AlipaySignature.rsaCheckV1(params, alipaypublicKey, charset, "RSA");

        System.out.println("验证签名的结果是：" + signVerfied);
        if (signVerfied == false) {
            logger.info("---------mtx-----------childAlipayNotify----yan qian shi bai----");
            response.getWriter().print("fail");
            return;
        }
        logger.info("------------mtx--------childAlipayNotify----yan qian success----");

        //按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure

        String callbackMsg = transMapToString(params);

        String prefix = env.getProperty("order.prefix");
        String orderIdstr = params.get("out_trade_no");
        Long orderId;
        //可以从订单号中区分 是单行本 还是  app
        orderId = Long.parseLong(orderIdstr.split("_")[1]);
        if (!orderIdstr.split("_")[0].equals(prefix)) {
            logger.info("--------mtx------------childAlipayNotify----dingdanbijiaoshibai----");
            response.getWriter().print("fail");
            return;
        }
        logger.info("------------mtx--------childAlipayNotify----getOrderId----" + orderId);

        PayRechargeOrder payRechargeOrder = payRechargeOrderService.getPayRechargeOrder(orderId);//获取此充值订单数据
        // 获取 payAccount  buyer_id
        String buyer_id = params.get("buyer_id");
        if (buyer_id == null) {
            buyer_id = "";
        }
        payRechargeOrder.setPayAccount(buyer_id);//买家支付宝账号


        String ostatus = params.get("trade_status");
        if (ostatus.equals("TRADE_FINISHED") || ostatus.equals("TRADE_SUCCESS")) {
            logger.info("-------------mtx-------childAlipayNotify-----TRADE_FINISHED--TRADE_SUCCESS-----");

            try{
                String tradeNo = params.get("trade_no");
                this.setOrderSuccess(payRechargeOrder.getId().intValue()
                        ,buyer_id
                        ,RechargePayStyle.ALI_PAY
                        ,callbackMsg
                        ,tradeNo
                        ,"支付宝支付充值");
            }catch (ApiNotFoundException e){
                e.printStackTrace();
                response.getWriter().print("fail");
                return;
            }

            response.getWriter().print("success");
            return;
        }
        return;
    }


    @RequestMapping(value = "/iospayNotify", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "IOS支付回调地址", notes = "IOS支付回调地址")
    @ApiResponses({@ApiResponse(code = 1, message = "成功", response = GetUserResponse.class)
            , @ApiResponse(code = 201, message = "没有找到这个用户", response = ExceptionResponse.class)})
    IosPayResponse iospayNotify(
            @ApiParam(value = "apple支付信息") @RequestParam String appStoreMsg,
//            @ApiParam(value = "是否沙盒环境") @RequestParam Integer isSandbox,
            @ApiParam(value = "用户token") @RequestParam String token,
//            @ApiParam(value = "价格id") @RequestParam Long priceId,
            @ApiParam(value = "渠道", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String channel,
            @ApiParam(value = "版本号", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String ver

    ) throws ApiException, Exception {
//        String testMsg = "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";
        Long userId = userService.checkAndGetCurrentUserId(token);
        User user = userService.getUser(userId);
        IosPayResponse response = new IosPayResponse();
        response.setHasSendCoupon(0);
        String callbackMsg="appstoryMsg:"+appStoreMsg+";token:"+token;
        //记录回调信息
        PayCallbackRecord callbackRecord=this.createCallback(callbackMsg, RechargePayStyle.IOS_PAY.getId(),null);

        IosNotify iosNotify=IosNotifyHttpUtil.getNotify(appStoreMsg);

        if(iosNotify.getStatus()!=0){
            response.getStatus().setCode(2);
            response.getStatus().setMsg("充值失败,iosNotify状态："+iosNotify.getStatus());
            return response;
        }

        if(iosNotify.getReceipt().getIn_app().size()==0){
            response.getStatus().setCode(2);
            response.getStatus().setMsg("充值失败,inapp=0"+iosNotify.getStatus());
            return response;
        }

        for(InApp inApp:iosNotify.getReceipt().getIn_app()){
            PayRechargeOrder order = payService.getPayRechargeOrderByTradeNo(inApp.getTransaction_id());
            if (order != null) {
//            此订单支付
                logger.info("此商品已购买过 transactionId:"+inApp.getTransaction_id());
                response.getErrorTransactionIds().add(inApp.getTransaction_id());
                continue;
            }
            PayRechargePrice payRechargePrice = payService.getPayRechargePriceByIap(inApp.getProduct_id());
            if (payRechargePrice==null) {
                logger.info("未找到购买项 transactionId:"+inApp.getTransaction_id());
                response.getErrorTransactionIds().add(inApp.getTransaction_id());
                continue;
            }

            /**
             * 发送有充值推送消息和赠送代金券
             */
            Map<String, String> contentMap = new HashMap<>();
            Integer money = payRechargePrice.getPrice() / 100;
            contentMap.put("money", money.toString());
            if(payRechargePrice.getId().equals(5L)){
                couponSerivce.collectCoupon(userId,4L,channel);
                response.setHasSendCoupon(1);
                templateNoticeService.addNoticeToUserByUserIdAndMessage(NoticeStyle.IOS_REWARD, contentMap, userId.intValue());
            } else {
                templateNoticeService.addNoticeToUserByUserIdAndMessage(NoticeStyle.IOS, contentMap, userId.intValue());
            }
            response.getSuccessTransactionIds().add(inApp.getTransaction_id());

            //创建定单
            order=this.createRechargeOrder(RechargePayStyle.IOS_PAY,payRechargePrice.getPrice(),user,channel,inApp.getTransaction_id(),iosNotify.getIsSandBox(),new Date());

            this.updateCallback(callbackRecord,order);
            /**
             * 增加购买vip的订阅购买
             */
            PayVipOrder payVipOrder = null;
            if(inApp.getProduct_id().equals(VipPriceStyle.YEAR_VIP.getIosSubPrice())){
                payVipOrder = payVipOrderService.createPayVipOrder(VipGoodsStyle.YEAR_VIP, user.getId(), null);
            } else if(inApp.getTransaction_id().equals(VipPriceStyle.HALF_YEAR_VIP.getIosSubPrice())){
                payVipOrder = payVipOrderService.createPayVipOrder(VipGoodsStyle.HALF_YEAR_VIP, user.getId(), null);
            } else if(inApp.getTransaction_id().equals(VipPriceStyle.SEASON_VIP.getIosSubPrice())){
                payVipOrder = payVipOrderService.createPayVipOrder(VipGoodsStyle.SEASON_VIP, user.getId(), null);
            }

            if(payVipOrder != null){
                payVipOrderService.buyVipByBalance(user.getId(), payVipOrder.getId(), OrderPayStyle.IOS_BLANCE, WalletStyle.IOS_WALLET);
            }
        }
        response.getStatus().setCode(1);
        response.getStatus().setMsg("购买成功");


        logger.info("--------iospay------------success---------");
        return response;
    }

    @RequestMapping(value = "/iospayNotifyOverTime", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "过时回调数据，补充历史定单数据", notes = "IOS支付回调地址")
    @ApiResponses({@ApiResponse(code = 1, message = "成功", response = GetUserResponse.class)
            , @ApiResponse(code = 201, message = "没有找到这个用户", response = ExceptionResponse.class)})
    IosPayResponse iospayNotifyOverTime(
            @ApiParam(value = "apple支付信息") @RequestParam String appStoreMsg,
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "渠道", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String channel,
            @ApiParam(value = "yyyy-MM-dd HH:mm:SS") @RequestParam String createTime
    ) throws ApiException, Exception {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date createDate=sdf1.parse(createTime);
        logger.info("iosnotify: token:"+token);
        Long userId = userService.checkAndGetCurrentUserId(token);
        logger.info("iosnotify: userId:"+userId);
        User user = userService.getUser(userId);
        logger.info("iosnotify: user:"+user);
        IosPayResponse response = new IosPayResponse();

        String callbackMsg="appstoryMsg:"+appStoreMsg+";token:"+token;
        //记录回调信息
        PayCallbackRecord callbackRecord=this.createCallback(callbackMsg, RechargePayStyle.IOS_PAY.getId(),null);

        IosNotify iosNotify=IosNotifyHttpUtil.getNotify(appStoreMsg);

        if(iosNotify.getStatus()!=0){
            response.getStatus().setCode(2);
            response.getStatus().setMsg("充值失败,iosNotify状态："+iosNotify.getStatus());
            return response;
        }

        if(user==null){
            response.getStatus().setCode(2);
            response.getStatus().setMsg("充值失败,请重新登录");
            return response;
        }

        if(iosNotify.getReceipt().getIn_app().size()==0){
            response.getStatus().setCode(2);
            response.getStatus().setMsg("充值失败,inapp=0"+iosNotify.getStatus());
            return response;
        }

        for(InApp inApp:iosNotify.getReceipt().getIn_app()){
            PayRechargeOrder order = payService.getPayRechargeOrderByTradeNo(inApp.getTransaction_id());
            if (order != null) {
//            此订单支付
                logger.info("此商品已购买过 transactionId:"+inApp.getTransaction_id());
                response.getErrorTransactionIds().add(inApp.getTransaction_id());
                continue;
            }
            PayRechargePrice payRechargePrice = payService.getPayRechargePriceByIap(inApp.getProduct_id());
            if (payRechargePrice==null) {
                logger.info("未找到购买项 transactionId:"+inApp.getTransaction_id());
                response.getErrorTransactionIds().add(inApp.getTransaction_id());
                continue;
            }
            response.getSuccessTransactionIds().add(inApp.getTransaction_id());


            //创建定单
            order=this.createRechargeOrder(RechargePayStyle.IOS_PAY,payRechargePrice.getPrice(),user,channel,inApp.getTransaction_id(),iosNotify.getIsSandBox(),createDate);

            this.updateCallback(callbackRecord,order);
        }
        response.getStatus().setCode(1);
        response.getStatus().setMsg("充值成功");

        logger.info("--------iospay------------success---------");
        return response;
    }

    /**
     * 记录回调数据公共方法,需要重构到service
     */
    private PayCallbackRecord createCallback(String callBackMsg,int payType,PayRechargeOrder rechargeOrder){
        PayCallbackRecord payCallbackRecord=callBackService.getPayCallbackRecordByCallBackMsg(callBackMsg);
        if(payCallbackRecord==null){
            payCallbackRecord = new PayCallbackRecord();
        }
        if(rechargeOrder!=null){
            payCallbackRecord.setOrderCode(rechargeOrder.getOrderCode());
            payCallbackRecord.setPayRechargeOrder(rechargeOrder);
        }

        payCallbackRecord.setPayType(RechargePayStyle.getById(payType));
        payCallbackRecord.setCreateTime(new Date());
        payCallbackRecord.setCallbackMsg(callBackMsg);
        PayCallbackRecord payCallbackRecord1=callBackService.addPayCallbackRecord(payCallbackRecord);
        return payCallbackRecord1;
    }

    /**
     * 修改callbackorder信息，需要重构到service
     * @param payCallbackRecord
     * @param rechargeOrder
     */
    private void updateCallback(PayCallbackRecord payCallbackRecord,PayRechargeOrder rechargeOrder){
        if(payCallbackRecord==null){
            return ;
        }
        if(rechargeOrder!=null){
            return;
        }
//        payCallbackRecord.setType(payType);
        payCallbackRecord.setCreateTime(new Date());
        payCallbackRecord.setOrderCode(rechargeOrder.getOrderCode());
        callBackService.addPayCallbackRecord(payCallbackRecord);
    }
    /**
     * 支付充值，创建recharge定单，公共方法
     */
    private PayRechargeOrder createRechargeOrder(RechargePayStyle payStyle, int amount, User user, String channel, String tradeNo, int isTest, Date createTime){
//        Integer buyType = 3;//ios支付
        String flag="_";
        Integer orderStatus = 1;
        PayRechargeOrder payRechargeOrder = new PayRechargeOrder();
        if (payStyle.equals(RechargePayStyle.IOS_PAY) || payStyle.equals(RechargePayStyle.ZHIJIAN_PAY)){
            payRechargeOrder.setWalletStyle(WalletStyle.IOS_WALLET);
        } else {
            payRechargeOrder.setWalletStyle(WalletStyle.ANDROID_WALLET);
        }
        payRechargeOrder.setCreateTime(createTime);
        payRechargeOrder.setSuccessTime(createTime);
        payRechargeOrder.setStatus(orderStatus);
        payRechargeOrder.setAmount(amount);
//                payRechargeOrder.setTargetType(targetType);
//                payRechargeOrder.setPayTarget(payTarget);
        payRechargeOrder.setPayStyle(payStyle);//3ios支付
//                payOrder.setDealerId(dealerId);
        payRechargeOrder.setUser(user);
        payRechargeOrder.setPayAccount("");
        payRechargeOrder.setOrderCode("");
        if (channel == null) {
            channel = "";
        }
        payRechargeOrder.setChannel(channel);
        payRechargeOrder.setTradeNo(tradeNo);
        payRechargeOrder.setRechargeStyle(RechargeStyle.RECHARGE);
        payRechargeOrder.setIsTest(isTest);
        payRechargeOrder.setAppName("");
        String prefix = env.getProperty("order.prefix");
//                PayOrder payOrder1=  orderService.addPayOrder(payOrder);
        PayRechargeOrder payRechargeOrder1 = orderService.addPayRechargeOrder(payRechargeOrder);
        payRechargeOrder1.setOrderCode(prefix + flag + payRechargeOrder1.getId());
//                orderService.updatePayOrder(payRechargeOrder1);
//                orderService.updatePayOrder(payRechargeOrder1);
        //存回调数据 orderId message time payType

        //增加钱包余额
        Integer type = 1;//1添加2减少
//                walletService.editWalletBalance(userId, payRechargeOrder.getAmount(), type);
        logger.info("--------iospay------------userId---------" + user.getId());
        logger.info("--------iospay-------------amount--------" + payRechargeOrder.getAmount());
        logger.info("--------iospay------------type---------" + type);
        logger.info("--------iospay---------walletService------------" + walletService);
//        walletService.editWalletBalance(user.getId(), payRechargeOrder.getAmount(), type);
//        //添加流水记录（进账）
//        userAccountRecordService.addUserAcountRecord(user.getId(), amount, AddStyle.UP, RechargeStyle.RECHARGE, "ios充值");//1添加2减少

        walletService.addAmountToWallet(payRechargeOrder.getUser().getId().intValue(),payRechargeOrder.getWalletStyle(),RechargeStyle.RECHARGE,payRechargeOrder.getAmount(),"recharge_"+tradeNo,"ios充值");
        this.fenxiaoCheckOrder(payRechargeOrder, RechargePayStyle.IOS_PAY);

        return payRechargeOrder1;
    }





    @RequestMapping(value = "/iospayNotifyRestore", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "ios支付回调补充接口", notes = "ios支付回调补充接口,只支持inapp长度是1的情况")
    IosPayResponse iospayNotifyRestore(
//            @ApiParam(value = "postId") @RequestParam Integer postId,
//            @ApiParam(value = "是否沙盒环境") @RequestParam Integer isSandbox,
            @ApiParam(value = "rechargeOrderId") @RequestParam Integer rechargeOrderId

    ) throws ApiException, Exception {

        PayRechargeOrder payRechargeOrder= payService.getPayRechargeOrder(rechargeOrderId.longValue());
        if(payRechargeOrder==null){
            return null;
        }
        IosPayResponse resp= new IosPayResponse();
        PayCallbackRecord payCallbackRecord= callBackService.getPayCallbackRecordByOrderId(payRechargeOrder.getId());
        if(payCallbackRecord==null){
            return null;
        }
        String jsonMsg=payCallbackRecord.getCallbackMsg();
        IosNotify iosNotify= IosNotifyHttpUtil.getNotify(jsonMsg);
        if(iosNotify.getReceipt().getIn_app().size()!=1){
            return resp;
        }
        for(InApp inapp: iosNotify.getReceipt().getIn_app()){
            String transId=inapp.getTransaction_id();//唯一标识

            //避免重复判断
            PayRechargeOrder dbOrder= payService.getPayRechargeOrderByTradeNo(transId);
            if(dbOrder!=null){
                logger.info("dup rechargeorder"+transId);
                continue;
            }

            payRechargeOrder.setTradeNo(transId);
            payRechargeOrderService.updateRechargePayOrder(payRechargeOrder);
        }

        return resp;
    }


    /**
     * 自定义form
     *
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/share.action", method = RequestMethod.GET)
    public String customFrom(HttpServletRequest request, HttpServletResponse response,
                             ModelMap modelMap, Long userId, Integer wxBackCode) throws Exception {


        return sharerController.share(request,response,modelMap,userId, wxBackCode);
//        userId = 18l;
//        modelMap.put("userId", ""+userId);
//
//        String acceptCouponUrl = env.getProperty("local.url") + "api/pay/acceptCoupon";
//        String shareSuccessUrl = env.getProperty("local.url") + "api/pay/shareSuccess.action";
//        modelMap.put("acceptCouponUrl", acceptCouponUrl);
//        modelMap.put("shareSuccessUrl", shareSuccessUrl);
//
//
//        return "share/share";

    }

    /**
     * 自定义form
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/shareSuccess.action", method = RequestMethod.GET)
    public String customFrom(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return sharerController.shareSuccess(request,response);
//        return "share/success";
    }

    /**
     * 自定义form
     *
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/couponRule.action", method = RequestMethod.GET)
    public String customFrom(HttpServletRequest request, HttpServletResponse response,
                             ModelMap modelMap) throws Exception {


//        CmsEntity cmsEntity=groupService.getEntityByName(e);
//        Class classType=groupService.getEntityClassByName(e);

//        modelMap.put("cmsEntity",cmsEntity);
//        String name = "分享";
//        userId = 18l;
//        modelMap.put("name",name);
//        CmsLink cmsLink= CustomMethodUtil.getAllCustomMethod(request,classType,r);

//        modelMap.put("cmsLink",cmsLink);
//        modelMap.put("id",id);


        return "couponRule/couponRule";

    }


    private String body;

    /**
     * 华为支付回调地址
     *
     * @throws Exception
     */
    @RequestMapping(value = "/huaweiPayNotify", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "华为回调地址", notes = "")
    @ApiResponses({@ApiResponse(code = 1, message = "成功", response = GetUserResponse.class)
            , @ApiResponse(code = 201, message = "没有找到这个用户", response = ExceptionResponse.class)})
    void huaweiPayNotify(
//            @ApiParam(value = "appname")@PathVariable("appname") String appname,
            HttpServletRequest request
    ) throws Exception {
        logger.info("--------------------huaweiPayNotify----start--appname--");
        request.setCharacterEncoding("UTF-8");

        Map<String, Object> map;
        CallbackDemo callbackDemo = new CallbackDemo();
        map = callbackDemo.getValue(request);
        logger.info("--------------------huaweiPayNotify----map----" + map);
        if (null == map) {
            return;
        }
        String sign = (String) map.get("sign");
        logger.info("--------------------huaweiPayNotify----sign----" + sign);
        ResultDomain result = new ResultDomain();
        result.setResult(1);

        logger.info("--------------------huaweiPayNotify----signType----" + (String) map.get("signType"));
        if (RSA.rsaDoCheck(map, sign, devPubKey, (String) map.get("signType"))) {
            result.setResult(0);
            logger.info("--------------------huaweiPayNotify----yan qian success----");


            String callbackMsg = transMapToString(map);

            String prefix = env.getProperty("order.prefix");

            String orderIdstr = (String) map.get("requestId");

            Long orderId;
            //可以从订单号获取
            orderId = Long.parseLong(orderIdstr.split("_")[1]);
            if (!orderIdstr.split("_")[0].equals(prefix)) {
                logger.info("--------------------huaweiPayNotify----dingdanbijiaoshibai----");
                result.setResult(98);//98: 参数错误,
                this.huaweiPayResult(result);
                return;
            }
            logger.info("--------------------huaweiPayNotify----getOrderId----" + orderId);

            PayRechargeOrder payRechargeOrder = payRechargeOrderService.getPayRechargeOrder(orderId);//获取此充值订单数据
            // 获取 payAccount  buyer_id
//            String buyer_id = (String) map.get("buyer_id");
//            if (buyer_id == null) {
            String buyer_id = "";
//            }
            payRechargeOrder.setPayAccount(buyer_id);//买家支付宝账号
            String ostatus = (String) map.get("result");
            if (ostatus.equals("0")) {
                logger.info("--------------------huaweiPayNotify------------");

                try{
                    String tradeNo = (String) map.get("userName");
                    this.setOrderSuccess(payRechargeOrder.getId().intValue()
                            ,buyer_id
                            ,RechargePayStyle.HUAWEI_PAY
                            ,callbackMsg
                            ,tradeNo
                            ,"支付宝支付充值");
                    result.setResult(0);
                    this.huaweiPayResult(result);
                }catch (ApiNotFoundException e){
                    e.printStackTrace();
                    result.setResult(3);

                }


            } else {
                //支付失败
                result.setResult(3);
            }
        } else {
            //验签失败
            logger.info("--------------------huaweiPayNotify-------yanqian shibai-----");
            result.setResult(1);
        }


        this.huaweiPayResult(result);
        return;
    }

    private void huaweiPayResult(ResultDomain result)throws Exception{
        CallbackDemo callbackDemo = new CallbackDemo();
        String resultinfo = callbackDemo.convertJsonStyle(result);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

//        System.out.println("Response string: " + resultinfo);

        PrintWriter out = response.getWriter();

        out.print(resultinfo);
        out.close();
        return ;
    }



}
