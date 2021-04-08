package com.ifenghui.storybookapi.app.user.service;

/**
 * Created by jia on 2016/12/28.
 */

import com.ifenghui.storybookapi.app.user.entity.UserAccount;

public interface UserAccountService {
    /**
     * 根据用户id 和 三方信息 获取用户三方关联数据
     * @param userId
     * @param srcType
     * @return
     */
    UserAccount getUserAccountByUserIdAndSrcType(Long userId, Integer srcType);

    UserAccount getUserAccountBySrcId(String srcId);
}
