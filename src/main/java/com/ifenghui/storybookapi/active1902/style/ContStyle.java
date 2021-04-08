package com.ifenghui.storybookapi.active1902.style;

/**
 * @Date: 2019/2/20 13:46
 * @Description:
 */
public enum ContStyle {

    /**
     * 统计类型
     */
    LOGIN(1, "用户参与"),
    STORY(2, "领取故事"),
    READ_STORY(3, "阅读故事"),
    FINISH(4, "完成问答"),
    SERIAL_STORY(5, "领取故事集"),
    READ_SERIAL(6, "阅读故事集"),

    ;

    int id;
    String name;

    ContStyle(int id,String name){
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

    public static ContStyle getById(int id) {
        for (ContStyle style : ContStyle.values()) {
            if (style.getId() == id) {
                return style;
            }
        }
        return null;
    }


}
