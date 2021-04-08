package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.transaction.entity.subscription.PaySubscriptionPrice;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * Created by wml on 2017/2/16.
 */
public interface PaySubscriptionPriceService {

    Page<PaySubscriptionPrice> getPaySubscriptionPrice();//

    Integer getPaySubscriptionPriceNeedWeek(Long userId);

    List<Long> getIsSubscriptionUserIds();

    boolean isNeedOpenNotice(Long userId);

    Date getMaxEndTimeByUserId(Long userId);

}
