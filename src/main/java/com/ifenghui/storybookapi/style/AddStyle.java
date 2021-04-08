package com.ifenghui.storybookapi.style;

/**
 * 增加减少类型
 */
public enum AddStyle {
    DEFAULT(0,"不变"),
    UP(1,"增加"),DOWN(2,"降低");
//    public static int STAR_INCREASE = 1;
//    public static int STAR_DECREASE = 2;
    int id;
    String name;
    AddStyle(int id, String name){
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

    public static AddStyle getById(int id){
        switch (""){}
        switch (id){
            case 1:
                return UP;
//                break;
            case 2:
                return DOWN;
//                break;
            case 0:
                return DEFAULT;

//                break;
            default:
                return null;
        }
    }
}
