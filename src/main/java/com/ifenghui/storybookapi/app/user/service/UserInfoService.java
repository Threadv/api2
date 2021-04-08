package com.ifenghui.storybookapi.app.user.service;

import com.ifenghui.storybookapi.app.user.entity.UserInfo;

/**
 * Created by wml on 2017/12/26.
 */
public interface UserInfoService {


    Void addUserInfo(Long userId,String name, String phone,String addr);

    UserInfo getUserInfo(Long userId);

}
