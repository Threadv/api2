package com.ifenghui.storybookapi.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ShopConfig {

    //实体物品
    public static int BUY_TYPE_REAL_GOODS = 0;
    //代金券
    public static int BUY_TYPE_COUPON = 1;
    //兑换码
    public static int BUY_TYPE_CODE = 2;
    //赠阅券
    public static int BUY_TYPE_COUPON_DEFERRED = 3;
    //单本故事赠送
    public static int BUY_TYPE_UNREAL_BOOK = 4;
    //故事兑换码码
    public static int BUY_TYPE_STORY_COUPON = 5;


}
