package com.ifenghui.storybookapi.adminapi.controlleradmin.resp;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.AbilityPlanOrder;

import java.util.List;

public class AbilityPlanOrdersResponse extends ApiPageResponse{
    List<AbilityPlanOrder> abilityPlanOrders;

    public List<AbilityPlanOrder> getAbilityPlanOrders() {
        return abilityPlanOrders;
    }

    public void setAbilityPlanOrders(List<AbilityPlanOrder> abilityPlanOrders) {
        this.abilityPlanOrders = abilityPlanOrders;
    }
}
