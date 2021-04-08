package com.ifenghui.storybookapi.app.wallet.response;

/**
 * Created by jia on 2017/1/9.
 */
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.wallet.entity.PayRechargePrice;

import java.util.List;

public class GetPayRechargePriceResponse extends ApiResponse {
    List<PayRechargePrice> payRechargePrices;

    Integer isCheck;

    public Integer getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }

    public List<PayRechargePrice> getPayRechargePrices() {
        return payRechargePrices;
    }

    public void setPayRechargePrices(List<PayRechargePrice> payRechargePrices) {
        this.payRechargePrices = payRechargePrices;
    }

}
