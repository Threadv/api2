package com.ifenghui.storybookapi.app.transaction.response;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;

/**
 * @Date: 2018/11/13 16:00
 * @Description:
 */
public class GetAbilityPlanPrice211Response extends BaseResponse{


   Integer price;
   String iosSubPrice;
    /**检测券用真实不需要随便赋值*/
    Integer targetValue;

    //信息
    String information;

    Integer onlineOnly;


    public String getIosSubPrice() {
        return iosSubPrice;
    }

    public void setIosSubPrice(String iosSubPrice) {
        this.iosSubPrice = iosSubPrice;
    }

    public Integer getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(Integer targetValue) {
        this.targetValue = targetValue;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Integer getOnlineOnly() {
        return onlineOnly;
    }

    public void setOnlineOnly(Integer onlineOnly) {
        this.onlineOnly = onlineOnly;
    }
}
