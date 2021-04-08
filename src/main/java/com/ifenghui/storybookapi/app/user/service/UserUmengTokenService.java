package com.ifenghui.storybookapi.app.user.service;

import com.ifenghui.storybookapi.app.user.entity.UserUmengToken;

public interface UserUmengTokenService {

    /**
     * 添加友盟token记录
     * @param userId
     * @param umengToken
     * @param userAgent
     * @param isSend
     * @return
     */
    UserUmengToken addUserUmengToken(Integer userId, String umengToken, String userAgent, Integer isSend);

    /**
     * 更新友盟token记录状态
     * @param userId
     * @param umengToken
     * @param userAgent
     * @param isSend
     * @return
     */
    UserUmengToken updateUserUmengToken(Integer userId, String umengToken, String userAgent, Integer isSend);
}
