package com.ifenghui.storybookapi.style;

/**
 * 星星提示文案类型
 */
public enum StarContentStyle {

    /**
     * 星星提示文案
     */
    LISTEN_STORY(2,"每日任务:听故事"),
    SHOP_BUY(3,"购买商城商品"),
    CHECK_IN(6, "完成签到"),
    LESSON_BUY(7, "购买课程"),
    MAGAZINE_BUY(9, "购买《锋绘》杂志套装"),
    GAME(10, "完成益智训练"),
    SHARE(12, "分享推广海报"),
    READ_STORY(13, "阅读故事"),
    RECORD(14, "记录宝宝成长"),
    SERIES_BUY(16, "购买故事集"),
    CHANGE_SINGLE(17, "兑换单本故事"),
    TEM_COUPON_BUY(18, "购买10元代金券"),
   STORY_COUPON_BUY(19, "购买故事兑换券"),
    SEASON_COUPON_BUY(20, "购买季度兑换券"),
    FAIL_DOWN(21, "答错题目扣除星值"),
    CONTINUE_CHECK(23, "连续打卡奖励"),
    DAY_CHECK(24, "完成每日打卡"),
    SINGLE_STORY_BUY(29, "购买单本故事"),
    VIP_BUY(30, "购买VIP"),
    BUY_ABILITY_PLAN(31, "购买宝宝会读" ),
    SHARE_INVITE(32, "分享邀请" ),
    COLLECT_TOOL(33, "收集道具" ),
    SUBSCRIBE(34, "订阅获得" ),
    CHECK_DOWN(35, "每日打卡星星值扣除" ),
    SHOP_BACK(36, "商城取消订单返还" ),
    HUIBEN_READ(37, "绘本故事阅读" ),
    REGISTER(38, "注册获得" ),

    ;


    int id;
    String name;
    StarContentStyle(int id, String name){
        this.id=id;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static StarContentStyle getById(int id){
        for(StarContentStyle style: StarContentStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
    }
}
