package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.transaction.entity.subscription.BuyMagazineRecord;
import org.springframework.data.domain.Page;

/**
 * Created by wml on 2017/2/15.
 */
public interface BuyMagazineRecordService {

    Page<BuyMagazineRecord> getBuyMagazineRecordByUserIdAndMagazineId(Long userId, Long magazineId);//
//    Page<BuyMagazineRecord> getBuyMagazineRecordByUserId(Long userId, Integer pageNo, Integer pageSize);//

    /**
     * 新注册用户赠送当期期刊内的故事
     * @param userId
     */
    void giveNewUserNowMagazineAndStory(Long userId);

    /**
     * 购买当期期刊 并且添加对应故事 公共方法
     * @param userId
     * @param type
     */
    public void addIsNowBuyMagazineRecordAndBuyStoryRecord(Long userId, Integer type);

    public BuyMagazineRecord addBuyMagazineRecord(Long userId, Long magazineId);

}
