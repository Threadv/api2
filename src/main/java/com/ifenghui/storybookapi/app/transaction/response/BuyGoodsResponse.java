package com.ifenghui.storybookapi.app.transaction.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.transaction.entity.goods.ExchangeRecord;

public class BuyGoodsResponse extends ApiResponse {

    ExchangeRecord exchangeRecord;

    public ExchangeRecord getExchangeRecord() {
        return exchangeRecord;
    }

    public void setExchangeRecord(ExchangeRecord exchangeRecord) {
        this.exchangeRecord = exchangeRecord;
    }

}
