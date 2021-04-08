package com.ifenghui.storybookapi.app.presale.response;



import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.presale.entity.PreSaleUser;

import java.util.List;

public class PreSaleUsersResponse extends ApiPageResponse {

    List<PreSaleUser> preSaleUsers;

    public List<PreSaleUser> getPreSaleUsers() {
        return preSaleUsers;
    }

    public void setPreSaleUsers(List<PreSaleUser> preSaleUsers) {
        this.preSaleUsers = preSaleUsers;
    }
}
