package com.ifenghui.storybookapi.app.transaction.controller;

import com.ifenghui.storybookapi.api.response.exception.ExceptionResponse;
import com.ifenghui.storybookapi.app.transaction.entity.PayStoryOrder;
import com.ifenghui.storybookapi.app.transaction.response.BuyOrderByBalanceResponse;
import com.ifenghui.storybookapi.app.transaction.response.GetOneOrderResponse;
import com.ifenghui.storybookapi.app.transaction.response.StandardOrder;
import com.ifenghui.storybookapi.app.transaction.service.BuyActivityGoodsService;
import com.ifenghui.storybookapi.app.transaction.service.BuyStoryService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.controller.PayController;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import com.ifenghui.storybookapi.style.WalletStyle;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
@Api(value = "故事购买相关api", description = "故事购买相关api")
@RequestMapping("/api/activitybuy")
public class ActivityBuyController {

    @Autowired
    UserService userService;

    @Autowired
    BuyActivityGoodsService buyActivityGoodsService;

    private static Logger logger = Logger.getLogger(PayController.class);



    @RequestMapping(value = "/balance_buy_activity_goods", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "余额购买活动中的内容", notes = "")
    public BuyOrderByBalanceResponse buyStorysByBalance(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "订单号") @RequestParam Long orderId,
            @ApiParam(value = "钱包类型") @RequestParam WalletStyle walletStyle
    ) throws ApiException {
        Long userId = userService.checkAndGetCurrentUserId(token);
        BuyOrderByBalanceResponse buyStorysResponse = new BuyOrderByBalanceResponse();

        OrderPayStyle orderPayStyle = OrderPayStyle.IOS_BLANCE;
        if(walletStyle.equals(WalletStyle.ANDROID_WALLET)){
            orderPayStyle = OrderPayStyle.ANDRIOD_BLANCE;
        }
        buyActivityGoodsService.buyActivityGoodsByBalance(userId, orderId, orderPayStyle, walletStyle);
        buyStorysResponse.getStatus().setMsg("购买成功");
        return buyStorysResponse;
    }

}
