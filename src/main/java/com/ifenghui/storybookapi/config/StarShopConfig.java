package com.ifenghui.storybookapi.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class StarShopConfig {
    public static int BUY_TYPE_REAL_BOOK = 0;
    public static int BUY_TYPE_COUPON = 1;
    public static int BUY_TYPE_VIP_CODE = 2;
    public static int BUY_TYPE_COUPON_DEFERRED = 3;
    public static int BUY_TYPE_UNREAL_BOOK = 4;
    public static int BUY_TYPE_STORY_COUPON = 5;

    public static int STAR_INCREASE = 1;
    public static int STAR_DECREASE = 2;

}
