package com.ifenghui.storybookapi.config;

import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;

import java.util.Locale;

/**
 * Created by wml on 2017/11/08.
 */

public class StarConfig {
    //新手任务
    public static int STAR_REGISTER = 10;//注册
    public static int STAR_SHARE_INVITE = 10;//分享邀请


    public static int STAR_TASK_READ = 5;//每日任务，阅读故事
    public static int STAR_TASK_LISTEN = 5;//每日任务，听故事

    public static int STAR_READ_TEN = 10;//累计阅读10本
    public static int STAR_READ_TWENTY  = 20;//累计阅读20本
    public static int STAR_READ_FIFTY = 50;//累计阅读50本
    public static int STAR_READ_HUNDRED = 100;//累计阅读100本

    public static int STAR_STORY_COLLECT = 10;//故事元素收集完成

    public static int STAR_USR_BUY = 1;//购买和订阅（价格*此数）【正式服1测试服100】

    /**
     * 完成每日打卡任务
     */
    public static int STAR_FINISH_SCHEDULE = 10;

    /**
     * 答题错误消耗星星值
     */
    public static int STAR_ERROR_QUESTION_ANSWER = 5;

    /**
     * 补打卡消耗星星值
     */
    public static int STAR_OFFSET_SCHEDULE = 10;

    //积分消耗
    public static int STAR_EXCHANGE_COLLECT = 10;//故事元素收集


    /************************************************ v 1.70 ******************************/
    /**
     * 全年订阅星星数量
     */
    public static int STAR_YEAR_SUBSCRIPTION = 268;
    public static String STAR_YEAR_SUBSCRIPTION_NAME = "阅读专区购买（年）";

    /**
     * 半年订阅星星数量
     */
    public static int STAR_HALFYEAR_SUBSCRIPTION = 158;
    public static String STAR_HALFYEAR_SUBSCRIPTION_NAME = "阅读专区购买（半年）";

    /**
     * 季度订阅星星数量
     */
    public static int STAR_QUARTER_SUBSCRIPTION = 98;
    public static String STAR_QUARTER_SUBSCRIPTION_NAME = "阅读专区购买（季度）";

    /**
     * 一个月订阅星星数量
     */
    public static int STAR_ONE_MONTH_SUBSCRIPTION = 28;
    public static String STAR_ONE_MONTH_SUBSCRIPTION_NAME = "阅读专区购买（月）";

    /**
     * 一周订阅星星数量
     */
    public static int STAR_ONE_WEEK_SUBSCRIPTION = 8;
    public static String STAR_ONE_WEEK_SUBSCRIPTION_NAME = "阅读专区购买（当期）";

    /**
     * 6元单本星星配置
     */
    public static int STAR_SIX_STORY = 6;
    public static String STAR_SIX_STORY_NAME = "故事购买（6元）";

    /**
     * 3元单本星星配置
     */
    public static int STAR_THREE_STORY = 3;
    public static String STAR_THREE_STORY_NAME = "故事购买（3元）";

    /**
     * 一元单本星星配置
     */
    public static int STAR_ONE_STORY = 1;
    public static String STAR_ONE_STORY_NAME = "故事购买（1元）";

    /**
     * 完善信息星星值
     */
    public static int STAR_FINISH_USER_INFO = 10;
    public static String STAR_FINISH_USER_INFO_NAME = "注册完善信息";

    /**
     * 分享海报星星值
     */
    public static int STAR_PASTER_SHARE = 10;
    public static String STAR_PASTER_SHARE_NAME = "分享故事飞船推广海报";
    public static int STAR_PASTER_SHARE_BUY_TYPE = 31;


    /**
     * 绘本故事阅读
     */
    public static int STAR_READ_STORY = 10;
    public static String STAR_READ_STORY_NAME = "绘本故事阅读";

    /**
     * 益智游戏
     */
    public static int STAR_READ_SMART_GAME = 5;
    public static String STAR_READ_SMART_GAME_NAME = "益智游戏";

    /**
     * 每日成长记录
     */
    public static int STAR_GROWTH_DAIRY = 5;
    public static String STAR_GROWTH_DAIRY_NAME = "每日成长记录";
    public static int STAR_GROWTH_DAIRY_BUY_TYPE = 51;

    public static int WEEK_PLAN_FINISH = 20;


}
