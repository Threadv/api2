package com.ifenghui.storybookapi.app.user.response;

/**
 * Created by jia on 2017/1/9.
 */
import com.ifenghui.storybookapi.app.user.entity.UserAccount;

public class GetSettingUserAccountResponse  {

    UserAccount wx;
    UserAccount ali;
//    UserAccount huawei;

    public UserAccount getWx() {
        return wx;
    }

    public void setWx(UserAccount wx) {
        this.wx = wx;
    }

    public UserAccount getAli() {
        return ali;
    }

    public void setAli(UserAccount ali) {
        this.ali = ali;
    }

//    public UserAccount getHuawei() {
//        return huawei;
//    }
//
//    public void setHuawei(UserAccount huawei) {
//        this.huawei = huawei;
//    }
}
