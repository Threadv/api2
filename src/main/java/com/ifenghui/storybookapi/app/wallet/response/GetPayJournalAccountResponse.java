package com.ifenghui.storybookapi.app.wallet.response;

/**
 * Created by jia on 2017/1/9.
 */
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.wallet.entity.UserAccountRecord;
import com.ifenghui.storybookapi.app.wallet.entity.UserAccountRecordInterface;

import java.util.List;

public class GetPayJournalAccountResponse extends ApiPageResponse {
    List<UserAccountRecordInterface> userAccountRecords;

    public List<UserAccountRecordInterface> getUserAccountRecords() {
        return userAccountRecords;
    }

    public void setUserAccountRecords(List<UserAccountRecordInterface> userAccountRecords) {
        this.userAccountRecords = userAccountRecords;
    }
}
