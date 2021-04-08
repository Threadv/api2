package com.ifenghui.storybookapi.app.express.response;

/**
 * Created by jia on 2016/12/23.
 */
import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterMag;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterTrack;

import java.util.List;

public class ExpressCenterMagsResponse extends ApiPageResponse{
    List<ExpressCenterMag> expressCenterMags;

    public List<ExpressCenterMag> getExpressCenterMags() {
        return expressCenterMags;
    }

    public void setExpressCenterMags(List<ExpressCenterMag> expressCenterMags) {
        this.expressCenterMags = expressCenterMags;
    }
}
