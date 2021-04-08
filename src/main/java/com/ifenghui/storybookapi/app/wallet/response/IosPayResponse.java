package com.ifenghui.storybookapi.app.wallet.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.api.response.base.ApiStatusResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/5.
 */
public class IosPayResponse extends ApiResponse {
    String code;
    Integer hasSendCoupon;
    List<String> successTransactionIds;
    List<String> errorTransactionIds;
    public IosPayResponse(){
        successTransactionIds=new ArrayList<>();
        errorTransactionIds=new ArrayList<>();
    }
    public String getCode() {
        return super.getStatus().getCode()+"";
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getSuccessTransactionIds() {
        return successTransactionIds;
    }

    public void setSuccessTransactionIds(List<String> successTransactionIds) {
        this.successTransactionIds = successTransactionIds;
    }

    public List<String> getErrorTransactionIds() {
        return errorTransactionIds;
    }

    public void setErrorTransactionIds(List<String> errorTransactionIds) {
        this.errorTransactionIds = errorTransactionIds;
    }

    public Integer getHasSendCoupon() {
        return hasSendCoupon;
    }

    public void setHasSendCoupon(Integer hasSendCoupon) {
        this.hasSendCoupon = hasSendCoupon;
    }
}
