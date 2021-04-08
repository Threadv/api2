package com.ifenghui.storybookapi.app.express.response;

/**
 * Created by jia on 2016/12/23.
 */
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterPhoneBind;

import java.util.List;

public class ExpressCenterPhoneBindResponse extends ApiPageResponse{
    ExpressCenterPhoneBind expressCenterPhoneBind;

    public ExpressCenterPhoneBind getExpressCenterPhoneBind() {
        return expressCenterPhoneBind;
    }

    public void setExpressCenterPhoneBind(ExpressCenterPhoneBind expressCenterPhoneBind) {
        this.expressCenterPhoneBind = expressCenterPhoneBind;
    }
}
