package com.ifenghui.storybookapi.app.user.service;

import com.ifenghui.storybookapi.app.user.entity.UserRelate;

public interface UserRelateService {
    UserRelate getUserRelateByPhone(String phone);

    UserRelate getUserRelateByUnionid(String unionid);
}
