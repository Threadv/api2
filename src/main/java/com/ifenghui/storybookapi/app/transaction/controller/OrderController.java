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
@Api(value="订单",description = "订单相关接口")
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
            @ApiResponse(code=212,message="用户与订单信息不符",response = ExceptionResponse.class)
    })
    @ApiOperation(value = "取消订单", notes = "")
    public CancelUserPayOrderResponse cancelUserPayOrder(
            @ApiParam(value = "用户token")@RequestParam String  token,
            @ApiParam(value = "订单id")@RequestParam Long orderId,
            @ApiParam(value = "类型（1购买2订阅3故事集4课程）")@RequestParam Integer type
    )throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);

        CancelUserPayOrderResponse cancelUserPayOrderResponse =new CancelUserPayOrderResponse();

        RechargeStyle rechargeStyle = RechargeStyle.getById(type);

//        orderService.cancelUserPayOrder(userId,orderId,rechargeStyle);
        orderMixService.cancelOrder(userId.intValue(),orderId.intValue(),rechargeStyle);
        cancelUserPayOrderResponse.getStatus().setMsg("取消订单成功");
        return cancelUserPayOrderResponse;
    }

    @RequestMapping(value="/getBuyStorysOrder",method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code = 209, message = "优惠券已使用", response = ExceptionResponse.class),
            @ApiResponse(code = 210, message = "优惠券已过期", response = ExceptionResponse.class)
    })
    @ApiOperation(value = "购物车结算/单本直接购买获取订单接口", notes = "")
    public GetBuyStorysOrderResponse getBuyStorysOrder(
            @ApiParam(value = "用户token")@RequestParam String  token,
            @ApiParam(value = "故事id（多个id逗号分割）")@RequestParam String storyIdsStr,
            @ApiParam(value = "优惠券id（多个id逗号分割）")@RequestParam(required = false, defaultValue = "") List<Integer> couponIdsStr
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
    @ApiOperation(value = "删除订单", notes = "")
    @ApiResponses({@ApiResponse(code=206,message="无权限删除",response = ExceptionResponse.class)})
    public DelUserPayOrderResponse delUserPayOrder(
            @ApiParam(value = "用户token")@RequestParam String  token,
            @ApiParam(value = "订单id")@RequestParam Long orderId,
            @ApiParam(value = "订单类型（1单本购买2订阅）")@RequestParam Integer type
    )throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
        /**
         * 删除旧的
         */
//        orderService.delUserPayOrder(userId,orderId,type);
        orderMixService.deleteOrder(userId.intValue(), orderId.intValue(), RechargeStyle.getById(type));
        DelUserPayOrderResponse delUserPayOrderResponse =new DelUserPayOrderResponse();
        delUserPayOrderResponse.getStatus().setMsg("删除订单成功");
        return delUserPayOrderResponse;
    }

    @RequestMapping(value = "/getBuySerialStoryOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({@ApiResponse(code = 209, message = "优惠券已使用", response = ExceptionResponse.class),
            @ApiResponse(code = 210, message = "优惠券已过期", response = ExceptionResponse.class)
    })
    @ApiOperation(value = "购买故事集获取订单号", notes = "")
    public GetBuySerialStoryOrderResponse getBuySerialStoryOrder(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "故事集id") @RequestParam Long serialStoryId,
            @ApiParam(value = "优惠券id（多个id逗号分割）", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") List<Integer> couponIdsStr
    ) throws ApiException {

        couponIdsStr= ListUtil.removeNull(couponIdsStr);

        Long userId = userService.checkAndGetCurrentUserId(token);
        GetBuySerialStoryOrderResponse response = new GetBuySerialStoryOrderResponse();
        PaySerialStoryOrder res = buySerialService.getBuySerialStoryOrder(serialStoryId, userId, couponIdsStr);//
        response.setPaySerialStoryOrder(res);
        return response;
    }
    /**
     * 返回混合订单，用户取消订单
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
//            @ApiResponse(code=212,message="用户与订单信息不符",response = ExceptionResponse.class)
//    })
    @ApiOperation(value = "获取用户总订单", notes = "订阅+购买")
    @Deprecated
    public GetUserPayOrderResponse getUserPayOrder(
            @ApiParam(value = "用户token")@RequestParam String  token,
            @ApiParam(value = "状态",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") Integer  status,
            @ApiParam(value = "页码")@RequestParam Integer pageNo,
            @ApiParam(value = "条数")@RequestParam Integer pageSize
//            @ApiParam(value = "（订阅）订阅订单id")@RequestParam Long orderId
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
    @ApiOperation(value = "获取用户总订单V2-表替代view-2.1版本增加", notes = "订阅+购买")
    public GetUserPayOrderV2Response getUserPayOrderV2(
            @ApiParam(value = "用户token")@RequestParam String  token,
            @ApiParam(value = "状态",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") Integer  status,
            @ApiParam(value = "页码")@RequestParam Integer pageNo,
            @ApiParam(value = "条数")@RequestParam Integer pageSize
//            @ApiParam(value = "（订阅）订阅订单id")@RequestParam Long orderId
    )throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
//        pageNo = pageNo - 1;
        GetUserPayOrderV2Response getUserPayOrderResponse =new GetUserPayOrderV2Response();

        if(userId!=null && userId==6917){
            //ios审核用户不显示这个列表
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
//            @ApiResponse(code=212,message="用户与订单信息不符",response = ExceptionResponse.class)
//    })
    @ApiOperation(value = "获取用户订单详情", notes = "订阅+购买")
    public GetUserPayOrderDetailResponse getUserPayOrderDetail(
            @ApiParam(value = "用户token")@RequestParam String  token,
            @ApiParam(value = "订单id")@RequestParam Long orderId,
            @ApiParam(value = "订单类型（1购买,2订阅,3故事集,4课程）")@RequestParam Integer type
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
            //获取订单的优惠券
            CouponSubscription couponSubscription = new CouponSubscription();
            couponSubscription.setOrderId(orderId);
            Page<CouponSubscription> couponSubscriptionPage=
                    couponSubscriptionService.getCouponSubscriptions(couponSubscription,new PageRequest(0,20));
            for(CouponSubscription couponSubscription1:couponSubscriptionPage.getContent()){
                userPayOrder.getCoupons().add(couponSubscription1.getCouponUser().getCoupon());
//                vCouponMices.add(new CouponAllInOne(couponSubscription1.getCouponUser().getCoupon()));
            }
            //获取订阅优惠券
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
        //查询支付订单
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
    @ApiOperation(value = "获取用户订单详情", notes = "订阅+购买")
    public GetUserPayOrderDetailV2Response getUserPayOrderDetailV2(
            @ApiParam(value = "用户token")@RequestParam String  token,
            @ApiParam(value = "订单id")@RequestParam Long orderId,
            @ApiParam(value = "订单类型（1购买,2订阅,3故事集,4课程,5活动 7vip订单 9 宝宝会读订单）")@RequestParam Integer type
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
            //获取订单的优惠券
            CouponSubscription couponSubscription = new CouponSubscription();
            couponSubscription.setOrderId(orderId);
            Page<CouponSubscription> couponSubscriptionPage=
                    couponSubscriptionService.getCouponSubscriptions(couponSubscription,new PageRequest(0,20));
            for(CouponSubscription couponSubscription1:couponSubscriptionPage.getContent()){
                userPayOrderMix.getCoupons().add(couponSubscription1.getCouponUser().getCoupon());
//                vCouponMices.add(new CouponAllInOne(couponSubscription1.getCouponUser().getCoupon()));
            }
            //获取订阅优惠券
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
        //查询支付订单
        PayAfterOrder payAfterOrder= payAfterOrderService.getPayAfterOrderByTypeAndPayOrderId(type,orderId);
        if(payAfterOrder!=null){
            userPayOrderMix.setRechargeOrder(payAfterOrder.getRechargeOrder());
            userPayOrderMix.setSuccessTime(payAfterOrder.getRechargeOrder().getSuccessTime());
        } else {
            userPayOrderMix.setSuccessTime(userPayOrderMix.getCreateTime());
        }

        //修改价格图片
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
//    @ApiOperation(value = "恢复故事购买记录")
//    GetUserPayOrderResponse

    @RequestMapping(value = "/balance_buy_all_order", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "余额购买各种订单", notes = "")
    public BuyOrderByBalanceResponse buyOrderByBalance(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "订单号") @RequestParam Long orderId,
            @ApiParam(value = "订单类型")@RequestParam OrderStyle orderStyle,
            @ApiParam(value = "钱包类型") @RequestParam WalletStyle walletStyle
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
    @ApiOperation(value = "验证是否可以创建订单", notes = "故事集和课程订单使用")
    public BaseResponse checkIsCanCreateOrder(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "订单类型")@RequestParam OrderStyle orderStyle,
            @ApiParam(value = "课程id/故事集id") @RequestParam Integer targetValue
    ){
        BaseResponse response = new BaseResponse();
        Long userId = userService.checkAndGetCurrentUserId(token);
        orderService.checkIsCanCreateOrder(userId.intValue(),targetValue,orderStyle);
        return response;
    }

    @RequestMapping(value = "/create_small_program_order", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "小程序创建JS-SDK订单", notes = "故事和课程订单使用")
    public WxPayObjectResponse createSmallProgramOrder(
        @ApiParam(value = "用户token") @RequestParam String token,
        @ApiParam(value = "订阅或购买订单号") @RequestParam Long payOrderId,
        @ApiParam(value = "类型（0充值，1购买，2订阅，3购买故事集 4课程）") @RequestParam Integer type,
        @ApiParam(value = "类型为充值0时传充值价格id（其他类型传0）", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") Long priceId,
        @ApiParam(value = "渠道", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") String channel,
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
                "故事飞船小程序订单", buyStorysPayApiResponse.getPayRechargeOrder().getAmount(), "", request, openId, wxkey, callBackUrl, appId, mchId);
        WxPayObjectResponse response = new WxPayObjectResponse();
        response.setWxPayObject(jsonObject);
        return response;
    }
}
