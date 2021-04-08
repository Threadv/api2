package com.ifenghui.storybookapi.app.presale.service;


import com.ifenghui.storybookapi.app.presale.exception.PreSaleActivityNotTokenException;
import com.ifenghui.storybookapi.app.user.entity.UserToken;

import java.util.List;

public interface YiZhiActivityUserService {


    /**
     * 获取有效的userToken列表
     * @param userId
     * @return
     */
    List<UserToken> getValidUserTokenListByUserId(long userId);
    /**
     * 通过token获得id
     * @param token
     * @return
     */
    UserToken getUserIdByToken(String token);

    /**
     * 通过token获得id
     * @param token
     * @return
     * @throws PreSaleActivityNotTokenException
     */
    Integer checkAndGetCurrentUserId(String token)throws PreSaleActivityNotTokenException;
}
