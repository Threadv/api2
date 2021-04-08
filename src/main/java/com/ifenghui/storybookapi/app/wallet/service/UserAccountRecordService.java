package com.ifenghui.storybookapi.app.wallet.service;

/**
 * Created by jia on 2016/12/28.
 */

import com.ifenghui.storybookapi.app.wallet.entity.UserAccountRecord;
import com.ifenghui.storybookapi.app.wallet.response.GetPayJournalAccountResponse;
import com.ifenghui.storybookapi.style.AddStyle;
import com.ifenghui.storybookapi.style.RechargeStyle;
import com.ifenghui.storybookapi.style.WalletStyle;
import org.springframework.data.domain.Page;

public interface UserAccountRecordService {
//    void addUserAcountRecord(Long userId, Integer amount, AddStyle type, RechargeStyle buyType, String intro);//添加流水
    Page<UserAccountRecord> getUserAccountRecordByUserId(Long userId, Integer pageNo,Integer pageSize);

    Page<UserAccountRecord> getUserAccountRecordByUserIdAndWalletType(Long userId, WalletStyle walletStyle, Integer type, Integer pageNo, Integer pageSize);

    GetPayJournalAccountResponse getPayJournalAccountFromUserAccountRecord(Long userId, WalletStyle walletStyle, Integer type, Integer pageNo, Integer pageSize);
}
