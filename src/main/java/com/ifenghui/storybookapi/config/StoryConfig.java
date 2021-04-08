package com.ifenghui.storybookapi.config;

public class StoryConfig {

    /******************************* story_buy_story_record  类型 type 的配置文件  *************************************/

    /**
     * 单本购买类型
     */
    public static int BUY_STORY_RECORD_ONE_STORY = 1;

    /**
     * 订阅类型
     */
    public static int BUY_STORY_RECORD_SUBSCRIPTION = 2;

    /**
     * 积分兑换类型
     */
    public static int BUY_STORY_RECORD_STAR_CHANGE = 3;

    /**
     * 活动赠送类型
     */
    public static int BUY_STORY_RECORD_ACTIVITY_ADD = 4;

    /**
     * 故事集购买类型
     */
    public static int BUY_STORY_RECORD_STORY_SERIAL = 5;

    /**
     * 兑换码购买类型
     */
    public static int BUY_STORY_RECORD_STORY_VIP_CODE = 6;

    /**
     * 新用户赠送当期故事类型
     */
    public static int BUY_STORY_RECORD_NEW_USER = 7;

    /******************************************************************/
//    static public IOSPrice iosPrice;


    public enum Platfrom{ANDROID,IOS,OTHER}

    //当前请求的平台类型
    public static Platfrom platform;
}
