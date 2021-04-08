package com.ifenghui.storybookapi.app.wallet.service;

import com.ifenghui.storybookapi.app.wallet.entity.UserCashAccountRecord;
import com.ifenghui.storybookapi.app.wallet.entity.Wallet;
import com.ifenghui.storybookapi.app.wallet.response.GetPayJournalAccountResponse;
import com.ifenghui.storybookapi.style.AddStyle;
import org.springframework.data.domain.Page;

public interface UserCashAccountRecordService {

    UserCashAccountRecord addUserCashAccountRecord(Integer userId, Integer amount, AddStyle addStyle, String intro, Integer balance, String outTradeNo);

    UserCashAccountRecord findUserCashAccountRecordByOutTradeNo(String outTradeNo);

    Wallet addCashAmountToCashWallet(Integer userId, Integer amount, String outTradeNo, String intro);

    Page<UserCashAccountRecord> getUserCashAccountRecordByUserIdAndType(Long userId, Integer type, Integer pageNo, Integer pageSize);

    GetPayJournalAccountResponse getPayJournalAccountFromUserCashAccountRecord(Long userId, Integer type, Integer pageNo, Integer pageSize);

    Page<UserCashAccountRecord> getUserCashAccountRecordByUserId(Long userId, Integer pageNo, Integer pageSize);

    GetPayJournalAccountResponse getAllPayJournalAccountFromUserCashAccountRecord(Long userId, Integer pageNo, Integer pageSize);
}
