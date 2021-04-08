package com.ifenghui.storybookapi.app.transaction.response;

import com.ifenghui.storybookapi.app.app.response.BaseResponse;

/**
 * @Date: 2018/11/13 16:00
 * @Description:
 */
public class GetAbilityPlanPriceResponse extends BaseResponse{

    AbilityPlanPrice abilityPlanPrice;

    Integer isBuyAbilityPlan;

    public Integer getIsBuyAbilityPlan() {
        return isBuyAbilityPlan;
    }

    public void setIsBuyAbilityPlan(Integer isBuyAbilityPlan) {
        this.isBuyAbilityPlan = isBuyAbilityPlan;
    }

    public AbilityPlanPrice getAbilityPlanPrice() {
        return abilityPlanPrice;
    }

    public void setAbilityPlanPrice(AbilityPlanPrice abilityPlanPrice) {
        this.abilityPlanPrice = abilityPlanPrice;
    }
}
