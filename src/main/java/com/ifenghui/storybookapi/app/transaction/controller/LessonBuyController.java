package com.ifenghui.storybookapi.app.transaction.controller;

import com.ifenghui.storybookapi.api.response.exception.ExceptionResponse;
import com.ifenghui.storybookapi.app.story.response.lesson.GetLessonPriceListResponse;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.PayLessonOrder;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.PayLessonPrice;
import com.ifenghui.storybookapi.app.transaction.response.BuyOrderByBalanceResponse;
import com.ifenghui.storybookapi.app.transaction.response.GetOneOrderResponse;
import com.ifenghui.storybookapi.app.transaction.response.GetSubscriptionOrderResponse;
import com.ifenghui.storybookapi.app.transaction.response.StandardOrder;
import com.ifenghui.storybookapi.app.transaction.response.lesson.BuyLessonResponse;
import com.ifenghui.storybookapi.app.transaction.response.lesson.GetLessonPriceItemResponse;
import com.ifenghui.storybookapi.app.transaction.response.lesson.GetPayLessonOrderResponse;
import com.ifenghui.storybookapi.app.transaction.response.lesson.GetShareMagazineStatusResponse;
import com.ifenghui.storybookapi.app.transaction.service.UserAbilityPlanRelateService;
import com.ifenghui.storybookapi.app.transaction.service.lesson.PayLessonOrderService;
import com.ifenghui.storybookapi.app.transaction.service.lesson.PayLessonPriceService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.response.BuySerialStoryResponse;
import com.ifenghui.storybookapi.exception.ApiBeyondLimitException;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import com.ifenghui.storybookapi.style.WalletStyle;
import com.ifenghui.storybookapi.util.ListUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@EnableAutoConfiguration
@Api(value = "??????????????????api", description = "??????????????????api")
@RequestMapping("/api/lessonBuy")
public class LessonBuyController {

    @Autowired
    PayLessonOrderService payLessonOrderService;

    @Autowired
    PayLessonPriceService payLessonPriceService;

    @Autowired
    UserService userService;

    @Autowired
    UserAbilityPlanRelateService userAbilityPlanRelateService;

    @RequestMapping(value="/create_pay_lesson_order",method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({
        @ApiResponse(code=209,message="??????????????????",response = ExceptionResponse.class),
        @ApiResponse(code=210,message="??????????????????",response = ExceptionResponse.class)
    })
    @ApiOperation(value = "?????????????????????")
    public GetOneOrderResponse getPayLessonOrder(
            @ApiParam(value = "??????token")@RequestParam String  token,
            @ApiParam(value = "??????id")@RequestParam Integer priceId,
            @ApiParam(value = "??????id")@RequestParam Integer lessonId,
            @ApiParam(value = "??????????????????")@RequestParam Integer lessonNum,
            @ApiParam(value = "????????????")@RequestParam Integer price,
            @ApiParam(value = "?????????id?????????id???????????????",defaultValue = "",required = false)@RequestParam(required = false,defaultValue = "") List<Integer> couponIdsStr
    ) {
        couponIdsStr= ListUtil.removeNull(couponIdsStr);
        GetOneOrderResponse response = new GetOneOrderResponse();
        if(lessonNum.equals(0)) {
            throw new ApiBeyondLimitException("?????????????????????");
        }
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);

        if(priceId.equals(6)){
            payLessonOrderService.checkCanBuyPriceIdSix(userId.intValue());
        }

        PayLessonOrder payLessonOrder = payLessonOrderService.createPayLessonOrder(lessonId, priceId, lessonNum, userId, couponIdsStr, price);
        StandardOrder standardOrder = new StandardOrder(payLessonOrder);
        response.setStandardOrder(standardOrder);
        return response;
    }

    @ApiOperation(value = "??????????????????????????????")
    @RequestMapping(value = "/lesson_price_list",method = RequestMethod.GET)
    @ResponseBody
    GetLessonPriceListResponse getLessonPriceList(
            @ApiParam(value = "??????token")@RequestParam(required = false) String  token,
            @ApiParam(value = "lessonId") @RequestParam Integer lessonId
    ) {
        GetLessonPriceListResponse response = new GetLessonPriceListResponse();
        Long userId;
        if(token == null || token.length() <= 0){
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(token);
        }
        List<PayLessonPrice> payLessonPriceList = payLessonPriceService.getPayLessonPriceList(lessonId,userId.intValue());
        response.setPayLessonPriceList(payLessonPriceList);

//        Integer countUserAbilityPlanRecord = userAbilityPlanRelateService.countUserAbilityPlan(userId.intValue());
        User user = userService.getUser(userId);
        if (user.getIsAbilityPlan() > 0) {
            response.setIsBuyAbilityPlan(1);
        }else {
            response.setIsBuyAbilityPlan(0);
        }
        return response;
    }

    @ApiOperation(value = "??????????????????????????????")
    @RequestMapping(value = "/lesson_price_item",method = RequestMethod.GET)
    @ResponseBody
    GetLessonPriceItemResponse getLessonPriceItem(
            @ApiParam(value = "lessonId") @RequestParam Integer lessonId,
            @ApiParam(value = "priceId") @RequestParam Integer priceId
    ) {
        GetLessonPriceItemResponse response = new GetLessonPriceItemResponse();
        response.setPayLessonPrice(payLessonPriceService.getPayLessonPriceItem(priceId, lessonId));
        return response;
    }

    @RequestMapping(value = "/story_coupon_buy_lesson", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "???????????????????????????")
    BuyOrderByBalanceResponse buyLessonByStoryCoupon(
            @ApiParam(value = "??????token")@RequestParam String token,
            @ApiParam(value = "??????id")@RequestParam Integer lessonId,
            @ApiParam(value = "????????????")@RequestParam Integer lessonNum,
            @ApiParam(value = "????????????id")@RequestParam Integer priceId
    ) throws ApiException {
        BuyOrderByBalanceResponse response = new BuyOrderByBalanceResponse();
        Long userId = userService.checkAndGetCurrentUserId(token);
        PayLessonOrder payLessonOrder = payLessonOrderService.buyLessonByStoryCoupon(userId.intValue(),lessonNum, lessonId, priceId);
        response.setStandardOrder(new StandardOrder(payLessonOrder));
        return response;
    }

    @RequestMapping(value = "/balance_buy_lesson", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "??????????????????")
    public BuyOrderByBalanceResponse buyLessonByBalance(
            @ApiParam(value = "??????token") @RequestParam String token,
            @ApiParam(value = "?????????") @RequestParam Integer orderId,
            @ApiParam(value = "????????????") @RequestParam WalletStyle walletStyle
    ) throws ApiException {

        Long userId = userService.checkAndGetCurrentUserId(token);

        BuyOrderByBalanceResponse response = new BuyOrderByBalanceResponse();

        OrderPayStyle orderPayStyle = OrderPayStyle.IOS_BLANCE;

        if (walletStyle.equals(WalletStyle.ANDROID_WALLET)) {
            orderPayStyle = OrderPayStyle.ANDRIOD_BLANCE;
        }
        PayLessonOrder payLessonOrder = payLessonOrderService.buyLessonByBalance(userId, orderId,orderPayStyle, walletStyle);
        response.setStandardOrder(new StandardOrder(payLessonOrder));
        return response;
    }

    @RequestMapping(value = "/share_magazine_status", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "?????????????????????")
    public GetShareMagazineStatusResponse getShareMagazineStatus(
            @ApiParam(value = "??????token") @RequestParam String token
    ) throws ApiException {
        Long userId = userService.checkAndGetCurrentUserId(token);
        return payLessonOrderService.getShareMagazineStatus(userId.intValue());
    }

}
