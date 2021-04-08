package com.ifenghui.storybookapi.style;

/**
 * 购买内容类型,同orderstyle
 */
public enum RechargeStyle {
    /**
     * 购买类型
     */
    RECHARGE(0,"直接充值")
    ,BUY_STORY(1,"购买故事")
    ,SUBSCRIPTION(2,"订阅")
    ,SERIAL(3,"系列")
    ,LESSON(4, "购买课程")
    ,ACTIVITY_GOODS(5, "活动商品")
    /**
     * 充值订单专用
     */
    ,REFUND_CASH_RECHARGE(6,"返现充值")
    ,BUY_SVIP(7, "购买vip")
    ,STAR_SHOP_ORDER(8, "商城订单")
    ,BUY_ABILITY_PLAN(9, "故事飞船优宝宝会读");

    int id;
    String name;
    RechargeStyle(int id, String name){
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


    public static RechargeStyle getById(int id){
        for(RechargeStyle style:RechargeStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
    }
}
