package com.ifenghui.storybookapi.style;

public enum  ExpressStatusStyle {

    HAS_NO_DELIVERY(0,"未发货"),
    HAS_DELIVERY(1, "已发货");

    int id;
    String name;

    ExpressStatusStyle(int id, String name){
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
