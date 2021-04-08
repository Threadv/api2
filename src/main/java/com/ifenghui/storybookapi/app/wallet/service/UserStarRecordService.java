package com.ifenghui.storybookapi.app.wallet.service;

import com.ifenghui.storybookapi.app.social.response.StarRuleIntro;
import com.ifenghui.storybookapi.app.wallet.entity.UserStarRecord;
import com.ifenghui.storybookapi.style.AddStyle;
import com.ifenghui.storybookapi.style.StarRechargeStyle;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;

public interface UserStarRecordService {
//    void addUserStarRecord(Long userId, Integer amount, AddStyle type, StarRechargeStyle buyType, String intro);//添加流水

    /**
     * 获取星星记录
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<UserStarRecord> getUserStarRecordByUserId(Long userId, Integer pageNo, Integer pageSize);



    /**
     * 获得付费任务星星值规则情况
     * @param userId
     * @return
     */
    List<StarRuleIntro> getMoneyTaskList(Long userId);

    /**
     * 获得新手任务星星值规则情况
     * @param userId
     * @return
     */
    List<StarRuleIntro> getRookieTaskList(Long userId);

    /**
     * 获得日常任务星星值规则情况
     * @param userId
     * @return
     */
    List<StarRuleIntro> getDayTaskList(Long userId);

    /**
     * 获取分享推广星星值情况
     * @param userId
     * @return
     */
    public UserStarRecord getSharePaster(Long userId);

    /**
     * 获得用户当天获得星星值数量
     * @param userId
     * @return
     * @throws ParseException
     */
    public Integer getTodayUserStarCount(Long userId) throws ParseException;
}
