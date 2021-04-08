package com.ifenghui.storybookapi.style;

/**
 * 3方账号
 * Created by wslhk on 2016/12/22.
 */
public enum GroupTargetStyle {
    GROUP_MORE(1,"手动免费分组"),SERIAL_STORY(2,"故事系列"),SERIAL_MORE(11,"单行本更多专用"),AUTO(3,"自动分组-关联无效")
    ,GROUP_MORE2(4,"手动其他分组")
    ;

    int id;
    String name;
    GroupTargetStyle(int id, String name){
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
}
