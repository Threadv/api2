package com.ifenghui.storybookapi.app.app.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.app.entity.Ver;

/**
 * Created by wml on 2016/12/26.
 */
public class GetVerResponse extends ApiResponse {
    Ver ver;

    public Ver getVer() {
        return ver;
    }

    public void setVer(Ver ver) {
        this.ver = ver;
    }
}
