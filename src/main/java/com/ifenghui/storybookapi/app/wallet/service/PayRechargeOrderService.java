package com.ifenghui.storybookapi.app.wallet.service;

/**
 * Created by jia on 2016/12/28.
 */

import com.ifenghui.storybookapi.adminapi.controlleradmin.order.DateQuery;
import com.ifenghui.storybookapi.app.wallet.entity.PayRechargeOrder;
import com.ifenghui.storybookapi.style.RechargePayStyle;
import com.ifenghui.storybookapi.style.RechargeStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface PayRechargeOrderService {
    PayRechargeOrder getPayRechargeOrder(Long id);

    @Transactional
    PayRechargeOrder updateRechargePayOrder(PayRechargeOrder payRechargeOrder);

    Integer getAmountByRechargeStyle(RechargeStyle rechargeStyle, Integer orderId, Long priceId);

    PayRechargeOrder addPayRechargeOrder(Integer userId, Integer amount, RechargePayStyle payStyle, RechargeStyle rechargeStyle, String channel, String appName);

    void setPayRechargeOrderNotifyAddress(PayRechargeOrder payRechargeOrder, String alipay_notify_url);

    Page<PayRechargeOrder> findAllBy(PayRechargeOrder payRechargeOrder, DateQuery dateQuery, PageRequest pageRequest);
}
