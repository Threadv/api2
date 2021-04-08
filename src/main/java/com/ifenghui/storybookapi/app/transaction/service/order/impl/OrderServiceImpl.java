package com.ifenghui.storybookapi.app.transaction.service.order.impl;

/**
 * Created by jia on 2016/12/28.
 */
import com.ifenghui.storybookapi.app.story.dao.SerialStoryDao;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.transaction.dao.*;
import com.ifenghui.storybookapi.app.transaction.entity.*;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponDeferredSubscription;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponDeferredUser;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponSubscription;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponUser;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.PayLessonOrder;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.PayLessonPrice;
import com.ifenghui.storybookapi.app.transaction.entity.serial.PaySerialStoryOrder;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.PaySubscriptionOrder;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.PaySubscriptionPrice;
import com.ifenghui.storybookapi.app.transaction.entity.vip.PayVipOrder;
import com.ifenghui.storybookapi.app.transaction.service.*;
import com.ifenghui.storybookapi.app.transaction.service.lesson.PayLessonOrderService;
import com.ifenghui.storybookapi.app.transaction.service.lesson.PayLessonPriceService;
import com.ifenghui.storybookapi.app.transaction.service.order.*;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.dao.PayRechargeOrderDao;
import com.ifenghui.storybookapi.app.wallet.dao.WalletDao;
import com.ifenghui.storybookapi.app.wallet.entity.PayRechargeOrder;
import com.ifenghui.storybookapi.app.wallet.entity.Wallet;
import com.ifenghui.storybookapi.exception.*;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import com.ifenghui.storybookapi.style.OrderStatusStyle;
import com.ifenghui.storybookapi.style.OrderStyle;
import com.ifenghui.storybookapi.style.RechargeStyle;
import com.ifenghui.storybookapi.util.VersionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Transactional
@Component
public class OrderServiceImpl  implements OrderService {

    @Autowired
    OrderMixService orderMixService;

    @Autowired
    BuyStoryRecordDao buyStoryRecordDao;
    @Autowired
    PayRechargeOrderDao payRechargeOrderDao;

    @Autowired
    CouponUserDao couponUserDao;

    @Autowired
    CouponDao couponDao;

    @Autowired
    WalletDao walletDao;

    @Autowired
    SerialStoryDao serialStoryDao;


    @Autowired
    UserService userService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    PaySerialStoryOrderDao paySerialStoryOrderDao;
    @Autowired
    private Environment env;

    @Autowired
    CouponSerialStoryOrderDao couponSerialStoryOrderDao;

    @Autowired
    PaySubscriptionOrderDao paySubscriptionOrderDao;

    @Autowired
    CouponSubscriptionDao couponSubscriptionDao;

    @Autowired
    CouponDeferredSubscriptionDao couponDeferredSubscriptionDao;

    @Autowired
    CouponDeferredUserDao couponDeferredUserDao;

    @Autowired
    PayAfterOrderDao payAfterOrderDao;

    @Autowired
    PayStoryOrderDao payStoryOrderDao;

    @Autowired
    PaySubscriptionPriceDao paySubscriptionPriceDao;

    @Autowired
    VPayOrderDao vPayOrderDao;

    @Autowired
    PaySubscriptionPriceService paySubscriptionPriceService;

    @Autowired
    BuySerialService buySerialService;

    @Autowired
    PayAfterOrderService payAfterOrderService;

    @Autowired
    PayStoryOrderService payStoryOrderService;

    @Autowired
    PaySubscriptionOrderService paySubscriptionOrderService;

    @Autowired
    PayLessonOrderService payLessonOrderService;

    @Autowired
    PayLessonPriceService payLessonPriceService;

    @Autowired
    PaySerialStoryOrderService paySerialStoryOrderService;

    @Autowired
    PayVipOrderService payVipOrderService;

//    @Override
//    public PayOrder addPayOrder(PayOrder payOrder) {
//        return payOrderDao.save(payOrder);
//    }
    @Override
    public PayRechargeOrder addPayRechargeOrder(PayRechargeOrder payRechargeOrder) {
        return payRechargeOrderDao.save(payRechargeOrder);
    }


    @Override
    public List<PayRechargeOrder> getPayRechargeOrderByUserIdAndOrderCodeAndBuyType(Long userId ,String orderCode, Integer status,Integer buyType) {
        return payRechargeOrderDao.getPayRechargeOrderByUserIdAndOrderCodeAndBuyType(userId ,orderCode,status,buyType);

    }




    @Override
    public void cancelUserPayOrder(Long userId,Long orderId,RechargeStyle type)throws ApiException{
//        获取充值订单数据
//        PayRechargeOrder payRechargeOrder = payRechargeOrderDao.findOne(orderId);
        if (type == RechargeStyle.BUY_STORY) {
            //购买订单
            PayStoryOrder payStoryOrder = payStoryOrderDao.findOne(orderId);
            //判断此是否为此用户订单
            if(!userId.equals(payStoryOrder.getUser().getId()) ){
                throw new ApiNotThisUserException("参数错误，用户与订单信息不符");
            }
            payStoryOrderService.cancelPayStoryOrder(payStoryOrder,orderId);

        } else if(type == RechargeStyle.SUBSCRIPTION) {
            //获取订阅订单数据
            PaySubscriptionOrder paySubscriptionOrder = paySubscriptionOrderDao.findOne(orderId);
            //判断此是否为此用户订单
            if(!userId.equals(paySubscriptionOrder.getUser().getId()) ){
                throw new ApiNotThisUserException("参数错误，用户与订单信息不符");
            }

            paySubscriptionOrderService.cancelPaySubscriptionOrder(paySubscriptionOrder,orderId, type);

        } else if(type == RechargeStyle.SERIAL) {
            //获取购买故事集订单数据
            PaySerialStoryOrder paySerialStoryOrder = paySerialStoryOrderDao.findOne(orderId.intValue());
            if(userId.intValue()!=paySerialStoryOrder.getUserId() ){
                throw new ApiNotThisUserException("参数错误，用户与订单信息不符");
            }

            buySerialService.cancelBuySerialStoryOrder(paySerialStoryOrder,orderId);

        } else if (type.equals(RechargeStyle.LESSON)) {
            payLessonOrderService.cancelPayLessonOrder(userId.intValue(), orderId.intValue());
        }

    }

    @Override
    public Page<VPayOrder> getUserPayOrder(Long userId,Integer status,Integer pageNo,Integer pageSize){
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"createTime"));
        VPayOrder findOrder=new VPayOrder();
        findOrder.setUserId(userId);
        findOrder.setStatus(status);
        findOrder.setIsDel(0);
        Page<VPayOrder> userPayOrderPage =  vPayOrderDao.findAll(Example.of(findOrder),pageable);


        for (VPayOrder item:userPayOrderPage.getContent()){
            this.setOrderToView(item);


        }
        return userPayOrderPage;
    }
    @Override
    public VPayOrder getUserPayOrderDetail(Long orderId,Integer type){

        VPayOrder userPayOrder = vPayOrderDao.getOrderByOrderIdAndType(orderId,type);

        this.setOrderToView(userPayOrder);

        return userPayOrder;
    }

    /**
     * 向视图模型中增加数据
     */
    public void setOrderToView(VPayOrder userPayOrder){

        if(userPayOrder.getType().equals(1)){

            PayStoryOrder payStoryOrder = payStoryOrderDao.findOne(userPayOrder.getOrderId());
            userPayOrder.setBuyType(payStoryOrder.getBuyType());
        } else if(userPayOrder.getType() == 2){
            //订阅订单
            PaySubscriptionOrder paySubscriptionOrder;
            PaySubscriptionPrice paySubscriptionPrice;
            paySubscriptionOrder = paySubscriptionOrderDao.findOne(userPayOrder.getOrderId());
            paySubscriptionPrice = paySubscriptionPriceDao.findOne(paySubscriptionOrder.getPriceId());
            userPayOrder.setPaySubscriptionPrice(paySubscriptionPrice);
            userPayOrder.setStorys(null);
            userPayOrder.setSerialStory(null);
            userPayOrder.setBuyType(paySubscriptionOrder.getType());
        } else if (userPayOrder.getType() == 3){
            //购买故事集订单
            PaySerialStoryOrder paySerialStoryOrder =  paySerialStoryOrderDao.findOne(userPayOrder.getOrderId().intValue());
            SerialStory serialStory = serialStoryDao.findOne(paySerialStoryOrder.getSerialStoryId().longValue());

            userPayOrder.setStorys(null);
            userPayOrder.setPaySubscriptionPrice(null);
            userPayOrder.setSerialStory(serialStory);
            userPayOrder.setBuyType(paySerialStoryOrder.getType());
        } else if (userPayOrder.getType().equals(4)){
            PayLessonOrder payLessonOrder = payLessonOrderService.getPayLessonOrderById(userPayOrder.getOrderId().intValue());
            PayLessonPrice payLessonPrice = payLessonPriceService.getPayLessonPrice(payLessonOrder.getPriceId(), payLessonOrder.getNum());
            userPayOrder.setPayLessonPrice(payLessonPrice);
            userPayOrder.setBuyType(payLessonOrder.getType());
        }
    }

    @Override
    public void delUserPayOrder(Long userId,Long orderId,Integer type)throws ApiException {
        Long uid;
        if(type==1){
            //单本购买订单
            PayStoryOrder payStoryOrder = payStoryOrderDao.findOne(orderId);
            uid = payStoryOrder.getUser().getId();
            if ( uid != userId.intValue()){
                //返回个状态//判断用户是否是此用户否则无权限删除
                throw new ApiNoPermissionDelException("无权限删除");
            }
            //删除订单
            payStoryOrder.setIsDel(1);
            payStoryOrderDao.save(payStoryOrder);

        }else if(type==2){
            //订阅订单
            PaySubscriptionOrder paySubscriptionOrder = paySubscriptionOrderDao.findOne(orderId);
            uid = paySubscriptionOrder.getUser().getId();
            if ( uid != userId.intValue()){
                //返回个状态//判断用户是否是此用户否则无权限删除
                throw new ApiNoPermissionDelException("无权限删除");
            }
            //删除订单
            paySubscriptionOrder.setIsDel(1);
            paySubscriptionOrderDao.save(paySubscriptionOrder);
        }else{
            //故事集订单
            PaySerialStoryOrder paySerialStoryOrder = paySerialStoryOrderDao.findOne(orderId.intValue());
            uid = paySerialStoryOrder.getUserId().longValue();
            if ( uid.intValue() != userId.intValue()){
                //返回个状态//判断用户是否是此用户否则无权限删除
                throw new ApiNoPermissionDelException("无权限删除");
            }
            //删除订单
            paySerialStoryOrder.setIsDel(1);
            paySerialStoryOrderDao.save(paySerialStoryOrder);
        }

    }

    @Override
    public PaySubscriptionOrder getSubscriptionOrder(Long priceId, Long userId, List<Integer> couponIds, Long couponDeferredId)throws ApiException {
        /**
         * V 1.7.0 增加当期订阅 需要查询是否订阅过
         * 是否需要购买周订阅
         */
        Integer isNeedBuyWeek = paySubscriptionPriceService.getPaySubscriptionPriceNeedWeek(userId);

        PaySubscriptionPrice paySubscriptionPrice = paySubscriptionPriceDao.findOne(priceId);

        if(paySubscriptionPrice.getMonth().equals(0) && isNeedBuyWeek.equals(0)){
            throw new ApiDuplicateException("您已订阅过当期阅读课！");
        }

        CouponsResult couponsResult=new CouponsResult();
        couponsResult.checkCoupons(couponIds,couponUserDao,couponDao, paySubscriptionPrice.getPrice());

        //查询此用户余额
        Wallet wallet = walletDao.getWalletByUserId(userId.intValue());
        //查询期刊订阅价格

        //折扣后的金额
        Integer lastAmount = paySubscriptionPrice.getPrice() - couponsResult.getCouponAmount();
        User user = userService.getUser(userId);
        user.setBalance(wallet.getBalance());
//        userDao.save(user);

//        if(user.getIsTest() ==1){
//            isTest = 1;//测试账号
//        }

        //添加期刊订阅订单story_pay_subscription_order
        PaySubscriptionOrder paySubscriptionOrder = new PaySubscriptionOrder();
        paySubscriptionOrder.init();
        paySubscriptionOrder.setUser(user);
        paySubscriptionOrder.setOriginalPrice(paySubscriptionPrice.getPrice());//原价
        paySubscriptionOrder.setAmount(lastAmount);//折扣/优惠后的金额
        paySubscriptionOrder.setPriceId(priceId);
        paySubscriptionOrder.setPayStyle(OrderPayStyle.IOS_BLANCE);//余额订阅
        paySubscriptionOrder.setMonth(paySubscriptionPrice.getMonth());
        paySubscriptionOrder.setIsTest(user.getIsTest());
        String channel = VersionUtil.getChannelInfo(request);


        paySubscriptionOrder.setChannel(channel);//渠道
        paySubscriptionOrder = paySubscriptionOrderDao.save(paySubscriptionOrder);

        //根据订单id拼接订单号order_id
//        String orderStr = "order_" + paySubscriptionOrder.getId();
        String prefix=env.getProperty("order.prefix");
        String orderStr = prefix + "_" + paySubscriptionOrder.getId();
        paySubscriptionOrder.setOrderCode(orderStr);//订单号
//        paySubscriptionOrder.setStatus(1);
        paySubscriptionOrder = paySubscriptionOrderDao.save(paySubscriptionOrder);

        //添加订阅订单和优惠券关联
        if(couponsResult.getCouponIds() != null){
            for (int i = 0; i < couponsResult.getCouponIds().size(); i++) {
                Long couponUserId = couponsResult.getCouponIds().get(i).longValue();
                CouponSubscription couponSubscription = new CouponSubscription();
                couponSubscription.setUserId(userId);
                couponSubscription.setCouponUserId(couponUserId);
                couponSubscription.setOrderId(paySubscriptionOrder.getId());
                couponSubscriptionDao.save(couponSubscription);
                //修改用户优惠券状态
                CouponUser cu = couponUserDao.findOne(couponUserId);
                cu.setStatus(1);
                couponUserDao.save(cu);
            }
        }
        //添加订阅订单和赠阅券关联
        if(couponDeferredId != null){
            CouponDeferredUser couponDeferredUser = couponDeferredUserDao.findOne(couponDeferredId);
            if(couponDeferredUser.getStatus() == 0){
                CouponDeferredSubscription couponDeferredSubscription = new CouponDeferredSubscription();
                couponDeferredSubscription.setUserId(userId);
                couponDeferredSubscription.setCouponUserId(couponDeferredId);
                couponDeferredSubscription.setOrderId(paySubscriptionOrder.getId());
                couponDeferredSubscription.setCreateTime(new Date());
                couponDeferredSubscriptionDao.save(couponDeferredSubscription);
                //修改用户优惠券状态
                CouponDeferredUser cdu = couponDeferredUserDao.findOne(couponDeferredId);
                cdu.setStatus(1);
                couponDeferredUserDao.save(cdu);
            }
        }
        return paySubscriptionOrder;
    }

    @Override
    public List<PaySubscriptionOrder> isUserSubscribed(Long userId){
//        PaySubscriptionOrder findorder = new PaySubscriptionOrder();
//        findorder.setUser(userDao.findOne(userId));
//        findorder.setStatus(1);
//        findorder.setMonth(12);

        List<PaySubscriptionOrder> paySubscriptionOrders = paySubscriptionOrderDao.isUserSubscribed(userService.getUser(userId));

        return paySubscriptionOrders;
    }

    @Override
    public void checkIsCanCreateOrder(Integer userId, Integer targetValue, OrderStyle orderStyle){
        if(orderStyle.equals(OrderStyle.LESSON_ORDER)){
            List<PayLessonOrder> payLessonOrderList = payLessonOrderService.getPayLessonOrderList(userId,0,targetValue);
            if(payLessonOrderList.size() > 0){
                PayLessonOrder payLessonOrder = payLessonOrderList.get(0);
                throw new ApiStoryOrderRepeatException(payLessonOrder,"您有待支付订单！");
            }
        } else if(orderStyle.equals(OrderStyle.SERIAL_ORDER)){
            paySerialStoryOrderService.isCanCreatePaySerialStoryOrder(userId, targetValue);
        }
    }

}
