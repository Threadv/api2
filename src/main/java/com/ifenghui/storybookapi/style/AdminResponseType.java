package com.ifenghui.storybookapi.style;

public enum AdminResponseType {
    SUCCESS(1,"success"),
    ERROR(0,"错误"),
    IOS_PAY_ERROR(201,"auth error"),
    AUTH_ERR(301,"auth error"),//没有登录
    AUTH_LOW(302,"auth low"),//权限低
    AUTH_USER_TOKEN_ERR(303,"user auth error"),//没有登录
    DUPLICATE(211,"duplicate error"),
    ERROR_USER(212, "用户信息有误！"),
    PARAM_CHECK_ERR(213,"数据验证错误"),//手机号验证错误,
    NOT_SUPPROT_ARTICLE_TYPE(214,"不支持这个文章类型"),//手机号验证错误,
    HAS_USER(401, "这个电话号码已被注册！"),
    ORDER_NOT_FOUND(601, "订单不存在"),
    ORDER_HAS_FAIL(603, "订单状态有误"),
    NOT_ENOUGH_MONEY(604, "余额不足"),
    NOTIFY_ERROR(605, "回调信息有误！"),
    IOS_NOTIFY_FAIL(701, "ios回调错误"),
    IOS_NOTIFY_SUCCESS(1, "ios回调成功"),
    FAVORITE_ERROR(801, "收藏失败！"),
    CONTENT_ERROR(901, "内容包含敏感词汇"),
    NAME_ERROR(902, "昵称包含敏感词汇")
    ;

    AdminResponseType(int code, String msg){
        this.code=code;
        this.msg=msg;
    }
    int code;
    String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static AdminResponseType getById(int id){
        for(AdminResponseType tagType:AdminResponseType.values()){
            if(tagType.getCode()==id){
                return tagType;
            }
        }
        return null;
    }
}
