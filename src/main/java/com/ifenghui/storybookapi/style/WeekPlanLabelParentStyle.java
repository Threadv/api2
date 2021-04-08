package com.ifenghui.storybookapi.style;

public enum WeekPlanLabelParentStyle {
    FIRST(1, "抽象认知"),
    SECOND(2, "生活习惯"),
    THIRD(3, "生活认知"),
    FOURTH(4, "自然科普");

    int id;
    String name;

    WeekPlanLabelParentStyle(int id, String name) {
        this.id = id;
        this.name = name;
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

    public static WeekPlanLabelParentStyle getById(int id){
        for(WeekPlanLabelParentStyle style:WeekPlanLabelParentStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
    }
}
