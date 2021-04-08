package com.ifenghui.storybookapi.style;

public enum WeekPlanLabelStyle {
    READ_TYPE(1,"阅读类型"),
    COGNITION_TYPE(2,"认知类型"),
    LITERACY_TYPE(3, "识字类型"),
    FIVE_AREA(4, "五大领域");

    int id;
    String name;

    WeekPlanLabelStyle(int id, String name) {
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

    public static WeekPlanLabelStyle getById(int id){
        for(WeekPlanLabelStyle style:WeekPlanLabelStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
    }
}
