package com.ifenghui.storybookapi.app.presale.service;

import com.ifenghui.storybookapi.config.MyEnv;

public class ShareGetMagazine {

    String title;

    String shareUrl;

    public String getTitle() {
        return "体验阅读课送杂志";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareUrl() {
        return MyEnv.env.getProperty("sale.activity.url") + "share20180709/index/index.html";
    }

}
