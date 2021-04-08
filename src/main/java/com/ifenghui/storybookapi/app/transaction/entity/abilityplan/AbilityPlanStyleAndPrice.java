package com.ifenghui.storybookapi.app.transaction.entity.abilityplan;

import com.ifenghui.storybookapi.style.AbilityPlanCodeStyle;

/**
 * @Date: 2018/12/5 12:43
 * @Description:
 */
public class AbilityPlanStyleAndPrice {

    AbilityPlanCodeStyle abilityPlanCodeStyle;

    Integer priceId;

    public AbilityPlanCodeStyle getAbilityPlanCodeStyle() {
        return abilityPlanCodeStyle;
    }

    public void setAbilityPlanCodeStyle(AbilityPlanCodeStyle abilityPlanCodeStyle) {
        this.abilityPlanCodeStyle = abilityPlanCodeStyle;
    }

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }
}
