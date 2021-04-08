package com.ifenghui.storybookapi.style;

/**
 * Created by wslhk on 2016/12/22.
 */
public enum GoodsCategoryType {
    CARE_CHOOSE(1,"精选商品"),STAR(2,"星值专区");

    int id;
    String name;
    GoodsCategoryType(int id, String name){
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

    public static GoodsCategoryType getById(int id){
        switch (id){
            case 1:
                return CARE_CHOOSE;
            case 2:
                return STAR;
            default:
                return null;
        }
    }

}
