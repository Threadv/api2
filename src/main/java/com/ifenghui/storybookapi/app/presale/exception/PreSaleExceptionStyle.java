package com.ifenghui.storybookapi.app.presale.exception;

public enum PreSaleExceptionStyle {
    ALREADY_EXISTS_OLD_EXCEPTION(2,"早期的状态，已经领取"),
    NOT_USER_EXCEPTION(3,"没有这个用户"),
    PASSWORD_EXCEPTION(4,"密码错误"),
    NOT_USER_BY_FORGET_EXCEPTION(8,"没有这个用户"),//没有这个用户用于找回密码
    ALREADY_EXISTS_USER_EXCEPTION(13,"这个用户已存在"),


    VIP_CODE_EXCEPTION(208,"code激活异常")
//    ,VIP_CODE_ACTIVE_EXCEPTION(208,"code激活异常")
    ,INDEX_OUT_OF_BOUNDS_EXCEPTION(207,"越界异常")//适用于购物车越界，
    ,COUPON_EXCEPTION(210,"优惠券过期")
//    ,COUPON_NOT_ENOUGH_EXCEPTION(210,"优惠券不足")
    ,COUPON_DUPLICATE_EXCEPTION(202,"数据重复")
    ,PARAMS_EXCEPTION(23,"提交参数格式错误")
    ,NOT_FOUND_USER(203,"没有这个用户")
    ,NOT_ENOUGH_MONEY_EXCEPTION(204,"没有足够的余额")
    ,NOT_PERMISSION_EXCEPTION(206,"无权限操作")
    ,ALREADY_EXISTS_EXCEPTION(205,"已经存在")//也可以用COUPON_DUPLICATE_EXCEPTION 202
    ,NOT_FOUND_EXCEPTION(201,"没有找到数据")
    ,NOT_BIND_PHONE (215,"未绑定电话号码")//示用于token数量限制验证
    ,USER_TOKEN_OUT_OF_BOUNDS_EXCEPTION(214,"用户设备超过限制")
//    ,NOT_BIND_PHONE(215,"用户设备超过限制")
    ,NOT_CURRENT_USER(212,"不是当前用户，没有权限操作")
    ,ORDER_DUPLICATE_EXCEPTION(213,"重复的订单号")
    ,VERSION_EXCEPTION(220,"版本低，请升级最新版本");


    int code;
    String msg;

    PreSaleExceptionStyle(int code, String msg){
        this.code=code;
        this.msg=msg;
    }
}
