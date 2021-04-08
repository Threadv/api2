package com.ifenghui.storybookapi.app.transaction.service.order.impl;

import com.ifenghui.storybookapi.app.transaction.dao.PayActivityOrderDao;
import com.ifenghui.storybookapi.app.transaction.entity.OrderMix;
import com.ifenghui.storybookapi.app.transaction.entity.OrderPayActivity;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderMixService;
import com.ifenghui.storybookapi.app.transaction.service.order.PayActivityOrderService;
import com.ifenghui.storybookapi.style.OrderStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class PayActivityOrderServiceImpl implements PayActivityOrderService{

    @Autowired
    PayActivityOrderDao payActivityOrderDao;

    @Autowired
    OrderMixService orderMixService;

    @Transactional
    @Override
    public OrderPayActivity addOrderPayActivity(Integer activityOrderId) {

        OrderPayActivity orderPayActivity=new OrderPayActivity();
        //TODO:需要查询activityOrderId中的内容设置到订单中

        orderPayActivity= payActivityOrderDao.save(orderPayActivity);

        orderMixService.addOrderMix(OrderStyle.ACTIVITY_GOODS_ORDER,orderPayActivity.getId(),orderPayActivity.getUserId());
        return orderPayActivity;
    }

    @Override
    public void cancelOrder(Integer orderId) {
        //不存在，不处理
    }

    @Override
    public void setDataToOrderMix(OrderMix orderMix) {

        OrderPayActivity orderPayActivity = payActivityOrderDao.findOne(orderMix.getOrderId());
        orderMix.setActivityGoods(orderPayActivity);
        orderMix.setAmount(orderPayActivity.getAmount());
        orderMix.setBuyType(orderPayActivity.getPayType());
    }

    @Override
    public void deleteOrder(Integer orderId) {

    }

    @Override
    public Integer getShareTradeAmount(Integer orderId) {
        return 0;
    }
}
