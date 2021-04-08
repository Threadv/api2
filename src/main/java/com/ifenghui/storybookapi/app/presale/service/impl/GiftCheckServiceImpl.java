package com.ifenghui.storybookapi.app.presale.service.impl;


import com.ifenghui.storybookapi.app.presale.dao.GiftCheckDao;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleGiftCheck;
import com.ifenghui.storybookapi.app.presale.service.GiftCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Component
public class GiftCheckServiceImpl implements GiftCheckService {


    @Autowired
    GiftCheckDao giftCheckDao;


    /**
     * 添加giftCheck
     * 在判断领取状态时添加
     */
    @Override
    public PreSaleGiftCheck add(Integer userId, Integer goodsId, Integer activityId) {

        PreSaleGiftCheck one = this.findOne(userId, goodsId, activityId);
        if (one != null) {
            return one;
        }
        PreSaleGiftCheck giftCheck = new PreSaleGiftCheck();
        giftCheck.setUserId(userId);
        giftCheck.setGoodsId(goodsId);
        giftCheck.setActivityId(activityId);
        giftCheck.setStatus(0);
        giftCheck.setCreateTime(new Date());
        return giftCheckDao.save(giftCheck);
    }

    /**
     * 设置成功状态
     *
     * @param id
     * @return
     */
    @Override
    public PreSaleGiftCheck setSuccess(Integer id) {
        PreSaleGiftCheck giftCheck = giftCheckDao.findOne(id);
        giftCheck.setStatus(1);
        return giftCheckDao.save(giftCheck);
    }

    /**
     * 查找校验礼物领取记录
     *
     * @param userId
     * @param goodsId
     * @param activityId
     * @return
     */
    @Override
    public PreSaleGiftCheck findOne(Integer userId, Integer goodsId, Integer activityId) {
        PreSaleGiftCheck giftCheck = giftCheckDao.findOne(userId, goodsId, activityId);
        return giftCheck;
    }


}
