package com.ifenghui.storybookapi.app.presale.service.impl;



import com.ifenghui.storybookapi.app.presale.dao.PreSaleGiftCountDao;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleGiftCount;
import com.ifenghui.storybookapi.app.presale.service.PreSaleGiftCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Component
public class PreSaleGiftCountServiceImpl implements PreSaleGiftCountService {


    @Autowired
    PreSaleGiftCountDao giftCountDao;


    /**
     * 添加统计信息
     * @param userId
     * @param activityId
     * @param str
     * @return
     */
    @Override
    public PreSaleGiftCount addItem(Integer userId,Integer userType,Integer giftId, Integer activityId, String str) {

        PreSaleGiftCount giftCount = new PreSaleGiftCount();
        giftCount.setUserId(userId);
        giftCount.setGiftId(giftId);
        giftCount.setActivityId(activityId);
        giftCount.setContent(str);
        giftCount.setUserType(userType);
        giftCount.setCreateTime(new Date());
        PreSaleGiftCount saleGiftCount = giftCountDao.save(giftCount);
        return saleGiftCount;
    }

    /**
     * t通过giftId查询统计信息
     * @param giftId
     * @return
     */
    @Override
    public PreSaleGiftCount findByGiftId(Integer giftId) {
        PreSaleGiftCount giftCount = giftCountDao.findByGiftId(giftId);
        return giftCount;
    }
}
