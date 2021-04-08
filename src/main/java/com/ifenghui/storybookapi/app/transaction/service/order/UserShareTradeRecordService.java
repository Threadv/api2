package com.ifenghui.storybookapi.app.transaction.service.order;

import com.ifenghui.storybookapi.app.transaction.entity.UserShareTradeRecord;

public interface UserShareTradeRecordService {

    UserShareTradeRecord addUserShareTradeRecord(Integer orderMixId, Integer userId, Integer userParentId, Integer orderAmount, Integer rewardAmount);

    /**
     * 创建邀请好友产生的订单
     * @param orderMixId 总订单id
     * @param userId 用户id
     * @param orderAmount 金额（总金额），按总金额抽成
     * @return
     */
    UserShareTradeRecord createUserShareTradeRecord(Integer orderMixId, Integer userId, Integer orderAmount);

    /**
     * 改变用户的分享交易记录
     * @param userId
     */
    public void changeUserShareTradeData(Integer userId);
}
