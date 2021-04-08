package com.ifenghui.storybookapi.app.wallet.controller;

/**
 * Created by wml on 2016/12/28.
 */

import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.config.AlipayConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huawei.pay.callback.demo.domain.ResultDomain;
import com.huawei.pay.callback.demo.servlet.CallbackDemo;
import com.huawei.pay.callback.demo.util.RSA;
import com.ifenghui.storybookapi.api.notify.WeixinNotify;
import com.ifenghui.storybookapi.api.response.exception.ExceptionResponse;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.service.SerialStoryService;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.transaction.controller.CouponController;
import com.ifenghui.storybookapi.app.transaction.controller.OrderController;
import com.ifenghui.storybookapi.app.transaction.controller.ShoppingCartController;
import com.ifenghui.storybookapi.app.transaction.controller.SubscriptionController;
import com.ifenghui.storybookapi.app.transaction.entity.PayAfterOrder;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponDeferredUser;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponUser;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.PaySubscriptionOrder;
import com.ifenghui.storybookapi.app.transaction.response.*;
import com.ifenghui.storybookapi.app.transaction.service.*;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.entity.UserAccount;
import com.ifenghui.storybookapi.app.user.entity.UserInfo;
import com.ifenghui.storybookapi.app.user.response.GetUserResponse;
import com.ifenghui.storybookapi.app.user.service.UserAccountService;
import com.ifenghui.storybookapi.app.user.service.UserInfoService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.dao.CashAccountCashApplyDao;
import com.ifenghui.storybookapi.app.wallet.dao.PayCallbackRecordDao;
import com.ifenghui.storybookapi.app.wallet.entity.*;
import com.ifenghui.storybookapi.app.wallet.response.*;
import com.ifenghui.storybookapi.app.wallet.service.*;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.config.VipcodeConfig;
import com.ifenghui.storybookapi.exception.*;
import com.ifenghui.storybookapi.style.*;
import com.ifenghui.storybookapi.util.HttpRequest;
import com.ifenghui.storybookapi.util.IosNotifyHttpUtil;
import com.ifenghui.storybookapi.util.VersionUtil;
import com.ifenghui.storybookapi.util.ios.InApp;
import com.ifenghui.storybookapi.util.ios.IosNotify;
import io.swagger.annotations.*;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.ifenghui.storybookapi.util.MD5Util.getMD5;
import static com.ifenghui.storybookapi.util.Map2StringUtil.transMapToString;
import static com.ifenghui.storybookapi.util.SetXMLUtil.setXML;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api/wallet")
@Api(value = "支付", description = "支付相关接口")
public class WalletController {

    @Autowired
    UserService userService;

    @Autowired
    WalletService walletService;

    @Autowired
    CashAccountCashApplyService cashAccountCashApplyService;

    @Autowired
    CashAccountCashApplyDao cashAccountCashApplyDao;

    @Autowired
    UserCashAccountRecordService userCashAccountRecordService;

    @Autowired
    UserStarRecordService userStarRecordService;


    @RequestMapping(value = "/getStars", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取星星值列表", notes = "")
    public GetStarRecordsResponse getStars(
            @RequestHeader(value = "ssToken") String ssToken,
            @ApiParam(value = "pageNo  1开始") @RequestParam Integer pageNo,
            @ApiParam(value = "pageSize") @RequestParam Integer pageSize
    ) throws ApiNotTokenException {

        GetStarRecordsResponse response = new GetStarRecordsResponse();
        Long userId;
        userId = userService.checkAndGetCurrentUserId(ssToken);
        Wallet wallet = walletService.getWalletByUserId(userId);

        Integer starCount = wallet.getStarCount();
        Page<UserStarRecord> starRecordPage = userStarRecordService.getUserStarRecordByUserId(userId, pageNo, pageSize);
        response.setStarCount(starCount);
        response.setJpaPage(starRecordPage);
        response.setUserStarRecordList(starRecordPage.getContent());
        return response;
    }


    @RequestMapping(value = "/add_cashAmount", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "增加减少现金", notes = "增加减少现金")
    GetWalletBalanceResponse addCashAmount(
            @ApiParam(value = "token") @RequestParam String token,
            @ApiParam(value = "amount") @RequestParam Integer amount,
            @ApiParam(value = "outTradeNo") @RequestParam String outTradeNo,
            @ApiParam(value = "intro") @RequestParam String intro
    ) throws ApiNotTokenException {

        GetWalletBalanceResponse response = new GetWalletBalanceResponse();

        Long userId = userService.checkAndGetCurrentUserId(token);
        Wallet wallet = userCashAccountRecordService.addCashAmountToCashWallet(userId.intValue(), amount, outTradeNo, intro);

        response.setWallet(wallet);
        return response;
    }


    @RequestMapping(value = "/get_wallet", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获得钱包")
    GetWalletBalanceResponse getWallet(
            @ApiParam(value = "用户token") @RequestParam String token
    ) {

        GetWalletBalanceResponse response = new GetWalletBalanceResponse();
        Long userId = userService.checkAndGetCurrentUserId(token);
        Wallet wallet = walletService.getWalletByUserId(userId);
        response.setWallet(wallet);
        return response;
    }

    @RequestMapping(value = "/create_cash", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "小程序现金提现申请")
    CashAccountCashApplyResponse createCash(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "金额") @RequestParam Integer amount
    ) throws ParseException {
        CashAccountCashApplyResponse response = new CashAccountCashApplyResponse();
        Long userId = userService.checkAndGetCurrentUserId(token);

        CashAccountCashApply cash = cashAccountCashApplyService.createCashAccountCashApply(amount, CashAccountApplyStyle.XIAOCHENGXU_PAY_REFUND, userId.intValue(), "", "");
        cashAccountCashApplyService.changeCashAccountApplyStatus(cash, "", CashAccountApplyStatusStyle.SUCCESS_FINISH, 1, 1);

        return response;
    }


    @RequestMapping(value = "/pay_refund", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "转账/退款", notes = "转账/退款")
    public void PayRefund(
            @ApiParam(value = "id") @RequestParam Integer id
    ) throws ApiNotTokenException {

        try {
            CashAccountCashApply item = cashAccountCashApplyDao.findOne(id);
            if (item.getType().equals(CashAccountApplyStyle.ALI_PAY_REFUND.getId())) {
                cashAccountCashApplyService.dealAliPayRefund(item);
            } else if (item.getType().equals(CashAccountApplyStyle.WECHAT_PAY_REFUND.getId())) {
                cashAccountCashApplyService.dealWechatPayRefund(item);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }


    @RequestMapping(value = "/getWalletBalance", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取钱包余额", notes = "")
    public GetWalletBalanceResponse getWalletBalance(
            @ApiParam(value = "用户token") @RequestParam String token
    ) throws ApiNotTokenException {
        Long userId;
        userId = userService.checkAndGetCurrentUserId(token);

        GetWalletBalanceResponse getWalletBalanceResponse = new GetWalletBalanceResponse();
        //获取钱包
        Wallet wallet = walletService.getWalletByUserId(userId);

//        for (ShoppingTrolley shoppingTrolley :shoppingTrolleys.getContent()) {
//            getShoppingTrolleyResponse.getStorys().add(shoppingTrolley.getStory());
//        }
        getWalletBalanceResponse.setWallet(wallet);
        //saleRule优惠规则
        return getWalletBalanceResponse;
    }

    @RequestMapping(value = "/create_cash_account_apply", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "现金提现申请")
    CashAccountCashApplyResponse cashAccountCashApply(
            @ApiParam(value = "用户token") @RequestParam String token,
            @ApiParam(value = "金额") @RequestParam Integer amount,
            @ApiParam(value = "账号") @RequestParam(required = false) String account,
            @ApiParam(value = "用户昵称") @RequestParam(required = false) String userInfo,
            @ApiParam(value = "类型 1 支付宝 2 微信 3ios钱包 4安卓钱包") @RequestParam CashAccountApplyStyle type
    ) throws ParseException {
        CashAccountCashApplyResponse response = new CashAccountCashApplyResponse();
        Long userId = userService.checkAndGetCurrentUserId(token);
        if (
                (type.equals(CashAccountApplyStyle.ALI_PAY_REFUND) || type.equals(CashAccountApplyStyle.WECHAT_PAY_REFUND)) &&
                        (account == null || account.equals("") || userInfo == null || userInfo.equals(""))
                ) {
            throw new ApiNotFoundException("没有填写账号信息！");
        }
        cashAccountCashApplyService.createCashAccountCashApply(amount, type, userId.intValue(), account, userInfo);
        return response;
    }

    @RequestMapping(value = "/pay_turn", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "转账/退款驳回", notes = "转账/退款驳回")
    public void PayTurn(
            @ApiParam(value = "id") @RequestParam Integer id
    ) throws ApiNotTokenException {
        cashAccountCashApplyService.turnCashAccountCashApply(id);
    }

}
