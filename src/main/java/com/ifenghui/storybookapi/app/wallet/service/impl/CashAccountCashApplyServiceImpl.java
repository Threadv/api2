package com.ifenghui.storybookapi.app.wallet.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifenghui.storybookapi.app.wallet.controller.PayController;
import com.ifenghui.storybookapi.app.wallet.dao.CashAccountCashApplyDao;
import com.ifenghui.storybookapi.app.wallet.entity.CashAccountCashApply;
import com.ifenghui.storybookapi.app.wallet.entity.Wallet;
import com.ifenghui.storybookapi.app.wallet.service.CashAccountCashApplyService;
import com.ifenghui.storybookapi.app.wallet.service.UserCashAccountRecordService;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import com.ifenghui.storybookapi.exception.ApiBeyondLimitException;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.style.CashAccountApplyStatusStyle;
import com.ifenghui.storybookapi.style.CashAccountApplyStyle;
import com.ifenghui.storybookapi.style.RechargeStyle;
import com.ifenghui.storybookapi.style.WalletStyle;
import com.ifenghui.storybookapi.util.HttpRequest;
import com.ifenghui.storybookapi.util.MD5Util;
import com.ifenghui.storybookapi.util.NumberUtil;
import com.ifenghui.storybookapi.util.weixin.WXPay;
import com.ifenghui.storybookapi.util.weixin.WXPayConfig;
import com.ifenghui.storybookapi.util.weixin.WXPayConfigImpl;
import com.ifenghui.storybookapi.util.weixin.WXPayUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CashAccountCashApplyServiceImpl implements CashAccountCashApplyService {

    private static Logger logger = Logger.getLogger(PayController.class);

    @Autowired
    CashAccountCashApplyDao cashAccountCashApplyDao;

    @Autowired
    WalletService walletService;

    @Autowired
    UserCashAccountRecordService userCashAccountRecordService;

    @Autowired
    private Environment env;

    private WXPay wxpay;
    private WXPayConfigImpl WxConfig;

    HttpServletRequest request;

    @Override
    public CashAccountCashApply addCashAccountCashApply(Integer amount, CashAccountApplyStyle cashAccountApplyStyle, Integer userId, String account, String userInfo) {
        CashAccountCashApply cashAccountCashApply = new CashAccountCashApply();
        cashAccountCashApply.setIntro("");
        cashAccountCashApply.setAmount(amount);
        cashAccountCashApply.setStatus(1);
        cashAccountCashApply.setCallbackStatus(0);
        cashAccountCashApply.setResultStatus(0);
        cashAccountCashApply.setType(cashAccountApplyStyle);
        cashAccountCashApply.setUserId(userId);
        cashAccountCashApply.setOrderId("");
        cashAccountCashApply.setAccount(account);
        cashAccountCashApply.setCallbackMsg("");
        cashAccountCashApply.setUserInfo(userInfo);
        cashAccountCashApply.setCreateTime(new Date());
        cashAccountCashApply.setSuccessTime(new Date());
        cashAccountCashApplyDao.save(cashAccountCashApply);
        return cashAccountCashApply;
    }

    @Transactional
    @Override
    public CashAccountCashApply createCashAccountCashApply(Integer amount, CashAccountApplyStyle type, Integer userId, String account, String userInfo) throws ParseException {

        Wallet wallet = walletService.getWalletByUserId(userId);
        if (wallet.getBalanceCash() < amount) {
            throw new ApiBeyondLimitException("剩余现金金额不足！");
        }
        CashAccountApplyStyle cashAccountApplyStyle = CashAccountApplyStyle.getById(type.getId());
        if (cashAccountApplyStyle == null) {
            throw new ApiNotFoundException("暂无此类型的提现方式！");
        }
        if (type.equals(CashAccountApplyStyle.ANDROID_WALLET_REFUND) || type.equals(CashAccountApplyStyle.IOS_WALLET_REFUND)) {
            account = "";
            userInfo = "";
        }
        this.isExceedOneMonthMaxApplyAmount(userId);
        CashAccountCashApply cashAccountCashApply = this.addCashAccountCashApply(amount, cashAccountApplyStyle, userId, account, userInfo);
        String outTradeNo = "cashAccountCashApply_" + cashAccountCashApply.getId();
        String intro = cashAccountApplyStyle.getName();
        userCashAccountRecordService.addCashAmountToCashWallet(userId, NumberUtil.unAbs(amount), outTradeNo, intro);
        if (type.equals(CashAccountApplyStyle.ANDROID_WALLET_REFUND) || type.equals(CashAccountApplyStyle.IOS_WALLET_REFUND)) {
            walletService.addAmountToWallet(userId, cashAccountApplyStyle.getWalletStyle(), RechargeStyle.REFUND_CASH_RECHARGE, amount, outTradeNo, cashAccountApplyStyle.getRechargeIntro());
        }
        return cashAccountCashApply;
    }

    //提现驳回
    @Transactional
    @Override
    public void turnCashAccountCashApply(Integer id) {

        CashAccountCashApply cashAccountCashApply = cashAccountCashApplyDao.findOne(id);
        CashAccountApplyStyle type = CashAccountApplyStyle.getById(cashAccountCashApply.getType());

        if (cashAccountCashApply.getStatus() != 4 && (type.equals(CashAccountApplyStyle.ALI_PAY_REFUND) || type.equals(CashAccountApplyStyle.WECHAT_PAY_REFUND))) {
            //金额返回钱包
            String outTradeNo = "turnCashAccount_" + cashAccountCashApply.getId();
            userCashAccountRecordService.addCashAmountToCashWallet(cashAccountCashApply.getUserId(), cashAccountCashApply.getAmount(), outTradeNo, "提现驳回");
            //修改已驳回状态
            cashAccountCashApply.setStatus(4);
            cashAccountCashApplyDao.save(cashAccountCashApply);
        }
    }

    @Transactional
    @Override
    public void isExceedOneMonthMaxApplyAmount(Integer userId) throws ParseException {
        SimpleDateFormat beginFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String beginTimeString = beginFormat.format(new Date());
        Date beginTime = beginFormat.parse(beginTimeString);

        SimpleDateFormat endFormt = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        String endTimeString = endFormt.format(new Date());
        Date endTime = endFormt.parse(endTimeString);

        Integer oneMonthSumAmount = cashAccountCashApplyDao.getSumCashAccountCashApplyByPeriodTime(userId, 3, beginTime, endTime);
        if (oneMonthSumAmount != null && oneMonthSumAmount > 800) {
            throw new ApiBeyondLimitException("超出当月提现金额限制！");
        }
    }

    @Override
    public void dealCashAccountApply() throws Exception {
        List<CashAccountCashApply> cashAccountCashApplyList = cashAccountCashApplyDao.getAllByStatus(CashAccountApplyStatusStyle.WAIT_PAY_CASH.getId());
        for (CashAccountCashApply item : cashAccountCashApplyList) {
            if (item.getType().equals(CashAccountApplyStyle.ALI_PAY_REFUND.getId())) {
                this.dealAliPayRefund(item);
            } else if (item.getType().equals(CashAccountApplyStyle.WECHAT_PAY_REFUND.getId())) {
                this.dealWechatPayRefund(item);
            }
        }
    }

    @Override
    public void changeCashAccountApplyStatus(CashAccountCashApply cashAccountCashApply, String callbackMsg, CashAccountApplyStatusStyle cashAccountApplyStatusStyle, Integer callbackStatus, Integer resultStatus) {
        cashAccountCashApply.setStatus(cashAccountApplyStatusStyle.getId());
        cashAccountCashApply.setCallbackMsg(callbackMsg);
        cashAccountCashApply.setResultStatus(resultStatus);
        cashAccountCashApply.setCallbackStatus(callbackStatus);
        cashAccountCashApplyDao.save(cashAccountCashApply);
    }

    @Override
    public void dealAliPayRefund(CashAccountCashApply cashAccountCashApply) {
        String prefix = env.getProperty("cash.transfer.prefix");
        String orderStr = prefix + "_" + cashAccountCashApply.getId();
        Float amount = cashAccountCashApply.getAmount().floatValue() / 100;
        String app_id = "2017091108667845";
        String privateKey = env.getProperty("private_key");
        String publicKey = env.getProperty("alipay_public_key");
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", app_id, privateKey, "json", "utf-8", publicKey, "RSA");
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        request.setBizContent("{" +
                "\"out_biz_no\":\"" + orderStr + "\"," +
                "\"payee_type\":\"ALIPAY_USERID\"," +
                "\"payee_account\":\"" + cashAccountCashApply.getAccount() + "\"," +
                "\"amount\":\"" + amount + "\"," +
                "\"remark\":\"故事飞船提现\"" +
                "}");
        try {
            AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);

            ObjectMapper objectMapper = new ObjectMapper();
            String responseString = "默认转账响应（问题）！";
            try {
                responseString = objectMapper.writeValueAsString(response);
            } catch (JsonProcessingException je) {
                throw new RuntimeException("ali pay refund json serialize error!");
            }

            if (response.isSuccess()) {
                String responseOutBizNo = response.getOutBizNo();
                if (response.getCode().equals("10000")) {
                    logger.info("AliPayRefund-Success: outBizNo is " + response.getOutBizNo());
                    Integer orderId = Integer.parseInt(responseOutBizNo.split("_")[1]);
                    if (orderId.equals(cashAccountCashApply.getId())) {
                        this.changeCashAccountApplyStatus(
                                cashAccountCashApply,
                                responseString,
                                CashAccountApplyStatusStyle.SUCCESS_FINISH, 1, 1);
                    } else {
                        this.changeCashAccountApplyStatus(
                                cashAccountCashApply,
                                responseString,
                                CashAccountApplyStatusStyle.WAIT_PAY_CASH, 2, 0);
                    }
                } else {
                    this.changeCashAccountApplyStatus(
                            cashAccountCashApply,
                            responseString,
                            CashAccountApplyStatusStyle.WAIT_PAY_CASH, 2, 0);
                }

            } else {
                throw new RuntimeException("ali pay refund error!");
            }
        } catch (com.alipay.api.AlipayApiException e) {
            e.printStackTrace();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String errorString = objectMapper.writeValueAsString(e);
                this.changeCashAccountApplyStatus(
                        cashAccountCashApply,
                        errorString,
                        CashAccountApplyStatusStyle.WAIT_PAY_CASH, 2, 0);
            } catch (JsonProcessingException je) {
                throw new RuntimeException("ali pay refund json serialize error!");
            }
            logger.info("AliPayRefund-Error:" + e.getErrMsg());
        }

    }

    @Override
    public void dealWechatPayRefund(CashAccountCashApply cashAccountCashApply) throws Exception {
        Map<String, String> response = this.wechatPayTransfer(cashAccountCashApply);
        String prefix = env.getProperty("cash.transfer.prefix");
        logger.info("==========微信回调数据============");
        logger.info("=========prefix ="+prefix+"============");
        String orderStr = prefix + cashAccountCashApply.getId();
        String partnerTradeNo = response.get("partner_trade_no");
        ObjectMapper objectMapper = new ObjectMapper();
        String resultCode = response.get("return_code");
        String responseString = "默认转账响应（问题）！";
        logger.info("==========默认转账响应（问题）============");
        try {
            responseString = objectMapper.writeValueAsString(response);
            logger.info("==========微信回调数据============");
            logger.info(responseString);
        } catch (JsonProcessingException je) {
            throw new RuntimeException("ali pay refund json serialize error!");
        }
        if (resultCode.equals("SUCCESS") && orderStr.equals(partnerTradeNo)) {
            logger.info("=========状态更改=============");
            logger.info(resultCode);

            this.changeCashAccountApplyStatus(
                    cashAccountCashApply,
                    responseString,
                    CashAccountApplyStatusStyle.SUCCESS_FINISH, 1, 1);
        } else {
            logger.info("=========状态更改=============");
            logger.info(resultCode);
            this.changeCashAccountApplyStatus(
                    cashAccountCashApply,
                    responseString,
                    CashAccountApplyStatusStyle.WAIT_PAY_CASH, 2, 0);
        }

    }

    public Map<String, String> wechatPayTransfer(CashAccountCashApply cashAccountCashApply) throws Exception {
        logger.info("-----微信提现---");
        logger.info("-----微信提现---");
        logger.info("-----微信提现---");

        try {
            String certpath = env.getProperty("certPath");
            logger.info("=======" + certpath + "======");
            String appId = env.getProperty("appid");
            logger.info("=======" + appId + "======");
            String mchId = env.getProperty("mch_id");
            logger.info("=======" + mchId + "======");
            WxConfig = WXPayConfigImpl.getInstance(certpath, appId, mchId);
            wxpay = new WXPay(WxConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String prefix = env.getProperty("cash.transfer.prefix");
        logger.info("----------" + prefix + "---------");
        logger.info("---------获取ip----------");
        String ip = "";
        try {
            ip = HttpRequest.getIpAddr(request);
        } catch (Exception e) {
            ip = "124.204.41.3";
        }
        logger.info("-----ip : " + ip + "---");
        String orderStr = prefix + cashAccountCashApply.getId();
        Integer amount = cashAccountCashApply.getAmount();
        logger.info("-----amount : " + amount + "---");
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("mch_appid", "wxd1ca18b21691b282");
        data.put("mchid", "1436256202");
        data.put("amount", amount.toString());
        data.put("check_name", "NO_CHECK");
        data.put("desc", "故事飞船分销提现");
        data.put("nonce_str", WXPayUtil.generateUUID());
        data.put("openid", cashAccountCashApply.getAccount());
        data.put("partner_trade_no", orderStr);
        data.put("spbill_create_ip", ip);
//        Map<String, String> response = wxpay.transfers(data);
        String sign = WXPayUtil.generateSignature(data, "gushifeichuan1988abcdefg12345678");
        data.put("sign", sign);

        logger.info("-----sign : " + sign + "---");

        String res = wxpay.requestWithCert("/mmpaymkttransfers/promotion/transfers", (Map<String, String>) data, this.WxConfig.getHttpConnectTimeoutMs(), this.WxConfig.getHttpReadTimeoutMs());

        logger.info("=============res==============");
        logger.info("-----res : " + res + "---");
        Map<String, String> response = WXPayUtil.xmlToMap(res);
//        Map<String, String> response = this.wxpay.processResponseXml(res);
        logger.info("=============response==============");
        logger.info(response);
        return response;
    }

}
