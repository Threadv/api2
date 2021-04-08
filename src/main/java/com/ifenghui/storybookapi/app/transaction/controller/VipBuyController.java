package com.ifenghui.storybookapi.app.transaction.controller;

import com.ifenghui.storybookapi.api.response.exception.ExceptionResponse;
import com.ifenghui.storybookapi.app.app.service.ConfigService;
import com.ifenghui.storybookapi.app.shop.service.ShopExpressService;
import com.ifenghui.storybookapi.app.system.service.GeoipService;
import com.ifenghui.storybookapi.app.transaction.entity.vip.PayVipOrder;
import com.ifenghui.storybookapi.app.transaction.response.*;
import com.ifenghui.storybookapi.app.transaction.service.PayVipOrderService;
import com.ifenghui.storybookapi.app.transaction.service.UserSvipService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.style.*;
import com.ifenghui.storybookapi.util.ListUtil;
import com.ifenghui.storybookapi.util.VersionUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/vipBuy")
@Api(value = "购买vip卡", description = "购买vip卡")
public class VipBuyController {

    @Autowired
    UserService userService;

    @Autowired
    PayVipOrderService payVipOrderService;

    @Autowired
    ShopExpressService shopExpressService;

    @Autowired
    UserSvipService userSvipService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    ConfigService configService;
    @Autowired
    GeoipService geoipService;

    @RequestMapping(value = "/get_svip_privilege", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "svip权益")
    public GetSvipPrivilegeResponse getSvipPrivilege(
            @RequestHeader(required = false) String ssToken
    ) {

        Long userId;
        if (ssToken == null || ssToken.length() <= 0) {
            userId = 0L;
        } else {
            userId = userService.checkAndGetCurrentUserId(ssToken);
        }
        GetSvipPrivilegeResponse response = payVipOrderService.getSvipPrivilege(userId);
        response.setIsCheck(VersionUtil.getIosIsCheck(request, configService,geoipService));
        return response;
    }

    @RequestMapping(value = "/create_pay_vip_order", method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses({
            @ApiResponse(code = 209, message = "优惠券已使用", response = ExceptionResponse.class),
            @ApiResponse(code = 210, message = "优惠券已过期", response = ExceptionResponse.class)
    })
    @ApiOperation(value = "创建购买会员卡订单号")
    public GetOneOrderResponse getPayLessonOrder(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "价格id") @RequestParam Integer priceStyle,
            @ApiParam(value = "收货人") @RequestParam String receiver,
            @ApiParam(value = "电话号码") @RequestParam String phone,
            @ApiParam(value = "地址") @RequestParam String address,
            @ApiParam(value = "区域") @RequestParam String area,
            @ApiParam(value = "优惠券id（多个id逗号分割）", defaultValue = "", required = false) @RequestParam(required = false, defaultValue = "") List<Integer> couponIdsStr
    ) {
        couponIdsStr= ListUtil.removeNull(couponIdsStr);
        GetOneOrderResponse response = new GetOneOrderResponse();
        Long userId = userService.checkAndGetCurrentUserId(token);
        VipGoodsStyle vipGoodsStyle = VipGoodsStyle.getById(priceStyle);
        if(vipGoodsStyle == null) {
            throw new ApiNotFoundException("没有这种类型的会员卡！");
        }
        PayVipOrder payVipOrder = payVipOrderService.createPayVipOrder(vipGoodsStyle, userId, couponIdsStr);
        shopExpressService.addExpress(payVipOrder.getId(), receiver, phone, address, area, ExpressStyle.DEFAULT_NULL, "", ExpressStatusStyle.HAS_NO_DELIVERY, OrderStyle.VIP_ORDER);
        StandardOrder standardOrder = new StandardOrder(payVipOrder);
        response.setStandardOrder(standardOrder);
        return response;
    }

    @RequestMapping(value = "/balance_buy_vip", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "余额购买vip")
    public BuyOrderByBalanceResponse buyVipByBalance(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "订单号") @RequestParam Integer orderId,
            @ApiParam(value = "钱包类型") @RequestParam WalletStyle walletStyle
    ) throws ApiException {

        Long userId = userService.checkAndGetCurrentUserId(token);

        BuyOrderByBalanceResponse response = new BuyOrderByBalanceResponse();

        OrderPayStyle orderPayStyle = OrderPayStyle.IOS_BLANCE;

        if (walletStyle.equals(WalletStyle.ANDROID_WALLET)) {
            orderPayStyle = OrderPayStyle.ANDRIOD_BLANCE;
        }
        PayVipOrder payVipOrder = payVipOrderService.buyVipByBalance(userId, orderId, orderPayStyle, walletStyle);
        response.setStandardOrder(new StandardOrder(payVipOrder));
        return response;
    }

}
