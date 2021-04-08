package com.ifenghui.storybookapi.style;

/**
 * 3方账号
 * Created by wslhk on 2016/12/22.
 */
public enum OrderProductStyle {
    PRODUCT_VIPCARD(1,"Vip卡/订阅"),PRODUCT_SINGLE_STORY(2,"单本购买"),PRODUCT_SERIAL(2,"单本购买");

    int id;
    String name;
    OrderProductStyle(int id, String name){
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
