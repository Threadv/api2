package com.ifenghui.storybookapi.style;

/**
 * 充值类型
 */
public enum StarRechargeStyle {

    BOOK(0,"实体图书"),
    COUPON(1,"代金券"),
    VIPCODE(2,"兑换码"),
    COUPON_TIME(3,"赠阅码"),
    GIVE_STORY(4,"送故事"),
    STORY_COUPON(5, "故事兑换券"),
    READ_STORY(11,"阅读故事"),
    READ_AUDIO(12,"听音频"),
    READ_COUNT(13,"累计阅读"),
    REG(14,"注册"),
    SHARE(15,"分享邀请"),
    PART(16,"碎片收集"),
    SUBSCRIPTION(17,"订阅"),
    BUYSTORY(18,"故事购买"),
    SIGNIN_SOME_DAY(19,"连续打卡"),

    SIGNIN_RESET(20,"补打卡星星值扣除"),
    BUY_SERIAL(21,"故事集/系列购买"),
    BUY_LESSON(22, "课程购买"),
    BUY_VIP(23, "购买vip" ),

    SHARE_PASTER(31,"分享海报"),

    READ_STORY2(41,"阅读故事"),
    READ_AUDIO2(42,"音频"),
    READ_GAME(43,"游戏"),
    READ_PAGESTORY(44,"翻页故事"),
    READ_LESSON(45,"识字课"),
    READ_ART(46,"艺术"),
    READ_ZHAOBUTONG(47,"着不同"),
    READ_TUPIANRENZHI(48,"图片认知"),

    GROWTH_DAIRY(51,"每日成长记录/一天一次"),

    CHECK_IN(61,"每日签到/一天一次"),


    STAR_SHOP_BUY_GOODS(71,"星星值商城购买"),
    STAR_SHOP_BACK(72,"星星值商城退还"),

    WEEK_PLAN_FINISH(81, "完成周任务奖励"),

    BUY_ABILITY_PLAN(82, "购买宝宝会读" ),;


    int id;
    String name;
    StarRechargeStyle(int id, String name){
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


    public static StarRechargeStyle getById(int id){
        for(StarRechargeStyle style:StarRechargeStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
    }
}
