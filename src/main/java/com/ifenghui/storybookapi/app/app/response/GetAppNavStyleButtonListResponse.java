package com.ifenghui.storybookapi.app.app.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;

import java.util.List;

public class GetAppNavStyleButtonListResponse extends ApiResponse {

    List<AppNavContainStyle> appNavContainStyleList;

    public List<AppNavContainStyle> getAppNavContainStyleList() {
        return appNavContainStyleList;
    }

    public void setAppNavContainStyleList(List<AppNavContainStyle> appNavContainStyleList) {
        this.appNavContainStyleList = appNavContainStyleList;
    }
}
