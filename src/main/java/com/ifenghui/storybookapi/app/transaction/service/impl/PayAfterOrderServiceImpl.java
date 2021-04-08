package com.ifenghui.storybookapi.app.transaction.service.impl;

/**
 * Created by jia on 2016/12/28.
 */

import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.transaction.dao.PayAfterOrderDao;
import com.ifenghui.storybookapi.app.user.dao.UserDao;

import com.ifenghui.storybookapi.app.transaction.entity.PayAfterOrder;
import com.ifenghui.storybookapi.app.transaction.service.PayAfterOrderService;
import com.ifenghui.storybookapi.app.wallet.dao.PayRechargeOrderDao;
import com.ifenghui.storybookapi.app.wallet.entity.PayRechargeOrder;
import com.ifenghui.storybookapi.style.RechargeStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Component
public class PayAfterOrderServiceImpl implements PayAfterOrderService{


    @Autowired
    UserDao userDao;

    @Autowired
    StoryDao storyDao;

    @Autowired
    PayAfterOrderDao payAfterOrderDao;

    @Autowired
    private Environment env;

    @Autowired
    PayRechargeOrderDao payRechargeOrderDao;

    @Override
    public PayAfterOrder getPayAfterOrderByOrderId(Long orderId) {
        return payAfterOrderDao.getPayAfterOrderByOrderId(orderId);
    }

    @Override
    public PayAfterOrder getPayAfterOrderByTypeAndPayOrderId(Integer type, Long payOrderId) {
        Pageable pageable = new PageRequest(0,1, new Sort(Sort.Direction.DESC,"createTime"));
        Page<PayAfterOrder> payAfterOrderPage = payAfterOrderDao.getPayAfterOrderByTypeAndPayOrderId(type,payOrderId,pageable);
        if(payAfterOrderPage != null && payAfterOrderPage.getContent().size() > 0){
            return payAfterOrderPage.getContent().get(0);
        } else {
            return null;
        }
    }

    @Override
    public void cancelPayRechargeOrder(Integer orderId, RechargeStyle rechargeStyle) {

        PayAfterOrder payAfterOrder = this.getPayAfterOrderByTypeAndPayOrderId(rechargeStyle.getId(),orderId.longValue());

        if(payAfterOrder != null){
            //修改充值订单状态2
            PayRechargeOrder payRechargeOrder = payRechargeOrderDao.findOne(payAfterOrder.getOrderId());
            payRechargeOrder.setStatus(2);
            payRechargeOrderDao.save(payRechargeOrder);
        }
    }

    @Override
    public PayAfterOrder addPayAfterOrder(Long userId, Long orderId, RechargeStyle rechargeStyle, Long payOrderId){
        PayAfterOrder payAfterOrder = new PayAfterOrder();
        payAfterOrder.setCreateTime(new Date());
        payAfterOrder.setOrderId(orderId);
        payAfterOrder.setPayOrderId(payOrderId);
        payAfterOrder.setRechargeStyle(rechargeStyle);
        payAfterOrder.setUserId(userId);
        payAfterOrder = payAfterOrderDao.save(payAfterOrder);
        return payAfterOrder;
    }


}
