package com.ifenghui.storybookapi.app.presale.response;



import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleGift;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.SubscriptionRecord;

public class SubscribeResponse extends ApiPageResponse {


    PreSaleGift gift;

    PreSaleCode code;

    SubscriptionRecord subscriptionRecord;

    public PreSaleGift getGift() {
        return gift;
    }

    public void setGift(PreSaleGift gift) {
        this.gift = gift;
    }

    public PreSaleCode getCode() {
        return code;
    }

    public void setCode(PreSaleCode code) {
        this.code = code;
    }

    public SubscriptionRecord getSubscriptionRecord() {
        return subscriptionRecord;
    }

    public void setSubscriptionRecord(SubscriptionRecord subscriptionRecord) {
        this.subscriptionRecord = subscriptionRecord;
    }
}
