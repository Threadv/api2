package com.ifenghui.storybookapi.style;

/**
 * 订单类型枚举
 * 同rechargestyle,需要同时修改！
 */
public enum  OrderStyle {

    /**
     * 订单类型
     */
    DEFAULT_ORDER(0, "默认-直接充值"),
    STORY_ORDER(1, "单本故事订单"),
    SUBSCRIPTION_ORDER(2, "订阅订单"),
    SERIAL_ORDER(3, "系列故事订单"),
    LESSON_ORDER(4, "课程订单"),
    ACTIVITY_GOODS_ORDER(5, "活动商品订单"),
    REFUND_CASH_RECHARGE(6,"返现充值"),
    VIP_ORDER(7, "VIP订单"),
    /**
     * 订单类型专用
     */
    STAR_SHOP_ORDER(8, "商城订单"),

    ABILITY_PLAN_ORDER(9, "宝宝会读订单");

    int id;

    String name;

    OrderStyle(int id, String name){
        this.id=id;
        this.name=name;
    }

    public static OrderStyle getById(int id){
        for(OrderStyle style:OrderStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
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
}
