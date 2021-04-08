package com.ifenghui.storybookapi.app.transaction.service.impl;
import com.ifenghui.storybookapi.app.story.dao.MagazineDao;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.transaction.dao.BuyMagazineRecordDao;
import com.ifenghui.storybookapi.app.transaction.dao.PaySubscriptionPriceDao;
import com.ifenghui.storybookapi.app.transaction.dao.SubscriptionRecordDao;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.SubscriptionRecord;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.BuyMagazineRecord;
import com.ifenghui.storybookapi.app.story.entity.Magazine;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.PaySubscriptionPrice;
import com.ifenghui.storybookapi.app.transaction.service.PaySubscriptionPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by wml on 2017/2/16.
 */
@Transactional
@Component
public class PaySubscriptionPriceServiceImpl implements PaySubscriptionPriceService {

    @Autowired
    SubscriptionRecordDao subscriptionRecordDao;

    @Autowired
    MagazineDao magazineDao;

    @Autowired
    PaySubscriptionPriceDao paySubscriptionPriceDao;

    @Autowired
    StoryDao storyDao;

    @Autowired
    UserDao userDao;

    @Autowired
    BuyMagazineRecordDao buyMagazineRecordDao;

    @Transactional
    @Override
    public Page<PaySubscriptionPrice> getPaySubscriptionPrice() {
        Integer status =1;
        Integer pageNo =0;
        Integer pageSize = 10;
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"orderBy","id"));
        Page<PaySubscriptionPrice> shoppingTrolleys = this.paySubscriptionPriceDao.getPaySubscriptionPriceByStatus(status,pageable);

        return shoppingTrolleys;
    }

    @Override
    public Integer getPaySubscriptionPriceNeedWeek(Long userId) {

        Pageable magazinePageable = new PageRequest(0, 6, new Sort(Sort.Direction.ASC,"publishTime","id"));
        Page<Magazine> magazinePage = magazineDao.getMagazinePageByIsNow(1,magazinePageable);
        List<Magazine> magazineList = magazinePage.getContent();

        Integer isNeedWeekBuy = 1;
        Pageable pageable = new PageRequest(0, 1, new Sort(Sort.Direction.DESC,"id"));
        for(Magazine item : magazineList){
            Page<BuyMagazineRecord> buyMagazineRecords = buyMagazineRecordDao.getRecordByUserIdAndMagazineId(userId,item.getId(),pageable);
            if (buyMagazineRecords.getContent().size() > 0){
                isNeedWeekBuy = 0;
                break;
            }
        }

        return isNeedWeekBuy;
    }

    @Override
    public List<Long> getIsSubscriptionUserIds() {
        return subscriptionRecordDao.getUserIdsByUserId();
    }

    @Override
    public boolean isNeedOpenNotice(Long userId) {
        Integer num = subscriptionRecordDao.getUserIdsByEndTime(userId);
        if(num != null && num > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Date getMaxEndTimeByUserId(Long userId) {
        Pageable pageable = new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "id"));
        List<SubscriptionRecord> subscriptionRecordList = subscriptionRecordDao.getSubscriptionRecordsByUserIdAndEndTime(userId, pageable);
        SubscriptionRecord subscriptionRecord = null;
        if (subscriptionRecordList != null && subscriptionRecordList.size() > 0) {
            subscriptionRecord = subscriptionRecordList.get(0);
        }
        if (subscriptionRecord != null) {
            return subscriptionRecord.getEndTime();
        } else {
            return null;
        }
    }
}
