package com.ifenghui.storybookapi.app.presale.service;




//import org.springframework.transaction.annotation.Transactional;

import com.ifenghui.storybookapi.app.presale.entity.PreSaleGiftCount;

import javax.transaction.Transactional;

public interface PreSaleGiftCountService {

    /**
     * 添加统计信息
     * @param userId
     * @param activityId
     * @param str
     * @return
     */
    @Transactional
    PreSaleGiftCount addItem(Integer userId, Integer userType, Integer giftId, Integer activityId, String str);

    /**
     * t通过giftId查询统计信息
     * @param id
     * @return
     */
    PreSaleGiftCount findByGiftId(Integer id);

}
