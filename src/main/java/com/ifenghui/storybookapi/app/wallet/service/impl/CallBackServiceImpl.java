package com.ifenghui.storybookapi.app.wallet.service.impl;

/**
 * Created by jia on 2016/12/28.
 */

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;

import com.ifenghui.storybookapi.app.story.dao.MagazineDao;
import com.ifenghui.storybookapi.app.story.dao.SerialStoryDao;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.transaction.dao.*;
import com.ifenghui.storybookapi.app.transaction.entity.*;
import com.ifenghui.storybookapi.app.transaction.service.BuyMagazineRecordService;
import com.ifenghui.storybookapi.app.transaction.service.BuyStoryRecordService;
import com.ifenghui.storybookapi.app.transaction.service.PaySubscriptionPriceService;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.wallet.dao.*;
import com.ifenghui.storybookapi.app.wallet.entity.*;
import com.ifenghui.storybookapi.app.wallet.response.BuySerialStoryResponse;
import com.ifenghui.storybookapi.app.wallet.response.BuyStorysResponse;
import com.ifenghui.storybookapi.app.wallet.response.SubscribeByBalanceResponse;
import com.ifenghui.storybookapi.app.wallet.service.CallBackService;
import com.ifenghui.storybookapi.app.wallet.service.PayService;
import com.ifenghui.storybookapi.app.wallet.service.UserAccountRecordService;
import com.ifenghui.storybookapi.app.wallet.service.UserStarRecordService;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.config.StarConfig;
import com.ifenghui.storybookapi.config.StoryConfig;
import com.ifenghui.storybookapi.exception.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Transactional
@Component
public class CallBackServiceImpl implements CallBackService {

    private static Logger logger = Logger.getLogger(CallBackServiceImpl.class);


    @Autowired
    BuyStoryRecordDao buyStoryRecordDao;


    @Autowired
    PayCallbackRecordDao payCallbackRecordDao;
    @Autowired
    PaySubscriptionPriceDao paySubscriptionPriceDao;
    @Autowired
    SubscriptionRecordDao subscriptionRecordDao;
    @Autowired
    PaySubscriptionOrderDao paySubscriptionOrderDao;
    @Autowired
    PaySerialStoryOrderDao paySerialStoryOrderDao;

    @Autowired
    MagazineDao magazineDao;
    @Autowired
    BuyMagazineRecordDao buyMagazineRecordDao;
    @Autowired
    BuySerialStoryRecordDao buySerialStoryRecordDao;

    @Autowired
    PayStoryOrderDao payStoryOrderDao;
    @Autowired
    VPayOrderDao vPayOrderDao;
//    @Autowired
//    ShoppingCartDao shoppingCartDao;
//    @Autowired
//WalletDao walletDao;
    @Autowired
    UserDao userDao;
    @Autowired
    StoryDao storyDao;

    @Autowired
    SerialStoryDao serialStoryDao;

    @Autowired
    OrderStoryDao orderStoryDao;
    @Autowired
    PayAfterOrderDao payAfterOrderDao;
    @Autowired
    PayRechargeOrderDao payRechargeOrderDao;
    @Autowired
    PayRechargePriceDao payRechargePriceDao;
    @Autowired
    CouponDeferredDao couponDeferredDao;
    @Autowired
    CouponDeferredUserDao couponDeferredUserDao;
    @Autowired
    CouponDeferredSubscriptionDao couponDeferredSubscriptionDao;
    @Autowired
    CouponDao couponDao;
    @Autowired
    CouponUserDao couponUserDao;
    @Autowired
    CouponSubscriptionDao couponSubscriptionDao;
    @Autowired
    CouponSerialStoryOrderDao couponSerialStoryOrderDao;
    @Autowired
    UserAccountRecordService userAccountRecordService;
    @Autowired
    UserStarRecordService userStarRecordService;
    @Autowired
    StoryService storyService;
    @Autowired
    HttpServletRequest request;

    @Autowired
    ShoppingCartDao shoppingCartDao;

    @Autowired
    BuyStoryRecordService buyStoryRecordService;

    @Autowired
    BuyMagazineRecordService buyMagazineRecordService;

    @Autowired
    private Environment env;

    @Autowired
    PaySubscriptionPriceService paySubscriptionPriceService;



    @Override
    public PayCallbackRecord addPayCallbackRecord(PayCallbackRecord payCallbackRecord) {
        return payCallbackRecordDao.save(payCallbackRecord)
;    }

    @Override
    public PayCallbackRecord getPayCallbackRecordByOrderCode(String orderCode) {
        return payCallbackRecordDao.getOneByOrderCode(orderCode);
    }

    @Override
    public PayCallbackRecord getPayCallbackRecordByCallBackMsg(String callbackMsg) {
        Pageable pageable = new PageRequest(0, 1, new Sort(Sort.Direction.DESC,"id"));
        Page<PayCallbackRecord> page = payCallbackRecordDao.getPreSalePayCallBacksByCallbackMsg(callbackMsg ,pageable);
        if(page.getTotalElements()==0){
            return null;
        }
        return page.getContent().get(0);
    }

    @Override
    public PayCallbackRecord getPayCallbackRecordByOrderId(Long orderId) {
        return payCallbackRecordDao.findOneByOrderId(orderId);
    }

}
