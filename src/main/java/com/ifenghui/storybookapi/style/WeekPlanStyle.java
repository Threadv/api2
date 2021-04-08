package com.ifenghui.storybookapi.style;

public enum WeekPlanStyle {
    TWO_FOUR(1,"2到4岁"),
    FOUR_SIX(2,"4到6岁"),
    ALL_PLAN(3, "全部"),
    DEFAULT(0, "没有参加");

    int id;
    String name;

    WeekPlanStyle(int id, String name){
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

    public static WeekPlanStyle getById(int id){
        for(WeekPlanStyle style:WeekPlanStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
    }
}
