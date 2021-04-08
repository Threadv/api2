package com.ifenghui.storybookapi.app.presale.service;



import com.ifenghui.storybookapi.app.presale.entity.PreSaleGiftCheck;
import org.springframework.transaction.annotation.Transactional;

public interface GiftCheckService {

    /**
     * 添加giftCheck
     * 在判断领取状态时添加
     * TODO  每次活动
     */
    @Transactional
    PreSaleGiftCheck add(Integer userId, Integer goodsId, Integer activityId);

    /**
     * 设置成功状态
     * @param id
     * @return
     */
    PreSaleGiftCheck setSuccess(Integer id);

    /**
     * 查找校验礼物领取记录
     * @param userId
     * @param goodsId
     * @param activityId
     * @return
     */
    PreSaleGiftCheck findOne(Integer userId, Integer goodsId, Integer activityId);
}
