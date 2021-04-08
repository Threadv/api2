package com.ifenghui.storybookapi.app.transaction.service.order;

/**
 * Created by jia on 2016/12/28.
 */

import com.ifenghui.storybookapi.app.transaction.entity.OrderMix;
import com.ifenghui.storybookapi.app.transaction.entity.OrderPayActivity;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.style.OrderStyle;
import com.ifenghui.storybookapi.style.RechargeStyle;
import org.springframework.data.domain.Page;

public interface PayActivityOrderService extends OrderPayProcess{

    /**
     * 创建订单
     * @param activityOrderId
     * @return
     */
    OrderPayActivity addOrderPayActivity(Integer activityOrderId);

}
