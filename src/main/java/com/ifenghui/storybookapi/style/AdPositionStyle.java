package com.ifenghui.storybookapi.style;

/**
 * 3方账号
 * Created by wslhk on 2016/12/22.
 */
public enum AdPositionStyle {
    INDEX(1,"首页"),LOADING(2,"闪屏"),SHOP(3,"商店");

    int id;
    String name;
    AdPositionStyle(int id, String name){
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

    public static AdPositionStyle getById(int id){
        for(AdPositionStyle style:AdPositionStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
    }
}
