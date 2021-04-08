package com.ifenghui.storybookapi.style;

/**
 * 宝宝会读（优能计划）年龄
 * Created by wslhk on 2016/12/22.
 */
public enum AbilityPlanStyle {

    /**
     * 宝宝会读（优能计划） 购买类型
     */
    TWO_FOUR(1,"2-4岁"),
    FOUR_SIX(2,"4-6岁"),
    DEFAULT(0,"无");

    int id;
    String name;
//    int price;
//    int days;
    AbilityPlanStyle(int id, String name){
        this.id=id;
        this.name=name;
//        this.price=price;

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

//    public int getPrice() {
//        return price;
//    }
//
//    public void setPrice(int price) {
//        this.price = price;
//    }

//    public int getDays() {
//        return days;
//    }
//
//    public void setDays(int days) {
//        this.days = days;
//    }

    public static AbilityPlanStyle getById(int id) {
        for (AbilityPlanStyle abilityPlanStyle : AbilityPlanStyle.values()) {
            if (abilityPlanStyle.getId() == id) {
                return abilityPlanStyle;
            }
        }
        return null;
    }
}
