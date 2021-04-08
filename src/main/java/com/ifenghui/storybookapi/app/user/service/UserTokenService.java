package com.ifenghui.storybookapi.app.user.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.entity.UserToken;
import com.ifenghui.storybookapi.app.user.response.AlipayAuthCodeGetUserInfoResponse;

import java.text.ParseException;
import java.util.List;

public interface UserTokenService {

    /**
     * 验证用户token有效性
     * @param token
     * @return
     */
    boolean checkValidUserToken(String token);

    public UserToken findOneByToken(String token);

    /**
     * //如果token状态错误返回0，否则返回用户id
     * @param token
     * @return
     */
    public Integer getUserIdByTokenAndCheck(String token);

    /**
     * 获取 V.1.6.3 版本有效的userToken列表
     * @param userId
     * @return
     */
    List<UserToken> getValidUserTokenListByUserId(long userId);

    /**
     * 增加或者编辑用户token
     * @param deviceUnique
     * @param userAgent
     * @param user
     * @param deviceName
     * @return
     */
    UserToken addOrEditUserToken(String device, String deviceUnique, String userAgent, User user, String deviceName);

    /**
     * 设置token失效
     * @param userTokenId
     * @param userId
     * @return
     */
    UserToken setUnValidUserToken(long userTokenId, long userId);

    /**
     * 旧版本生成token
     * @param userId
     * @param userAgent
     * @param device
     * @return
     */
    UserToken oldAddUserToken(Integer userId, String userAgent, String device);

    public String getAlipayUserAuthGrantString() throws java.io.UnsupportedEncodingException;

    /**
     * 获取阿里第三方授权
     * @param authCode
     * @param userId
     * @return
     * @throws AlipayApiException
     */
    public AlipayAuthCodeGetUserInfoResponse getAlipayUserInfoFromAuthCode(String authCode, String userId) throws AlipayApiException;

    public AlipayAuthCodeGetUserInfoResponse getApipayUserInfoFromAccessToken(String accessToken, String userId) throws AlipayApiException;

    public AlipayClient getAlipayClient();

    /**
     * 补数据
     * @throws ParseException
     */
    void setUserSvipToSubscriptionUser() throws ParseException;
}
