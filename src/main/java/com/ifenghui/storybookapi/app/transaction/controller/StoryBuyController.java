package com.ifenghui.storybookapi.app.transaction.controller;

import com.ifenghui.storybookapi.api.response.exception.ExceptionResponse;
import com.ifenghui.storybookapi.app.transaction.entity.PayStoryOrder;
import com.ifenghui.storybookapi.app.transaction.response.BuyOrderByBalanceResponse;
import com.ifenghui.storybookapi.app.transaction.response.GetBuyStorysOrderResponse;
import com.ifenghui.storybookapi.app.transaction.response.GetOneOrderResponse;
import com.ifenghui.storybookapi.app.transaction.response.StandardOrder;
import com.ifenghui.storybookapi.app.transaction.response.lesson.BuyLessonResponse;
import com.ifenghui.storybookapi.app.transaction.service.BuyStoryService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.controller.PayController;
import com.ifenghui.storybookapi.app.wallet.response.BuyStorysResponse;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import com.ifenghui.storybookapi.style.WalletStyle;
import com.ifenghui.storybookapi.util.ListUtil;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
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
@Api(value = "故事购买相关api", description = "故事购买相关api")
@RequestMapping("/api/storyBuy")
public class StoryBuyController {

    @Autowired
    UserService userService;

    @Autowired
    BuyStoryService buyStoryService;

    private static Logger logger = Logger.getLogger(PayController.class);

    @RequestMapping(value="/create_story_order",method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code = 209, message = "优惠券已使用", response = ExceptionResponse.class),
            @ApiResponse(code = 210, message = "优惠券已过期", response = ExceptionResponse.class)
    })
    @ApiOperation(value = "购物车结算/单本直接购买获取订单接口")
    public GetOneOrderResponse createPayStoryOrder(
            @ApiParam(value = "用户token")@RequestParam String  token,
            @ApiParam(value = "故事id（多个id逗号分割）")@RequestParam String storyIdsStr,
            @ApiParam(value = "优惠券id（多个id逗号分割）")@RequestParam(required = false, defaultValue = "") List<Integer> couponIds
    )throws ApiException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);
        couponIds= ListUtil.removeNull(couponIds);
        PayStoryOrder payStoryOrder = buyStoryService.createBuyStorysOrder(userId,storyIdsStr, couponIds);
        StandardOrder standardOrder = new StandardOrder(payStoryOrder);
        GetOneOrderResponse response = new GetOneOrderResponse();
        response.setStandardOrder(standardOrder);
        return response;
    }

    @RequestMapping(value = "/balance_buy_story", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "余额购买故事", notes = "")
    public BuyOrderByBalanceResponse buyStorysByBalance(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "订单号") @RequestParam Long orderId,
            @ApiParam(value = "钱包类型") @RequestParam WalletStyle walletStyle
    ) throws ApiException {
        Long userId = userService.checkAndGetCurrentUserId(token);
        BuyOrderByBalanceResponse buyStorysResponse;

        OrderPayStyle orderPayStyle = OrderPayStyle.IOS_BLANCE;
        if(walletStyle.equals(WalletStyle.ANDROID_WALLET)){
            orderPayStyle = OrderPayStyle.ANDRIOD_BLANCE;
        }
        buyStorysResponse = buyStoryService.buyStorysByBalance(userId, orderId, orderPayStyle, walletStyle);
        buyStorysResponse.getStatus().setMsg("购买成功");
        return buyStorysResponse;
    }

    @RequestMapping(value = "/story_coupon_buy_story", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "故事兑换券购买课程")
    BuyOrderByBalanceResponse buyStorysByStoryCoupon(
            @ApiParam(value = "用户token")@RequestParam String token,
            @ApiParam(value = "故事id（多个id逗号分割）")@RequestParam String storyIdsStr
    ) throws ApiException {
        Long userId = userService.checkAndGetCurrentUserId(token);
        return buyStoryService.buyStorysByStoryCoupon(userId,storyIdsStr);
    }

}
