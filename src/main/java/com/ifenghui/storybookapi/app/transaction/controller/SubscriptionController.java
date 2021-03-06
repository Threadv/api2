package com.ifenghui.storybookapi.app.transaction.controller;


import com.ifenghui.storybookapi.api.response.exception.ExceptionResponse;
import com.ifenghui.storybookapi.app.story.service.StoryService;

import com.ifenghui.storybookapi.app.transaction.entity.subscription.PaySubscriptionOrder;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.PaySubscriptionPrice;

import com.ifenghui.storybookapi.app.transaction.response.GetSubscriptionOrderResponse;
import com.ifenghui.storybookapi.app.transaction.service.*;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.response.GetSubscriptionPriceResponse;

import com.ifenghui.storybookapi.app.wallet.response.IsUserSubscribedResponse;
import com.ifenghui.storybookapi.app.wallet.response.SubscribeByBalanceResponse;
import com.ifenghui.storybookapi.app.wallet.service.PayService;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.exception.ExceptionStyle;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import com.ifenghui.storybookapi.style.WalletStyle;
import com.ifenghui.storybookapi.util.VersionUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/subscription")
@Api(value="??????",description = "???????????????????????????????????????")
public class SubscriptionController {
    @Autowired
    CouponService couponService;

    @Autowired
    UserService userService;

    @Autowired
    CouponDeferredService couponDeferredService;

    @Autowired
    CouponStoryExchangeUserService couponStoryExchangeUserService;

    @Autowired
    private Environment env;

    @Autowired
    StoryService storyService;


    @Autowired
    PaySubscriptionPriceService paySubscriptionPriceService;

    @Autowired
    HttpServletRequest request;


    @Autowired
    CouponService couponSerivce;
    @Autowired
    OrderService orderService;

    @Autowired

    PayService payService;
    @Deprecated
    @RequestMapping(value = "/getSubscriptionPrice", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "??????????????????", notes = "")
    public GetSubscriptionPriceResponse getSubscriptionPrice(
            @ApiParam(value = "??????token") @RequestParam String token
    ) throws ApiNotTokenException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
        GetSubscriptionPriceResponse getSubscriptionPriceResponse = new GetSubscriptionPriceResponse();
        //???????????????
        Page<PaySubscriptionPrice> paySubscriptionPrices = paySubscriptionPriceService.getPaySubscriptionPrice();
        /**
         * ???????????????????????????
         */
        Integer isNeedBuyWeek = paySubscriptionPriceService.getPaySubscriptionPriceNeedWeek(userId);
        List<PaySubscriptionPrice> paySubscriptionPriceList = new ArrayList<>(paySubscriptionPrices.getContent());

        String ver = VersionUtil.getVerionInfo(request);

        if(isNeedBuyWeek.equals(0)){
            Iterator<PaySubscriptionPrice> paySubscriptionPriceIterator = paySubscriptionPriceList.iterator();
            while (paySubscriptionPriceIterator.hasNext()){
                PaySubscriptionPrice paySubscriptionPrice = paySubscriptionPriceIterator.next();
                if(paySubscriptionPrice.getMonth().equals(0)){
                    if(ver.compareTo("1.7.0") >= 0){
                        paySubscriptionPrice.setIsCanBuy(0);
                    } else {
                        paySubscriptionPriceIterator.remove();
                    }
                }
            }
        }

        getSubscriptionPriceResponse.setPaySubscriptionPrices(paySubscriptionPriceList);
        getSubscriptionPriceResponse.setJpaPage(paySubscriptionPrices);
        //???????????????????????????????????????
        Integer couponCount = couponSerivce.getUserValidityCouponsCount(userId);
        Integer couponDeferredCount = couponDeferredService.getUserValidityDeferredCouponsCount(userId);

        getSubscriptionPriceResponse.setCouponCount(couponCount);
        getSubscriptionPriceResponse.setCouponDeferredCount(couponDeferredCount);
        return getSubscriptionPriceResponse;
    }

    @Deprecated
    @RequestMapping(value="/createSubscriptionOrder",method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({@ApiResponse(code=209,message="??????????????????",response = ExceptionResponse.class),
            @ApiResponse(code=210,message="??????????????????",response = ExceptionResponse.class)
    })
    @ApiOperation(value = "???????????????????????????", notes = "")
    public GetSubscriptionOrderResponse createSubscriptionOrder(
            @ApiParam(value = "??????token")@RequestParam String  token,
            @ApiParam(value = "??????id")@RequestParam Long priceId,
            @ApiParam(value = "?????????id?????????id???????????????",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") String couponIdsStr,
            @ApiParam(value = "?????????id",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") Long couponDeferredId
    )throws ApiException {

        throw new ApiException(ExceptionStyle.COUPON_EXCEPTION, "??????????????????????????????????????????????????????");

//        Long userId;
//        userId = userService.checkAndGetCurrentUserId(token);
//
//        GetSubscriptionOrderResponse getSubscriptionOrderResponse =new GetSubscriptionOrderResponse();
//        //
//        PaySubscriptionOrder res = orderService.getSubscriptionOrder(priceId,userId,couponIdsStr,couponDeferredId);//
//        getSubscriptionOrderResponse.setPaySubscriptionOrder(res);
//        return getSubscriptionOrderResponse;
    }
    @RequestMapping(value = "/subscribeByBalance", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "??????????????????", notes = "")
    @ApiResponses({@ApiResponse(code = 204, message = "????????????", response = ExceptionResponse.class)})
    public SubscribeByBalanceResponse subscribeByBalance(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "?????????") @RequestParam String orderCode,
            @ApiParam(value = "????????????") @RequestParam(required = false) WalletStyle walletStyle
    ) throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);

//        SubscribeByBalanceResponse subscribeByBalanceResponse = new SubscribeByBalanceResponse();
        //
        Integer buyType = 3;//????????????
        Long orderId = null;
        if (orderCode.indexOf("_") != -1) {
            orderId = Long.parseLong(orderCode.split("_")[1]);
        } else {
            orderId = Long.parseLong(orderCode);
        }
        if(walletStyle==null){
            walletStyle=WalletStyle.IOS_WALLET;
        }
        SubscribeByBalanceResponse subscribeByBalanceResponse = payService.subscribeByBalance(orderId, userId, OrderPayStyle.IOS_BLANCE,walletStyle);//

        subscribeByBalanceResponse.getStatus().setMsg("????????????");

        return subscribeByBalanceResponse;
    }

    @RequestMapping(value = "/isUserSubscribed", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "????????????????????????????????????", notes = "")
    public IsUserSubscribedResponse isUserSubscribed(
            @ApiParam(value = "??????id") @RequestParam String token
    ) throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);

        IsUserSubscribedResponse isUserSubscribedResponse = new IsUserSubscribedResponse();

        //?????????????????????1???
        List<PaySubscriptionOrder> paySubscriptionOrders = orderService.isUserSubscribed(userId);
        if (paySubscriptionOrders.size() == 0) {
//            throw new ApiIsAddException("?????????);
            isUserSubscribedResponse.getStatus().setCode(0);
            isUserSubscribedResponse.getStatus().setMsg("?????????");
        } else {
            isUserSubscribedResponse.getStatus().setMsg("?????????");
        }

        return isUserSubscribedResponse;
    }
}
