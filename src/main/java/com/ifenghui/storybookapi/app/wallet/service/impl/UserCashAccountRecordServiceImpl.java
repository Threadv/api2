package com.ifenghui.storybookapi.app.wallet.service.impl;

import com.ifenghui.storybookapi.app.wallet.dao.UserCashAccountRecordDao;
import com.ifenghui.storybookapi.app.wallet.dao.WalletDao;
import com.ifenghui.storybookapi.app.wallet.entity.UserAccountRecord;
import com.ifenghui.storybookapi.app.wallet.entity.UserAccountRecordInterface;
import com.ifenghui.storybookapi.app.wallet.entity.UserCashAccountRecord;
import com.ifenghui.storybookapi.app.wallet.entity.Wallet;
import com.ifenghui.storybookapi.app.wallet.response.GetPayJournalAccountResponse;
import com.ifenghui.storybookapi.app.wallet.service.UserCashAccountRecordService;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import com.ifenghui.storybookapi.exception.ApiDuplicateException;
import com.ifenghui.storybookapi.exception.ApiLackBalanceException;
import com.ifenghui.storybookapi.style.AddStyle;
import com.ifenghui.storybookapi.style.RechargeStyle;
import com.ifenghui.storybookapi.style.WalletStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserCashAccountRecordServiceImpl implements UserCashAccountRecordService {

    @Autowired
    UserCashAccountRecordDao userCashAccountRecordDao;

    @Autowired
    WalletService walletService;

    @Autowired
    WalletDao walletDao;

    @Override
    public UserCashAccountRecord addUserCashAccountRecord(Integer userId, Integer amount, AddStyle addStyle, String intro, Integer balance, String outTradeNo){
        UserCashAccountRecord userCashAccountRecord = this.findUserCashAccountRecordByOutTradeNo(outTradeNo);
        if(userCashAccountRecord != null){
            throw new ApiDuplicateException("该笔订单已经完成！");
        } else {
            userCashAccountRecord = new UserCashAccountRecord();
            userCashAccountRecord.setUserId(userId.longValue());
            userCashAccountRecord.setAmount(amount);
            userCashAccountRecord.setAddStyle(addStyle);
            userCashAccountRecord.setCreateTime(new Date());
            userCashAccountRecord.setIntro(intro);
            userCashAccountRecord.setBalance(balance);
            userCashAccountRecord.setOutTradeNo(outTradeNo);
            return userCashAccountRecordDao.save(userCashAccountRecord);
        }
    }

    @Override
    public UserCashAccountRecord findUserCashAccountRecordByOutTradeNo(String outTradeNo){
        return userCashAccountRecordDao.findUserCashAccountRecordByOutTradeNo(outTradeNo);
    }

    @Transactional
    @Override
    public Wallet addCashAmountToCashWallet(Integer userId, Integer amount, String outTradeNo, String intro){
        UserCashAccountRecord userCashAccountRecordSrc = this.findUserCashAccountRecordByOutTradeNo(outTradeNo);
        Wallet wallet = walletService.getWalletByUserId(userId);
        if(amount == 0){
            return wallet;
        }
        if(userCashAccountRecordSrc != null){
            return wallet;
        } else {
            AddStyle addStyle;
            if(amount > 0){
                addStyle = AddStyle.UP;
            } else {
                addStyle = AddStyle.DOWN;
            }
            int newBalance = wallet.getBalanceCash() + amount;
            if(newBalance < 0){
                throw new ApiLackBalanceException("您的余额不足！");
            }
            this.addUserCashAccountRecord(userId, amount, addStyle, intro, newBalance, outTradeNo);
            wallet.setBalanceCash(newBalance);
            walletDao.save(wallet);
            return wallet;
        }
    }


    @Override
    public Page<UserCashAccountRecord> getUserCashAccountRecordByUserIdAndType(Long userId, Integer type, Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "id"));
        return userCashAccountRecordDao.getUserCashAccountRecordsByUserIdAndType(userId, type, pageable);
    }

    @Override
    public Page<UserCashAccountRecord> getUserCashAccountRecordByUserId(Long userId, Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "id"));
        return userCashAccountRecordDao.getUserCashAccountRecordsByUserId(userId, pageable);
    }

    @Override
    public GetPayJournalAccountResponse getPayJournalAccountFromUserCashAccountRecord(Long userId, Integer type, Integer pageNo, Integer pageSize){
        Page<UserCashAccountRecord> userCashAccountRecordPage = this.getUserCashAccountRecordByUserIdAndType(userId, type, pageNo, pageSize);
        List<UserCashAccountRecord> userCashAccountRecordList = userCashAccountRecordPage.getContent();
        List<UserAccountRecordInterface> userAccountRecordInterfaceList = new ArrayList<>(userCashAccountRecordList);
        GetPayJournalAccountResponse getPayJournalAccountResponse = new GetPayJournalAccountResponse();
        getPayJournalAccountResponse.setUserAccountRecords(userAccountRecordInterfaceList);
        getPayJournalAccountResponse.setJpaPage(userCashAccountRecordPage);
        return getPayJournalAccountResponse;
    }

    @Override
    public GetPayJournalAccountResponse getAllPayJournalAccountFromUserCashAccountRecord(Long userId, Integer pageNo, Integer pageSize) {
        Page<UserCashAccountRecord> userCashAccountRecordPage = this.getUserCashAccountRecordByUserId(userId, pageNo, pageSize);
        List<UserCashAccountRecord> userCashAccountRecordList = userCashAccountRecordPage.getContent();
        List<UserAccountRecordInterface> userAccountRecordInterfaceList = new ArrayList<>(userCashAccountRecordList);
        GetPayJournalAccountResponse getPayJournalAccountResponse = new GetPayJournalAccountResponse();
        getPayJournalAccountResponse.setUserAccountRecords(userAccountRecordInterfaceList);
        getPayJournalAccountResponse.setJpaPage(userCashAccountRecordPage);
        return getPayJournalAccountResponse;
    }
}
