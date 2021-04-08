package com.ifenghui.storybookapi.app.app.response;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.app.entity.AppNavButton;

import java.util.List;

public class GetAppNavButtonListResponse extends ApiResponse {

    List<AppNavButton> appNavButtonList;

    String bgIcon;

    Integer isShowBg;

    public List<AppNavButton> getAppNavButtonList() {
        return appNavButtonList;
    }

    public void setAppNavButtonList(List<AppNavButton> appNavButtonList) {
        this.appNavButtonList = appNavButtonList;
    }

    public String getBgIcon() {
        return bgIcon;
    }

    public void setBgIcon(String bgIcon) {
        this.bgIcon = bgIcon;
    }

    public Integer getIsShowBg() {
        return isShowBg;
    }

    public void setIsShowBg(Integer isShowBg) {
        this.isShowBg = isShowBg;
    }
}
