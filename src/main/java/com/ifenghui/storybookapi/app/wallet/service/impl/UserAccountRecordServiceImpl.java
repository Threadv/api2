package com.ifenghui.storybookapi.app.wallet.service.impl;

/**
 * Created by jia on 2016/12/28.
 */

import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.wallet.dao.UserAccountRecordDao;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.wallet.dao.WalletDao;
import com.ifenghui.storybookapi.app.wallet.entity.UserAccountRecordInterface;
import com.ifenghui.storybookapi.app.wallet.entity.Wallet;
import com.ifenghui.storybookapi.app.wallet.entity.UserAccountRecord;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.wallet.response.GetPayJournalAccountResponse;
import com.ifenghui.storybookapi.app.wallet.service.UserAccountRecordService;
import com.ifenghui.storybookapi.style.AddStyle;
import com.ifenghui.storybookapi.style.RechargeStyle;
import com.ifenghui.storybookapi.style.WalletStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Component
public class UserAccountRecordServiceImpl implements UserAccountRecordService{

    @Autowired
    WalletDao walletDao;
    @Autowired
    UserDao userDao;
    @Autowired
    StoryDao storyDao;
    @Autowired
    UserAccountRecordDao userAccountRecordDao;
//    @Override
//    public void addUserAcountRecord(Long userId, Integer amount, AddStyle type, RechargeStyle buyType, String intro) {
//        Wallet wallet = walletDao.getWalletByUserId(userId);
//        UserAccountRecord userAccountRecord =  new UserAccountRecord();
//        User user = userDao.findOne(userId);
//        userAccountRecord.setUser(user);
//        userAccountRecord.setAmount(amount);
//        userAccountRecord.setAddStyle(type);
//        userAccountRecord.setRechargeStyle(buyType);
//        userAccountRecord.setIntro(intro);
//        userAccountRecord.setBalance(wallet.getBalance());
//        userAccountRecord.setCreateTime(new Date());
//        userAccountRecordDao.save(userAccountRecord);
//
//    }

    @Override
    public Page<UserAccountRecord> getUserAccountRecordByUserId(Long userId, Integer pageNo,Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        Page<UserAccountRecord> userAccountRecords = userAccountRecordDao.getUserAccountRecordByUserId(userId,pageable);
        return userAccountRecords;
    }

    @Override
    public Page<UserAccountRecord> getUserAccountRecordByUserIdAndWalletType(Long userId, WalletStyle walletStyle, Integer type, Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        Integer walletType = walletStyle.getId();
//        Page<UserAccountRecord> userAccountRecordPage = userAccountRecordDao.findAll(Example.of(userAccountRecord), pageable);
        return userAccountRecordDao.getUserAccountRecordsByUserIdAndTypeAndWalletType(userId, type, walletType, pageable);
    }

    @Override
    public GetPayJournalAccountResponse getPayJournalAccountFromUserAccountRecord(Long userId, WalletStyle walletStyle, Integer type, Integer pageNo, Integer pageSize){
        Page<UserAccountRecord> userAccountRecords = this.getUserAccountRecordByUserIdAndWalletType(userId, walletStyle, type, pageNo, pageSize);
        GetPayJournalAccountResponse getPayJournalAccountResponse = new GetPayJournalAccountResponse();
        List<UserAccountRecord> userAccountRecordList = userAccountRecords.getContent();
        List<UserAccountRecordInterface> userAccountRecordInterfaceList = new ArrayList<>(userAccountRecordList);
        getPayJournalAccountResponse.setUserAccountRecords(userAccountRecordInterfaceList);
        getPayJournalAccountResponse.setJpaPage(userAccountRecords);
        return getPayJournalAccountResponse;
    }
}
