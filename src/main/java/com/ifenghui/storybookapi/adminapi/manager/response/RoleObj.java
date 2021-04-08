package com.ifenghui.storybookapi.adminapi.manager.response;

/**
 * Created by wslhk on 2018/8/26.
 * 以controller为粒度的权限管理
 */
public class RoleObj {
    String key;
    String name;
    Integer appId;
//    String intro;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }
//    public String getIntro() {
//        return intro;
//    }
//
//    public void setIntro(String intro) {
//        this.intro = intro;
//    }
}
