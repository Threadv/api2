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
//        ????????????????????????
//        PayRechargeOrder payRechargeOrder = payRechargeOrderDao.findOne(orderId);
        if (type == RechargeStyle.BUY_STORY) {
            //????????????
            PayStoryOrder payStoryOrder = payStoryOrderDao.findOne(orderId);
            //?????????????????????????????????
            if(!userId.equals(payStoryOrder.getUser().getId()) ){
                throw new ApiNotThisUserException("??????????????????????????????????????????");
            }
            payStoryOrderService.cancelPayStoryOrder(payStoryOrder,orderId);

        } else if(type == RechargeStyle.SUBSCRIPTION) {
            //????????????????????????
            PaySubscriptionOrder paySubscriptionOrder = paySubscriptionOrderDao.findOne(orderId);
            //?????????????????????????????????
            if(!userId.equals(paySubscriptionOrder.getUser().getId()) ){
                throw new ApiNotThisUserException("??????????????????????????????????????????");
            }

            paySubscriptionOrderService.cancelPaySubscriptionOrder(paySubscriptionOrder,orderId, type);

        } else if(type == RechargeStyle.SERIAL) {
            //?????????????????????????????????
            PaySerialStoryOrder paySerialStoryOrder = paySerialStoryOrderDao.findOne(orderId.intValue());
            if(userId.intValue()!=paySerialStoryOrder.getUserId() ){
                throw new ApiNotThisUserException("??????????????????????????????????????????");
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
     * ??????????????????????????????
     */
    public void setOrderToView(VPayOrder userPayOrder){

        if(userPayOrder.getType().equals(1)){

            PayStoryOrder payStoryOrder = payStoryOrderDao.findOne(userPayOrder.getOrderId());
            userPayOrder.setBuyType(payStoryOrder.getBuyType());
        } else if(userPayOrder.getType() == 2){
            //????????????
            PaySubscriptionOrder paySubscriptionOrder;
            PaySubscriptionPrice paySubscriptionPrice;
            paySubscriptionOrder = paySubscriptionOrderDao.findOne(userPayOrder.getOrderId());
            paySubscriptionPrice = paySubscriptionPriceDao.findOne(paySubscriptionOrder.getPriceId());
            userPayOrder.setPaySubscriptionPrice(paySubscriptionPrice);
            userPayOrder.setStorys(null);
            userPayOrder.setSerialStory(null);
            userPayOrder.setBuyType(paySubscriptionOrder.getType());
        } else if (userPayOrder.getType() == 3){
            //?????????????????????
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
            //??????????????????
            PayStoryOrder payStoryOrder = payStoryOrderDao.findOne(orderId);
            uid = payStoryOrder.getUser().getId();
            if ( uid != userId.intValue()){
                //???????????????//???????????????????????????????????????????????????
                throw new ApiNoPermissionDelException("???????????????");
            }
            //????????????
            payStoryOrder.setIsDel(1);
            payStoryOrderDao.save(payStoryOrder);

        }else if(type==2){
            //????????????
            PaySubscriptionOrder paySubscriptionOrder = paySubscriptionOrderDao.findOne(orderId);
            uid = paySubscriptionOrder.getUser().getId();
            if ( uid != userId.intValue()){
                //???????????????//???????????????????????????????????????????????????
                throw new ApiNoPermissionDelException("???????????????");
            }
            //????????????
            paySubscriptionOrder.setIsDel(1);
            paySubscriptionOrderDao.save(paySubscriptionOrder);
        }else{
            //???????????????
            PaySerialStoryOrder paySerialStoryOrder = paySerialStoryOrderDao.findOne(orderId.intValue());
            uid = paySerialStoryOrder.getUserId().longValue();
            if ( uid.intValue() != userId.intValue()){
                //???????????????//???????????????????????????????????????????????????
                throw new ApiNoPermissionDelException("???????????????");
            }
            //????????????
            paySerialStoryOrder.setIsDel(1);
            paySerialStoryOrderDao.save(paySerialStoryOrder);
        }

    }

    @Override
    public PaySubscriptionOrder getSubscriptionOrder(Long priceId, Long userId, List<Integer> couponIds, Long couponDeferredId)throws ApiException {
        /**
         * V 1.7.0 ?????????????????? ???????????????????????????
         * ???????????????????????????
         */
        Integer isNeedBuyWeek = paySubscriptionPriceService.getPaySubscriptionPriceNeedWeek(userId);

        PaySubscriptionPrice paySubscriptionPrice = paySubscriptionPriceDao.findOne(priceId);

        if(paySubscriptionPrice.getMonth().equals(0) && isNeedBuyWeek.equals(0)){
            throw new ApiDuplicateException("?????????????????????????????????");
        }

        CouponsResult couponsResult=new CouponsResult();
        couponsResult.checkCoupons(couponIds,couponUserDao,couponDao, paySubscriptionPrice.getPrice());

        //?????????????????????
        Wallet wallet = walletDao.getWalletByUserId(userId.intValue());
        //????????????????????????

        //??????????????????
        Integer lastAmount = paySubscriptionPrice.getPrice() - couponsResult.getCouponAmount();
        User user = userService.getUser(userId);
        user.setBalance(wallet.getBalance());
//        userDao.save(user);

//        if(user.getIsTest() ==1){
//            isTest = 1;//????????????
//        }

        //????????????????????????story_pay_subscription_order
        PaySubscriptionOrder paySubscriptionOrder = new PaySubscriptionOrder();
        paySubscriptionOrder.init();
        paySubscriptionOrder.setUser(user);
        paySubscriptionOrder.setOriginalPrice(paySubscriptionPrice.getPrice());//??????
        paySubscriptionOrder.setAmount(lastAmount);//??????/??????????????????
        paySubscriptionOrder.setPriceId(priceId);
        paySubscriptionOrder.setPayStyle(OrderPayStyle.IOS_BLANCE);//????????????
        paySubscriptionOrder.setMonth(paySubscriptionPrice.getMonth());
        paySubscriptionOrder.setIsTest(user.getIsTest());
        String channel = VersionUtil.getChannelInfo(request);


        paySubscriptionOrder.setChannel(channel);//??????
        paySubscriptionOrder = paySubscriptionOrderDao.save(paySubscriptionOrder);

        //????????????id???????????????order_id
//        String orderStr = "order_" + paySubscriptionOrder.getId();
        String prefix=env.getProperty("order.prefix");
        String orderStr = prefix + "_" + paySubscriptionOrder.getId();
        paySubscriptionOrder.setOrderCode(orderStr);//?????????
//        paySubscriptionOrder.setStatus(1);
        paySubscriptionOrder = paySubscriptionOrderDao.save(paySubscriptionOrder);

        //????????????????????????????????????
        if(couponsResult.getCouponIds() != null){
            for (int i = 0; i < couponsResult.getCouponIds().size(); i++) {
                Long couponUserId = couponsResult.getCouponIds().get(i).longValue();
                CouponSubscription couponSubscription = new CouponSubscription();
                couponSubscription.setUserId(userId);
                couponSubscription.setCouponUserId(couponUserId);
                couponSubscription.setOrderId(paySubscriptionOrder.getId());
                couponSubscriptionDao.save(couponSubscription);
                //???????????????????????????
                CouponUser cu = couponUserDao.findOne(couponUserId);
                cu.setStatus(1);
                couponUserDao.save(cu);
            }
        }
        //????????????????????????????????????
        if(couponDeferredId != null){
            CouponDeferredUser couponDeferredUser = couponDeferredUserDao.findOne(couponDeferredId);
            if(couponDeferredUser.getStatus() == 0){
                CouponDeferredSubscription couponDeferredSubscription = new CouponDeferredSubscription();
                couponDeferredSubscription.setUserId(userId);
                couponDeferredSubscription.setCouponUserId(couponDeferredId);
                couponDeferredSubscription.setOrderId(paySubscriptionOrder.getId());
                couponDeferredSubscription.setCreateTime(new Date());
                couponDeferredSubscriptionDao.save(couponDeferredSubscription);
                //???????????????????????????
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
                throw new ApiStoryOrderRepeatException(payLessonOrder,"????????????????????????");
            }
        } else if(orderStyle.equals(OrderStyle.SERIAL_ORDER)){
            paySerialStoryOrderService.isCanCreatePaySerialStoryOrder(userId, targetValue);
        }
    }

}
