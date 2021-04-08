package com.ifenghui.storybookapi.app.app.response;

import com.ifenghui.storybookapi.app.app.entity.AppNavButton;

import java.util.List;

public class AppNavContainStyle {

    List<AppNavButton> appNavButtonList;

    Integer style;

    String bgIcon;

    Integer isShowBg;

    public AppNavContainStyle(List<AppNavButton> appNavButtonList){
        if(appNavButtonList != null && appNavButtonList.size() == 4) {
            this.appNavButtonList = appNavButtonList;
            if (appNavButtonList.get(0).getBgIcon() != null && !appNavButtonList.get(0).getBgIcon().equals("")){
                this.bgIcon = appNavButtonList.get(0).getBgIconUrl();
                this.isShowBg = 1;
            } else {
                this.isShowBg = 0;
                this.bgIcon = null;
            }
            this.style = appNavButtonList.get(0).getStyle();
        }
    }

    public List<AppNavButton> getAppNavButtonList() {
        return appNavButtonList;
    }

    public void setAppNavButtonList(List<AppNavButton> appNavButtonList) {
        this.appNavButtonList = appNavButtonList;
    }

    public Integer getStyle() {
        return style;
    }

    public void setStyle(Integer style) {
        this.style = style;
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
