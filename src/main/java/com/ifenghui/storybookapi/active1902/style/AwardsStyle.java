package com.ifenghui.storybookapi.active1902.style;

/**
 * @Date: 2019/2/19 14:54
 * @Description:
 */
public enum AwardsStyle {

    /**
     * 奖励类型
     */
    STORY(1, "故事"),
    SERIAL_STORY(2, "故事集");

    int id;
    String name;

    AwardsStyle(int id,String name){
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

    public static AwardsStyle getById(int id) {
        for (AwardsStyle style : AwardsStyle.values()) {
            if (style.getId() == id) {
                return style;
            }
        }
        return null;
    }
}
