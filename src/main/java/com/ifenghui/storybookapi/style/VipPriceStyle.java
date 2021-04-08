package com.ifenghui.storybookapi.style;

public enum  VipPriceStyle {
    /**
     * sVIP类型 宝宝会读（优能计划）
     */
    DEFAULT_NULL(0, "默认无", 0, 0, 0, SvipStyle.DEFAULT_VIP, ""),
    YEAR_VIP(1, "全年", 366, 79800, 28, SvipStyle.LEVEL_THREE, "subscription_auto_a_year"),
    HALF_YEAR_VIP(2, "半年", 188, 42800, 29, SvipStyle.LEVEL_THREE, "subscription_auto_half_a_year"),
    SEASON_VIP(3, "季度", 99, 21800, 30, SvipStyle.LEVEL_THREE, "subscription_auto_quarter"),
    GIFT_YEAR_VIP(1, "全年", 366, 79800, 31, SvipStyle.LEVEL_FOUR, "subscription_auto_a_year"),
    GIFT_HALF_YEAR_VIP(2, "半年", 188, 42800, 32, SvipStyle.LEVEL_FOUR, "subscription_auto_half_a_year"),
    GIFT_SEASON_VIP(3, "季度", 99, 21800, 33, SvipStyle.LEVEL_FOUR, "subscription_auto_quarter");

    int id;
    String name;
    int days;
    int price;
    int priceId;
    SvipStyle svipStyle;
    String iosSubPrice;

    public String getIosSubPrice() {
        return iosSubPrice;
    }

    public void setIosSubPrice(String iosSubPrice) {
        this.iosSubPrice = iosSubPrice;
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

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    public SvipStyle getSvipStyle() {
        return svipStyle;
    }

    public void setSvipStyle(SvipStyle svipStyle) {
        this.svipStyle = svipStyle;
    }

    VipPriceStyle(int id, String name, int days, int price, int priceId, SvipStyle svipStyle, String iosSubPrice){
        this.id = id;
        this.name = name;
        this.days = days;
        this.price = price;
        this.priceId = priceId;
        this.svipStyle = svipStyle;
        this.iosSubPrice = iosSubPrice;
    }

    public static VipPriceStyle getById(int id){
        for(VipPriceStyle style:VipPriceStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
    }

}
