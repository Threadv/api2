package com.ifenghui.storybookapi.app.transaction.controller;

import com.ifenghui.storybookapi.api.response.exception.ExceptionResponse;
import com.ifenghui.storybookapi.app.transaction.entity.serial.PaySerialStoryOrder;
import com.ifenghui.storybookapi.app.transaction.response.*;
import com.ifenghui.storybookapi.app.transaction.service.BuySerialService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.response.BuySerialStoryResponse;
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
@RequestMapping("/api/serialBuy")
@Api(value = "系列购买相关api", description = "系列购买相关api")
public class SerialBuyController {

    @Autowired
    BuySerialService buySerialService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/story_coupon_buy_serial_story", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "故事兑换券购买故事集")
    BuyOrderByBalanceResponse buySerialStoryByStoryCoupon(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "系列故事id") @RequestParam Integer serialStoryId
    ) throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
        BuyOrderByBalanceResponse response = buySerialService.buySerialStoryByStoryCoupon(userId.intValue(),serialStoryId);
        return response;
    }

    @RequestMapping(value = "/balance_buy_serial_story", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "余额购买故事集", notes = "")
    public BuyOrderByBalanceResponse buySerialStoryByBalance(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "订单号") @RequestParam Long orderId,
            @ApiParam(value = "钱包类型") @RequestParam WalletStyle walletStyle
    ) throws ApiException {

        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);

        BuyOrderByBalanceResponse response;

        OrderPayStyle orderPayStyle = OrderPayStyle.IOS_BLANCE;

        if(walletStyle.equals(WalletStyle.ANDROID_WALLET)){
            orderPayStyle = OrderPayStyle.ANDRIOD_BLANCE;
        }

        response = buySerialService.buySerialStoryByBalance(userId, orderId, orderPayStyle, walletStyle);
        response.getStatus().setMsg("购买成功");
        return response;
    }

    @RequestMapping(value = "/create_serial_story_order", method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({
        @ApiResponse(code = 209, message = "优惠券已使用", response = ExceptionResponse.class),
        @ApiResponse(code = 210, message = "优惠券已过期", response = ExceptionResponse.class)
    })
    @ApiOperation(value = "创建购买故事集订单号")
    public GetOneOrderResponse createPaySerialStoryOrder(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "故事集id") @RequestParam Long serialStoryId,
            @ApiParam(value = "优惠券id（多个id逗号分割）", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") List<Integer> couponIdsStr
    ) throws ApiException {
        couponIdsStr= ListUtil.removeNull(couponIdsStr);
        Long userId = userService.checkAndGetCurrentUserId(token);
        PaySerialStoryOrder paySerialStoryOrder = buySerialService.getBuySerialStoryOrder(serialStoryId, userId, couponIdsStr);//
        StandardOrder standardOrder = new StandardOrder(paySerialStoryOrder);
        GetOneOrderResponse response = new GetOneOrderResponse();
        response.setStandardOrder(standardOrder);
        return response;
    }

}
