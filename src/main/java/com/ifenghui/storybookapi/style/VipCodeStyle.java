package com.ifenghui.storybookapi.style;

/**
 * 兑换码类型
 */
public enum VipCodeStyle {

    /**
     * 1跳转书架订阅区
     * 2跳故事集区
     * 3跳转购买单本区
     * 4跳转到课程章节详情区
     * 5跳转至vip购买区域
     * 6跳转至宝宝会读（优能计划）购买区域
     */
    SUBSCRIBE(1,"跳转书架订阅区页"),
    SERIAL(2,"跳故事集区"),
    SINGLE_STORY(3,"跳转购买单本区"),
    LESSON(4,"跳转到课程章节详情区"),
    VIP(5,"跳转至vip购买区域"),
    ABILITY_PLAN_TWO_FOUR(6,"跳转至宝宝会读2-4购买区域"),
    ABILITY_PLAN_FOUR_SIX(7,"跳转至宝宝会读4-6购买区域"),
    ;

    int id;
    String name;
    VipCodeStyle(int id, String name){
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

    public static VipCodeStyle getById(int id){
        for(VipCodeStyle style: VipCodeStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
    }
}
