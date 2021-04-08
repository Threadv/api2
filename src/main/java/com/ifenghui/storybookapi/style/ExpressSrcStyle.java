package com.ifenghui.storybookapi.style;

/**
 * 增加减少类型
 */
public enum ExpressSrcStyle {
    DEFAULT(0,"无"),
    APP(1,"app中")
    ,APP_SHOP(2,"app商城")
    ,WX_FUWUHAO(3,"服务号-分销")
    ,WX_WEIDIAN(4,"微信微店")
    ,TUANGOU(5,"三方团购/自定义主id")
    ,SALE_ACTIVITY(6,"微信-售卖活动")
    ;
//    public static int STAR_INCREASE = 1;
//    public static int STAR_DECREASE = 2;
    int id;
    String name;
    ExpressSrcStyle(int id, String name){
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

    public static ExpressSrcStyle getById(int id){
        for(ExpressSrcStyle style:ExpressSrcStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
    }
}
