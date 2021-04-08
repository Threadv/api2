package com.ifenghui.storybookapi.app.presale.service.impl;



import com.ifenghui.storybookapi.app.presale.dao.SubscribeDao;
import com.ifenghui.storybookapi.app.presale.service.SubscribeService;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.SubscriptionRecord;
import com.ifenghui.storybookapi.util.DateCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Component
public class SubscribeServiceImpl implements SubscribeService {


    @Autowired
    SubscribeDao subscribeDao;

    /**
     * 查询为过期的订阅用户
     *
     * @param userId
     * @return
     */
    @Override
    public SubscriptionRecord getStatus(Integer userId) {

        List<SubscriptionRecord> subscriptionRecordList = subscribeDao.findListByUserId(userId);
        for (SubscriptionRecord s : subscriptionRecordList) {
            if (DateCheckUtil.isEffectiveDate(new Date(), s.getStartTime(), s.getEndTime())) {
                return s;
            }
        }
        return null;
    }
}
