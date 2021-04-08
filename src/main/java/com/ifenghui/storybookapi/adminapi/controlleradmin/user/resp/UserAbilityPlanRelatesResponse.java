package com.ifenghui.storybookapi.adminapi.controlleradmin.user.resp;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.UserAbilityPlanRelate;

import java.util.List;

public class UserAbilityPlanRelatesResponse extends ApiPageResponse {
    List<UserAbilityPlanRelate> userAbilityPlanRelates;

    public List<UserAbilityPlanRelate> getUserAbilityPlanRelates() {
        return userAbilityPlanRelates;
    }

    public void setUserAbilityPlanRelates(List<UserAbilityPlanRelate> userAbilityPlanRelates) {
        this.userAbilityPlanRelates = userAbilityPlanRelates;
    }
}
