package com.ifenghui.storybookapi.app.transaction.service.impl;


import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.service.SerialStoryService;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.transaction.dao.*;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponStoryExchangeUser;
import com.ifenghui.storybookapi.app.transaction.entity.serial.PaySerialStoryOrder;
import com.ifenghui.storybookapi.app.transaction.response.BuyOrderByBalanceResponse;
import com.ifenghui.storybookapi.app.transaction.response.StandardOrder;
import com.ifenghui.storybookapi.app.transaction.service.*;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderMixService;
import com.ifenghui.storybookapi.app.transaction.service.order.PaySerialStoryOrderService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.dao.PayRechargeOrderDao;
import com.ifenghui.storybookapi.app.wallet.entity.Wallet;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import com.ifenghui.storybookapi.config.StarConfig;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.style.*;
import com.ifenghui.storybookapi.util.NumberUtil;
import com.ifenghui.storybookapi.util.VersionUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Component
public class BuySerialServiceImpl implements BuySerialService{

    @Autowired
    UserService userService;

    @Autowired
    SerialStoryService serialStoryService;
    @Autowired
    HttpServletRequest request;

    @Autowired
    PaySerialStoryOrderDao paySerialStoryOrderDao;

    @Autowired
    private Environment env;

    @Autowired
    BuySerialStoryRecordDao buySerialStoryRecordDao;

    @Autowired
    StoryService storyService;

    @Autowired
    BuyStoryRecordDao buyStoryRecordDao;



    @Autowired
    WalletService walletService;

    @Autowired
    CouponUserDao couponUserDao;
    @Autowired
    CouponDao couponDao;
    @Autowired
    CouponSerialStoryOrderDao couponSerialStoryOrderDao;

    @Autowired
    PayAfterOrderDao payAfterOrderDao;
    @Autowired
    PayRechargeOrderDao payRechargeOrderDao;

    @Autowired
    PayAfterOrderService payAfterOrderService;

    @Autowired
    PaySerialStoryOrderService paySerialStoryOrderService;

    @Autowired
    CouponSerialStoryOrderService couponSerialStoryOrderService;

    @Autowired
    BuySerialStoryRecordService buySerialStoryRecordService;

    @Autowired
    CouponStoryExchangeUserService couponStoryExchangeUserService;

    @Autowired
    CouponStoryExchangeOrderService couponStoryExchangeOrderService;

    @Autowired
    OrderMixService orderMixService;

    Logger logger= Logger.getLogger(BuySerialServiceImpl.class);

    @Override
    public String buySerialStoryByCode(Long userId, String code,Integer serialStoryId)throws ApiException {

        logger.info("--------------------buySerialStoryByCode-------buy-----");

        User user = userService.getUser(userId);

        SerialStory serialStory = serialStoryService.getSerialStory(serialStoryId.longValue());

        paySerialStoryOrderService.isCanCreatePaySerialStoryOrder(userId.intValue(),serialStoryId);

        /**
         * ?????????????????????????????????story_pay_serial_story
         */
        String channel = VersionUtil.getChannelInfo(request);
        PaySerialStoryOrder paySerialStoryOrder = paySerialStoryOrderService.addPaySerialStoryOrder(
                user, serialStory.getPrice(), serialStory.getPrice(), 0, serialStoryId, 100, code, channel, 0
        );

        /**
         * ???????????????????????????
         */
        buySerialStoryRecordService.addBuySerialStoryRecord(user,paySerialStoryOrder.getSerialStoryId());

        /**
         * ??????????????????
         */
        paySerialStoryOrder.setStatus(1);
        paySerialStoryOrder.setPayStyle(OrderPayStyle.CODE);
        paySerialStoryOrder.setSuccessTime(new Date());
        paySerialStoryOrderDao.save(paySerialStoryOrder);
        orderMixService.updateOrderMixStatus(OrderStyle.SERIAL_ORDER, paySerialStoryOrder.getId(), OrderStatusStyle.PAY_SUCCESS);
        /**
         * ???????????????????????? ?????????
         */
        Integer starCount = StarConfig.STAR_USR_BUY*(paySerialStoryOrder.getAmount())/100;
        walletService.addStarToWallet(user.getId().intValue(), StarRechargeStyle.GIVE_STORY,starCount,StarContentStyle.SERIES_BUY.getName());

        String intro = "????????????"+serialStory.getName()+"???\n??????????????????????????????";
        return intro;
    }

    @Override
    public BuyOrderByBalanceResponse buySerialStoryByBalance(Long userId, Long orderId, OrderPayStyle payStyle, WalletStyle walletStyle)throws ApiException {

        logger.info("--------------------buySerialStoryByBalance-------buy-----");
        //????????????id

        logger.info("--------------------buySerialStoryByBalance-------orderID-----"+orderId);
        //??????????????????
        PaySerialStoryOrder paySerialStoryOrder = paySerialStoryOrderDao.findOne(orderId.intValue());

        if(payStyle.equals(OrderPayStyle.ANDRIOD_BLANCE) && walletStyle.equals(WalletStyle.ANDROID_WALLET)){
            Wallet wallet = walletService.getWalletByUserId(userId.intValue());
            Float newAmount = ((float)wallet.getDiscount() / 100) * (float) paySerialStoryOrder.getAmount();
            Integer androidAmount = newAmount.intValue();
            paySerialStoryOrder.setAmount(androidAmount);
            paySerialStoryOrder.setUserDiscount(wallet.getDiscount());
        }

        User user = userService.getUser(userId);
        walletService.addAmountToWallet(userId.intValue(),walletStyle,RechargeStyle.SERIAL,NumberUtil.unAbs(paySerialStoryOrder.getAmount()),"buyserial"+orderId,"???????????????");

        /**
         * ???????????????????????????
         */
        buySerialStoryRecordService.addBuySerialStoryRecord(user,paySerialStoryOrder.getSerialStoryId());

        /**
         * ??????????????????
         * payStyle 1?????? 2????????? 3?????? 4????????? 5???????????? 6?????? 7?????? 8???????????????
         */
        paySerialStoryOrder.setPayStyle(payStyle);
        paySerialStoryOrder.setStatus(1);
        paySerialStoryOrder.setSuccessTime(new Date());
        paySerialStoryOrder = paySerialStoryOrderDao.save(paySerialStoryOrder);
        orderMixService.updateOrderMixStatus(OrderStyle.SERIAL_ORDER, paySerialStoryOrder.getId(), OrderStatusStyle.PAY_SUCCESS);
        /**
         * ???????????????????????? ?????????
         */
        Integer starCount = StarConfig.STAR_USR_BUY*(paySerialStoryOrder.getAmount())/100;
        walletService.addStarToWallet(userId.intValue(),StarRechargeStyle.BUY_SERIAL,starCount,StarContentStyle.SERIES_BUY.getName());

        BuyOrderByBalanceResponse response = new BuyOrderByBalanceResponse();
        response.setStandardOrder(new StandardOrder(paySerialStoryOrder));
        return response;
    }

    @Transactional
    @Override
    public BuyOrderByBalanceResponse buySerialStoryByStoryCoupon(Integer userId, Integer serialStoryId) throws ApiException {

        User user = userService.getUser(userId);
        SerialStory serialStory = serialStoryService.getSerialStory(serialStoryId.longValue());
        paySerialStoryOrderService.isCanCreatePaySerialStoryOrder(userId,serialStoryId);

        /**
         * ????????????????????????????????????
         */
        List<CouponStoryExchangeUser> couponStoryExchangeUserList = couponStoryExchangeUserService.checkStoryCouponAndGetCouponNum(userId, serialStory.getCouponCount());

        /**
         * ?????????????????????????????????story_pay_serial_story
         */
        String channel = VersionUtil.getChannelInfo(request);
        PaySerialStoryOrder paySerialStoryOrder = paySerialStoryOrderService.addPaySerialStoryOrder(
                user, serialStory.getPrice(), serialStory.getPrice(), 0, serialStoryId, 100, "", channel, serialStory.getStoryCount()
        );

        /**
         * ??????????????????????????????????????????
         */
        couponStoryExchangeOrderService.addCouponStoryExchangeOrderByList(
                couponStoryExchangeUserList, paySerialStoryOrder.getId(), OrderStyle.SERIAL_ORDER
        );

        /**
         * ???????????????????????????
         */
        buySerialStoryRecordService.addBuySerialStoryRecord(user,paySerialStoryOrder.getSerialStoryId());

        /**
         * ??????????????????
         * payStyle 1?????? 2????????? 3?????? 4????????? 5???????????? 6?????? 7?????? 8???????????????
         */
        paySerialStoryOrder.setPayStyle(OrderPayStyle.STORY_COUPON);
        paySerialStoryOrder.setStatus(1);
        paySerialStoryOrder.setSuccessTime(new Date());
        paySerialStoryOrder = paySerialStoryOrderDao.save(paySerialStoryOrder);
        orderMixService.updateOrderMixStatus(OrderStyle.SERIAL_ORDER, paySerialStoryOrder.getId(), OrderStatusStyle.PAY_SUCCESS);
        BuyOrderByBalanceResponse response = new BuyOrderByBalanceResponse();
        response.setStandardOrder(new StandardOrder(paySerialStoryOrder));
        return response;
    }

    @Transactional
    @Override
    public PaySerialStoryOrder getBuySerialStoryOrder(Long serialStoryId, Long userId, List<Integer> couponIds)throws ApiException {

        paySerialStoryOrderService.isCanCreatePaySerialStoryOrder(userId.intValue(),serialStoryId.intValue());


        /**
         * ?????????????????????
         */
        SerialStory serialStory = serialStoryService.getSerialStory(serialStoryId);

        CouponsResult couponsResult= new CouponsResult();
        couponsResult.checkCoupons(couponIds,couponUserDao,couponDao, serialStory.getPrice());
        Integer userDiscount = 100;
        /**
         * ??????????????????
         */
        Float amount = serialStory.getPrice() - couponsResult.getCouponAmount() * ((float)userDiscount /100);
        User user = userService.getUser(userId);
        String channel = VersionUtil.getChannelInfo(request);

        /**
         * ??????????????????????????? story_pay_serial_story_order
         */
        PaySerialStoryOrder paySerialStoryOrder = paySerialStoryOrderService.addPaySerialStoryOrder(
          user, serialStory.getPrice(), amount.intValue(), couponsResult.getCouponAmount(), serialStoryId.intValue(), userDiscount, "", channel, 0
        );

        /**
         * ???????????????????????????????????????
         */
        couponSerialStoryOrderService.addCouponSerialOrderByCouponsResult(couponsResult,userId.intValue(),paySerialStoryOrder.getId());
        return paySerialStoryOrder;
    }

    @Transactional
    @Override
    public void cancelBuySerialStoryOrder(PaySerialStoryOrder paySerialStoryOrder,Long orderId) {

        paySerialStoryOrder.setStatus(2);
        paySerialStoryOrderDao.save(paySerialStoryOrder);
        /**
         * ????????????????????????????????????
         */
        couponSerialStoryOrderService.deleteCouponSerialOrderByOrderId(orderId);


    }

}
