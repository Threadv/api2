package com.ifenghui.storybookapi.app.transaction.service.order.impl;

import com.ifenghui.storybookapi.app.transaction.dao.UserShareTradeRecordDao;
import com.ifenghui.storybookapi.app.transaction.entity.UserShareTradeRecord;
import com.ifenghui.storybookapi.app.transaction.service.order.UserShareTradeRecordService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.entity.UserExtend;
import com.ifenghui.storybookapi.app.user.service.UserExtendService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.entity.UserCashAccountRecord;
import com.ifenghui.storybookapi.app.wallet.service.UserCashAccountRecordService;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import com.ifenghui.storybookapi.style.SvipStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class UserShareTradeRecordServiceImpl implements UserShareTradeRecordService {

    @Autowired
    UserShareTradeRecordDao userShareTradeRecordDao;

    @Autowired
    UserExtendService userExtendService;

    @Autowired
    UserCashAccountRecordService userCashAccountRecordService;

    @Autowired
    UserService userService;

    @Transactional
    @Override
    public UserShareTradeRecord addUserShareTradeRecord(Integer orderMixId, Integer userId, Integer userParentId, Integer orderAmount, Integer rewardAmount){
        UserShareTradeRecord userShareTradeRecord = new UserShareTradeRecord();
        userShareTradeRecord.setOrderMixId(orderMixId);
        userShareTradeRecord.setUserParentId(userParentId);
        userShareTradeRecord.setOrderAmount(orderAmount);
        userShareTradeRecord.setUserId(userId);
        userShareTradeRecord.setRewardAmount(rewardAmount);
        userShareTradeRecord.setCreateTime(new Date());
        return userShareTradeRecordDao.save(userShareTradeRecord);
    }

    @Transactional
    @Override
    public UserShareTradeRecord createUserShareTradeRecord(Integer orderMixId, Integer userId, Integer orderAmount){
        UserExtend userExtend = userExtendService.findUserExtendByUserId(userId.longValue());
        User user = userService.getUser(userId.longValue());
        if(userExtend == null || userExtend.getUserParentId().equals(0)){
            return null;
        } else {
            User parentUser = userService.getUser(userExtend.getUserParentId());
            SvipStyle svipStyle = SvipStyle.getById(parentUser.getSvip());
            if(svipStyle == null){
                return null;
            }
            Integer discount = svipStyle.getDiscount();
            Integer rewardAmount = (orderAmount * discount) / 100;
            UserShareTradeRecord userShareTradeRecord = this.addUserShareTradeRecord(orderMixId, userId, userExtend.getUserParentId(), orderAmount, rewardAmount);
            String outTradeNo = "userShareTradeRecord_" + userShareTradeRecord.getId();
            String intro = "邀请好友分销返现";
            userCashAccountRecordService.addCashAmountToCashWallet(userExtend.getUserParentId(), userShareTradeRecord.getRewardAmount(), outTradeNo, intro);
            this.changeUserShareTradeData(userShareTradeRecord.getUserParentId());
            return userShareTradeRecord;
        }
    }

    @Override
    public void changeUserShareTradeData(Integer userId){
        Integer cashBalance = userShareTradeRecordDao.getSumOfRewardAmountFromUserShareTradeRecord(userId);
        if(cashBalance == null){
            cashBalance = 0;
        }
        Integer friendTradeNum = userShareTradeRecordDao.getCountNumFromUserShareTradeRecord(userId);
        if(friendTradeNum == null){
            friendTradeNum = 0;
        }
        Integer friendTradeAmount = userShareTradeRecordDao.getSumOfOrderAmountFromUserShareTradeRecord(userId);
        if(friendTradeAmount == null){
            friendTradeAmount = 0;
        }
        userExtendService.changeUserShareTradeData(userId, cashBalance, friendTradeNum, friendTradeAmount);
    }

}
