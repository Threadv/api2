package com.ifenghui.storybookapi.app.express.response;

/**
 * Created by jia on 2016/12/23.
 */
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterOrder;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterPhoneBind;

import java.util.List;

public class ExpressCenterPhoneBindsResponse extends ApiPageResponse{
    List<ExpressCenterPhoneBind> expressCenterPhoneBinds;

    public List<ExpressCenterPhoneBind> getExpressCenterPhoneBinds() {
        return expressCenterPhoneBinds;
    }

    public void setExpressCenterPhoneBinds(List<ExpressCenterPhoneBind> expressCenterPhoneBinds) {
        this.expressCenterPhoneBinds = expressCenterPhoneBinds;
    }
}
