package com.ifenghui.storybookapi.app.transaction.response;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;
import java.util.List;

/**
 * @Date: 2018/11/13 16:00
 * @Description:
 */
public class GetAbilityPlanPriceListResponse extends BaseResponse{

    List<AbilityPlanPrice> abilityPlanPriceList;

    Integer isBuyAbilityPlan;

    /**检测券用真实不需要随便赋值*/
    Integer targetValue;

    Integer weekPlanStyle;

    Integer iosIsCheck;

    public Integer getIosIsCheck() {
        return iosIsCheck;
    }

    public void setIosIsCheck(Integer iosIsCheck) {
        this.iosIsCheck = iosIsCheck;
    }

    public Integer getIsBuyAbilityPlan() {
        return isBuyAbilityPlan;
    }

    public void setIsBuyAbilityPlan(Integer isBuyAbilityPlan) {
        this.isBuyAbilityPlan = isBuyAbilityPlan;
    }

    public List<AbilityPlanPrice> getAbilityPlanPriceList() {
        return abilityPlanPriceList;
    }

    public void setAbilityPlanPriceList(List<AbilityPlanPrice> abilityPlanPriceList) {
        this.abilityPlanPriceList = abilityPlanPriceList;
    }

    public Integer getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(Integer targetValue) {
        this.targetValue = targetValue;
    }

    public Integer getWeekPlanStyle() {
        return weekPlanStyle;
    }

    public void setWeekPlanStyle(Integer weekPlanStyle) {
        this.weekPlanStyle = weekPlanStyle;
    }
}
