package com.ifenghui.storybookapi.app.app.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.app.entity.Hotword;

import java.util.List;

/**
 * Created by wml on 2016/12/26.
 */
public class GetHotwordsResponse extends ApiResponse {
    List<Hotword> hotwords;

    public List<Hotword> getHotwords() {
        return hotwords;
    }

    public void setHotwords(List<Hotword> hotwords) {
        this.hotwords = hotwords;
    }
}
