package com.ifenghui.storybookapi.config;

public class ExceptionCodeConfig {

    /**
     * 用户有效token数量超出限制
     */
    public static Integer USER_TOKEN_BEYOND_LIMIT_STATUS = 214;
    public static String USER_TOKEN_BEYOND_LIMIT_MSG = "登录设备数量超出最大限制！请进行手机号验证";

    /**
     * 用户有效token数量超出限制时 第三方登录未绑定电话号码
     */
    public static Integer USER_TOKEN_LOGIN_NOT_BIND_PHONE = 215;
    public static String USER_TOKEN_LOGIN_NOT_BIND_PHONE_MSG = "登录设备数量超出最大限制！请绑定手机号验证";
}
