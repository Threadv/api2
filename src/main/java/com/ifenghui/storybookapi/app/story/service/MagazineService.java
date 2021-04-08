package com.ifenghui.storybookapi.app.story.service;

import com.ifenghui.storybookapi.app.story.response.GetUserSubscribeRecordsResponse;
import com.ifenghui.storybookapi.app.social.response.SubscriptionSchedule;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.BuyMagazineRecord;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.SubscriptionRecord;
import com.ifenghui.storybookapi.app.story.entity.Magazine;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by wml on 2017/2/15.
 */
public interface MagazineService {

    List<Magazine> getMagazineByIsNow(Integer isNow);//
    Magazine getMagazineById(Long id);//
    Page<BuyMagazineRecord> getBuyMagazineRecordByUserId(Long userId, Integer pageNo, Integer pageSize);//
    SubscriptionRecord getUserSubscribeInfo(Long userId);
    /**
     * 最新一次用户订阅信息
     * @param userId
     * @return
     */
    SubscriptionRecord getUserLastSubscribeInfo(Long userId);

    GetUserSubscribeRecordsResponse getUserSubscribeRecords(Long userId);

    /**
     * 设置为当期
     * @param id
     * @return
     */
    Magazine setMagazineToNow(Long id);//
    /**
     * 获取期刊发布排期
     * @return
     */
    List<SubscriptionSchedule> getSubscriptionSchedules(String month);

}
