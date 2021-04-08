package com.ifenghui.storybookapi.app.express.response;

/**
 * Created by jia on 2016/12/23.
 */
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterMag;

import java.util.List;

public class ExpressCenterMagResponse extends ApiPageResponse{
    ExpressCenterMag expressCenterMag;

    public ExpressCenterMag getExpressCenterMag() {
        return expressCenterMag;
    }

    public void setExpressCenterMag(ExpressCenterMag expressCenterMag) {
        this.expressCenterMag = expressCenterMag;
    }
}
