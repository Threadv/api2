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
@Api(value="??????",description = "??????????????????")
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
//    @ApiOperation(value = "???????????????,?????????shopping????????????", notes = "")
    GetShoppingCartResponse test(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "??????") @RequestParam Integer pageNo,
            @ApiParam(value = "??????") @RequestParam Integer pageSize
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
    @ApiOperation(value = "???????????????,?????????shopping????????????", notes = "")
    GetShoppingCartResponse getShoppingCart(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "??????") @RequestParam Integer pageNo,
            @ApiParam(value = "??????") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {
        return shoppingCartController.getShoppingCart(token, pageNo, pageSize);
    }


    @RequestMapping(value = "/delShoppingCart", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "???????????????,?????????shopping????????????", notes = "")
    @ApiResponses({@ApiResponse(code = 206, message = "???????????????", response = ExceptionResponse.class)})
    DelShoppingCartResponse delShoppingCart(
            @ApiParam(value = "??????token") @RequestParam String token,
//              @ApiParam(value = "?????????id?????????id?????????")@RequestParam Long cartIds[]
            @ApiParam(value = "?????????id?????????id???????????????") @RequestParam String cartIdsStr
    ) throws ApiException {
        return shoppingCartController.delShoppingCart(token, cartIdsStr);

    }


    @RequestMapping(value = "/addShoppingCart", method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({@ApiResponse(code = 205, message = "?????????????????????", response = ExceptionResponse.class)
            , @ApiResponse(code = 207, message = "??????????????????", response = ExceptionResponse.class)})
    @ApiOperation(value = "???????????????,?????????shopping????????????", notes = "")
    AddShoppingCartResponse addShoppingCart(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "??????id") @RequestParam Long storyId
    ) throws ApiException {
        return shoppingCartController.addShoppingCart(token, storyId);
    }


    @RequestMapping(value = "/createPayStoryOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "???????????????/????????????????????????????????????", notes = "")
    GetBuyStorysOrderResponse getBuyStorysOrder(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "??????id?????????id???????????????") @RequestParam String storyIdsStr
    ) throws ApiException {
        return orderController.getBuyStorysOrder(token,storyIdsStr,new ArrayList<>());
    }


    //??????????????????1.7?????????????????????????????????????????????
    @RequestMapping(value = "/getBuyStorysOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "???????????????getBuyStorysOrderOld-1.7", notes = "")
    GetBuyStorysOrderResponse getBuyStorysOrderOld(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "??????id?????????id???????????????") @RequestParam String storyIdsStr
    ) throws ApiException {
//        GetBuyStorysOrderResponse response=new GetBuyStorysOrderResponse();
        //        response.getStatus().setCode(0);
//        response.getStatus().setMsg("?????????APP??????????????????");
//        return response;
//        return response;
        return orderController.getBuyStorysOrder(token,storyIdsStr,new ArrayList<>());

    }


    @RequestMapping(value = "/buyStorysByBalance", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "???????????????/????????????????????????????????????", notes = "")
    BuyOrderByBalanceResponse buyStorysByBalance(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "?????????") @RequestParam String orderCode,
             @ApiParam(value = "????????????") @RequestParam(required = false) WalletStyle walletStyle
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
    @ApiOperation(value = "???????????????/????????????????????????????????????", notes = "")
    BuyOrderByBalanceResponse buyStorys(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "?????????") @RequestParam String orderCode
    ) throws ApiException {
        return this.buyStorysByBalance(token, orderCode, null);
    }


    @RequestMapping(value = "/buySerialStory", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "?????????????????????", notes = "")
    BuyOrderByBalanceResponse buySerialStory(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "?????????") @RequestParam String orderCode,
            @ApiParam(value = "????????????") @RequestParam(required = false) WalletStyle walletStyle
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
    @ApiOperation(value = "???????????????????????????", notes = "")
    EditShoppingCartResponse editShoppingCartStatus(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "?????????id") @RequestParam Long cartId,
            @ApiParam(value = "??????1??????2????????????") @RequestParam Integer type
    ) throws ApiNotTokenException {
        return shoppingCartController.editShoppingCartStatus(token,cartId,type);

    }

    @RequestMapping(value = "/getSubscriptionPrice", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "??????????????????->????????? subscription/getSubscriptionPrice", notes = "")
    GetSubscriptionPriceResponse getSubscriptionPrice(
            @ApiParam(value = "??????token") @RequestParam String token
    ) throws ApiNotTokenException {

        return subscriptionController.getSubscriptionPrice(token);
    }

    @RequestMapping(value = "/getSubscriptionOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({@ApiResponse(code = 209, message = "??????????????????", response = ExceptionResponse.class),
            @ApiResponse(code = 210, message = "??????????????????", response = ExceptionResponse.class)
    })
    @ApiOperation(value = "???????????????????????????,?????????subscription.createSubscriptionOrder", notes = "")
    GetSubscriptionOrderResponse getSubscriptionOrder(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "??????id") @RequestParam Long priceId,
            @ApiParam(value = "?????????id?????????id???????????????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String couponIdsStr,
            @ApiParam(value = "?????????id", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") Long couponDeferredId
    ) throws ApiException {
        return subscriptionController.createSubscriptionOrder(token,priceId,couponIdsStr,couponDeferredId);
    }


    /**
     * ???????????????????????????????????????
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
//            @ApiResponse(code=212,message="???????????????????????????",response = ExceptionResponse.class)
//    })
    @ApiOperation(value = "?????????????????????", notes = "??????+??????")
    GetUserPayOrderResponse getUserPayOrder(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "??????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") Integer status,
            @ApiParam(value = "??????") @RequestParam Integer pageNo,
            @ApiParam(value = "??????") @RequestParam Integer pageSize
//            @ApiParam(value = "????????????????????????id")@RequestParam Long orderId
    ) throws ApiException {
        return orderController.getUserPayOrder(token,status,pageNo,pageSize);

    }


    @RequestMapping(value = "/getUserPayOrderDetail", method = RequestMethod.GET)
    @ResponseBody
//    @ApiResponses({
//            @ApiResponse(code=212,message="???????????????????????????",response = ExceptionResponse.class)
//    })
    @ApiOperation(value = "?????????????????????", notes = "??????+??????+???????????????,?????????Order ->getUserPayOrderDetail")
    GetUserPayOrderDetailResponse getUserPayOrderDetail(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "???????????????????????????id") @RequestParam Long orderId,
            @ApiParam(value = "???????????????1??????,2??????3????????????") @RequestParam Integer type
    ) throws ApiException {

        return orderController.getUserPayOrderDetail(token,orderId,type);

    }


    @RequestMapping(value = "/delUserPayOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "????????????", notes = "")
    @ApiResponses({@ApiResponse(code = 206, message = "???????????????", response = ExceptionResponse.class)})
    DelUserPayOrderResponse delUserPayOrder(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "??????id") @RequestParam Long orderId,
            @ApiParam(value = "???????????????1????????????2??????3????????????") @RequestParam Integer type
    ) throws ApiException {
        return orderController.delUserPayOrder(token,orderId,type);
    }

    @RequestMapping(value = "/cancelUserPayOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code = 212, message = "???????????????????????????", response = ExceptionResponse.class)
    })
    @ApiOperation(value = "????????????", notes = "")
    CancelUserPayOrderResponse cancelUserPayOrder(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "??????id") @RequestParam Long orderId,
            @ApiParam(value = "?????????1??????2??????3?????????4?????????") @RequestParam Integer type
    ) throws ApiException {
        return orderController.cancelUserPayOrder(token,orderId,type);
    }


    @RequestMapping(value = "/subscribeByBalance", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "??????????????????", notes = "")
    @ApiResponses({@ApiResponse(code = 204, message = "????????????", response = ExceptionResponse.class)})
    SubscribeByBalanceResponse subscribeByBalance(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "?????????") @RequestParam String orderCode,
            @ApiParam(value = "????????????") @RequestParam(required = false) WalletStyle walletStyle
    ) throws ApiException {

        return subscriptionController.subscribeByBalance(token,orderCode,walletStyle);
    }

    @RequestMapping(value = "/getWalletBalance", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "??????????????????", notes = "")
    GetWalletBalanceResponse getWalletBalance(
            @ApiParam(value = "??????token") @RequestParam String token
    ) throws ApiNotTokenException {
        return walletController.getWalletBalance(token);
    }

    @RequestMapping(value = "/getCodes", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "?????????????????????->?????????vipcode/getCodes", notes = "")
    GetCodesResponse getCodes(
            @ApiParam(value = "??????token") @RequestParam String token
    ) throws ApiException {

        return vipCodeController.getCodes(token);

    }

    @RequestMapping(value = "/subscribeByCode", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "?????????????????????/???????????????->?????????vipcode/subscribeByCode", notes = "")
    @ApiResponses({@ApiResponse(code = 208, message = "code????????????", response = ExceptionResponse.class)})
    SubscribeByCodeResponse subscribeByCode(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "?????????") @RequestParam String code
    ) throws ApiException {
        return  vipCodeController.subscribeByCode(token,code, null, null, null, null,null);

    }

    @RequestMapping(value = "/vip_code_detail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "???????????????-????????????vipcode/vip_code_detail", notes = "???????????????")
    GetVipCodeDetailResponse getVipcodeDetail(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "?????????") @RequestParam String code
    ) throws ApiException {
        return vipCodeController.getVipcodeDetail(token,code);
    }




    @RequestMapping(value = "/getPayJournalAccount", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "????????????????????????", notes = "????????????????????????")
    GetPayJournalAccountResponse getPayJournalAccount(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "????????????") @RequestParam(required = false) WalletStyle walletStyle,
            @ApiParam(value = "?????? 1?????? 2??????") @RequestParam(required = false, defaultValue = "0") Integer type,
            @ApiParam(value = "??????") @RequestParam Integer pageNo,
            @ApiParam(value = "??????") @RequestParam Integer pageSize
    ) throws ApiException {
        if(walletStyle == null){
            walletStyle = WalletStyle.IOS_WALLET;
        }
        Long userId = userService.checkAndGetCurrentUserId(token);
//        pageNo = pageNo - 1;//?????????0????????????????????????1h

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
    @ApiOperation(value = "????????????????????????", notes = "????????????????????????")
    GetPayJournalAccountResponse getAllCashWater(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "??????") @RequestParam Integer pageNo,
            @ApiParam(value = "??????") @RequestParam Integer pageSize
    ) throws ApiException {
        Long userId = userService.checkAndGetCurrentUserId(token);
//        pageNo = pageNo - 1;//?????????0????????????????????????1h

        return userCashAccountRecordService.getAllPayJournalAccountFromUserCashAccountRecord(userId, pageNo, pageSize);
    }

    @RequestMapping(value = "/getRechargePrice", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "????????????????????????)", notes = "????????????????????????")
    GetPayRechargePriceResponse getRechargePrice(
            @ApiParam(value = "?????? 1ios 2 ??????") @RequestParam(required = false) WalletStyle walletStyle
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
    @ApiOperation(value = "??????????????????????????????getRechargeOrder?????????????????????)", notes = "?????????????????????????????????")
    BuyStorysPayApiResponse getPayOrder(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "?????????") @RequestParam String orderCode,
            @ApiParam(value = "???????????????1??????2??????0???") @RequestParam Integer type,
            @ApiParam(value = "???????????????0??????????????????id??????????????????0???", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") Long priceId,
            @ApiParam(value = "??????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String channel
    ) throws ApiException {
        throw new ApiVersionException("?????????????????????,????????????????????????");
    }

    @RequestMapping(value = "/getRechargeOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "????????????????????????-?????????????????????????????????)", notes = "?????????????????????????????????")
    BuyStorysPayApiResponse getRechargeOrder(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "????????????????????????") @RequestParam Long payOrderId,
            @ApiParam(value = "???????????????1??????2??????0???") @RequestParam Integer type,
            @ApiParam(value = "???????????????0??????????????????id??????????????????0???", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") Long priceId,
            @ApiParam(value = "??????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String channel
    ) throws ApiException {
        throw new ApiVersionException("?????????????????????,????????????????????????");
    }

    @RequestMapping(value = "/getNewRechargeOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "????????????????????????-??????????????????)???1.7?????????????????????", notes = "?????????????????????????????????")
    public BuyStorysPayApiResponse getNewRechargeOrder(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "????????????????????????") @RequestParam Long payOrderId,
            @ApiParam(value = "?????????0?????????1?????????2?????????3??????????????? 4????????????, 7??????vip, 9 ?????????????????????") @RequestParam Integer type,
            @ApiParam(value = "???????????????0??????????????????id??????????????????0???", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") Long priceId,
            @ApiParam(value = "??????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String channel
    ) throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
        //?????????????????????
        String appName = "";
        String app_id;
        String private_key;
        String notify_url;
        String userAgent=request.getHeader("User-Agent");
        logger.info("------------new--------getNewRechargeOrder------userAgent--" + userAgent);
        if(userAgent.indexOf("appname:childstory") == -1){//?????????
             app_id = "2017091108667845";
             private_key = env.getProperty("private_key");
             notify_url = env.getProperty("mtxalipay.notify");
        }else{
            logger.info("------------new--------getNewRechargeOrder------majiabao--" );
            //?????????????????????
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
    @ApiOperation(value = "?????????????????????????????????->?????????coupon/getUserValidityCoupons", notes = "")
    GetUserValidityCouponsResponse getUserValidityCouponsOut(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "?????????1?????????2??????????????????") @RequestParam(required = false) Integer type,
            @ApiParam(value = "????????????id/?????????id") @RequestParam(required = false) Long priceId,
            @ApiParam(value = "??????") @RequestParam Integer pageNo,
            @ApiParam(value = "??????") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {
        return couponController.getUserValidityCoupons(token,type,priceId,pageNo,pageSize);
    }

    @RequestMapping(value = "/getUserCoupons", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "???????????????????????????>?????????coupon/getUserCoupons", notes = "")
    GetUserCouponsResponse getUserCoupons(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "??????(1?????????,2?????????)") @RequestParam Integer type,
            @ApiParam(value = "??????") @RequestParam Integer pageNo,
            @ApiParam(value = "??????") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {
        return couponController.getUserCoupons(token,type,pageNo,pageSize);

    }

    @RequestMapping(value = "/getExpiredMixCoupons", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "??????????????????????????????>?????????coupon/getExpiredMixCoupons", notes = "")
    GetExpiredMixCouponsResponse getExpiredMixCoupons(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "??????") @RequestParam Integer pageNo,
            @ApiParam(value = "??????") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {
        return couponController.getExpiredMixCoupons(token,pageNo,pageSize);

    }

    @RequestMapping(value = "/getUserUnReadCoupons", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "?????????????????????????????????>?????????coupon/getUserUnReadCoupons", notes = "")
    GetUserUnReadCouponsResponse getUserUnReadCoupons(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "??????") @RequestParam Integer pageNo,
            @ApiParam(value = "??????") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {
        return couponController.getUserUnReadCoupons(token,pageNo,pageSize);

    }

    @RequestMapping(value = "/getCouponByShare", method = RequestMethod.GET)
    @ResponseBody
    @ApiResponse(code = 210, message = "???????????????????????????????????????", response = ExceptionResponse.class)
    @ApiOperation(value = "?????????????????????", notes = "")
    GetCouponByShareResponse getCouponByShare(
            @ApiParam(value = "??????token") @RequestParam String token
    ) throws ApiException {
        return couponController.getCouponByShare(token);

    }

    @RequestMapping(value = "/getShareInfo", method = RequestMethod.GET)
    @ResponseBody
//    @ApiResponse(code=210,message="???????????????????????????????????????",response = ExceptionResponse.class)
    @ApiOperation(value = "???????????????????????????", notes = "")
    GetShareInfoResponse getShareInfo(
            @ApiParam(value = "??????token") @RequestParam String token
    ) throws ApiException {
        return couponController.getShareInfo(token);

    }

    @RequestMapping(value = "/acceptCoupon", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "???????????????", notes = "")
    public AcceptCouponResponse acceptCoupon(
            @ApiParam(value = "??????id") @RequestParam Long userId,
            @ApiParam(value = "?????????") @RequestParam String phone
    ) throws ApiException {
        return couponController.acceptCoupon(userId,phone);

    }

    @RequestMapping(value = "/collectDeferredCoupon", method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({@ApiResponse(code = 203, message = "????????????????????????", response = ExceptionResponse.class),
            @ApiResponse(code = 1, message = "??????", response = ExceptionResponse.class),
            @ApiResponse(code = 2, message = "?????????", response = ExceptionResponse.class)
    })
    @ApiOperation(value = "???????????????", notes = "")
    CollectDeferredCouponResponse collectDeferredCoupon(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "?????????id") @RequestParam Long couponId,
            @ApiParam(value = "????????????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String channel
    ) throws ApiException {
        return couponController.collectDeferredCoupon(token,couponId,channel);

    }

    @RequestMapping(value = "/getUserValidityDeferredCoupons", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "?????????????????????????????????", notes = "")
    GetUserValidityDeferredCouponsResponse getUserValidityDeferredCoupons(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "????????????id") @RequestParam Long priceId,
            @ApiParam(value = "??????") @RequestParam Integer pageNo,
            @ApiParam(value = "??????") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {
        return couponController.getUserValidityDeferredCoupons(token,priceId,pageNo,pageSize);

    }

    @RequestMapping(value = "/getUserDeferredCoupons", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "???????????????????????????", notes = "")
    GetUserDeferredCouponsResponse getUserDeferredCoupons(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "??????(1?????????,2?????????)") @RequestParam Integer type,
            @ApiParam(value = "??????") @RequestParam Integer pageNo,
            @ApiParam(value = "??????") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {
        return couponController.getUserDeferredCoupons(token,type,pageNo,pageSize);

    }

    @RequestMapping(value = "/isUserSubscribed", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "????????????????????????????????????", notes = "")
    IsUserSubscribedResponse isUserSubscribed(
            @ApiParam(value = "??????id") @RequestParam String token
    ) throws ApiException {
        return subscriptionController.isUserSubscribed(token);
    }

    @RequestMapping(value = "/addUserInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "????????????????????????", notes = "")
    AddUserInfoResponse addUserInfo(
            @ApiParam(value = "??????id") @RequestParam String token,
            @ApiParam(value = "??????") @RequestParam String name,
            @ApiParam(value = "?????????") @RequestParam String phone,
            @ApiParam(value = "??????") @RequestParam String addr
    ) throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);

        AddUserInfoResponse addUserInfoResponse = new AddUserInfoResponse();

        //???????????????????????????
        UserInfo userInfoTest = userInfoService.getUserInfo(userId);
        if (userInfoTest != null) {
            throw new ApiIsAddException("????????????");
        }

        Void res = userInfoService.addUserInfo(userId, name, phone, addr);
        addUserInfoResponse.getStatus().setMsg("????????????");
        return addUserInfoResponse;
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "????????????????????????", notes = "")
    GetUserInfoResponse getUserInfo(
            @ApiParam(value = "??????id") @RequestParam String token
    ) throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);

        GetUserInfoResponse getUserInfoResponse = new GetUserInfoResponse();
        UserInfo userInfo = userInfoService.getUserInfo(userId);
        getUserInfoResponse.setUserInfo(userInfo);
        return getUserInfoResponse;
    }

    /**
     * ????????????????????????
     *
     * @param orderId ?????????
     * @param payStyle ????????????1?????? 2?????????
     * @throws ApiException
     */
    private void processOrderAfter(Long orderId, RechargePayStyle payStyle) throws ApiException {
        /**
         * ???????????????????????????
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
             * ???????????????????????????
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
                 *  ??????????????????????????????
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
         * ?????????????????????????????????????????????
         */
        if (payAfterOrder.getType().equals(RechargeStyle.BUY_STORY.getId())) {
            logger.info("--------------------payNotify-------pay-after-Buy Story-");
            /**
             * ??????
             */
            buyStoryService.buyStorysByBalance(payAfterOrder.getUserId(), payAfterOrder.getPayOrderId(), orderPayStyle,rechargeOrder.getWalletStyle());
        } else if (payAfterOrder.getType().equals(RechargeStyle.SUBSCRIPTION.getId())) {
            logger.info("--------------------payNotify-------pay-after- Subscription -");
            /**
             * ??????
             */
            payService.subscribeByBalance(payAfterOrder.getPayOrderId(), payAfterOrder.getUserId(), orderPayStyle,rechargeOrder.getWalletStyle());
        } else if (payAfterOrder.getType().equals(RechargeStyle.SERIAL.getId())) {
            logger.info("--------------------payNotify-------pay-after---Buy Serial Story-");
            /**
             * ???????????????
             */
            buySerialService.buySerialStoryByBalance(payAfterOrder.getUserId(), payAfterOrder.getPayOrderId(),orderPayStyle,rechargeOrder.getWalletStyle());
        } else if (payAfterOrder.getType().equals(RechargeStyle.LESSON.getId())) {
            logger.info("--------------------payNotify-------pay-after---Buy Lesson-");
            /**
             * ????????????
             */
            payLessonOrderService.buyLessonByBalance(payAfterOrder.getUserId(), payAfterOrder.getPayOrderId().intValue(), orderPayStyle, rechargeOrder.getWalletStyle());
        } else if (payAfterOrder.getType().equals(RechargeStyle.BUY_SVIP.getId())){
            logger.info("--------------------payNotify-------pay-after---Buy vip-");
            /**
             * ??????svip
             */
            payVipOrderService.buyVipByBalance(payAfterOrder.getUserId(), payAfterOrder.getPayOrderId().intValue(), orderPayStyle, rechargeOrder.getWalletStyle());
        }else if (payAfterOrder.getType().equals(RechargeStyle.BUY_ABILITY_PLAN.getId())){
            logger.info("--------------------payNotify-------pay-after---Buy abilityPlan-");
            /**
             * ????????????????????????????????????
             */
            abilityPlanOrderService.buyAbilityPlanByBalance(payAfterOrder.getUserId(), payAfterOrder.getPayOrderId().intValue(), orderPayStyle, rechargeOrder.getWalletStyle());
        }
    }

    /**
     * ???????????????????????????????????????????????????????????????
     */
    private void setOrderSuccess(int orderId, String payAccount, RechargePayStyle rechargePayStyle, String notifyMsg, String tradeNo, String intro) throws ApiNotFoundException{
        PayRechargeOrder payRechargeOrder = payRechargeOrderService.getPayRechargeOrder((long)orderId);//???????????????????????????
        if (payRechargeOrder == null) {
            throw new ApiNotFoundException("???????????????");
//            logger.info("--------------------wxpayNotify----payRechargeOrder--null---");
//            logger.info("???????????????");
//            response.getWriter().write(setXML("FAILURE", "recharge order has delete!"));
//            return;
        } else if(payRechargeOrder.getStatus().equals(1)){
            return ;
        }

        PayCallbackRecord checkRecord = callBackService.getPayCallbackRecordByOrderCode(payRechargeOrder.getOrderCode());

        //??????????????? orderId message time payType
        if (checkRecord == null) {
            PayCallbackRecord payCallbackRecord = new PayCallbackRecord();
            payRechargeOrder.setPayAccount(payAccount);
            payCallbackRecord.setOrderCode(payRechargeOrder.getOrderCode());
            payCallbackRecord.setPayType(rechargePayStyle);//????????????
            payCallbackRecord.setPayRechargeOrder(payRechargeOrder);
            payCallbackRecord.setCreateTime(new Date());
            payCallbackRecord.setCallbackMsg(notifyMsg);
            logger.info("--------------------wxpayNotify------add-paycallrecord--");
            callBackService.addPayCallbackRecord(payCallbackRecord);
        }

        //????????????  ?????????????????????
        logger.info("??????????????????");
        logger.info("??????????????????");
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
         * ??????????????????
         */
        walletService.addAmountToWallet(payRechargeOrder.getUserId(),payRechargeOrder.getWalletStyle(),RechargeStyle.RECHARGE,payRechargeOrder.getAmount(),"recharge_after_"+orderId,intro);
        /**
         * ??????????????????????????????????????????
         * ????????????????????????
         */
//        this.fenxiaoCheckOrder(payRechargeOrder, rechargePayStyle);
        /**
         * ??????????????????
         */
        logger.info("--------------------payNotify------have-processOrder--");
        this.processOrderAfter((long)orderId, rechargePayStyle);
        logger.info("--------------------payNotify------have-processOrder--end--");
    }


    @RequestMapping(value = "/wxpayNotify", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "????????????????????????", notes = "????????????????????????")
    @ApiResponses({@ApiResponse(code = 1, message = "??????", response = GetUserResponse.class)
            , @ApiResponse(code = 201, message = "????????????????????????", response = ExceptionResponse.class)})
    void wxpayNotify(
    ) throws IOException, ApiException {

        logger.info("????????????");
        logger.info("entry");
        //??????url??????????????????
//        appname = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/")+1);//??????url????????????/?????????

        WeixinNotify wxNotify = new WeixinNotify(request);
//        //????????????????????????????????????

        logger.info("????????????");
        //??????
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

        if (!flagSign) {//????????????
            logger.info("fail");
            response.getWriter().write(setXML("FAILURE", "444444"));
            return;
        }

        try{
            this.setOrderSuccess(wxNotify.getOrderId().intValue(),wxNotify.getPayAccount(),RechargePayStyle.WEIXINP_PAY,wxNotify.getXmlDoc(),wxNotify.getTradeNo(),"??????????????????");
        }catch (ApiNotFoundException e){
            e.printStackTrace();
            logger.info("--------------------wxpayNotify----payRechargeOrder--null---");
            logger.info("???????????????");
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
    @ApiOperation(value = "????????????????????????", notes = "????????????????????????")
    @ApiResponses({@ApiResponse(code = 1, message = "??????", response = GetUserResponse.class)
            , @ApiResponse(code = 201, message = "????????????????????????", response = ExceptionResponse.class)})
    void childWxpayNotify(
            @ApiParam(value = "appname")@PathVariable("appname")  String appname
    ) throws IOException, ApiException {

        logger.info("--------------------wxpayNotify------appname---"+appname);
        logger.info("????????????");
        logger.info("entry");
        //??????url??????????????????
//        appname = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/")+1);//??????url????????????/?????????

        WeixinNotify wxNotify = new WeixinNotify(request);
//        //????????????????????????????????????

        logger.info("????????????");
        //??????
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

        if (!flagSign) {//????????????
            logger.info("fail");
            response.getWriter().write(setXML("FAILURE", "444444"));
            return;
        }

        try{
            this.setOrderSuccess(wxNotify.getOrderId().intValue(),wxNotify.getPayAccount(),RechargePayStyle.WEIXINP_PAY,wxNotify.getXmlDoc(),wxNotify.getTradeNo(),"??????????????????");
        }catch (ApiNotFoundException e){
            e.printStackTrace();
            logger.info("--------------------wxpayNotify----payRechargeOrder--null---");
            logger.info("???????????????");
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
    @ApiOperation(value = "ios?????????????????????????????????", notes = "ios???????????????/ios????????????????????????")
    @ApiResponses({@ApiResponse(code = 1, message = "??????", response = GetUserResponse.class)
            , @ApiResponse(code = 201, message = "????????????????????????", response = ExceptionResponse.class)})
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
//        //????????????????????????????????????

        logger.info("????????????");
        //??????
        String strA = "bundleid="+bundleid+"&fee="+fee+"&goodsid="+goodsid+"&goodsname="+goodsname+"&orderno="+orderno+"&paytime="+paytime+"&paytype="+paytype+"&result="+result+"&timestamp="+timestamp+"&userid="+userid;
        String strB = strA + "&key=SsHSivBkt0QOWQ4g9CkCdMtoEAmy5ZWi";
        String fhsign = getMD5(strB).toUpperCase();

        logger.info("--------------------zhijianWxpayNotify------sign---"+sign);
        logger.info("--------------------zhijianWxpayNotify------fhsign---"+fhsign);

        if(!fhsign.equals(sign)){
            //????????????
            logger.info("yan qian shi bai");
            response.setReturncode("FAIL");
            response.setReturnmsg("????????????");
            return response;
        }


        //??????????????????
        Long orderId = Long.parseLong(goodsid);

        try{
            String intro="??????";
            if(paytype.equals("0")){
                intro = "????????????????????????";
            }
            if(paytype.equals("6")){
                intro = "??????ios????????????";
            }
            this.setOrderSuccess(orderId.intValue(),bundleid,RechargePayStyle.ZHIJIAN_PAY,result,orderno,intro);
        }catch (ApiNotFoundException e){
            e.printStackTrace();
            logger.info("--------------zhijian-----wxpayNotify----payRechargeOrder--null---");
            logger.info("???????????????");
            response.setReturncode("FAIL");
            response.setReturnmsg("???????????????");
            return response;
        }


        response.setReturncode("SUCCESS");
        response.setReturnmsg("");
        return response;
    }
    /**
     * ????????????????????????
     *
     * @param payRechargeOrder
     * @param rechargePayStyle      1ios2??????3?????????4??????
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
            Integer amount = payRechargeOrder.getAmount();//????????????
//            Integer source_type = source_type;//1ios2??????3?????????
            String ip = (String) request.getRemoteAddr();//?????????????????????ip
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
     * ??????????????????????????????
     *
     * @throws Exception
     */
    @RequestMapping(value = "/mtxAlipayNotify", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "??????????????????????????????", notes = "???????????????")
    @ApiResponses({@ApiResponse(code = 1, message = "??????", response = GetUserResponse.class)
            , @ApiResponse(code = 201, message = "????????????????????????", response = ExceptionResponse.class)})
    void mtxAlipayNotify(
    ) throws Exception {
        logger.info("-------------mtx-------alipayNotify--top-appname-----");

        //??????app???
        AlipayConfig.partner = env.getProperty("partner");
        AlipayConfig.private_key = env.getProperty("private_key");
        AlipayConfig.alipay_public_key = env.getProperty("alipay_public_key");



        logger.info("-------------mtx-------alipayNotify--------");
        //???????????????POST??????????????????
        Map<String, String> params = VersionUtil.getParams(request);

        logger.info("-------------mtx-alipayNotify-----params---" + params);

        String alipaypublicKey = env.getProperty("alipay_public_key");
        String charset = "utf-8";
        boolean signVerfied = AlipaySignature.rsaCheckV1(params, alipaypublicKey, charset, "RSA");

        System.out.println("???????????????????????????" + signVerfied);
        if (signVerfied == false) {

            response.getWriter().print("fail");
            return;
        }

        //????????????????????????????????????????????????????????????????????????????????????1\2\3\4?????????????????????????????????response?????????success?????????????????????failure

        String callbackMsg = transMapToString(params);

        String prefix = env.getProperty("order.prefix");

        String orderIdstr = params.get("out_trade_no");

        long orderId;
        //??????????????????????????? ???????????? ??????  app
        orderId = Long.parseLong(orderIdstr.split("_")[1]);
        if (!orderIdstr.split("_")[0].equals(prefix)) {
            logger.info("--------mtx------------alipayNotify----dingdanbijiaoshibai----");
            response.getWriter().print("fail");
            return;
        }
        logger.info("------------mtx--------alipayNotify----getOrderId----" + orderId);

        PayRechargeOrder payRechargeOrder = payRechargeOrderService.getPayRechargeOrder(orderId);//???????????????????????????
        // ?????? payAccount  buyer_id
        String buyer_id = params.get("buyer_id");
        if (buyer_id == null) {
            buyer_id = "";
        }
        payRechargeOrder.setPayAccount(buyer_id);//?????????????????????


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
                        ,"?????????????????????");
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
     * ??????????????????????????????
     *
     * @throws Exception
     */
    @RequestMapping(value = "/childAlipayNotify/{appname}", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "??????????????????????????????", notes = "???????????????")
    @ApiResponses({@ApiResponse(code = 1, message = "??????", response = GetUserResponse.class)
            , @ApiResponse(code = 201, message = "????????????????????????", response = ExceptionResponse.class)})
    void childAlipayNotify(
            @ApiParam(value = "appname")@PathVariable("appname") String appname
    ) throws Exception {
        logger.info("-------------mtx-------childAlipayNotify--top-appname-----"+appname);
        //??????url??????????????????
//         appname = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/")+1);//??????url????????????/?????????
        if(appname.equals("childstory")){
            logger.info("-------------mtx-------childAlipayNotify---majiabao-----");
            //?????????
            AlipayConfig.partner = env.getProperty("partner");
            AlipayConfig.private_key = env.getProperty("child_private_key");
            AlipayConfig.alipay_public_key = env.getProperty("child_alipay_public_key");
        }else{
            //??????app???
            AlipayConfig.partner = env.getProperty("partner");
            AlipayConfig.private_key = env.getProperty("private_key");
            AlipayConfig.alipay_public_key = env.getProperty("alipay_public_key");
        }


        logger.info("-------------mtx-------childAlipayNotify--------");
        //???????????????POST??????????????????
        Map<String, String> params =VersionUtil.getParams(request);

        //??????alipaypublickey??????????????????????????????open.alipay.com????????????????????????
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        String alipaypublicKey = env.getProperty("alipay_public_key");
        String charset = "utf-8";
        boolean signVerfied = AlipaySignature.rsaCheckV1(params, alipaypublicKey, charset, "RSA");

        System.out.println("???????????????????????????" + signVerfied);
        if (signVerfied == false) {
            logger.info("---------mtx-----------childAlipayNotify----yan qian shi bai----");
            response.getWriter().print("fail");
            return;
        }
        logger.info("------------mtx--------childAlipayNotify----yan qian success----");

        //????????????????????????????????????????????????????????????????????????????????????1\2\3\4?????????????????????????????????response?????????success?????????????????????failure

        String callbackMsg = transMapToString(params);

        String prefix = env.getProperty("order.prefix");
        String orderIdstr = params.get("out_trade_no");
        Long orderId;
        //??????????????????????????? ???????????? ??????  app
        orderId = Long.parseLong(orderIdstr.split("_")[1]);
        if (!orderIdstr.split("_")[0].equals(prefix)) {
            logger.info("--------mtx------------childAlipayNotify----dingdanbijiaoshibai----");
            response.getWriter().print("fail");
            return;
        }
        logger.info("------------mtx--------childAlipayNotify----getOrderId----" + orderId);

        PayRechargeOrder payRechargeOrder = payRechargeOrderService.getPayRechargeOrder(orderId);//???????????????????????????
        // ?????? payAccount  buyer_id
        String buyer_id = params.get("buyer_id");
        if (buyer_id == null) {
            buyer_id = "";
        }
        payRechargeOrder.setPayAccount(buyer_id);//?????????????????????


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
                        ,"?????????????????????");
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
    @ApiOperation(value = "IOS??????????????????", notes = "IOS??????????????????")
    @ApiResponses({@ApiResponse(code = 1, message = "??????", response = GetUserResponse.class)
            , @ApiResponse(code = 201, message = "????????????????????????", response = ExceptionResponse.class)})
    IosPayResponse iospayNotify(
            @ApiParam(value = "apple????????????") @RequestParam String appStoreMsg,
//            @ApiParam(value = "??????????????????") @RequestParam Integer isSandbox,
            @ApiParam(value = "??????token") @RequestParam String token,
//            @ApiParam(value = "??????id") @RequestParam Long priceId,
            @ApiParam(value = "??????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String channel,
            @ApiParam(value = "?????????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String ver

    ) throws ApiException, Exception {
//        String testMsg = "MIISoAYJKoZIhvcNAQcCoIISkTCCEo0CAQExCzAJBgUrDgMCGgUAMIICQQYJKoZIhvcNAQcBoIICMgSCAi4xggIqMAoCARQCAQEEAgwAMAsCARkCAQEEAwIBAzAMAgEDAgEBBAQMAjI3MAwCAQoCAQEEBBYCNCswDAIBDgIBAQQEAgIAjTAMAgETAgEBBAQMAjI3MA0CAQsCAQEEBQIDCB+cMA0CAQ0CAQEEBQIDAYdoMA4CAQECAQEEBgIER0W4rDAOAgEJAgEBBAYCBFAyNDcwDgIBEAIBAQQGAgQw6R/RMBACAQ8CAQEECAIGR91jBBedMBQCAQACAQEEDAwKUHJvZHVjdGlvbjAYAgEEAgECBBB/3u9AmC3JPe3uLQU+MetdMBwCAQUCAQEEFLooZkHYP+N1BZtDHzXLkjy6b4AYMB4CAQgCAQEEFhYUMjAxNy0wNy0wOVQwMzo1MzozMVowHgIBDAIBAQQWFhQyMDE3LTA3LTA5VDAzOjUzOjMxWjAeAgESAgEBBBYWFDIwMTctMDYtMjhUMTM6MTI6MjVaMCACAQICAQEEGAwWY29tLmlmZW5naHVpLnN0b3J5c2hpcDBSAgEGAgEBBEph+vWPGQTwPrzJJUU5R8ECbsTL74/xVzA9ADbGUNxiMZd/teDtFuFt8spc1oc7s29bYK9CMn4josHnkBiMBu+n2ZPaRpJXnXLkDDBTAgEHAgEBBEtT2LYvMaP9n81f6U75l5hiyV1JlpPy/wlYO9pMWXqrrZIRlVyRi05GcvTkI18ILUmRa3tMe3ZfBGoojbJf4fNjPffjHBxPA2XqAB+ggg5lMIIFfDCCBGSgAwIBAgIIDutXh+eeCY0wDQYJKoZIhvcNAQEFBQAwgZYxCzAJBgNVBAYTAlVTMRMwEQYDVQQKDApBcHBsZSBJbmMuMSwwKgYDVQQLDCNBcHBsZSBXb3JsZHdpZGUgRGV2ZWxvcGVyIFJlbGF0aW9uczFEMEIGA1UEAww7QXBwbGUgV29ybGR3aWRlIERldmVsb3BlciBSZWxhdGlvbnMgQ2VydGlmaWNhdGlvbiBBdXRob3JpdHkwHhcNMTUxMTEzMDIxNTA5WhcNMjMwMjA3MjE0ODQ3WjCBiTE3MDUGA1UEAwwuTWFjIEFwcCBTdG9yZSBhbmQgaVR1bmVzIFN0b3JlIFJlY2VpcHQgU2lnbmluZzEsMCoGA1UECwwjQXBwbGUgV29ybGR3aWRlIERldmVsb3BlciBSZWxhdGlvbnMxEzARBgNVBAoMCkFwcGxlIEluYy4xCzAJBgNVBAYTAlVTMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApc+B/SWigVvWh+0j2jMcjuIjwKXEJss9xp/sSg1Vhv+kAteXyjlUbX1/slQYncQsUnGOZHuCzom6SdYI5bSIcc8/W0YuxsQduAOpWKIEPiF41du30I4SjYNMWypoN5PC8r0exNKhDEpYUqsS4+3dH5gVkDUtwswSyo1IgfdYeFRr6IwxNh9KBgxHVPM3kLiykol9X6SFSuHAnOC6pLuCl2P0K5PB/T5vysH1PKmPUhrAJQp2Dt7+mf7/wmv1W16sc1FJCFaJzEOQzI6BAtCgl7ZcsaFpaYeQEGgmJjm4HRBzsApdxXPQ33Y72C3ZiB7j7AfP4o7Q0/omVYHv4gNJIwIDAQABo4IB1zCCAdMwPwYIKwYBBQUHAQEEMzAxMC8GCCsGAQUFBzABhiNodHRwOi8vb2NzcC5hcHBsZS5jb20vb2NzcDAzLXd3ZHIwNDAdBgNVHQ4EFgQUkaSc/MR2t5+givRN9Y82Xe0rBIUwDAYDVR0TAQH/BAIwADAfBgNVHSMEGDAWgBSIJxcJqbYYYIvs67r2R1nFUlSjtzCCAR4GA1UdIASCARUwggERMIIBDQYKKoZIhvdjZAUGATCB/jCBwwYIKwYBBQUHAgIwgbYMgbNSZWxpYW5jZSBvbiB0aGlzIGNlcnRpZmljYXRlIGJ5IGFueSBwYXJ0eSBhc3N1bWVzIGFjY2VwdGFuY2Ugb2YgdGhlIHRoZW4gYXBwbGljYWJsZSBzdGFuZGFyZCB0ZXJtcyBhbmQgY29uZGl0aW9ucyBvZiB1c2UsIGNlcnRpZmljYXRlIHBvbGljeSBhbmQgY2VydGlmaWNhdGlvbiBwcmFjdGljZSBzdGF0ZW1lbnRzLjA2BggrBgEFBQcCARYqaHR0cDovL3d3dy5hcHBsZS5jb20vY2VydGlmaWNhdGVhdXRob3JpdHkvMA4GA1UdDwEB/wQEAwIHgDAQBgoqhkiG92NkBgsBBAIFADANBgkqhkiG9w0BAQUFAAOCAQEADaYb0y4941srB25ClmzT6IxDMIJf4FzRjb69D70a/CWS24yFw4BZ3+Pi1y4FFKwN27a4/vw1LnzLrRdrjn8f5He5sWeVtBNephmGdvhaIJXnY4wPc/zo7cYfrpn4ZUhcoOAoOsAQNy25oAQ5H3O5yAX98t5/GioqbisB/KAgXNnrfSemM/j1mOC+RNuxTGf8bgpPyeIGqNKX86eOa1GiWoR1ZdEWBGLjwV/1CKnPaNmSAMnBjLP4jQBkulhgwHyvj3XKablbKtYdaG6YQvVMpzcZm8w7HHoZQ/Ojbb9IYAYMNpIr7N4YtRHaLSPQjvygaZwXG56AezlHRTBhL8cTqDCCBCIwggMKoAMCAQICCAHevMQ5baAQMA0GCSqGSIb3DQEBBQUAMGIxCzAJBgNVBAYTAlVTMRMwEQYDVQQKEwpBcHBsZSBJbmMuMSYwJAYDVQQLEx1BcHBsZSBDZXJ0aWZpY2F0aW9uIEF1dGhvcml0eTEWMBQGA1UEAxMNQXBwbGUgUm9vdCBDQTAeFw0xMzAyMDcyMTQ4NDdaFw0yMzAyMDcyMTQ4NDdaMIGWMQswCQYDVQQGEwJVUzETMBEGA1UECgwKQXBwbGUgSW5jLjEsMCoGA1UECwwjQXBwbGUgV29ybGR3aWRlIERldmVsb3BlciBSZWxhdGlvbnMxRDBCBgNVBAMMO0FwcGxlIFdvcmxkd2lkZSBEZXZlbG9wZXIgUmVsYXRpb25zIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyjhUpstWqsgkOUjpjO7sX7h/JpG8NFN6znxjgGF3ZF6lByO2Of5QLRVWWHAtfsRuwUqFPi/w3oQaoVfJr3sY/2r6FRJJFQgZrKrbKjLtlmNoUhU9jIrsv2sYleADrAF9lwVnzg6FlTdq7Qm2rmfNUWSfxlzRvFduZzWAdjakh4FuOI/YKxVOeyXYWr9Og8GN0pPVGnG1YJydM05V+RJYDIa4Fg3B5XdFjVBIuist5JSF4ejEncZopbCj/Gd+cLoCWUt3QpE5ufXN4UzvwDtIjKblIV39amq7pxY1YNLmrfNGKcnow4vpecBqYWcVsvD95Wi8Yl9uz5nd7xtj/pJlqwIDAQABo4GmMIGjMB0GA1UdDgQWBBSIJxcJqbYYYIvs67r2R1nFUlSjtzAPBgNVHRMBAf8EBTADAQH/MB8GA1UdIwQYMBaAFCvQaUeUdgn+9GuNLkCm90dNfwheMC4GA1UdHwQnMCUwI6AhoB+GHWh0dHA6Ly9jcmwuYXBwbGUuY29tL3Jvb3QuY3JsMA4GA1UdDwEB/wQEAwIBhjAQBgoqhkiG92NkBgIBBAIFADANBgkqhkiG9w0BAQUFAAOCAQEAT8/vWb4s9bJsL4/uE4cy6AU1qG6LfclpDLnZF7x3LNRn4v2abTpZXN+DAb2yriphcrGvzcNFMI+jgw3OHUe08ZOKo3SbpMOYcoc7Pq9FC5JUuTK7kBhTawpOELbZHVBsIYAKiU5XjGtbPD2m/d73DSMdC0omhz+6kZJMpBkSGW1X9XpYh3toiuSGjErr4kkUqqXdVQCprrtLMK7hoLG8KYDmCXflvjSiAcp/3OIK5ju4u+y6YpXzBWNBgs0POx1MlaTbq/nJlelP5E3nJpmB6bz5tCnSAXpm4S6M9iGKxfh44YGuv9OQnamt86/9OBqWZzAcUaVc7HGKgrRsDwwVHzCCBLswggOjoAMCAQICAQIwDQYJKoZIhvcNAQEFBQAwYjELMAkGA1UEBhMCVVMxEzARBgNVBAoTCkFwcGxlIEluYy4xJjAkBgNVBAsTHUFwcGxlIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MRYwFAYDVQQDEw1BcHBsZSBSb290IENBMB4XDTA2MDQyNTIxNDAzNloXDTM1MDIwOTIxNDAzNlowYjELMAkGA1UEBhMCVVMxEzARBgNVBAoTCkFwcGxlIEluYy4xJjAkBgNVBAsTHUFwcGxlIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MRYwFAYDVQQDEw1BcHBsZSBSb290IENBMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5JGpCR+R2x5HUOsF7V55hC3rNqJXTFXsixmJ3vlLbPUHqyIwAugYPvhQCdN/QaiY+dHKZpwkaxHQo7vkGyrDH5WeegykR4tb1BY3M8vED03OFGnRyRly9V0O1X9fm/IlA7pVj01dDfFkNSMVSxVZHbOU9/acns9QusFYUGePCLQg98usLCBvcLY/ATCMt0PPD5098ytJKBrI/s61uQ7ZXhzWyz21Oq30Dw4AkguxIRYudNU8DdtiFqujcZJHU1XBry9Bs/j743DN5qNMRX4fTGtQlkGJxHRiCxCDQYczioGxMFjsWgQyjGizjx3eZXP/Z15lvEnYdp8zFGWhd5TJLQIDAQABo4IBejCCAXYwDgYDVR0PAQH/BAQDAgEGMA8GA1UdEwEB/wQFMAMBAf8wHQYDVR0OBBYEFCvQaUeUdgn+9GuNLkCm90dNfwheMB8GA1UdIwQYMBaAFCvQaUeUdgn+9GuNLkCm90dNfwheMIIBEQYDVR0gBIIBCDCCAQQwggEABgkqhkiG92NkBQEwgfIwKgYIKwYBBQUHAgEWHmh0dHBzOi8vd3d3LmFwcGxlLmNvbS9hcHBsZWNhLzCBwwYIKwYBBQUHAgIwgbYagbNSZWxpYW5jZSBvbiB0aGlzIGNlcnRpZmljYXRlIGJ5IGFueSBwYXJ0eSBhc3N1bWVzIGFjY2VwdGFuY2Ugb2YgdGhlIHRoZW4gYXBwbGljYWJsZSBzdGFuZGFyZCB0ZXJtcyBhbmQgY29uZGl0aW9ucyBvZiB1c2UsIGNlcnRpZmljYXRlIHBvbGljeSBhbmQgY2VydGlmaWNhdGlvbiBwcmFjdGljZSBzdGF0ZW1lbnRzLjANBgkqhkiG9w0BAQUFAAOCAQEAXDaZTC14t+2Mm9zzd5vydtJ3ME/BH4WDhRuZPUc38qmbQI4s1LGQEti+9HOb7tJkD8t5TzTYoj75eP9ryAfsfTmDi1Mg0zjEsb+aTwpr/yv8WacFCXwXQFYRHnTTt4sjO0ej1W8k4uvRt3DfD0XhJ8rxbXjt57UXF6jcfiI1yiXV2Q/Wa9SiJCMR96Gsj3OBYMYbWwkvkrL4REjwYDieFfU9JmcgijNq9w2Cz97roy/5U2pbZMBjM3f3OgcsVuvaDyEO2rpzGU+12TZ/wYdV2aeZuTJC+9jVcZ5+oVK3G72TQiQSKscPHbZNnF5jyEuAF1CqitXa5PzQCQc3sHV1ITGCAcswggHHAgEBMIGjMIGWMQswCQYDVQQGEwJVUzETMBEGA1UECgwKQXBwbGUgSW5jLjEsMCoGA1UECwwjQXBwbGUgV29ybGR3aWRlIERldmVsb3BlciBSZWxhdGlvbnMxRDBCBgNVBAMMO0FwcGxlIFdvcmxkd2lkZSBEZXZlbG9wZXIgUmVsYXRpb25zIENlcnRpZmljYXRpb24gQXV0aG9yaXR5AggO61eH554JjTAJBgUrDgMCGgUAMA0GCSqGSIb3DQEBAQUABIIBAGafjimqZXiWkQm7yX3/AvzflKNzrrOoQfl9NMIOmi/KGDSyH8NW5yQPamEP1MiCK0sdDqyDtRVMKPV/M4Oci/zAwyAFmPcmQNsAedg3AZmgooyUNj9IEZijd1+8epQaRn42CKg3BqJgF9Ju0eU6z4L5+H1YMpqyWHckRpsCqITURwGI/OdI/vbhJ2XA/uYjqHxWQgE1Z1I3uF6LtXgq1SQQ0yEQYl28GwLHqJJIrHwIjQQxR0gYJO11fO1cnyti1BbTITOWXwbddtdQtEu8TDuhFx0PRk6A5eeckB1PUTt/VFDngfZtlzoYKJATOgEL5822ghcjm3aEdPtifd86bXs=";
        Long userId = userService.checkAndGetCurrentUserId(token);
        User user = userService.getUser(userId);
        IosPayResponse response = new IosPayResponse();
        response.setHasSendCoupon(0);
        String callbackMsg="appstoryMsg:"+appStoreMsg+";token:"+token;
        //??????????????????
        PayCallbackRecord callbackRecord=this.createCallback(callbackMsg, RechargePayStyle.IOS_PAY.getId(),null);

        IosNotify iosNotify=IosNotifyHttpUtil.getNotify(appStoreMsg);

        if(iosNotify.getStatus()!=0){
            response.getStatus().setCode(2);
            response.getStatus().setMsg("????????????,iosNotify?????????"+iosNotify.getStatus());
            return response;
        }

        if(iosNotify.getReceipt().getIn_app().size()==0){
            response.getStatus().setCode(2);
            response.getStatus().setMsg("????????????,inapp=0"+iosNotify.getStatus());
            return response;
        }

        for(InApp inApp:iosNotify.getReceipt().getIn_app()){
            PayRechargeOrder order = payService.getPayRechargeOrderByTradeNo(inApp.getTransaction_id());
            if (order != null) {
//            ???????????????
                logger.info("????????????????????? transactionId:"+inApp.getTransaction_id());
                response.getErrorTransactionIds().add(inApp.getTransaction_id());
                continue;
            }
            PayRechargePrice payRechargePrice = payService.getPayRechargePriceByIap(inApp.getProduct_id());
            if (payRechargePrice==null) {
                logger.info("?????????????????? transactionId:"+inApp.getTransaction_id());
                response.getErrorTransactionIds().add(inApp.getTransaction_id());
                continue;
            }

            /**
             * ?????????????????????????????????????????????
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

            //????????????
            order=this.createRechargeOrder(RechargePayStyle.IOS_PAY,payRechargePrice.getPrice(),user,channel,inApp.getTransaction_id(),iosNotify.getIsSandBox(),new Date());

            this.updateCallback(callbackRecord,order);
            /**
             * ????????????vip???????????????
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
        response.getStatus().setMsg("????????????");


        logger.info("--------iospay------------success---------");
        return response;
    }

    @RequestMapping(value = "/iospayNotifyOverTime", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "?????????????????????????????????????????????", notes = "IOS??????????????????")
    @ApiResponses({@ApiResponse(code = 1, message = "??????", response = GetUserResponse.class)
            , @ApiResponse(code = 201, message = "????????????????????????", response = ExceptionResponse.class)})
    IosPayResponse iospayNotifyOverTime(
            @ApiParam(value = "apple????????????") @RequestParam String appStoreMsg,
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "??????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String channel,
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
        //??????????????????
        PayCallbackRecord callbackRecord=this.createCallback(callbackMsg, RechargePayStyle.IOS_PAY.getId(),null);

        IosNotify iosNotify=IosNotifyHttpUtil.getNotify(appStoreMsg);

        if(iosNotify.getStatus()!=0){
            response.getStatus().setCode(2);
            response.getStatus().setMsg("????????????,iosNotify?????????"+iosNotify.getStatus());
            return response;
        }

        if(user==null){
            response.getStatus().setCode(2);
            response.getStatus().setMsg("????????????,???????????????");
            return response;
        }

        if(iosNotify.getReceipt().getIn_app().size()==0){
            response.getStatus().setCode(2);
            response.getStatus().setMsg("????????????,inapp=0"+iosNotify.getStatus());
            return response;
        }

        for(InApp inApp:iosNotify.getReceipt().getIn_app()){
            PayRechargeOrder order = payService.getPayRechargeOrderByTradeNo(inApp.getTransaction_id());
            if (order != null) {
//            ???????????????
                logger.info("????????????????????? transactionId:"+inApp.getTransaction_id());
                response.getErrorTransactionIds().add(inApp.getTransaction_id());
                continue;
            }
            PayRechargePrice payRechargePrice = payService.getPayRechargePriceByIap(inApp.getProduct_id());
            if (payRechargePrice==null) {
                logger.info("?????????????????? transactionId:"+inApp.getTransaction_id());
                response.getErrorTransactionIds().add(inApp.getTransaction_id());
                continue;
            }
            response.getSuccessTransactionIds().add(inApp.getTransaction_id());


            //????????????
            order=this.createRechargeOrder(RechargePayStyle.IOS_PAY,payRechargePrice.getPrice(),user,channel,inApp.getTransaction_id(),iosNotify.getIsSandBox(),createDate);

            this.updateCallback(callbackRecord,order);
        }
        response.getStatus().setCode(1);
        response.getStatus().setMsg("????????????");

        logger.info("--------iospay------------success---------");
        return response;
    }

    /**
     * ??????????????????????????????,???????????????service
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
     * ??????callbackorder????????????????????????service
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
     * ?????????????????????recharge?????????????????????
     */
    private PayRechargeOrder createRechargeOrder(RechargePayStyle payStyle, int amount, User user, String channel, String tradeNo, int isTest, Date createTime){
//        Integer buyType = 3;//ios??????
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
        payRechargeOrder.setPayStyle(payStyle);//3ios??????
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
        //??????????????? orderId message time payType

        //??????????????????
        Integer type = 1;//1??????2??????
//                walletService.editWalletBalance(userId, payRechargeOrder.getAmount(), type);
        logger.info("--------iospay------------userId---------" + user.getId());
        logger.info("--------iospay-------------amount--------" + payRechargeOrder.getAmount());
        logger.info("--------iospay------------type---------" + type);
        logger.info("--------iospay---------walletService------------" + walletService);
//        walletService.editWalletBalance(user.getId(), payRechargeOrder.getAmount(), type);
//        //??????????????????????????????
//        userAccountRecordService.addUserAcountRecord(user.getId(), amount, AddStyle.UP, RechargeStyle.RECHARGE, "ios??????");//1??????2??????

        walletService.addAmountToWallet(payRechargeOrder.getUser().getId().intValue(),payRechargeOrder.getWalletStyle(),RechargeStyle.RECHARGE,payRechargeOrder.getAmount(),"recharge_"+tradeNo,"ios??????");
        this.fenxiaoCheckOrder(payRechargeOrder, RechargePayStyle.IOS_PAY);

        return payRechargeOrder1;
    }





    @RequestMapping(value = "/iospayNotifyRestore", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "ios????????????????????????", notes = "ios????????????????????????,?????????inapp?????????1?????????")
    IosPayResponse iospayNotifyRestore(
//            @ApiParam(value = "postId") @RequestParam Integer postId,
//            @ApiParam(value = "??????????????????") @RequestParam Integer isSandbox,
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
            String transId=inapp.getTransaction_id();//????????????

            //??????????????????
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
     * ?????????form
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
     * ?????????form
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
     * ?????????form
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
//        String name = "??????";
//        userId = 18l;
//        modelMap.put("name",name);
//        CmsLink cmsLink= CustomMethodUtil.getAllCustomMethod(request,classType,r);

//        modelMap.put("cmsLink",cmsLink);
//        modelMap.put("id",id);


        return "couponRule/couponRule";

    }


    private String body;

    /**
     * ????????????????????????
     *
     * @throws Exception
     */
    @RequestMapping(value = "/huaweiPayNotify", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "??????????????????", notes = "")
    @ApiResponses({@ApiResponse(code = 1, message = "??????", response = GetUserResponse.class)
            , @ApiResponse(code = 201, message = "????????????????????????", response = ExceptionResponse.class)})
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
            //????????????????????????
            orderId = Long.parseLong(orderIdstr.split("_")[1]);
            if (!orderIdstr.split("_")[0].equals(prefix)) {
                logger.info("--------------------huaweiPayNotify----dingdanbijiaoshibai----");
                result.setResult(98);//98: ????????????,
                this.huaweiPayResult(result);
                return;
            }
            logger.info("--------------------huaweiPayNotify----getOrderId----" + orderId);

            PayRechargeOrder payRechargeOrder = payRechargeOrderService.getPayRechargeOrder(orderId);//???????????????????????????
            // ?????? payAccount  buyer_id
//            String buyer_id = (String) map.get("buyer_id");
//            if (buyer_id == null) {
            String buyer_id = "";
//            }
            payRechargeOrder.setPayAccount(buyer_id);//?????????????????????
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
                            ,"?????????????????????");
                    result.setResult(0);
                    this.huaweiPayResult(result);
                }catch (ApiNotFoundException e){
                    e.printStackTrace();
                    result.setResult(3);

                }


            } else {
                //????????????
                result.setResult(3);
            }
        } else {
            //????????????
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
