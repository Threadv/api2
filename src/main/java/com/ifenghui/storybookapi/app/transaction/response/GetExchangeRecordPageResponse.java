package com.ifenghui.storybookapi.app.transaction.response;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.transaction.entity.goods.ExchangeRecord;

import java.util.List;

public class GetExchangeRecordPageResponse extends ApiPageResponse {

    List<ExchangeRecord> exchangeRecordList;

    public List<ExchangeRecord> getExchangeRecordList() {
        return exchangeRecordList;
    }

    public void setExchangeRecordList(List<ExchangeRecord> exchangeRecordList) {
        this.exchangeRecordList = exchangeRecordList;
    }
}