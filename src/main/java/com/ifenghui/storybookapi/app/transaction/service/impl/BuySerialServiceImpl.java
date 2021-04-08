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
         * 创建订单故事集购买订单story_pay_serial_story
         */
        String channel = VersionUtil.getChannelInfo(request);
        PaySerialStoryOrder paySerialStoryOrder = paySerialStoryOrderService.addPaySerialStoryOrder(
                user, serialStory.getPrice(), serialStory.getPrice(), 0, serialStoryId, 100, code, channel, 0
        );

        /**
         * 添加购买故事集记录
         */
        buySerialStoryRecordService.addBuySerialStoryRecord(user,paySerialStoryOrder.getSerialStoryId());

        /**
         * 修改订单状态
         */
        paySerialStoryOrder.setStatus(1);
        paySerialStoryOrder.setPayStyle(OrderPayStyle.CODE);
        paySerialStoryOrder.setSuccessTime(new Date());
        paySerialStoryOrderDao.save(paySerialStoryOrder);
        orderMixService.updateOrderMixStatus(OrderStyle.SERIAL_ORDER, paySerialStoryOrder.getId(), OrderStatusStyle.PAY_SUCCESS);
        /**
         * 调用积分流水添加 故事集
         */
        Integer starCount = StarConfig.STAR_USR_BUY*(paySerialStoryOrder.getAmount())/100;
        walletService.addStarToWallet(user.getId().intValue(), StarRechargeStyle.GIVE_STORY,starCount,StarContentStyle.SERIES_BUY.getName());

        String intro = "故事集《"+serialStory.getName()+"》\n已成功添加至已购故事";
        return intro;
    }

    @Override
    public BuyOrderByBalanceResponse buySerialStoryByBalance(Long userId, Long orderId, OrderPayStyle payStyle, WalletStyle walletStyle)throws ApiException {

        logger.info("--------------------buySerialStoryByBalance-------buy-----");
        //获取订单id

        logger.info("--------------------buySerialStoryByBalance-------orderID-----"+orderId);
        //获取订单数据
        PaySerialStoryOrder paySerialStoryOrder = paySerialStoryOrderDao.findOne(orderId.intValue());

        if(payStyle.equals(OrderPayStyle.ANDRIOD_BLANCE) && walletStyle.equals(WalletStyle.ANDROID_WALLET)){
            Wallet wallet = walletService.getWalletByUserId(userId.intValue());
            Float newAmount = ((float)wallet.getDiscount() / 100) * (float) paySerialStoryOrder.getAmount();
            Integer androidAmount = newAmount.intValue();
            paySerialStoryOrder.setAmount(androidAmount);
            paySerialStoryOrder.setUserDiscount(wallet.getDiscount());
        }

        User user = userService.getUser(userId);
        walletService.addAmountToWallet(userId.intValue(),walletStyle,RechargeStyle.SERIAL,NumberUtil.unAbs(paySerialStoryOrder.getAmount()),"buyserial"+orderId,"购买故事集");

        /**
         * 添加购买故事集记录
         */
        buySerialStoryRecordService.addBuySerialStoryRecord(user,paySerialStoryOrder.getSerialStoryId());

        /**
         * 修改订单状态
         * payStyle 1微信 2支付宝 3余额 4兑换码 5华为支付 6指尖 7安卓 8故事兑换券
         */
        paySerialStoryOrder.setPayStyle(payStyle);
        paySerialStoryOrder.setStatus(1);
        paySerialStoryOrder.setSuccessTime(new Date());
        paySerialStoryOrder = paySerialStoryOrderDao.save(paySerialStoryOrder);
        orderMixService.updateOrderMixStatus(OrderStyle.SERIAL_ORDER, paySerialStoryOrder.getId(), OrderStatusStyle.PAY_SUCCESS);
        /**
         * 调用积分流水添加 故事集
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
         * 检测下故事兑换券是否足够
         */
        List<CouponStoryExchangeUser> couponStoryExchangeUserList = couponStoryExchangeUserService.checkStoryCouponAndGetCouponNum(userId, serialStory.getCouponCount());

        /**
         * 创建订单故事集购买订单story_pay_serial_story
         */
        String channel = VersionUtil.getChannelInfo(request);
        PaySerialStoryOrder paySerialStoryOrder = paySerialStoryOrderService.addPaySerialStoryOrder(
                user, serialStory.getPrice(), serialStory.getPrice(), 0, serialStoryId, 100, "", channel, serialStory.getStoryCount()
        );

        /**
         * 创建故事兑换券使用与订单关联
         */
        couponStoryExchangeOrderService.addCouponStoryExchangeOrderByList(
                couponStoryExchangeUserList, paySerialStoryOrder.getId(), OrderStyle.SERIAL_ORDER
        );

        /**
         * 添加购买故事集记录
         */
        buySerialStoryRecordService.addBuySerialStoryRecord(user,paySerialStoryOrder.getSerialStoryId());

        /**
         * 修改订单状态
         * payStyle 1微信 2支付宝 3余额 4兑换码 5华为支付 6指尖 7安卓 8故事兑换券
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
         * 查询故事集价格
         */
        SerialStory serialStory = serialStoryService.getSerialStory(serialStoryId);

        CouponsResult couponsResult= new CouponsResult();
        couponsResult.checkCoupons(couponIds,couponUserDao,couponDao, serialStory.getPrice());
        Integer userDiscount = 100;
        /**
         * 折扣后的金额
         */
        Float amount = serialStory.getPrice() - couponsResult.getCouponAmount() * ((float)userDiscount /100);
        User user = userService.getUser(userId);
        String channel = VersionUtil.getChannelInfo(request);

        /**
         * 添加故事集购买订单 story_pay_serial_story_order
         */
        PaySerialStoryOrder paySerialStoryOrder = paySerialStoryOrderService.addPaySerialStoryOrder(
          user, serialStory.getPrice(), amount.intValue(), couponsResult.getCouponAmount(), serialStoryId.intValue(), userDiscount, "", channel, 0
        );

        /**
         * 添加故事集订单和优惠券关联
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
         * 获取订阅订单和优惠券关联
         */
        couponSerialStoryOrderService.deleteCouponSerialOrderByOrderId(orderId);


    }

}
