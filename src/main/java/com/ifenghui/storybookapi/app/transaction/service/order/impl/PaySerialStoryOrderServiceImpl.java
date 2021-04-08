package com.ifenghui.storybookapi.app.transaction.service.order.impl;

import com.ifenghui.storybookapi.app.story.dao.SerialStoryDao;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.transaction.dao.PaySerialStoryOrderDao;
import com.ifenghui.storybookapi.app.transaction.entity.OrderMix;
import com.ifenghui.storybookapi.app.transaction.entity.serial.PaySerialStoryOrder;
import com.ifenghui.storybookapi.app.transaction.service.BuySerialStoryRecordService;
import com.ifenghui.storybookapi.app.transaction.service.CouponSerialStoryOrderService;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderMixService;
import com.ifenghui.storybookapi.app.transaction.service.order.PaySerialStoryOrderService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.exception.ApiStoryOrderRepeatException;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import com.ifenghui.storybookapi.style.OrderStatusStyle;
import com.ifenghui.storybookapi.style.OrderStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Component
public class PaySerialStoryOrderServiceImpl implements PaySerialStoryOrderService {

    @Autowired
    PaySerialStoryOrderDao paySerialStoryOrderDao;

    @Autowired
    BuySerialStoryRecordService buySerialStoryRecordService;

    @Autowired
    OrderMixService orderMixService;

    @Autowired
    CouponSerialStoryOrderService couponSerialStoryOrderService;


    @Autowired
    SerialStoryDao serialStoryDao;

    @Transactional
    @Override
    public PaySerialStoryOrder addPaySerialStoryOrder(User user, Integer originalPrice, Integer amount, Integer couponAmount, Integer serialStoryId, Integer userDiscount, String code, String channel, Integer couponNum) {
        PaySerialStoryOrder paySerialStoryOrder = new PaySerialStoryOrder();
        paySerialStoryOrder.setUserId(user.getId().intValue());
        paySerialStoryOrder.setOriginalPrice(originalPrice);
        paySerialStoryOrder.setCouponAmount(couponAmount);
        paySerialStoryOrder.setAmount(amount);
        paySerialStoryOrder.setStatus(0);
        paySerialStoryOrder.setSerialStoryId(serialStoryId);
        paySerialStoryOrder.setCreateTime(new Date());
        paySerialStoryOrder.setDiscount(1);
        paySerialStoryOrder.setUserDiscount(userDiscount);
        paySerialStoryOrder.setPayStyle(OrderPayStyle.DEFAULT_NULL);
        paySerialStoryOrder.setCode(code);
        paySerialStoryOrder.setIsDel(0);
        paySerialStoryOrder.setIsTest(user.getIsTest());
        paySerialStoryOrder.setChannel(channel);
        paySerialStoryOrder.setCouponNum(couponNum);
        paySerialStoryOrder.setRemark("");
        paySerialStoryOrder = paySerialStoryOrderDao.save(paySerialStoryOrder);

        OrderMix orderMix = orderMixService.addOrderMix(OrderStyle.SERIAL_ORDER,paySerialStoryOrder.getId(),user.getId().intValue());
        paySerialStoryOrder.setMixOrderId(orderMix.getId());
        return paySerialStoryOrder;
    }

    @Override
    public void isCanCreatePaySerialStoryOrder(Integer userId, Integer serialStoryId){
        buySerialStoryRecordService.checkIsCanAddBuySerialStoryRecrod(userId, serialStoryId);
        List<PaySerialStoryOrder> paySerialStoryOrderList = paySerialStoryOrderDao.getPaySerialStoryOrdersByUserIdAndSerialStoryId(userId, serialStoryId);
        if(paySerialStoryOrderList.size() > 0){
            throw new ApiStoryOrderRepeatException(paySerialStoryOrderList.get(0), "您有相关未支付订单！");
        }
    }

    @Override
    public PaySerialStoryOrder getPaySerialStoryOrderById(Integer orderId) {
        return paySerialStoryOrderDao.findOne(orderId);
    }

    @Override
    public void cancelOrder(Integer orderId) {
        PaySerialStoryOrder paySerialStoryOrder=paySerialStoryOrderDao.findOne(orderId);
        paySerialStoryOrder.setStatus(OrderStatusStyle.PAY_BACK.getId());
        paySerialStoryOrderDao.save(paySerialStoryOrder);
        /**
         * 获取订阅订单和优惠券关联
         */
        couponSerialStoryOrderService.deleteCouponSerialOrderByOrderId(orderId.longValue());
    }

    @Override
    public void setDataToOrderMix(OrderMix orderMix) {
        PaySerialStoryOrder paySerialStoryOrder =  paySerialStoryOrderDao.findOne(orderMix.getOrderId().intValue());
        SerialStory serialStory = serialStoryDao.findOne(paySerialStoryOrder.getSerialStoryId().longValue());
        orderMix.setStorys(null);
        orderMix.setPaySubscriptionPrice(null);
        orderMix.setSerialStory(serialStory);
        orderMix.setAmount(paySerialStoryOrder.getAmount());
        orderMix.setOriginalPrice(paySerialStoryOrder.getOriginalPrice());
        orderMix.setCreateTime(paySerialStoryOrder.getCreateTime());
        orderMix.setSuccessTime(paySerialStoryOrder.getSuccessTime());
        orderMix.setIsDel(paySerialStoryOrder.getIsDel());
        orderMix.setBuyType(paySerialStoryOrder.getType());
    }

    @Override
    public void deleteOrder(Integer orderId) {
        PaySerialStoryOrder paySerialStoryOrder = paySerialStoryOrderDao.findOne(orderId);
        paySerialStoryOrder.setIsDel(1);
        paySerialStoryOrderDao.save(paySerialStoryOrder);
    }

    @Override
    public Integer getShareTradeAmount(Integer orderId) {
        PaySerialStoryOrder paySerialStoryOrder = this.getPaySerialStoryOrderById(orderId);
        if(
                paySerialStoryOrder.getStatus().equals(OrderStatusStyle.PAY_SUCCESS.getId()) &&
                        !paySerialStoryOrder.getType().equals(OrderPayStyle.CODE.getId()) &&
                        !paySerialStoryOrder.getType().equals(OrderPayStyle.DEFAULT_NULL.getId()) &&
                        !paySerialStoryOrder.getType().equals(OrderPayStyle.STORY_COUPON.getId())
                ){
            return paySerialStoryOrder.getAmount();
        }
        return 0;
    }
}
