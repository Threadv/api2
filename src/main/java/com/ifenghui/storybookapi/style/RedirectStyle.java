package com.ifenghui.storybookapi.style;

public enum RedirectStyle {
  /* 1外链
    2单本
    3分组
    4故事集
    5文字
    6课程*/

    URL(1,"外链"), ONE_STORY(2,"单本"),GROUP(3,"分组"),STORYS(4,"故事集"),FONT(5,"文字"),CLASS(6,"课程");

    int id;
    String name;
    RedirectStyle(int id, String name){
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


    public static RedirectStyle getById(int id){
        switch (id){
            case 1:
                return URL;
            case 2:
                return ONE_STORY;
            case 3:
                return GROUP;
            case 4:
                return STORYS;
            case 5:
                return FONT;
            case 6:
                return CLASS;
            default:
                return null;
        }
    }


}
