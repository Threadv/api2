package com.ifenghui.storybookapi.style;

/**
 * 3方账号
 * Created by wslhk on 2016/12/22.
 */
public enum AdTargetStyle {
    URL(1,"链接"),SINGLE_STORY(2,"单本故事"),GROUP(3,"分组"),SERIAL_STORY(4,"故事集");

    int id;
    String name;
    AdTargetStyle(int id, String name){
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
