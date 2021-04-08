package com.ifenghui.storybookapi.style;

public enum GoodsPriceStyle {

    ORDINARY_PRICE(1, "普通价格"),
    VIP_PRICE(2, "会员价格"),
    STAR_CASH_PRICE(3, "星星值加现金"),
    STAR_PRICE(4, "纯星星值价格"),
    VIP_STAR_CASH_PRICE(5, "会员星星加现金");

    int id;
    String name;

    GoodsPriceStyle(int id, String name){
        this.id=id;
        this.name=name;
    }

    public static GoodsPriceStyle getById(int id){
        switch (id){
            case 1:
                return ORDINARY_PRICE;
            case 2:
                return VIP_PRICE;
            case 3:
                return STAR_CASH_PRICE;
            case 4:
                return STAR_PRICE;
            case 5:
                return VIP_STAR_CASH_PRICE;
            default:
                return null;
        }
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
