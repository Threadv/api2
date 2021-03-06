package com.ifenghui.storybookapi.app.transaction.controller;

/**
 * Created by jia on 2016/12/28.
 */


import com.ifenghui.storybookapi.api.response.exception.ExceptionResponse;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.transaction.entity.OrderMix;
import com.ifenghui.storybookapi.app.transaction.entity.PayAfterOrder;
import com.ifenghui.storybookapi.app.transaction.entity.PayStoryOrder;
import com.ifenghui.storybookapi.app.transaction.entity.VPayOrder;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.AbilityPlanOrder;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponDeferredSubscription;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponSerialStoryOrder;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponStoryOrder;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponSubscription;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.CouponLessonOrder;
import com.ifenghui.storybookapi.app.transaction.entity.serial.PaySerialStoryOrder;
import com.ifenghui.storybookapi.app.transaction.response.*;
import com.ifenghui.storybookapi.app.transaction.service.*;
import com.ifenghui.storybookapi.app.transaction.service.lesson.CouponLessonOrderService;
import com.ifenghui.storybookapi.app.transaction.service.lesson.PayLessonPriceService;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderMixService;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderService;
import com.ifenghui.storybookapi.app.transaction.service.order.PayActivityOrderService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.controller.PayController;
import com.ifenghui.storybookapi.app.wallet.entity.PayRechargeOrder;
import com.ifenghui.storybookapi.app.wallet.service.PayRechargeOrderService;
import com.ifenghui.storybookapi.app.wallet.service.PayService;
import com.ifenghui.storybookapi.app.wallet.service.XiaochengxuService;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.style.OrderStyle;
import com.ifenghui.storybookapi.style.RechargeStyle;
import com.ifenghui.storybookapi.style.WalletStyle;
import com.ifenghui.storybookapi.util.ListUtil;
import io.swagger.annotations.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Controller
@EnableAutoConfiguration
@RequestMapping("/api/order")
@Api(value="??????",description = "??????????????????")
public class OrderController {

    @Autowired
    HttpServletRequest request;

    @Autowired
    XiaochengxuService xiaochengxuService;

    @Autowired
    PayActivityOrderService payActivityOrderService;

    @Autowired
    PayController payController;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    PayService payService;
    @Autowired
    PayAfterOrderService payAfterOrderService;

    @Autowired
    CouponDeferredSubscriptionService couponDeferredSubscriptionService;
    @Autowired
    CouponSubscriptionService couponSubscriptionService;

    @Autowired
    StoryService storyService;

    @Autowired
    PayRechargeOrderService payRechargeOrderService;

    @Autowired
    BuyStoryService buyStoryService;

    @Autowired
    BuySerialService buySerialService;

    @Autowired
    StoryBuyController storyBuyController;

    @Autowired
    ActivityBuyController activityBuyController;

    @Autowired
    private Environment env;
    private static Logger logger = Logger.getLogger(PayController.class);

    @Autowired
    SerialBuyController serialBuyController;

    @Autowired
    LessonBuyController lessonBuyController;

    @Autowired
    CouponLessonOrderService couponLessonOrderService;

    @Autowired
    CouponService couponService;

    @Autowired
    CouponSerialStoryOrderService couponSerialStoryOrderService;

    @Autowired
    CouponStoryOrderService couponStoryOrderService;

    @Autowired
    PayLessonPriceService payLessonPriceService;

    @Autowired
    OrderMixService orderMixService;

    @Autowired
    VipBuyController vipBuyController;
    @Autowired
    AbilityPlanBuyController abilityPlanBuyController;

    @Autowired
    AbilityPlanOrderService planOrderService;




    @RequestMapping(value="/cancelUserPayOrder",method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code=212,message="???????????????????????????",response = ExceptionResponse.class)
    })
    @ApiOperation(value = "????????????", notes = "")
    public CancelUserPayOrderResponse cancelUserPayOrder(
            @ApiParam(value = "??????token")@RequestParam String  token,
            @ApiParam(value = "??????id")@RequestParam Long orderId,
            @ApiParam(value = "?????????1??????2??????3?????????4?????????")@RequestParam Integer type
    )throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);

        CancelUserPayOrderResponse cancelUserPayOrderResponse =new CancelUserPayOrderResponse();

        RechargeStyle rechargeStyle = RechargeStyle.getById(type);

//        orderService.cancelUserPayOrder(userId,orderId,rechargeStyle);
        orderMixService.cancelOrder(userId.intValue(),orderId.intValue(),rechargeStyle);
        cancelUserPayOrderResponse.getStatus().setMsg("??????????????????");
        return cancelUserPayOrderResponse;
    }

    @RequestMapping(value="/getBuyStorysOrder",method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code = 209, message = "??????????????????", response = ExceptionResponse.class),
            @ApiResponse(code = 210, message = "??????????????????", response = ExceptionResponse.class)
    })
    @ApiOperation(value = "???????????????/????????????????????????????????????", notes = "")
    public GetBuyStorysOrderResponse getBuyStorysOrder(
            @ApiParam(value = "??????token")@RequestParam String  token,
            @ApiParam(value = "??????id?????????id???????????????")@RequestParam String storyIdsStr,
            @ApiParam(value = "?????????id?????????id???????????????")@RequestParam(required = false, defaultValue = "") List<Integer> couponIdsStr
    )throws ApiException {
        Long userId;
        couponIdsStr=ListUtil.removeNull(couponIdsStr);
        userId = userService.checkAndGetCurrentUserId(token);
        PayStoryOrder payStoryOrder = buyStoryService.createBuyStorysOrder(userId,storyIdsStr, couponIdsStr);
        GetBuyStorysOrderResponse getBuyStorysOrderResponse = new GetBuyStorysOrderResponse();
        getBuyStorysOrderResponse.setPayStoryOrder(payStoryOrder);
        return getBuyStorysOrderResponse;
    }

    @RequestMapping(value="/delUserPayOrder",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "????????????", notes = "")
    @ApiResponses({@ApiResponse(code=206,message="???????????????",response = ExceptionResponse.class)})
    public DelUserPayOrderResponse delUserPayOrder(
            @ApiParam(value = "??????token")@RequestParam String  token,
            @ApiParam(value = "??????id")@RequestParam Long orderId,
            @ApiParam(value = "???????????????1????????????2?????????")@RequestParam Integer type
    )throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
        /**
         * ????????????
         */
//        orderService.delUserPayOrder(userId,orderId,type);
        orderMixService.deleteOrder(userId.intValue(), orderId.intValue(), RechargeStyle.getById(type));
        DelUserPayOrderResponse delUserPayOrderResponse =new DelUserPayOrderResponse();
        delUserPayOrderResponse.getStatus().setMsg("??????????????????");
        return delUserPayOrderResponse;
    }

    @RequestMapping(value = "/getBuySerialStoryOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({@ApiResponse(code = 209, message = "??????????????????", response = ExceptionResponse.class),
            @ApiResponse(code = 210, message = "??????????????????", response = ExceptionResponse.class)
    })
    @ApiOperation(value = "??????????????????????????????", notes = "")
    public GetBuySerialStoryOrderResponse getBuySerialStoryOrder(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "?????????id") @RequestParam Long serialStoryId,
            @ApiParam(value = "?????????id?????????id???????????????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") List<Integer> couponIdsStr
    ) throws ApiException {

        couponIdsStr= ListUtil.removeNull(couponIdsStr);

        Long userId = userService.checkAndGetCurrentUserId(token);
        GetBuySerialStoryOrderResponse response = new GetBuySerialStoryOrderResponse();
        PaySerialStoryOrder res = buySerialService.getBuySerialStoryOrder(serialStoryId, userId, couponIdsStr);//
        response.setPaySerialStoryOrder(res);
        return response;
    }
    /**
     * ???????????????????????????????????????
     * @param token
     * @param status
     * @param pageNo
     * @param pageSize
     * @return
     * @throws ApiException
     */
    @RequestMapping(value="/getUserPayOrder",method = RequestMethod.GET)
    @ResponseBody
//    @ApiResponses({
//            @ApiResponse(code=212,message="???????????????????????????",response = ExceptionResponse.class)
//    })
    @ApiOperation(value = "?????????????????????", notes = "??????+??????")
    @Deprecated
    public GetUserPayOrderResponse getUserPayOrder(
            @ApiParam(value = "??????token")@RequestParam String  token,
            @ApiParam(value = "??????",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") Integer  status,
            @ApiParam(value = "??????")@RequestParam Integer pageNo,
            @ApiParam(value = "??????")@RequestParam Integer pageSize
//            @ApiParam(value = "????????????????????????id")@RequestParam Long orderId
    )throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
//        pageNo = pageNo - 1;
        GetUserPayOrderResponse getUserPayOrderResponse =new GetUserPayOrderResponse();

        Page<VPayOrder> userPayOrderPage = orderService.getUserPayOrder(userId,status,pageNo,pageSize);//
        getUserPayOrderResponse.setPayOrders(userPayOrderPage.getContent());
        getUserPayOrderResponse.setJpaPage(userPayOrderPage);
        return getUserPayOrderResponse;
    }

    @RequestMapping(value="/getUserPayOrderV2",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "?????????????????????V2-?????????view-2.1????????????", notes = "??????+??????")
    public GetUserPayOrderV2Response getUserPayOrderV2(
            @ApiParam(value = "??????token")@RequestParam String  token,
            @ApiParam(value = "??????",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") Integer  status,
            @ApiParam(value = "??????")@RequestParam Integer pageNo,
            @ApiParam(value = "??????")@RequestParam Integer pageSize
//            @ApiParam(value = "????????????????????????id")@RequestParam Long orderId
    )throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
//        pageNo = pageNo - 1;
        GetUserPayOrderV2Response getUserPayOrderResponse =new GetUserPayOrderV2Response();

        if(userId!=null && userId==6917){
            //ios?????????????????????????????????
            getUserPayOrderResponse.setPayOrders(new ArrayList<>());
            return getUserPayOrderResponse;
        }
        Page<OrderMix> userPayOrderPage = orderMixService.getListByStatus(userId.intValue(),status,pageNo,pageSize);

        getUserPayOrderResponse.setPayOrders(userPayOrderPage.getContent());
        getUserPayOrderResponse.setJpaPage(userPayOrderPage);
        return getUserPayOrderResponse;
    }

    @RequestMapping(value="/getUserPayOrderDetail",method = RequestMethod.GET)
    @ResponseBody
//    @ApiResponses({
//            @ApiResponse(code=212,message="???????????????????????????",response = ExceptionResponse.class)
//    })
    @ApiOperation(value = "????????????????????????", notes = "??????+??????")
    public GetUserPayOrderDetailResponse getUserPayOrderDetail(
            @ApiParam(value = "??????token")@RequestParam String  token,
            @ApiParam(value = "??????id")@RequestParam Long orderId,
            @ApiParam(value = "???????????????1??????,2??????,3?????????,4?????????")@RequestParam Integer type
    )throws ApiException {
        Long userId = userService.checkAndGetCurrentUserId(token);
        GetUserPayOrderDetailResponse getUserPayOrderDetailResponse =new GetUserPayOrderDetailResponse();

        VPayOrder userPayOrder = orderService.getUserPayOrderDetail(orderId,type);//

//        List<CouponAllInOne> vCouponMices=new ArrayList();

        if( userPayOrder.getType().equals(VPayOrder.type_story) ){
            List<CouponStoryOrder> couponStoryOrderList = couponStoryOrderService.getCouponStoryOrderListByOrderId(orderId.intValue());
            if(couponStoryOrderList != null && couponStoryOrderList.size() > 0){
                for(CouponStoryOrder item : couponStoryOrderList){
                    userPayOrder.getCoupons().add(couponService.getCouponUserById(item.getCouponId().longValue()).getCoupon());
                }
            }

        } else if ( userPayOrder.getType().equals(VPayOrder.type_subscription) ){
            //????????????????????????
            CouponSubscription couponSubscription = new CouponSubscription();
            couponSubscription.setOrderId(orderId);
            Page<CouponSubscription> couponSubscriptionPage=
                    couponSubscriptionService.getCouponSubscriptions(couponSubscription,new PageRequest(0,20));
            for(CouponSubscription couponSubscription1:couponSubscriptionPage.getContent()){
                userPayOrder.getCoupons().add(couponSubscription1.getCouponUser().getCoupon());
//                vCouponMices.add(new CouponAllInOne(couponSubscription1.getCouponUser().getCoupon()));
            }
            //?????????????????????
            CouponDeferredSubscription couponDeferredSubscription = new CouponDeferredSubscription();
            couponDeferredSubscription.setOrderId(orderId);
            Page<CouponDeferredSubscription> couponDeferredSubscriptionPage =
                    couponDeferredSubscriptionService.getCouponDeferredSubscriptions(couponDeferredSubscription, new PageRequest(0, 20));
            for (CouponDeferredSubscription couponSubscription1 : couponDeferredSubscriptionPage.getContent()) {
                userPayOrder.setCouponDeferred(couponSubscription1.getCouponDeferredUser().getCouponDeferred());
//                    vCouponMices.add(
//                            new CouponAllInOne(couponSubscription1.getCouponDeferredUser().getCouponDeferred()));
            }
        } else if (userPayOrder.getType() == VPayOrder.type_serial_story){
            List<CouponSerialStoryOrder> couponSerialStoryOrderList = couponSerialStoryOrderService.getCouponSerialStoryOrderListByOrderId(orderId);
            if(couponSerialStoryOrderList != null && couponSerialStoryOrderList.size() > 0){
                for(CouponSerialStoryOrder item : couponSerialStoryOrderList){
                    userPayOrder.getCoupons().add(item.getCouponUser().getCoupon());
                }
            }
        } else if (userPayOrder.getType() == VPayOrder.type_lesson){
            List<CouponLessonOrder> couponLessonOrderList = couponLessonOrderService.getCouponLessonOrderListByOrderId(userId.intValue(), orderId.intValue());
            if(couponLessonOrderList != null && couponLessonOrderList.size() > 0){
                for(CouponLessonOrder item : couponLessonOrderList){
                    userPayOrder.getCoupons().add(couponService.getCouponUserById(item.getCouponId().longValue()).getCoupon());
                }
            }
        }
//        userPayOrde(vCouponMices);
        //??????????????????
        PayAfterOrder payAfterOrder= payAfterOrderService.getPayAfterOrderByTypeAndPayOrderId(type,orderId);
        if(payAfterOrder!=null){
//            PayRechargeOrder payRechargeOrder = payRechargeOrderService.getPayRechargeOrder(payAfterOrder.getOrderId());
            userPayOrder.setRechargeOrder(payAfterOrder.getRechargeOrder());
            userPayOrder.setSuccessTime(payAfterOrder.getRechargeOrder().getSuccessTime());
        } else {
            userPayOrder.setSuccessTime(userPayOrder.getCreateTime());
        }
        getUserPayOrderDetailResponse.setPayOrder(userPayOrder);

        return getUserPayOrderDetailResponse;
    }

    @RequestMapping(value="/getUserPayOrderDetailV2",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "????????????????????????", notes = "??????+??????")
    public GetUserPayOrderDetailV2Response getUserPayOrderDetailV2(
            @ApiParam(value = "??????token")@RequestParam String  token,
            @ApiParam(value = "??????id")@RequestParam Long orderId,
            @ApiParam(value = "???????????????1??????,2??????,3?????????,4??????,5?????? 7vip?????? 9 ?????????????????????")@RequestParam Integer type
    ) throws ApiException {
        Long userId = userService.checkAndGetCurrentUserId(token);
        GetUserPayOrderDetailV2Response response =  new GetUserPayOrderDetailV2Response();
        OrderMix userPayOrderMix = orderMixService.getUserOrderMixDetail(OrderStyle.getById(type), orderId.intValue());

        if( userPayOrderMix.getOrderType().equals(OrderStyle.STORY_ORDER.getId()) ){
            List<CouponStoryOrder> couponStoryOrderList = couponStoryOrderService.getCouponStoryOrderListByOrderId(orderId.intValue());
            if(couponStoryOrderList != null && couponStoryOrderList.size() > 0){
                for(CouponStoryOrder item : couponStoryOrderList){
                    userPayOrderMix.getCoupons().add(couponService.getCouponUserById(item.getCouponId().longValue()).getCoupon());
                }
            }

        } else if ( userPayOrderMix.getOrderType().equals(OrderStyle.SUBSCRIPTION_ORDER.getId()) ){
            //????????????????????????
            CouponSubscription couponSubscription = new CouponSubscription();
            couponSubscription.setOrderId(orderId);
            Page<CouponSubscription> couponSubscriptionPage=
                    couponSubscriptionService.getCouponSubscriptions(couponSubscription,new PageRequest(0,20));
            for(CouponSubscription couponSubscription1:couponSubscriptionPage.getContent()){
                userPayOrderMix.getCoupons().add(couponSubscription1.getCouponUser().getCoupon());
//                vCouponMices.add(new CouponAllInOne(couponSubscription1.getCouponUser().getCoupon()));
            }
            //?????????????????????
            CouponDeferredSubscription couponDeferredSubscription = new CouponDeferredSubscription();
            couponDeferredSubscription.setOrderId(orderId);
            Page<CouponDeferredSubscription> couponDeferredSubscriptionPage =
                    couponDeferredSubscriptionService.getCouponDeferredSubscriptions(couponDeferredSubscription, new PageRequest(0, 20));
            for (CouponDeferredSubscription couponSubscription1 : couponDeferredSubscriptionPage.getContent()) {
                userPayOrderMix.setCouponDeferred(couponSubscription1.getCouponDeferredUser().getCouponDeferred());
//                    vCouponMices.add(
//                            new CouponAllInOne(couponSubscription1.getCouponDeferredUser().getCouponDeferred()));
            }
        } else if (userPayOrderMix.getOrderType().equals(OrderStyle.SERIAL_ORDER.getId())){
            List<CouponSerialStoryOrder> couponSerialStoryOrderList = couponSerialStoryOrderService.getCouponSerialStoryOrderListByOrderId(orderId);
            if(couponSerialStoryOrderList != null && couponSerialStoryOrderList.size() > 0){
                for(CouponSerialStoryOrder item : couponSerialStoryOrderList){
                    userPayOrderMix.getCoupons().add(item.getCouponUser().getCoupon());
                }
            }
        } else if (userPayOrderMix.getOrderType().equals(OrderStyle.LESSON_ORDER.getId())){
            List<CouponLessonOrder> couponLessonOrderList = couponLessonOrderService.getCouponLessonOrderListByOrderId(userId.intValue(), orderId.intValue());
            if(couponLessonOrderList != null && couponLessonOrderList.size() > 0){
                for(CouponLessonOrder item : couponLessonOrderList){
                    userPayOrderMix.getCoupons().add(couponService.getCouponUserById(item.getCouponId().longValue()).getCoupon());
                }
            }
        } else if(userPayOrderMix.getOrderType().equals(OrderStyle.ACTIVITY_GOODS_ORDER.getId())){
            payActivityOrderService.setDataToOrderMix(userPayOrderMix);
        }
        //??????????????????
        PayAfterOrder payAfterOrder= payAfterOrderService.getPayAfterOrderByTypeAndPayOrderId(type,orderId);
        if(payAfterOrder!=null){
            userPayOrderMix.setRechargeOrder(payAfterOrder.getRechargeOrder());
            userPayOrderMix.setSuccessTime(payAfterOrder.getRechargeOrder().getSuccessTime());
        } else {
            userPayOrderMix.setSuccessTime(userPayOrderMix.getCreateTime());
        }

        //??????????????????
        AbilityPlanOrder planOrder =  planOrderService.getAbilityPlanOrder(userPayOrderMix.getOrderId());
        Random r = new Random();
        int tt = r.nextInt(1000000)+1000;
        if(userPayOrderMix.getAbilityPlanPrice() != null){
            if (planOrder.getOnlineOnly() == 1){
                userPayOrderMix.getAbilityPlanPrice().setImgPath("http://storybook.oss-cn-hangzhou.aliyuncs.com/abilityplan/pay_ability_plan-online_card.png?tt="+tt);
            }else {
                userPayOrderMix.getAbilityPlanPrice().setImgPath("http://storybook.oss-cn-hangzhou.aliyuncs.com/abilityplan/pay_ability_plan_card.png?tt="+tt);
            }
        }

        response.setOrderMix(userPayOrderMix);

        return response;
    }


//    @RequestMapping(value="/recover_buy_story_record", method = RequestMethod.GET)
//    @ResponseBody
//    @ApiOperation(value = "????????????????????????")
//    GetUserPayOrderResponse

    @RequestMapping(value = "/balance_buy_all_order", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "????????????????????????", notes = "")
    public BuyOrderByBalanceResponse buyOrderByBalance(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "?????????") @RequestParam Long orderId,
            @ApiParam(value = "????????????")@RequestParam OrderStyle orderStyle,
            @ApiParam(value = "????????????") @RequestParam WalletStyle walletStyle
    ){
        if (orderStyle.equals(OrderStyle.STORY_ORDER)){
            return storyBuyController.buyStorysByBalance(token,orderId,walletStyle);
        } else if(orderStyle.equals(OrderStyle.SERIAL_ORDER)) {
            return serialBuyController.buySerialStoryByBalance(token,orderId,walletStyle);
        } else if(orderStyle.equals(OrderStyle.LESSON_ORDER)){
            return lessonBuyController.buyLessonByBalance(token, orderId.intValue(), walletStyle);
        } else if(orderStyle.equals(OrderStyle.ACTIVITY_GOODS_ORDER)) {
            return activityBuyController.buyStorysByBalance(token, orderId, walletStyle);
        } else if(orderStyle.equals(OrderStyle.VIP_ORDER)){
            return vipBuyController.buyVipByBalance(token, orderId.intValue(), walletStyle);
        } else if(orderStyle.equals(OrderStyle.ABILITY_PLAN_ORDER)){
            return abilityPlanBuyController.buyAbilityPlanByBalance(token, orderId.intValue(), walletStyle);
        } else {
            return new BuyOrderByBalanceResponse();
        }
    }

    @RequestMapping(value = "/check_create_order", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "??????????????????????????????", notes = "??????????????????????????????")
    public BaseResponse checkIsCanCreateOrder(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "????????????")@RequestParam OrderStyle orderStyle,
            @ApiParam(value = "??????id/?????????id") @RequestParam Integer targetValue
    ){
        BaseResponse response = new BaseResponse();
        Long userId = userService.checkAndGetCurrentUserId(token);
        orderService.checkIsCanCreateOrder(userId.intValue(),targetValue,orderStyle);
        return response;
    }

    @RequestMapping(value = "/create_small_program_order", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "???????????????JS-SDK??????", notes = "???????????????????????????")
    public WxPayObjectResponse createSmallProgramOrder(
        @ApiParam(value = "??????token") @RequestParam String token,
        @ApiParam(value = "????????????????????????") @RequestParam Long payOrderId,
        @ApiParam(value = "?????????0?????????1?????????2?????????3??????????????? 4?????????") @RequestParam Integer type,
        @ApiParam(value = "???????????????0??????????????????id??????????????????0???", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") Long priceId,
        @ApiParam(value = "??????", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String channel,
        @ApiParam(value = "openId") @RequestParam String openId
    ){
        BuyStorysPayApiResponse buyStorysPayApiResponse = payController.getNewRechargeOrder(token, payOrderId, type, priceId, channel);
        PayRechargeOrder payRechargeOrder = buyStorysPayApiResponse.getPayRechargeOrder();
        payRechargeOrder.setWalletStyle(WalletStyle.SMALL_PROGRAM_WALLET);
        payRechargeOrderService.updateRechargePayOrder(payRechargeOrder);
        String wxkey = env.getProperty("wxkey");
        String appId = env.getProperty("xiaochengxu_appid");
        String mchId = env.getProperty("mch_id");
        String appName = "xiaochengxu";
        String callBackUrl = env.getProperty("childwxpay.notify")+"/"+appName;
        JSONObject jsonObject = xiaochengxuService.doUnifiedOrder(buyStorysPayApiResponse.getPayRechargeOrder().getOrderCode(),
                "???????????????????????????", buyStorysPayApiResponse.getPayRechargeOrder().getAmount(), "", request, openId, wxkey, callBackUrl, appId, mchId);
        WxPayObjectResponse response = new WxPayObjectResponse();
        response.setWxPayObject(jsonObject);
        return response;
    }
}
