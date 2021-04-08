package com.ifenghui.storybookapi.style;

/**
 * @Date: 2018/11/26 17:09
 * @Description:
 */
public enum SaleStyle {

    /**
     *
     */
    SALE(1,"后台创建用于销售"),
    GIVE(2,"后台创建用于赠送"),
    APP(3,"app生成"),
    WX_SERVICE(4,"服务号生成");

    int id;
    String name;

    SaleStyle(int id,String name){
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
    public static SaleStyle getById(int id) {
        for (SaleStyle saleStyle : SaleStyle.values()) {
            if (saleStyle.getId() == id) {
                return saleStyle;
            }
        }
        return null;
    }

}
